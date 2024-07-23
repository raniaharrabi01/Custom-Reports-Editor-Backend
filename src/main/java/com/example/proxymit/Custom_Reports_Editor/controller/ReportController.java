package com.example.proxymit.Custom_Reports_Editor.controller;

import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins ="http://localhost:5173")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/verifyTemplateNameExist")
    public String prepareThymeleafSyntax(@RequestBody String htmlcode) {
        return reportService.prepareThymeleafSyntax(htmlcode);
    }
}
