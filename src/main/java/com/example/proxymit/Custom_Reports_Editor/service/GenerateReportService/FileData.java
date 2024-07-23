package com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService;

import com.example.proxymit.Custom_Reports_Editor.model.Template;
import java.util.List;
import java.util.Map;

public class FileData {
    private Map<String, List<Map<String, Object>>> dataFile;
    private Template modelData;

    public Map<String, List<Map<String, Object>>> getDataFile() {
        return dataFile;
    }
    public Template getModelData() {
        return modelData;
    }
}
