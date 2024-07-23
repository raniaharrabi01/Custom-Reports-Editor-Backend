package com.example.proxymit.Custom_Reports_Editor.service.ExcelReportService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
public interface ExcelReportService {
    String exportHtmlCodeTodExcelFile(String processedHtml);
    String exportExcelReportFromSQLQuery(List<Map<String, String>> Sql_query, ConnectionData data, Template template);
    String exportExcelReportFromSelectColumns(String tableName, ConnectionData data, Template modelData, ColumnsData colonnename);
    String exportExcelReportFromCSVFile(Map<String, List<Map<String, Object>>> dataFile, Template modelData);
}
