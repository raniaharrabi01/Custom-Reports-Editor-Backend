package com.example.proxymit.Custom_Reports_Editor.service.ExcelReportService;
import com.example.proxymit.Custom_Reports_Editor.model.HistoryReport;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.repository.HistoryReportRepository;
import com.example.proxymit.Custom_Reports_Editor.repository.TemplateRepository;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportService;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalDataBaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class ExcelReportServiceImpl implements  ExcelReportService{
    @Autowired
    ExternalDataBaseService externalDataBaseService;
    @Autowired
    ReportService reportService;
    @Autowired
    HistoryReportRepository historyReportRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Override
    public String exportHtmlCodeTodExcelFile(String processedHtml) {
        try {
            // Analyser le contenu HTML
            Document doc = Jsoup.parse(processedHtml);
            // Créer un nouveau classeur
            Workbook workbook = new XSSFWorkbook();
            // Trouver tous les éléments de table
            Elements tables = doc.select("table");
            for (int i = 0; i < tables.size(); i++) {
                Element table = tables.get(i);
                // Créer une nouvelle feuille pour chaque table
                Sheet sheet = workbook.createSheet("Sheet" + (i + 1));
                // Traiter les lignes et les cellules de la table
                Elements rows = table.select("tr");
                int rowNum = 0;
                for (Element row : rows) {
                    Row excelRow = sheet.createRow(rowNum++);
                    Elements cells;
                    // Vérifier si la ligne contient des cellules d'en-tête
                    if (row.select("th").size() > 0) {
                        cells = row.select("th");
                    } else {
                        cells = row.select("td");
                    }
                    int cellNum = 0;
                    for (Element cell : cells) {
                        excelRow.createCell(cellNum++).setCellValue(cell.text());
                    }
                }
            }
            // Écrire le classeur dans ByteArrayOutputStream
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] excelBytes = bos.toByteArray();
            // Encoder le tableau de bytes en base64
            String encodedString = Base64.getEncoder().encodeToString(excelBytes);
            return encodedString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String exportExcelReportFromSQLQuery(List<Map<String, String>> SQLquery, ConnectionData data, Template modelData) {
        Map<String, List<Map<String, Object>>> results = externalDataBaseService.executeSQLQueries(SQLquery, data);
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(results, code);
        String sourceData = "SQL DataBase";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodExcelFile(processedHtml);
    }

    @Override
    public String exportExcelReportFromSelectColumns(String tablename, ConnectionData data, Template modelData, ColumnsData colonnename) {
        Map<String, List<Map<String, Object>>> results = externalDataBaseService.getDataFromSelectColumnsList(tablename, colonnename, data);
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(results, code);
        String sourceData = "SQL DataBase";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodExcelFile(processedHtml);
    }

    @Override
    public String exportExcelReportFromCSVFile(Map<String, List<Map<String, Object>>>dataFile, Template modelData) {
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(dataFile, code);
        String sourceData = "CSV File";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodExcelFile(processedHtml);
    }
    public String sauvegarderReportHistory(String sourceData, Template modelData) {
        // Check if the Template exists in the database or needs to be persisted
        Template persistedTemplate;
        if (modelData.getId() != null) {
            persistedTemplate = templateRepository.findById(modelData.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + modelData.getId()));
        } else {
            // If modelData is new and not persisted, save it first
            return "modèle nest pas enregistré";
        }

        // Now persistedTemplate should be managed (attached to the persistence context)
        Date currentDate = new Date();
        HistoryReport historyReport = new HistoryReport();
        historyReport.setTemplate(persistedTemplate); // Use the managed template
        historyReport.setExportFormat("Excel");
        historyReport.setGenerationDate(currentDate);
        historyReport.setTypeDataSource(sourceData);
        historyReportRepository.save(historyReport);

        return "Historique bien enregistré";
    }
}
