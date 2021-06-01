package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ReviewDao {

    ArrayList<Review> selectByArticleId(Integer articleId);

    Integer insert(Review review);

    /**
     * Null attribute = clear
     */
    Integer updateBySelf(Review review);

    Integer deleteByArticleId(Integer articleId);

}
