package com.example.proxymit.Custom_Reports_Editor.service.PDFReportService;
import com.example.proxymit.Custom_Reports_Editor.model.HistoryReport;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.repository.HistoryReportRepository;
import com.example.proxymit.Custom_Reports_Editor.repository.TemplateRepository;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ConnectionData;
import com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ReportService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PDFReportServiceImpl implements PDFReportService {
    @Autowired
    ExternalDataBaseService externalDataBaseService;
    @Autowired
    ReportService reportService;
    @Autowired
    HistoryReportRepository historyReportRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Override
    public String exportHtmlCodeTodPdfFile(String processedHtml) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {// Crée un écrivain PDF pour écrire dans un flux de bytes
            PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
            // Fournit une gestion par défaut des polices pour la conversion HTML vers PDF
            DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
            // Propriétés de conversion pour spécifier le fournisseur de polices
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(defaultFont);
            // Convertit le HTML traité en PDF
            HtmlConverter.convertToPdf(processedHtml, pdfwriter, converterProperties);
            // Encode le contenu du PDF en base64
            String encodedString = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return encodedString;
        } catch(Exception ex) {
            ex.printStackTrace(); // Ajoutez une gestion appropriée des erreurs
        }
        return null;
    }

    @Override
    public String exportPDFReportFromSQLquery(List<Map<String, String>> SQLquery, ConnectionData data, Template modelData) {
        // Récupérer les données du rapport en appelant la fonction du service
        Map<String, List<Map<String, Object>>> results = externalDataBaseService.executeSQLQueries(SQLquery, data);
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(results, code);
        String sourceData = "SQL DataBase";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodPdfFile(processedHtml);
    }

    @Override
    public String exportPDFReportFromSelectColumns(String tablename, ConnectionData data, Template modelData , ColumnsData colonnename) {
        // Récupérer les données du rapport en appelant la fonction du service
        Map<String, List<Map<String, Object>>> results = externalDataBaseService.getDataFromSelectColumnsList(tablename, colonnename, data);
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(results, code);
        Integer idTemplate = modelData.getId();
        String sourceData = "SQL DataBase";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodPdfFile(processedHtml);
    }

    @Override
    public String exportPDFReportFromCSVFile(Map<String, List<Map<String, Object>>>dataFile, Template modelData) {
        String code = reportService.prepareTemplateCodeSyntax(modelData);
        String processedHtml = reportService.generateReportUsingThymleaf(dataFile, code);
        String sourceData = "CSV File";
        sauvegarderReportHistory(sourceData,modelData);
        return exportHtmlCodeTodPdfFile(processedHtml);
    }

    public String sauvegarderReportHistory(String sourceData, Template modelData) {
        // Check if the Template exists in the database or needs to be persisted
        Template persistedTemplate;
        if (modelData.getId() != null) {
            persistedTemplate = templateRepository.findById(modelData.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + modelData.getId()));
        } else {
            // If modelData is new and not persisted, save it first
            return "modèle non enregistré";
        }

        // Now persistedTemplate should be managed (attached to the persistence context)
        Date currentDate = new Date();
        HistoryReport historyReport = new HistoryReport();
        historyReport.setTemplate(persistedTemplate); // Use the managed template
        historyReport.setExportFormat("PDF");
        historyReport.setGenerationDate(currentDate);
        historyReport.setTypeDataSource(sourceData);
        historyReportRepository.save(historyReport);

        return "Historique bien enregistré";
    }
}
