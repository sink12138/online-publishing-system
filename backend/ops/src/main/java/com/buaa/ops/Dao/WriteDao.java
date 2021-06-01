package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Write;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WriteDao {

    ArrayList<Write> selectByEditorId(Integer editorId);

    Integer insert(Write write);

    /**
     * Null attribute = do not updateById
     */
    Integer updateBySelf(Write write);

    Integer deleteBySelf(Write write);

}
