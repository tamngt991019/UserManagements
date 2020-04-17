/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.PromotionDTO;
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
public class PromotionDAO {

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

    public Vector<PromotionDTO> loadPromotion() {
        Vector<PromotionDTO> data = new Vector<>();
        String sql = "select ProID, ProName, Description from tblPromotions";
        try {
            cn = db.createConnection();
            pStm = cn.prepareStatement(sql);
            rs = pStm.executeQuery();
            while (rs.next()) {
                String proID = rs.getString(1).trim();
                String proName = rs.getString(2).trim();
                String description = rs.getString(3).trim();
                data.add(new PromotionDTO(proID, proName, description));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot load promotion !");
//            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return data;
    }
   

}
