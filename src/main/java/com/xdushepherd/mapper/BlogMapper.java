package com.xdushepherd.mapper;

import com.xdushepherd.annotation.Select;
import com.xdushepherd.entity.Blog;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:11
 */
public interface BlogMapper {

    @Select("select * from blog limit 1")
    Blog getFirstBlog();
}
