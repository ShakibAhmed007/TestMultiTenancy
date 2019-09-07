package com.example.demoMDB.dao;

import com.example.demoMDB.mapper.TestMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class TestDao{
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public TestDao(DataSource dataSource){
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Object> getResult(String query) {
        Object[] params = new Object[]{};
        TestMapper mapper = new TestMapper();
        List<Object> list = jdbcTemplate.query(query,params,mapper);

        return list;
    }
}
