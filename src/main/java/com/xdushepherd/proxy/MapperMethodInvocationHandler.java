package com.xdushepherd.proxy;

import com.xdushepherd.example.Blog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:07
 */
public class MapperMethodInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new Blog("toy-mybatis的第一篇博客");
    }
}
