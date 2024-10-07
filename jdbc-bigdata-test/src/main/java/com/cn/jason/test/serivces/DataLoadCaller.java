package com.cn.jason.test.serivces;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.Callable;

public class DataLoadCaller implements Callable {

    private int taskId;
    private DataLoadService dataLoadService;

    private DataSource dataSource;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public DataLoadService getDataLoadService() {
        return dataLoadService;
    }

    public void setDataLoadService(DataLoadService dataLoadService) {
        this.dataLoadService = dataLoadService;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object call() throws Exception {
        dataLoadService.insertData(dataSource, taskId);
        return null;
    }
}
