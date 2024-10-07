package com.cn.jason.test.controller;

import com.cn.jason.test.serivces.DataExportSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/jdbc")
@Controller
public class JdbcContController {

    @Autowired
    private DataExportSerivce exportSerivce;


    @PostMapping("/test")
    public String test(){
        return "";
    }

    @GetMapping("/export")
    public String export(){
        /*System.out.println("开始导出100万数据");
        exportSerivce.exportData100();*/
        System.out.println("开始导出500万数据");
        exportSerivce.exportData500();
        return "end";
    }
}
