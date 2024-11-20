package com.lee93.coc.service;

import com.lee93.coc.dao.FileDao;
import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.FileEntity;
import com.lee93.coc.model.entity.ThumbnailEntity;
import com.lee93.coc.modelMappers.FileMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FileDao fileDao;

    public void validateFilesByType(PostsType type, MultipartFile[] files) {

        if(files.length > type.getLimitFileCnt()){
            // TODO 업로드 파일의 개수 체크 후 예외 던지기
        }
        for(MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String fileExtension = originalName.substring(originalName.lastIndexOf(".") + 1);
            int fileSizeInKb = (int) file.getSize() / 1024;

            if (fileSizeInKb > type.getLimitFileSizeInKb()) {
                logger.error("파일 사이즈 초과 :: ", fileSizeInKb);
                // TODO 파일 사이즈 제한 예외 던지기
            }else if(!Arrays.asList(type.getExtensions()).contains(fileExtension)){
                logger.error("업로드 불가능한 확장자");
                // TODO 업로드 불가능 확장자 예외 던지기
            }else if(file.isEmpty()){
                // TODO 파일이 비어있을 경우 예외 던지기
            }
        }
    }

    public void saveFile(int postId, MultipartFile[] files, PostsType type ) {
        String projectDir = System.getProperty("user.dir");
        String postsTypeToLowerCase = type.name().toLowerCase();
        String sp = File.separator;
        String uploadDir = projectDir+sp+"coc-uploads"+sp+postsTypeToLowerCase+sp;

        existDirAndCreate(uploadDir);

        for(int i=0; i<files.length; i++){
            String originalName = files[i].getOriginalFilename();
            String fileExtension = originalName.substring(originalName.lastIndexOf(".")+1);
            String newName = System.currentTimeMillis()+"_"+UUID.randomUUID()+"."+fileExtension;
            int fileSizeInKb = (int)files[i].getSize() / 1024;

            if(type.name().equals(PostsType.GALLERY.name()) && i == 0){
                saveThumbnail(postId, files[i]);
            }
            try {
                Path uploadPath = Paths.get(uploadDir, newName);
                Files.write(uploadPath, files[i].getBytes());
                FileEntity fileEntity = FileEntity.builder()
                        .postId(postId)
                        .originalName(originalName)
                        .saveName(newName)
                        .filePath(uploadDir+newName)
                        .fileSize(fileSizeInKb)
                        .build();
                fileDao.saveFile(fileEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveThumbnail(int postId, MultipartFile file) {
        String projectDir = System.getProperty("user.dir");
        String sp = File.separator;
        String uploadDirForThumb = projectDir+sp+"coc-uploads"+sp+"thumbnail"+sp;

        existDirAndCreate(uploadDirForThumb);

        String originalName = file.getOriginalFilename();
        String fileExtension = originalName.substring(originalName.lastIndexOf(".")+1);
        String newName = System.currentTimeMillis()+"_"+UUID.randomUUID()+"_thumbnail"+"."+fileExtension;
        int fileSizeInKb = (int)file.getSize() / 1024;

        File thumbnailImg = resizeForThumbnail(file);

        // 썸네일 로컬에 저장
        try {
            Path uploadPath = Paths.get(uploadDirForThumb, newName);
            Files.write(uploadPath, file.getBytes());
            // TODO 대용량 파일일 경우에는 Stream 기반으로 하는것이 좋음
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 썸네일 DB에 저장
        ThumbnailEntity thumbnailEntity = ThumbnailEntity.builder()
                .postId(postId)
                .thumbnailSaveName(newName)
                .thumbnailPath(uploadDirForThumb+newName)
                .thumbnailSize(fileSizeInKb)
                .build();
        fileDao.saveThumbnail(thumbnailEntity);
    }

    private File resizeForThumbnail(MultipartFile file) throws IOException {
        // 이미지 가져오기
        BufferedImage originalImg = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        
        // 이미지 해상도 가져오기
        // 해상도 조절
        return null;
    }


    private void existDirAndCreate(String uploadDir) {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
                logger.info("Create directory : ", uploadDir);
            } catch (IOException e) {
                logger.info("Failed to create directory", uploadDir);
                // TODO directory 생성 실패 예외 던지기
                throw new RuntimeException(e);
            }
        }else{
            logger.info("Directory already exists", uploadDir);
        }
    }





}
