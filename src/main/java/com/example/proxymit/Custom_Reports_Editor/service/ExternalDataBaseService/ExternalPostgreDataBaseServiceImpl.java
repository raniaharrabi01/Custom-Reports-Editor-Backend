package com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService;

import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExternalPostgreDataBaseServiceImpl implements ExternalPostgreDataBaseService {

    @Override
    public DataSource createDataSource(ConnectionData data) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        try {
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
            String url = String.format("jdbc:postgresql://"+data.getHost()+":"+data.getPort()+"/"+data.getBase_name());
            dataSourceBuilder.url(url);
            dataSourceBuilder.username(data.getUsername());
            dataSourceBuilder.password(data.getPassword());
            return dataSourceBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public JdbcTemplate createJdbcTemplate(ConnectionData data) {
        return new JdbcTemplate(createDataSource(data));
    }

    @Override
    public Map<String, List<String>> getAllTablesAndColumnsName(ConnectionData data) {
        JdbcTemplate jdbcTemplateTest = createJdbcTemplate(data);
        // Requête pour obtenir les noms des tables dans PostgreSQL
        List<String> tableNames = jdbcTemplateTest.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_catalog = ? AND table_schema = 'public'", new Object[]{data.getBase_name()}, String.class
        );        Map<String, List<String>> tablesAndColumns = new HashMap<>();
        // Requête pour obtenir les noms des colonnes pour chaque table
        for (String tableName : tableNames) {
            List<String> columns = jdbcTemplateTest.queryForList(
                    "SELECT column_name FROM information_schema.columns WHERE table_schema = 'public' AND table_name = ?", new Object[]{tableName}, String.class
            );
            tablesAndColumns.put(tableName, columns);
        }
        return tablesAndColumns;
    }

    @Override
    public List<Map<String, Object>> getDataFromPostgreQuery(String PostgreQuery, ConnectionData data) {
        try {
            JdbcTemplate jdbcTemplate = createJdbcTemplate(data);
            return jdbcTemplate.queryForList(PostgreQuery);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Ou une autre valeur par défaut appropriée
        }
    }
    @Override
    public Map<String, List<Map<String, Object>>> executePostgreQueries(List<Map<String, String>> PostgreQuery, ConnectionData data) {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        for (Map<String, String> query : PostgreQuery) {
            String variableName = query.get("variableName");
            String sqlQuery = query.get("sqlquery");
            List<Map<String, Object>> result = getDataFromPostgreQuery(sqlQuery, data);
            results.put(variableName, result);
        }
        return results;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getDataFromSelectColumnsList(String tablename, ColumnsData colonnename, ConnectionData data) {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        JdbcTemplate jdbcTemplate = createJdbcTemplate(data);
        // Ensure proper handling of column names and table names in case of special characters or reserved keywords
        String selectColumns = String.join(",", colonnename.getColumns().stream().map(column -> "\"" + column + "\"").collect(Collectors.toList()));
        String quotedTableName = "\"" + tablename + "\"";
        // Construire la requête SQL avec les noms de colonnes et le nom de la table spécifiée
        String sql = "SELECT " + selectColumns + " FROM " + quotedTableName;
        String variableName = colonnename.getVariableName();
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        results.put(variableName, result);
        return results;
    }


}
