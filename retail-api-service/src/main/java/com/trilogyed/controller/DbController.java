package com.trilogyed.controller;

import com.trilogyed.DbFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {

    @Autowired
    DbFiller dbFiller;

    @GetMapping(value = "/dbFiller")
    public void fillDatabase() {
        dbFiller.fillDataBase();
    }
}
