package com.xdushepherd.example;

import com.xdushepherd.ToyMybatis;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:10
 */
public class Test {

    public static void main(String[] args) {
        BlogMapper mapper = (BlogMapper) ToyMybatis.getMapper(BlogMapper.class);
        Blog firstBlog = mapper.getFirstBlog();
        System.out.println(firstBlog.getTitle());
    }


}
