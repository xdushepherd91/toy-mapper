package com.xdushepherd.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangqianyi03
 * @date 2022/3/28 23:43
 */
public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public Long getResult(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getLong(columnName);
    }
}
