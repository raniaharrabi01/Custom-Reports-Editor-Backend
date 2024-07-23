package com.example.proxymit.Custom_Reports_Editor.service.PDFReportService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import java.util.List;
import java.util.Map;
public interface PDFReportService {
     String exportHtmlCodeTodPdfFile(String processedHtml);
     String exportPDFReportFromSQLquery(List<Map<String, String>> Sql_query, ConnectionData data, Template template);
     String exportPDFReportFromSelectColumns(String tablename, ConnectionData data, Template modelData , ColumnsData colonnename);
     String exportPDFReportFromCSVFile(Map<String, List<Map<String, Object>>> dataFile, Template modelData);
}
