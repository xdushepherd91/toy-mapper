package com.xdushepherd.proxy;

import com.sun.xml.internal.ws.util.StringUtils;
import com.xdushepherd.annotation.Param;
import com.xdushepherd.annotation.Select;
import com.xdushepherd.entity.Blog;
import com.xdushepherd.parser.PrepareSqlBuilder;
import com.xdushepherd.session.SqlSession;
import com.xdushepherd.typehandler.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

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
        PrepareSqlBuilder prepareSqlBuilder = new PrepareSqlBuilder();
        String prepareSql = prepareSqlBuilder.build(sqlCommand);
        PreparedStatement preparedStatement = dbConnection.prepareStatement(prepareSql);
        List<String> properties = prepareSqlBuilder.getProperties();
        Parameter[] parameters = method.getParameters();
        HashMap<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Param annotation = parameter.getAnnotation(Param.class);
            if (annotation == null) {
                throw new IllegalStateException("Param annotation is missing");
            }

            paramsMap.put(annotation.value(), args[i]);
        }

        for (int i = 0; i < properties.size(); i++) {
            Object o = paramsMap.get(properties.get(i));
            Class<?> aClass = o.getClass();
            if (aClass.equals(String.class)) {
                preparedStatement.setString(i + 1, (String) o);
            }

            if (aClass.equals(Long.class)) {
                preparedStatement.setLong(i + 1, (Long) o);
            }
        }

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
