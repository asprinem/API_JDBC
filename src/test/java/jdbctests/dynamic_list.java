package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ybilgin
 * @project JDBC
 */


public class dynamic_list {

    String dbUrl = "jdbc:oracle:thin:@3.87.87.218:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void dynamic_list() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //ResultSet.TYPE_SCROLL_INSENSITIVE--> allow us to navigate up and down in query result.
        //ResultSet.CONCUR_READ_ONLY--> Read only, don't update the database
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select first_name,last_name,salary,job_id from employees where rownum < 6");

        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String,Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount = rsMetadata.getColumnCount();

        //loop through each row
        while(resultSet.next()){
            Map<String,Object> row = new HashMap<>();

            //awesome I did it :)
            for (int i = 1; i <= colCount; i++) {

                row.put(rsMetadata.getColumnName(i),resultSet.getString(i));

            }

            queryData.add(row);
        }

        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
