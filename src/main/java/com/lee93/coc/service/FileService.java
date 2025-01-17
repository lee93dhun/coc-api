package com.lee93.coc.service;

import com.lee93.coc.dao.FileDao;
import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.FileEntity;
import com.lee93.coc.model.entity.ThumbnailEntity;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * 게시판 타입에 따라 업로드할 파일들의 유효성 검사 기능
     * @param type 게시판 타입
     * @param files 유효성 검사를 진행할 파일 집합
     */
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
                // TODO 파일이 비어있을 경우 예외
            }
        }
    }

    /**
     * 업로드 할 파일의 로컬 및 DB에 저장하는 기능
     * @param postId 업로드할 파일들의 게시물 ID
     * @param files 업로드 할 파일 집합
     * @param type 업로드할 파일들의 게시판 타입
     */
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

    /**
     * 섬네일 파일을 생성하고 저장하는 기능
     * @param postId 저장할 섬네일 파일의 게시물 ID
     * @param file 저장할 섬네일 파일
     */
    private void saveThumbnail(int postId, MultipartFile file) {
        String projectDir = System.getProperty("user.dir");
        String sp = File.separator;
        String uploadDirForThumb = projectDir+sp+"coc-uploads"+sp+"thumbnail"+sp;

        existDirAndCreate(uploadDirForThumb);

        String newName = System.currentTimeMillis()+"_"+UUID.randomUUID()+"_thumbnail.jpg";

        Path uploadPath = Paths.get(uploadDirForThumb, newName);
        try {
            BufferedImage thumbnailImg = resizeForThumbnail(file);
            ImageIO.write(thumbnailImg, "jpg", uploadPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int savedImgFileSizeInKb = (int) uploadPath.toFile().length() / 1024;

        ThumbnailEntity thumbnailEntity = ThumbnailEntity.builder()
                .postId(postId)
                .thumbnailSaveName(newName)
                .thumbnailPath(uploadDirForThumb+newName)
                .thumbnailSize(savedImgFileSizeInKb)
                .build();
        fileDao.saveThumbnail(thumbnailEntity);
    }

    /**
     * 섬네일 파일 규격에 맞게 해상도 조절 및 이미지 크롭
     * @param file 해상도 조절 및 크롭할 이미지 파일
     * @return 규격에 맞게 생성된 이미지 파일
     * @throws IOException 이미지 생성중 발생하는 예외
     */
    public BufferedImage resizeForThumbnail(MultipartFile file) throws IOException {
        int width = 400;
        int height = 300;
        BufferedImage inputImg = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

        return Thumbnails.of(inputImg)
                .size(width, height)
                .crop(net.coobird.thumbnailator.geometry.Positions.CENTER)
                .asBufferedImage();
    }

    /**
     * 로컬에 파일 저장시 파일이 저장될 폴더 확인 및 생성
      * @param uploadDir 폴더가 존재하는지 확인할 경로
     */
    private void existDirAndCreate(String uploadDir) {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                // TODO directory 생성 실패 예외 던지기
                throw new RuntimeException(e);
            }
        }else{
            // TODO 경로에 폴더가 이미 존재할때 처리 방향? boolean 반환?
            logger.info("Directory already exists", uploadDir);
        }
    }


    public Resource getFileByFileId(int fileId) {
        FileEntity fileEntity = fileDao.getFileById(fileId);
        File file = new File(fileEntity.getFilePath());
        Resource resource = new FileSystemResource(file);
        if(!resource.exists()){
            try {
                throw new FileNotFoundException("File not found at path ");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return resource;
    }
}
