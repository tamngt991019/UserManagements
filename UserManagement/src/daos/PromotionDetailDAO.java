/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.PromotionDetailDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import utils.DBConnection;

/**
 *
 * @author Kami.Sureiya
 */
public class PromotionDetailDAO {
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
    
    public Vector<PromotionDetailDTO> loadProDetail(){
        Vector<PromotionDetailDTO> data = new Vector<>();
        String sql = "Select UserID, ProID, Ranks, Date from tblPromotionsDetails";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(sql);
            rs = pStm.executeQuery();
            while(rs.next()){
                String userID = rs.getString(1).trim();
                String proID = rs.getString(2).trim();
                int rank = rs.getInt(3);
                String date = rs.getString(4).trim();
                data.add(new PromotionDetailDTO(userID, proID, rank, date));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot load promotion detail !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return data;
    }
    
    public void insertProDetail(String userID, String proID, int rank, String date){
        String sql = "Insert into tblPromotionsDetails values(?,?,?,?)";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, userID);
            pStm.setString(2, proID);
            pStm.setInt(3, rank);
            pStm.setString(4, date);
            pStm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Insert Successfully !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot insert user to promotion details !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    
    public void deleteProDetail(String userID) {
        
        String delete = "DELETE FROM tblPromotionsDetails WHERE UserID = ?";
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
}
