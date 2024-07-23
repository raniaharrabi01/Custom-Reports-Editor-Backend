package com.example.proxymit.Custom_Reports_Editor.service.TemplateService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
public interface TemplateService {
     String saveTemplate(Template template);
     String getAllTemplate();
     String deleteTemplate(String name);
     TemplateData getDataTemplate(String name);
     String getContextData(String name);
     String ModifyTemplateData(Template template);
}
