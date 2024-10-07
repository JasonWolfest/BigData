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

    @GetMapping("/export100")
    public String export100(){
        System.out.println("开始导出100万数据");
        exportSerivce.exportData100();
        return "end";
    }

    @GetMapping("/export500")
    public String export500(){
        System.out.println("开始导出500万数据");
        exportSerivce.exportData500();
        return "end";
    }

    @GetMapping("/export1000")
    public String export1000(){
        System.out.println("开始导出1000万数据");
        exportSerivce.exportData1000();
        return "end";
    }

    @GetMapping("/export5000")
    public String export5000(){
        System.out.println("开始导出5000万数据");
        exportSerivce.exportData5000();
        return "end";
    }

    @GetMapping("/exportAll")
    public String exportAll(){
        System.out.println("开始导出多表数据");
        exportSerivce.exportDataAll();
        return "";
    }
}
