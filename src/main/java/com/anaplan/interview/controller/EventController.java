package com.anaplan.interview.controller;

import com.anaplan.interview.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventDao eventDao;

}


