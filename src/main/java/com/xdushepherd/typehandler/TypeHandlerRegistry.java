package com.xdushepherd.typehandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangqianyi03
 * @date 2022/3/28 23:43
 */
public class TypeHandlerRegistry {

    private static final Map<Class<?>,TypeHandler<?>> handlers;

    static {
        handlers = new HashMap<>();
        handlers.put(String.class, new StringTypeHandler());
        handlers.put(Long.class, new LongTypeHandler());
    }

    public static TypeHandler<?> getHandler(Class<?> type) {
        return handlers.get(type);
    }
}
