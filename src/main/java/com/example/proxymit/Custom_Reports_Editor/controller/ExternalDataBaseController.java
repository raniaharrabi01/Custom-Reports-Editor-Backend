package com.example.proxymit.Custom_Reports_Editor.controller;

import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalOracleDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalPostgreDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportRequestFromTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins ="http://localhost:5173")
public class  ExternalDataBaseController {
    @Autowired
    private ExternalDataBaseService databaseMetadataService;
    @Autowired
    private ExternalPostgreDataBaseService externalPostgreDataBaseService;
    @Autowired
    private ExternalOracleDataBaseService externalOracleDataBaseService;

    @PostMapping("/tablesAndColumns/SQLDataBase")
    public Map<String, List<String>> getAllTablesAndColumnsFromMysqlDataBase(@RequestBody ConnectionData data) {
        return  databaseMetadataService.getAllTablesAndColumnsName(data);
    }

    @PostMapping("/tablesAndColumns/PostgreDataBase")
    public Map<String, List<String>> getAllTablesAndColumnsFromPostgreDataBase(@RequestBody ConnectionData data) {
        return  externalPostgreDataBaseService.getAllTablesAndColumnsName(data);
    }

    @PostMapping("/tablesAndColumns/OracleDataBase")
    public Map<String, List<String>> getAllTablesAndColumnsFromOracleDataBase(@RequestBody ConnectionData data) {
        return  externalOracleDataBaseService.getAllTablesAndColumnsName(data);
    }

    //test function
//    @GetMapping("/selectColumns/{tableName}")
//    public Map<String, List<Map<String, Object>>> prepareThymeleafSyntax(@PathVariable String tableName,@RequestBody ReportRequestFromTables reportRequest) {
//        return databaseMetadataService.getDataFromSelectColumnsList(tableName,reportRequest.getColumnsData(),reportRequest.getData());
//    }
//    //test function
//    @GetMapping("/selectColumns/{tableName}")
//    public Map<String, List<Map<String, Object>>> getDataFromSelectColumnsList(@PathVariable String tableName,@RequestBody ReportRequestFromTables reportRequest) {
//        return externalPostgreDataBaseService.getDataFromSelectColumnsList(tableName,reportRequest.getColumnsData(),reportRequest.getData());
//    }
    @GetMapping("/SQLquery")
    public Map<String, List<Map<String, Object>>> getDataSQLquery(@RequestBody ReportRequestFromTables reportRequestFromTables) {
        return databaseMetadataService.executeSQLQueries(reportRequestFromTables.getSqlquery(),reportRequestFromTables.getData());
    }
}
