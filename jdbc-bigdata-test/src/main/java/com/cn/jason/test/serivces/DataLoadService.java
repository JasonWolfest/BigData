package com.cn.jason.test.serivces;

import com.cn.jason.test.factory.JdbcFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class DataLoadService {
    @Autowired
    private JdbcFactory jdbcFactory;

    public void loadData() throws SQLException {
        long start = System.currentTimeMillis();
        DataSource dataSource = jdbcFactory.getJdbcTemplate().getDataSource();
        System.out.println("造数开始时间： " + start);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DataLoadCaller caller = new DataLoadCaller();
            caller.setDataLoadService(new DataLoadService());
            caller.setTaskId(i);
            caller.setDataSource(dataSource);
            Future future = executorService.submit(caller);
            futures.add(future);
        }
        for (Future<?> future : futures) {
            try {
                future.get(); // 等待每一个任务完成
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown(); // 关闭线程池
        long end = System.currentTimeMillis();
        System.out.println("造数开始时间： " + end);
        System.out.println("造数花费时间： " + (end - start));
    }

    public void insertData(DataSource dataSource, int taskId) {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        System.out.println("Inserting data for task " + taskId + " on thread " + Thread.currentThread().getName());
        for (int j = 0; j < 50; j++) {
            try (Connection conn = DriverManager.getConnection(hikariDataSource.getJdbcUrl(), hikariDataSource.getUsername(), hikariDataSource.getPassword())) {
                conn.setAutoCommit(false);
                StringBuffer sqlBuffer = new StringBuffer();
                sqlBuffer.append("insert into country_language_5000 (country_code, `language`, is_official, percentage) values ");
                for (int i = 0; i < 10000; i++) {
                    sqlBuffer.append("(").append(" 'ARG' ").append(",")
                            .append(" 'Indian Languages' ").append(",")
                            .append(" 'F' ").append(",")
                            .append(" '0.3' ").append(")");
                    if (i < 9999) {
                        sqlBuffer.append(",");
                    }
                }
                PreparedStatement preparedStatement = conn.prepareStatement(sqlBuffer.toString());
                preparedStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) {
                    // 重试插入
                    insertData(dataSource, taskId);
                }
            }
        }
    }
}
