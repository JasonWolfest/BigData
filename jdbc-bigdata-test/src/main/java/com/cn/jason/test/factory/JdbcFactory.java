package com.cn.jason.test.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcFactory {
    @Autowired
    private DataSource dataSource;

    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}
