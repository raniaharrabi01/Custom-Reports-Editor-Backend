package com.example.proxymit.Custom_Reports_Editor.controller;

import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportRequestFromTables;
import com.example.proxymit.Custom_Reports_Editor.service.HtmlReportService.HtmlReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ExportHTMLReport")
@CrossOrigin(origins ="http://localhost:5173")
public class HtmlReportController {
    @Autowired
    HtmlReportService htmlReportService;

    @PostMapping("/FromSQLquery")
    public String exportHTMLReportFromSQLquery(@RequestBody ReportRequestFromTables reportRequest ) {
        return htmlReportService.exporterHTMLReport(reportRequest.getSqlquery(),reportRequest.getData(),reportRequest.getModelData());
    }

}
