/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import daos.PromotionDAO;
import daos.PromotionDetailDAO;
import daos.UserDAO;
import dtos.PromotionDTO;
import dtos.PromotionDetailDTO;
import dtos.UserDTO;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kami.Sureiya
 */
public class PromotionFrame extends javax.swing.JDialog {

    Vector<String> headerPromotion = new Vector<>();
    Vector<Vector> proTableList = new Vector<>();

    DefaultTableModel modelPro = null;
    DefaultComboBoxModel cbxModelUser = null;

    UserDTO userDTO = new UserDTO();
    UserDAO userDAO = new UserDAO();
    PromotionDAO proDAO = new PromotionDAO();
    PromotionDTO proDTO = new PromotionDTO();
    Vector<PromotionDTO> proDTOList = new Vector<>();

    Vector<String> headerProDetail = new Vector<>();
    PromotionDetailDAO pdDAO = new PromotionDetailDAO();
    Vector<PromotionDetailDTO> pdDTOList = new Vector<>();
    Vector<Vector> pdTableList = new Vector<>();
    DefaultTableModel modelProDetail = null;

    public PromotionFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocation(550, 200);
        loadHeaderPromotion();
        loadDataPromotion();
        loadHeaderProDetail();
        loadUserToComboBox();
    }

    public void loadDataPromotion() {
        proDTOList = proDAO.loadPromotion();
        if (proDTOList.isEmpty()) {
            return;
        }
        for (int i = 0; i < proDTOList.size(); i++) {
            proTableList.add(proDTOList.get(i).changeToNormalList());
        }

        modelPro = new DefaultTableModel(proTableList, headerPromotion) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPro.setModel(modelPro);
    }

    public void loadDataProDetail() {
        pdTableList.clear();
        for (int i = 0; i < pdDTOList.size(); i++) {
            pdTableList.add(pdDTOList.get(i).changeToNormalList());
        }
        modelProDetail = new DefaultTableModel(pdTableList, headerProDetail) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProDetail.setModel(modelProDetail);
    }

    public void loadHeaderPromotion() {
        headerPromotion.add("Promotion ID");
        headerPromotion.add("Promotion Name");
        headerPromotion.add("Description");
    }

    public void loadHeaderProDetail() {
        headerProDetail.add("User ID");
        headerProDetail.add("Pro ID");
        headerProDetail.add("Rank");
        headerProDetail.add("Date");
    }

//    public Vector<Vector> changeList(Vector<PromotionDTO> list) {
//        Vector<Vector> data = new Vector();
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                data.add(list.get(i).changeToNormalList());
//            }
//        }
//        return data;
//    }
    public void loadUserToComboBox() {
        Vector<UserDTO> uList = new Vector<>();
        uList = userDAO.loadUser();
        Vector<String> cbx = new Vector<>();
        cbx.add("UserID");
        for (int i = 0; i < uList.size(); i++) {
            cbx.add(uList.get(i).getUserID());
        }
        cbxModelUser = new DefaultComboBoxModel(cbx);
        cbxUserID.setModel(cbxModelUser);
    }

    public boolean validData() {
        int row = tblPro.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select promotion !");
            return false;
        }
        int index = cbxUserID.getSelectedIndex();
        if (index == 0) {
            JOptionPane.showMessageDialog(this, "Please select user id !");
            return false;
        }
        return true;
    }

    public boolean checkDuplicateUserID(String userID) {
        for (int i = 0; i < pdDTOList.size(); i++) {
            if(userID.equals(pdDTOList.get(i).getUserID())){
                return false;
            }
        }
        return true;
    }
    
    public boolean checkDuplicateProDetail(String userID, String proID) {
        Vector<PromotionDetailDTO> list = new Vector<>();
        list = pdDAO.loadProDetail();
        for (int i = 0; i < list.size(); i++) {
            if(userID.equals(list.get(i).getUserID()) && proID.equals(list.get(i).getProID())){
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbxUserID = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPro = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProDetail = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cbxRank = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnConfirm = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Promotion Frame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Promotion List");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Select User:");

        cbxUserID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblPro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item 1", "Item 2", "Item 3", "Item 4"
            }
        ));
        tblPro.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPro.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblPro);
        tblPro.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cbxUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblProDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProDetail.getTableHeader().setReorderingAllowed(false);
        tblProDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProDetailMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProDetail);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Rank:");

        cbxRank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        btnUpdate.setText("Update Rank");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove User");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("User's Promotion");

        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(cbxRank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnUpdate)
                            .addGap(18, 18, 18)
                            .addComponent(btnRemove)
                            .addGap(18, 18, 18)
                            .addComponent(btnConfirm))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxRank)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancel1.setText("Cancel");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        int ans = JOptionPane.showConfirmDialog(this, " Do you wanna cancel ?", "Cancel", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validData()) {
            return;
        }
        int row = tblPro.getSelectedRow();
        String userID = (String) cbxUserID.getSelectedItem();
        if (!checkDuplicateUserID(userID)) {
            JOptionPane.showMessageDialog(this, "Duplicate user id !");
            return;
        }
        String proID = (String) modelPro.getValueAt(row, 0);
        if(!checkDuplicateProDetail(userID, proID)){
            JOptionPane.showMessageDialog(this, "Duplicate promotion detail !");
            return;
        }
        if (row >= 0) {
            String date = String.valueOf(java.time.LocalDate.now());
            pdDTOList.add(new PromotionDetailDTO(userID, proID, 5, date));
            loadDataProDetail();
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void tblProDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProDetailMouseClicked
        if(pdTableList.isEmpty()){
            return;
        }
        int row = tblProDetail.getSelectedRow();
        if (row >= 0) {
            int ranks = (int) modelProDetail.getValueAt(row, 2);
            cbxRank.setSelectedIndex(ranks - 1);
        }
    }//GEN-LAST:event_tblProDetailMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (!validData()) {
            return;
        }
        int row = tblProDetail.getSelectedRow();
        if (row >= 0) {
            String userID = (String) modelProDetail.getValueAt(row, 0);
            int ranks = (int) cbxRank.getSelectedIndex() + 1;
            String date = (String) modelProDetail.getValueAt(row, 3);
            for (int i = 0; i < pdDTOList.size(); i++) {
                if (userID.equals(pdDTOList.get(i).getUserID())) {
                    pdDTOList.get(i).setRank(ranks);
                    pdDTOList.get(i).setDate(date);
                }
            }
            loadDataProDetail();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if(pdTableList.isEmpty()){
            return;
        }
        int row = tblProDetail.getSelectedRow();
        if (row >= 0) {
            String userID = (String) modelProDetail.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Delete user ID: " + userID + " ?")
                    == JOptionPane.OK_OPTION) {
                for (int i = 0; i < pdDTOList.size(); i++) {
                    if (userID.equals(pdDTOList.get(i).getUserID())) {
                        pdDTOList.remove(i);
                    }
                }
                loadDataProDetail();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select user !");
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        formWindowClosing(null);
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        if(pdTableList.isEmpty()){
            return;
        }
        for (int i = 0; i < pdDTOList.size(); i++) {
            String userID = pdDTOList.get(i).getUserID();
            String proID = pdDTOList.get(i).getProID();
            int ranks = pdDTOList.get(i).getRank();
            String date = pdDTOList.get(i).getDate();
            pdDAO.insertProDetail(userID, proID, ranks, date);
            pdDTOList.clear();
            loadDataProDetail();
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PromotionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PromotionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PromotionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PromotionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PromotionFrame dialog = new PromotionFrame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxRank;
    private javax.swing.JComboBox<String> cbxUserID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblPro;
    private javax.swing.JTable tblProDetail;
    // End of variables declaration//GEN-END:variables
}
