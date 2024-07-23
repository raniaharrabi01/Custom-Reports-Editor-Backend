package com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import java.util.List;
import java.util.Map;
public interface ReportService {
    public String prepareTemplateCodeSyntax(Template template);
    public String prepareThymeleafSyntax(String htmlString);
    public String generateReportUsingThymleaf(Map<String, List<Map<String, Object>>> results, String code);
    }
