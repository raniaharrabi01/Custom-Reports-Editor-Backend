package com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService;

import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;

import java.util.List;
import java.util.Map;


public class ReportRequestFromTables {
    private ConnectionData data;
    private Template modelData;
    private ColumnsData columnsData;
    private List<Map<String, String>> sqlquery;

    public ColumnsData getColumnsData() {
        return columnsData;
    }

    public List<Map<String, String>> getSqlquery() {
        return sqlquery;
    }

    public Template getModelData() {
        return modelData;
    }

    public ConnectionData getData() {
        return data;
    }
}
