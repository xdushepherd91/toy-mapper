package com.xdushepherd;

import com.xdushepherd.proxy.MapperMethodInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:06
 */
public class ToyMybatis {

    public static Object getMapper(Class<?> t) {
        return Proxy.newProxyInstance(t.getClassLoader(), new Class[]{t}, new MapperMethodInvocationHandler());
    }
}
