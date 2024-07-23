package com.example.proxymit.Custom_Reports_Editor.controller;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import com.example.proxymit.Custom_Reports_Editor.service.TemplateService.TemplateData;
import com.example.proxymit.Custom_Reports_Editor.service.TemplateService.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Template")
@CrossOrigin(origins ="http://localhost:5173")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    @PostMapping("/save")
    public String saveTemplate(@RequestBody Template ModelData) {return templateService.saveTemplate(ModelData);}

    @GetMapping("/getAllTemplateData")
    public String getAllTemplate() {
        return templateService.getAllTemplate();
    }

    @DeleteMapping("/deleteTemplate")
    public String deleteTemplate(@RequestBody String name) {
        return templateService.deleteTemplate(name);
    }

    @GetMapping("/getDataTemplate/{name}")
    public TemplateData getDataTemplate(@PathVariable String name){return templateService.getDataTemplate(name);}

    @GetMapping("/getContextData/{name}")
    public String getContextData(@PathVariable String name){return templateService.getContextData(name);}

    @PutMapping("/ModifyTemplateData")
    public String ModifyTemplateData(@RequestBody Template template){return templateService.ModifyTemplateData(template);}

}
