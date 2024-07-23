package com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public String generateReportUsingThymleaf(Map<String, List<Map<String, Object>>> results, String code)
    {
        // Crée une instance de TemplateEngine, qui est responsable du traitement des templates
        TemplateEngine templateEngine = new TemplateEngine();
        // Crée un résolveur de templates pour les templates de type String
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        // Définit le mode de template comme HTML, ce qui signifie que les templates seront traités comme du HTML
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Définit le résolveur de templates pour le moteur de templates
        templateEngine.setTemplateResolver(templateResolver);
        // Crée un contexte pour le template, qui contient les variables nécessaires pour le rendu
        Context context = new Context();
        // Parcourir chaque entrée dans la map results
        for (Map.Entry<String, List<Map<String, Object>>> entry : results.entrySet()) {
            String variableName = entry.getKey(); // Obtenir le nom de la variable
            List<Map<String, Object>> result = entry.getValue(); // Obtenir la liste des résultats correspondants
            // Ajouter la liste des résultats au contexte avec le nom de la variable comme clé
            context.setVariable(variableName, result);
        }
        // Traite le template avec le code donné et le contexte, et retourne le contenu rendu
        return templateEngine.process(code, context);
    }
    public String generateReportUsingThymleaf(List<Map<String, Object>> results, String code) {
        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("result", results); // Ajouter la liste des résultats au contexte avec la clé "results"
        return templateEngine.process(code , context);
    }

    @Override
    public String prepareTemplateCodeSyntax(Template modelData) {
        String html = modelData.getHtmlData();
    html = prepareThymeleafSyntax(html);
    String css = modelData.getCssData();
    if (html != null && css != null) {
        String htmlWithCss = "<!DOCTYPE html>" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">" +
                "<head>" +
                "<style>" + css + "</style>" +
                "</head>" +
                 html +
                "</html>";
        return htmlWithCss;
    }
    return "null";
}

    @Override
    public String prepareThymeleafSyntax(String htmlString) {
        String pattern = "paramétre='(.*?)'";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(htmlString);
        String output = htmlString;
        // Find the variable value from the pattern
        if (matcher.find()) {
            String variableValue = matcher.group(1);
            // Replace th:each='data' with th:each='data : ${variable}'
            output = output.replace("th:each='data'", "th:each='data : ${" + variableValue + "}'");
            // Replace <p>${variable.field}</p> with <p th:text='${data.field}'></p>
            String pattern2 = "<p>\\$\\{" + variableValue + "\\.(.*?)\\}</p>";
            Pattern regex2 = Pattern.compile(pattern2);
            Matcher matcher2 = regex2.matcher(output);
            while (matcher2.find()) {
                String text1 = matcher2.group(1);
                output = output.replace("<p>${" + variableValue + "." + text1 + "}</p>", "<p th:text='${data." + text1 + "}'></p>");
            }
            // Replace <p id='...'>${variable.field}</p> with <p id='...' th:text='${data.field}'></p>
            String pattern3 = "<p id='(.*?)'>\\$\\{" + variableValue + "\\.(.*?)\\}</p>";
            Pattern regex3 = Pattern.compile(pattern3);
            Matcher matcher3 = regex3.matcher(output);
            while (matcher3.find()) {
                String id = matcher3.group(1);
                String field = matcher3.group(2);
                output = output.replace("<p id='" + id + "'>${" + variableValue + "." + field + "}</p>", "<p id='" + id + "' th:text='${data." + field + "}'></p>");
            }
        }
        //balise p  a l'exter
            String pattern4 = ">\\$(.*?)</p>";
            Pattern regex4 = Pattern.compile(pattern4);
            Matcher matcher4 = regex4.matcher(output);
            String var = "<p id='(.*?)'>";
            Pattern regexVar = Pattern.compile(var);
            while (matcher4.find()) {
                String var2 = "<div id='(.*?)'>";
                Pattern regexVar2 = Pattern.compile(var2);
                Matcher matcherVar2 = regexVar2.matcher(output);
                while (matcherVar2.find()) {
                    String text2 = matcher4.group(1);
                    String text3 = matcherVar2.group(1);
                    Matcher matcherVar = regexVar.matcher(output);
                    output = output.replace("<div id='"+text3+"'><p>$"+text2+"</p>", "<div id='"+text3+"' th:each='map :${"+extractVariableName(text2)+"}' ><p th:text='${map."+extractPropertyName(text2) + "}'></p>");
                    while (matcherVar.find()) {
                        String textVar = matcherVar.group(1);
                        output = output.replace("<div id='"+text3+"'><p id='" + textVar + "'" + ">$" + text2 + "</p>", "<div id='"+text3+"' th:each='map :${"+extractVariableName(text2)+"}' ><p id='" + textVar + "' th:text='${map."+extractPropertyName(text2) + "}'></p>");
                    }
                }
            }
            String pattern3 = "<h1>(.*?)</h1>";
            Pattern regex3 = Pattern.compile(pattern3);
            Matcher matcher3 = regex3.matcher(output);
            while (matcher3.find()) {
                String title = matcher3.group(1);
                output = output.replace(">" + title + "</h1>", "<h1 th:text='$" + title + "'></h1>");
            }
        return output;
    }
    public static String extractVariableName(String input) {
        // Ensure the input starts with '{' and ends with '}'
        if (input.startsWith("{") && input.endsWith("}")) {
            // Extract the content within {...}
            String content = input.substring(1, input.length() - 1);
            // Find the index of the dot
            int dotIndex = content.indexOf(".");
            if (dotIndex != -1) {
                // Extract the substring before the dot
                return content.substring(0, dotIndex);
            }
        }
        return null; // Return null if the format is invalid
    }
    public static String extractPropertyName(String input) {
        // Ensure the input starts with '{' and ends with '}'
        if (input.startsWith("{") && input.endsWith("}")) {
            // Extract the content within {...}
            String content = input.substring(1, input.length() - 1);
            // Find the index of the dot
            int dotIndex = content.indexOf(".");
            if (dotIndex != -1) {
                // Extract the substring after the dot
                return content.substring(dotIndex + 1);
            }
        }
        return null; // Return null if the format is invalid
    }
}
