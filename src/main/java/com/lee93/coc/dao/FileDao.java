package com.lee93.coc.dao;

import com.lee93.coc.model.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao {
    void saveFile(FileEntity fileEntity);
}
