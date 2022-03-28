package com.xdushepherd.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangqianyi03
 * @date 2022/3/28 23:44
 */
public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public String getResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getString(columnName);
    }
}
