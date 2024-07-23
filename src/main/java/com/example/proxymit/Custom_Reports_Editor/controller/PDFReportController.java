package com.example.proxymit.Custom_Reports_Editor.controller;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.FileData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportRequestFromTables;
import com.example.proxymit.Custom_Reports_Editor.service.PDFReportService.PDFReportService;
import com.example.proxymit.Custom_Reports_Editor.service.PDFReportService.PDFReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/ExportPDFReport")
@CrossOrigin(origins ="http://localhost:5173")
public class PDFReportController {
    @Autowired
    PDFReportService pdfReportService;
    @PostMapping("/FromSQLquery")
    public String exportPDFReportFromSQLQuery(@RequestBody ReportRequestFromTables reportRequest) {
        return pdfReportService.exportPDFReportFromSQLquery(reportRequest.getSqlquery(), reportRequest.getData(),
                reportRequest.getModelData());
    }
    @PostMapping("/FromSelectColumns/{tablename}")
    public String exportPDFReportFromSelectColumns(@PathVariable String tablename,
                                                   @RequestBody ReportRequestFromTables reportRequest) {
        return pdfReportService.exportPDFReportFromSelectColumns(tablename, reportRequest.getData(),
                reportRequest.getModelData(), reportRequest.getColumnsData());
    }
    @PostMapping("/FromCSVFile")
    public String exportPDFReportFromCSVFile(@RequestBody FileData fileData) {
            return pdfReportService.exportPDFReportFromCSVFile(fileData.getDataFile(),fileData.getModelData());
    }
}
