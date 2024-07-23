package com.example.proxymit.Custom_Reports_Editor.service.ExternalDataBaseService;
import com.example.proxymit.Custom_Reports_Editor.service.GenerateReportService.ColumnsData;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
public interface ExternalOracleDataBaseService {
    public DataSource createDataSource(ConnectionData Data);
    public Map<String, List<String>> getAllTablesAndColumnsName(ConnectionData data);
    public List<Map<String, Object>> getDataFromOracleQuery(String Sql_query, ConnectionData data);
    public Map<String, List<Map<String, Object>>> getDataFromSelectColumnsList(String table_name, ColumnsData columns_name, ConnectionData data);
    public Map<String, List<Map<String, Object>>> executeOracleQueries(List<Map<String, String>> queries, ConnectionData data);
}
