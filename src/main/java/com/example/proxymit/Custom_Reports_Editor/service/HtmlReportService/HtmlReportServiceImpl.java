package com.example.proxymit.Custom_Reports_Editor.service.HtmlReportService;

import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@Service
public class HtmlReportServiceImpl implements HtmlReportService{
    @Autowired
    ExternalDataBaseService externalDataBaseService;
    @Autowired
    ReportService reportService;
    @Override
    public String exporterHTMLReport(List<Map<String, String>> SQLquery, ConnectionData data, Template modelData) {
        // Récupérer les données du rapport en appelant la fonction du service
        Map<String, List<Map<String, Object>>> results = externalDataBaseService.executeSQLQueries(SQLquery, data);
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(results, code);
        return exportHtmlCodeTodHtmlFile(processedHtml);
    }

    @Override
    public String exportHTMLReportFromSQLquery(List<Map<String, String>> Sql_query, ConnectionData data, Template template) {
        return null;
    }

    @Override
    public String exportHTMLReportFromSelectColumns(String tablename, ConnectionData data, Template modelData, List<String> colonnename) {
        return null;
    }

    @Override
    public String exportHTMLReportFromCSVFile(MultipartFile file, Template modelData) {
        return null;
    }

    @Override
    public String exportHtmlCodeTodHtmlFile(String processedHtml) {
        try {
            String userDownloadsDir = System.getProperty("user.home") + File.separator + "Downloads";
            String filePath = userDownloadsDir + File.separator + "rapport.html";
            // Create a FileWriter object for the specified file path
            FileWriter fileWriter = new FileWriter(filePath);
            // Write the HTML content to the file
            fileWriter.write(processedHtml);
            // Close the FileWriter
            fileWriter.close();
            System.out.println("HTML file generated successfully: " + filePath);
            return "success";
        } catch (IOException e) {
            System.out.println("An error occurred while generating the HTML file: " + e.getMessage());
        }
        return null;
    }

}
