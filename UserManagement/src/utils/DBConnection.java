/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Kami.Sureiya
 */
public class DBConnection {

    final String url = " user=sa; password=1234567890";

    public Connection createConnection() {
        Connection cn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433; databaseName=UserManagements";
            cn = DriverManager.getConnection(url, "sa", "1234567890");
            return cn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Connect to Database !");
        }
        return null;
    }
    
    
}
