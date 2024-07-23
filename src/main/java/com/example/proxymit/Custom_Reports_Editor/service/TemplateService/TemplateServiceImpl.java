package com.example.proxymit.Custom_Reports_Editor.service.TemplateService;
import com.example.proxymit.Custom_Reports_Editor.model.HistoryReport;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.repository.HistoryReportRepository;
import com.example.proxymit.Custom_Reports_Editor.repository.TemplateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    HistoryReportRepository historyReportRepository;
    @Override
    public String saveTemplate(Template modelData) {
        if (verifyTemplateNameExist(modelData.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "un modèle avec ce nom existe déjà.");
        } else {
            Template entity = new Template();
            entity.setHtmlData(modelData.getHtmlData());
            entity.setCssData(modelData.getCssData());
            entity.setName(modelData.getName());
            entity.setContext(modelData.getContext());
            // Obtenez la date actuelle
            Date currentDate = new Date();
            entity.setSaveDate(currentDate);
            templateRepository.save(entity);
            return ("Le modèle a été enregistrer avec succès.");
        }
    }

    public String getContextData(String name){
        Optional<Template> model = templateRepository.findByName(name);
        if (model.isPresent()) {
            String ContextData = model.get().getContext();
            return ContextData;
        } else {
            return null;
        }
    }

    @Override
    public String getAllTemplate() {
        List<Template> modelDataList = templateRepository.findAll();
        List<Map<String, Object>> modelDataMapList = modelDataList.stream()
                .map(modelData -> {
                    Map<String, Object> modelMap = new HashMap<>();
                    modelMap.put("name", modelData.getName());
                    modelMap.put("saveDate", formatDate(modelData.getSaveDate()));
                    if (modelData.getModifyDate() != null) {
                        modelMap.put("modifyDate", formatDate(modelData.getModifyDate()));
                    }
                    return modelMap;
                })
                .collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(modelDataMapList);
        } catch (Exception e) {
            e.printStackTrace();
            return "erreur";
        }
    }
    // Fonction pour formater la date au format souhaité

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Définissez le format de date souhaité
        return sdf.format(date);
    }

    @Override
    public String deleteTemplate(String name) {
        Optional<Template> model = templateRepository.findByName(name);
        if (model.isPresent()) {
            Template entity = model.get();
            // Supprimer ou dissocier les références dans HistoryReport
            List<HistoryReport> historyReports = historyReportRepository.findByTemplate(entity);
            for (HistoryReport report : historyReports) {
                historyReportRepository.delete(report); // Supprimer les entrées de HistoryReport
            }
            templateRepository.delete(entity);
            return ("Le modèle a été supprimé avec succès.");
        } else {
            return ("Le modèle n'a pas été trouvé.");
        }
    }


    public Boolean verifyTemplateNameExist(String name) {
        Optional<Template> model = templateRepository.findByName(name);
        if (model.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public TemplateData getDataTemplate(String name) {
        Optional<Template> model = templateRepository.findByName(name);
        if (model.isPresent()) {
            TemplateData originalTemplate = new TemplateData();
            originalTemplate.setId(model.get().getId());
            originalTemplate.setHtmlData(model.get().getHtmlData());
            originalTemplate.setCssData(model.get().getCssData());
            return originalTemplate;
        } else {
            return null;
        }
    }

    @Override
    public String ModifyTemplateData(Template template) {
        Optional<Template> existingTemplate = templateRepository.findByName(template.getName());
        if (existingTemplate.isPresent() && !(existingTemplate.get().getId().equals(template.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un modèle avec ce nom existe déjà.");
        } else {
            Template entity = templateRepository.getReferenceById(template.getId());
            entity.setName(template.getName());
            entity.setHtmlData(template.getHtmlData());
            entity.setCssData(template.getCssData());
            entity.setContext(template.getContext());
            // Obtenez la date actuelle
            Date currentDate = new Date();
            entity.setModifyDate(currentDate);
            templateRepository.save(entity);
            return ("Le modèle a été enregistrer avec succès.");
        }
    }
}