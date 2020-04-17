/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoleDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import utils.DBConnection;

/**
 *
 * @author Kami.Sureiya
 */
public class RoleDAO {
    private DBConnection db = new DBConnection();
    private Connection cn = null;
    private PreparedStatement pStm = null;
    private ResultSet rs = null;

    public void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pStm != null) {
                pStm.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Cannot close connection !");
        }
    }
    
    public Vector<RoleDTO> loadRole() {
        Vector<RoleDTO> data = new Vector<>();

        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement("select RoleID, RoleName from tblRoles");
            rs = pStm.executeQuery();
            while (rs.next()) {
                String roleID = rs.getString(1).trim();
                String toleName = rs.getString(2).trim();
                data.add(new RoleDTO(roleID, toleName));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot load role !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return data;
    }
    
   
}
