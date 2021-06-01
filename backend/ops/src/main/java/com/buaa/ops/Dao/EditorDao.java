package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Editor;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface EditorDao {

    Editor selectById(Integer id);

    Editor selectByAccountId(Integer accountId);

    /**
     * Select the editor who are responsible for the least articles.
     */
    Editor selectLeastBusy();

    ArrayList<Editor> selectAll();

    Integer insert(Editor editor);

    Integer deleteById(Integer id);

}
