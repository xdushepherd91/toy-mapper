package com.xdushepherd.proxy;

import com.sun.xml.internal.ws.util.StringUtils;
import com.xdushepherd.annotation.Select;
import com.xdushepherd.entity.Blog;
import com.xdushepherd.session.SqlSession;
import com.xdushepherd.typehandler.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author wangqianyi03
 * @date 2022/3/27 20:07
 */
public class MapperMethodInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select select = method.getAnnotation(Select.class);
        if (select == null) {
            return null;
        }

        String sqlCommand = select.value();

        Connection dbConnection = SqlSession.getDBConnection();
        PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlCommand);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Class<?> returnType = method.getReturnType();
            Object result = returnType.newInstance();
            Field[] declaredFields = returnType.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Method setMethod = returnType.getDeclaredMethod("set" + StringUtils.capitalize(declaredField.getName()), declaredField.getType());
                setMethod.invoke(result, TypeHandlerRegistry.getHandler(declaredField.getType()).getResult(resultSet, declaredField.getName()));
            }
            return result;
        }

        return null;
    }
}
