package com.example.demoMDB.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestMapper implements RowMapper<Object> {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int noOfColumn = rsmd.getColumnCount();

        Map<String, Object> result = new LinkedHashMap<>();
        for (int j = 1; j <= noOfColumn; j++) {
            result.put(rsmd.getColumnLabel(j),resultSet.getString(rsmd.getColumnLabel(j)));
        }
        return result;
    }
}
