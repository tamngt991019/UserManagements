/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoleDTO;
import dtos.UserDTO;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Kami.Sureiya
 */
public class UserDAO {

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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Cannot Close !");
//            e.printStackTrace();
        }
    }

    public Vector<UserDTO> loadUser() {
        Vector<UserDTO> data = new Vector<>();

        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement("select UserID, UserName, Password, RoleID, Email, Phone, Photo, Status from tblUsers where Status = 1");
            rs = pStm.executeQuery();
            while (rs.next()) {
                String userID = rs.getString(1).trim();
                String userName = rs.getString(2).trim();
                String password = rs.getString(3).trim();
                String roleID = rs.getString(4).trim();
                String email = rs.getString(5).trim();
                String phone = rs.getString(6).trim();
                String photo = rs.getString(7).trim();
                boolean status = rs.getBoolean(8);
                data.add(new UserDTO(userID, userName, roleID, email, phone, photo, status, password));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot load user !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return data;
    }

    public void createUser(String userID, String userName, String password,
            String roleID, String email, String phone, String photo) {
        String insert = "Insert into tblUsers values(?,?,?,?,?,?,?,?)";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(insert);
            pStm.setString(1, userID);
            pStm.setString(2, userName);
            pStm.setString(3, password);
            pStm.setString(4, roleID);
            pStm.setString(5, email);
            pStm.setString(6, phone);
            pStm.setString(7, photo);
            pStm.setBoolean(8, true);
            pStm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Duplicate User ID ! Cannot create user !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateUser(String userID, String userName, String roleID, String email, String phone, String photo) {
        String update = "Update tblUsers Set UserName = ?, RoleID = ?, Email = ?, Phone = ?, Photo = ? Where UserID = ?";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(update);
            pStm.setString(1, userName);
            if (roleID.equalsIgnoreCase("Admin")) {
                pStm.setString(2, "AD");
            } else if (roleID.equalsIgnoreCase("User")) {
                pStm.setString(2, "UR");
            }
            pStm.setString(3, email);
            pStm.setString(4, phone);
            pStm.setString(5, photo);
            pStm.setString(6, userID);
            pStm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot update user !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteUser(String userID) {
        String delete = "Update tblUsers Set Status = 0 Where UserID = ?";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(delete);
            pStm.setString(1, userID);
            pStm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot delete " + userID);
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updatePassword(String userID, String password) {
        String delete = "Update tblUsers Set Password = ? Where UserID = ?";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(delete);
            pStm.setString(1, password);
            pStm.setString(2, userID);
            pStm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot delete " + userID);
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public Vector<UserDTO> loadSearchUser(String keyword) {
        Vector<UserDTO> data = new Vector<>();
        String sql = "SELECT UserID, UserName, Password, RoleID, Email, Phone, Photo, Status FROM tblUsers WHERE UserName like ? and Status = 1";
        try {
            cn = db.createConnection();
            if (cn != null) {

                pStm = cn.prepareStatement(sql);
                pStm.setString(1, "%" + keyword + "%");
                rs = pStm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString(1).trim();
                    String userName = rs.getString(2).trim();
                    String password = rs.getString(3).trim();
                    String roleID = rs.getString(4).trim();
                    String email = rs.getString(5).trim();
                    String phone = rs.getString(6).trim();
                    String photo = rs.getString(7).trim();
                    boolean status = rs.getBoolean(8);
                    data.add(new UserDTO(userID, userName, roleID, email, phone, photo, status, password));

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot load search user !");
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return data;
    }

}
