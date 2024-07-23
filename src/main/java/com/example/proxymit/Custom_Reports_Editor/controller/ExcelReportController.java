package com.example.proxymit.Custom_Reports_Editor.controller;

import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.FileData;
import com.example.proxymit.Custom_Reports_Editor.service.ExcelReportService.ExcelReportService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportRequestFromTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ExportExcelReport")
@CrossOrigin(origins ="http://localhost:5173")
public class ExcelReportController {
    @Autowired
    ExcelReportService excelReportService;
    @PostMapping("/FromSQLquery")
    public String exportExcelReportFromSQLQuery(@RequestBody ReportRequestFromTables reportRequest){
        return  excelReportService.exportExcelReportFromSQLQuery(reportRequest.getSqlquery(),
                reportRequest.getData(),reportRequest.getModelData());
    }
    @PostMapping("/FromSelectColumns/{tableName}")
    public String exportExcelReportFromSelectColumns(@PathVariable String tableName,
                                                     @RequestBody ReportRequestFromTables reportRequest) {
        return excelReportService.exportExcelReportFromSelectColumns(tableName, reportRequest.getData(),
                reportRequest.getModelData(), reportRequest.getColumnsData());
    }
    @PostMapping("/FromCSVFile")
    public String exportExcelReportFromCSVFile(@RequestBody FileData fileData) {
        return excelReportService.exportExcelReportFromCSVFile(fileData.getDataFile(),fileData.getModelData());
    }
}
