package com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class ExternalSQLDataBaseServiceImpl implements ExternalDataBaseService {
    @Override
    public DataSource createDataSource(ConnectionData data) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        try {
            dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://"+data.getHost()+":"+data.getPort()+"/"+data.getBase_name());
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
        List<String> tableNames = jdbcTemplateTest.queryForList("SELECT table_name FROM information_schema.tables WHERE table_schema = ?", new Object[]{data.getBase_name()}, String.class);
        Map<String, List<String>> tablesAndColumns = new HashMap<>();
        for (String tableName : tableNames) {
            List<String> columns = jdbcTemplateTest.queryForList("SELECT column_name FROM information_schema.columns WHERE table_name = ?", new Object[]{tableName}, String.class);
            tablesAndColumns.put(tableName, columns);
        }
        return tablesAndColumns;
    }

    public List<Map<String, Object>> getDataFromSQLQuery(String SQLQuery, ConnectionData data) {
        try {
            JdbcTemplate jdbcTemplate = createJdbcTemplate(data);
            return jdbcTemplate.queryForList(SQLQuery);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Collections.emptyList(); // Ou une autre valeur par défaut appropriée
        }
    }
    @Override
    public String validateSQLQuery(List<String> SQLQuery) {
        for (String query : SQLQuery) {
            // Utilisez une expression régulière pour vérifier si la requête commence par "SELECT"
            String trimmedQuery = query.trim();
            Pattern.compile("^SELECT", Pattern.CASE_INSENSITIVE).matcher(trimmedQuery).find();
            return "";
        }
        return null;
    }
    @Override
    public Map<String, List<Map<String, Object>>> executeSQLQueries(List<Map<String, String>> SQLQuery, ConnectionData data) {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        for (Map<String, String> query : SQLQuery) {
            String variableName = query.get("variableName");
            String sqlQuery = query.get("sqlquery");
             List<Map<String, Object>> result = getDataFromSQLQuery(sqlQuery, data);
                results.put(variableName, result);
        }
        return results;
    }
    @Override
    public Map<String, List<Map<String, Object>>> getDataFromSelectColumnsList(String tablename, ColumnsData colonnename, ConnectionData data) {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        JdbcTemplate jdbcTemplate = createJdbcTemplate(data);
        // Properly quote column names
        String selectColumns = colonnename.getColumns().stream()
                .map(column -> "`" + column + "`")
                .collect(Collectors.joining(", "));
        // Properly quote table name
        String quotedTableName = "`" + tablename + "`";
        // Construct the SQL query with quoted column names and table name
        String sql = "SELECT " + selectColumns + " FROM " + quotedTableName;
        String variableName = colonnename.getVariableName();
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        results.put(variableName, result);
        return results;
    }
}
