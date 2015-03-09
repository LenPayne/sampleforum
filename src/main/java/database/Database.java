/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author c0587637
 */
public class Database {
    public static Connection getConnection() throws SQLException {        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        String hostname = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String portnum = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String pass = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String jdbc = "jdbc:mysql://" + hostname + ":" + portnum + "/sampleforum";
        return DriverManager.getConnection(jdbc, user, pass);
    }
    
    public static JsonArray getResults(String sql, String... params) {
        JsonArray json = null;
        try {
            JsonArrayBuilder array = Json.createArrayBuilder();
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                pstmt.setString(i+1, params[i]);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                array.add(Json.createObjectBuilder()
                    .add("id", rs.getInt("id"))
                    .add("name", rs.getString("name"))
                    .add("text", rs.getString("postText"))
                    .add("time", rs.getDate("time").toString()));
            }
            conn.close();
            json = array.build();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
