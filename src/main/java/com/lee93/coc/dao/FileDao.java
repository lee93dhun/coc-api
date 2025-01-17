package com.lee93.coc.dao;

import com.lee93.coc.model.entity.FileEntity;
import com.lee93.coc.model.entity.ThumbnailEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao {
    void saveFile(FileEntity fileEntity);

    void saveThumbnail(ThumbnailEntity thumbnailEntity);

    FileEntity getFileById(int fileId);
}
