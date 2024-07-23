package com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
public interface ExternalDataBaseService {
     DataSource createDataSource(ConnectionData Data);
     Map<String, List<String>> getAllTablesAndColumnsName(ConnectionData data);
     List<Map<String, Object>> getDataFromSQLQuery(String Sql_query, ConnectionData data);
     Map<String, List<Map<String, Object>>> getDataFromSelectColumnsList(String table_name, ColumnsData columns_name, ConnectionData data);
     Map<String, List<Map<String, Object>>> executeSQLQueries(List<Map<String, String>> queries, ConnectionData data);
     String validateSQLQuery(List<String> SqlQuery);

}