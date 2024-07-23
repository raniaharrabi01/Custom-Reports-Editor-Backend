package com.example.proxymit.Custom_Reports_Editor.service.HtmlReportService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
public interface HtmlReportService {
    public String exportHtmlCodeTodHtmlFile(String processedHtml);
    public String exporterHTMLReport(List<Map<String, String>> Sql_query, ConnectionData data, Template template );
    String exportHTMLReportFromSQLquery(List<Map<String, String>> Sql_query, ConnectionData data, Template template);
    public String exportHTMLReportFromSelectColumns(String tablename, ConnectionData data, Template modelData , List<String> colonnename);
    public String exportHTMLReportFromCSVFile(MultipartFile file, Template modelData);
}
