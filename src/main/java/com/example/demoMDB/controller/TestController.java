package com.example.demoMDB.controller;


import com.example.demoMDB.dao.TestDao;
import com.example.demoMDB.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping(value = "/get-data")
    public List<Object> getFirstData(@RequestParam String db) {
        List<Object> data = testService.getData(db);
        return data;
    }
}
