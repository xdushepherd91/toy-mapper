package com.xdushepherd;

import com.xdushepherd.example.Blog;
import com.xdushepherd.example.BlogMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:34
 */
class ToyMybatisTest {

    @Test
    void testGetMapper() {
        BlogMapper mapper = (BlogMapper) ToyMybatis.getMapper(BlogMapper.class);
        Blog firstBlog = mapper.getFirstBlog();
        assertEquals("toy-mybatis的第一篇博客", firstBlog.getTitle());
    }
}