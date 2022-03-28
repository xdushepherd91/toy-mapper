package com.xdushepherd.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangqianyi03
 * @date 2022/3/28 23:42
 */
public interface TypeHandler<T> {

    T getResult(ResultSet resultSet, String columnName) throws SQLException;
}
