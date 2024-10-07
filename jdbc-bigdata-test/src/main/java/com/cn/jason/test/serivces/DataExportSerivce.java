package com.cn.jason.test.serivces;

import com.cn.jason.test.factory.JdbcFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DataExportSerivce {

    @Autowired
    private JdbcFactory jdbcFactory;

    public void exportData100() {
        try (Connection conn = jdbcFactory.getJdbcTemplate().getDataSource().getConnection()) {
            String sql = "select id, CountryCode, `Language`, IsOfficial, Percentage from countrylanguage ";
            long start = System.currentTimeMillis();
            System.out.println("导出开始时间： "+ start);
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setFetchSize(Integer.MIN_VALUE);
                //((StatementImpl) preparedStatement).enableStreamingResults();
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get("D:\\storage\\test\\data.txt")), "UTF-8"))) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // 导出100万数据文件路径
                            StringBuilder rsStr = new StringBuilder("");
                            rsStr.append(resultSet.getInt("id")).append(" | ");
                            rsStr.append(resultSet.getString("CountryCode")).append(" | ");
                            rsStr.append(resultSet.getString("Language")).append(" | ");
                            rsStr.append(resultSet.getString("IsOfficial")).append(" | ");
                            rsStr.append(resultSet.getString("Percentage")).append(" | ").append("\r\n");
                            writer.write(rsStr.toString());
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("导出结束时间： "+ end);
            System.out.println("导出结束时间： "+ (end - start));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void exportData500() {
        try (Connection conn = jdbcFactory.getJdbcTemplate().getDataSource().getConnection()) {
            String sql = "select id, country_code, `language`, is_official, percentage from country_language_500 ";
            long start = System.currentTimeMillis();
            System.out.println("导出开始时间： "+ start);
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setFetchSize(Integer.MIN_VALUE);
                //((StatementImpl) preparedStatement).enableStreamingResults();
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get("D:\\storage\\test\\data_500.txt")), "UTF-8"))) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // 导出100万数据文件路径
                            StringBuilder rsStr = new StringBuilder("");
                            rsStr.append(resultSet.getInt("id")).append(" | ");
                            rsStr.append(resultSet.getString("country_code")).append(" | ");
                            rsStr.append(resultSet.getString("language")).append(" | ");
                            rsStr.append(resultSet.getString("is_official")).append(" | ");
                            rsStr.append(resultSet.getString("percentage")).append(" | ").append("\r\n");
                            writer.write(rsStr.toString());
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("导出结束时间： "+ end);
            System.out.println("导出结束时间： "+ (end - start));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void exportData1000(){}

    public void exportData5000(){}

    public void exportDataAll(){
        // 多线程导出，线程池5个，测试表10个，每张表数据100万~5000万


    }
}
