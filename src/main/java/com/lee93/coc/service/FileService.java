package com.lee93.coc.service;

import com.lee93.coc.dao.FileDao;
import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.FileEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    public void saveFile(int postId, MultipartFile file, PostsType postsType ) {
        String projectDir = System.getProperty("user.dir");
        String postsTypeToLowerCase = postsType.name().toLowerCase();
        String sp = File.separator;
        String uploadDir = projectDir+sp+"coc-uploads"+sp+postsTypeToLowerCase+sp;

        existDirAndCreate(uploadDir);

        String originalName = file.getOriginalFilename();
        String fileExtension = originalName.substring(originalName.lastIndexOf(".")+1);
        String newName = UUID.randomUUID().toString()+ "_" + System.currentTimeMillis()+"."+fileExtension;
        int fileSizeInKb = (int)file.getSize() / 1024;

        try {
            Path uploadPath = Paths.get(uploadDir, newName);
            Files.write(uploadPath, file.getBytes());

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

    public void validateFiles(PostsType type, MultipartFile[] files) {

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



}
