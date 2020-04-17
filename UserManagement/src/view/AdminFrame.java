/*
 * To change this license headerUser, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.glass.events.KeyEvent;
import daos.PromotionDetailDAO;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.RoleDTO;
import dtos.UserDTO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kami.Sureiya
 */
public class AdminFrame extends javax.swing.JFrame {

    Vector<Vector> allTableList = new Vector<>();
    Vector<Vector> adminTableList = new Vector<>();
    Vector<Vector> userTableList = new Vector<>();
    Vector<String> headerUser = new Vector<>();

    Vector<Vector> allSearchList = new Vector<>();
    Vector<Vector> adminSearchList = new Vector<>();
    Vector<Vector> userSearchList = new Vector<>();

    boolean searchUser = false;
    String photoName = "default.jpg";

    DefaultTableModel modelAll = null;
    DefaultTableModel modelAdmin = null;
    DefaultTableModel modelUser = null;

    DefaultComboBoxModel cbxModel = null;

    RoleDAO roleDao = new RoleDAO();
    Vector<RoleDTO> roleDTOList = null;

    UserDAO userDAO = new UserDAO();
    Vector<UserDTO> userDTOList = null;
    Vector<UserDTO> searchUserDTOList = null;

    JFileChooser fc = null;
    File file;

    String id = "";
    
    PromotionDetailDAO pdDAO = new PromotionDetailDAO();
    
    public AdminFrame(String id) {
        initComponents();
        this.id = id;
        lblUserID.setText("User ID: " + id);
        userDTOList = userDAO.loadUser();
        setLocation(600, 100);

        classifyByRole(userDTOList, allTableList, adminTableList, userTableList);
        loadHeaderUser();
        initTable(allTableList, adminTableList, userTableList);
        loadPhoto();
        loadRoleToComboBox();

    }

    private AdminFrame() {
    }

    public void initTable(Vector<Vector> allList, Vector<Vector> adminList, Vector<Vector> userList) {
        modelAll = loadDataToTable(allList, headerUser, modelAll);
        tblAll.setModel(modelAll);
        modelAdmin = loadDataToTable(adminList, headerUser, modelAdmin);
        tblAdmin.setModel(modelAdmin);
        modelUser = loadDataToTable(userList, headerUser, modelUser);
        tblUser.setModel(modelUser);
    }

    public void loadHeaderUser() {
        headerUser.add("User ID");
        headerUser.add("User Name");
        headerUser.add("Role ID");
        headerUser.add("Email");
        headerUser.add("Phone");
        headerUser.add("Photo");
    }

    public void setDefault() {
        txtUserID.setText("");
        txtUserName.setText("");
        cbxRole.setSelectedIndex(0);
        txtPhone.setText("");
        txtEmail.setText("");
        photoName = "default.jpg";
        loadPhoto();
    }

    public void classifyByRole(Vector<UserDTO> list, Vector<Vector> allList, Vector<Vector> adminList, Vector<Vector> userList) {
        allList.clear();
        adminList.clear();
        userList.clear();
        for (int i = 0; i < list.size(); i++) {
            allList.add(list.get(i).changeToNormalList());
            if (list.get(i).getRoleID().equals("AD")) {
                adminList.add(list.get(i).changeToNormalList());
            } else if (list.get(i).getRoleID().equals("UR")) {
                userList.add(list.get(i).changeToNormalList());
            }
        }
    }

    DefaultTableModel loadDataToTable(Vector<Vector> list, Vector title, DefaultTableModel model) {
        model = new DefaultTableModel(list, title) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return model;
    }

    public void clickIntoTable(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtUserID.setText((String) model.getValueAt(row, 0));
            txtUserName.setText((String) model.getValueAt(row, 1));
            String role = (String) model.getValueAt(row, 2);
            if (role.equals("AD")) {
                cbxRole.setSelectedIndex(1);
            } else {
                cbxRole.setSelectedIndex(2);
            }
            txtEmail.setText((String) model.getValueAt(row, 3));
            txtPhone.setText((String) model.getValueAt(row, 4));

            photoName = (String) model.getValueAt(row, 5);
            loadPhoto();
        }
    }

    public void loadRoleToComboBox() {
        roleDTOList = roleDao.loadRole();
        Vector<String> cbx = new Vector<>();
        cbx.add(". . .");
        for (int i = 0; i < roleDTOList.size(); i++) {
            cbx.add((String) roleDTOList.get(i).getRoleName().toUpperCase());
        }
        cbxModel = new DefaultComboBoxModel(cbx);
        cbxRole.setModel(cbxModel);
    }

    public void loadPhoto() {
        try {
            File file = new File(photoName);
            BufferedImage bi = ImageIO.read(file);
            Image image = bi.getScaledInstance(120, 110, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            lblPhoto.setIcon(icon);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Cannot load photo");
        }
    }

    public void doDelete(JTable table, DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String code = (String) model.getValueAt(row, 0);
            if (code.equals(id)) {
                JOptionPane.showMessageDialog(this, "You cannot delete yourseft !");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Delete user ID: " + code + " ?")
                    == JOptionPane.OK_OPTION) {
                userDAO.deleteUser(code);
                pdDAO.deleteProDetail(code);
                setDefault();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select user !");
        }

    }

    public boolean validData() {
        String userID = txtUserID.getText().trim();
        if (userID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select user !");
            return false;
        }
        String userName = txtUserName.getText().trim();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input user name !");
            return false;
        }
        int index = cbxRole.getSelectedIndex();
        if (index == 0) {
            JOptionPane.showMessageDialog(this, "Please select role !");
            return false;
        }
        String phone = txtPhone.getText().trim();
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input phone number !");
            return false;
        }
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input email !");
            return false;
        }
        return true;
    }

    public boolean validFormatData() {
        String phone = txtPhone.getText().trim();
        if (!phone.matches("[0]\\d{9}")) {
            JOptionPane.showMessageDialog(this, "Phone number is incorrect format (0XXXXXXXXX)!");
            return false;
        }
        String email = txtEmail.getText().trim();
        if (!email.matches("[a-zA-Z0-9]{6,30}"
                + "@[a-zA-Z0-9]{2,20}"
                + "([.][a-zA-Z]{2,5}){1,2}")) {
            JOptionPane.showMessageDialog(this, "Invalid Email !");
            return false;
        }
        return true;
    }

    public void doUpdate(JTable table, DefaultTableModel model) {
        String userID = txtUserID.getText().trim();
        String userName = txtUserName.getText().trim().toUpperCase();
        String role = (String) cbxRole.getSelectedItem();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String photo = photoName;
        userDAO.updateUser(userID, userName, role.toUpperCase(), email, phone, photo);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTab = new javax.swing.JPanel();
        tpnTab = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAll = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAdmin = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        btnLogout = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblPhoto = new javax.swing.JLabel();
        btnChangePhoto = new javax.swing.JButton();
        btnCreateUser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUserID = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxRole = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnAddToPromotion = new javax.swing.JButton();
        lblUserID = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Frame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlTab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tpnTab.setToolTipText("io");
        tpnTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpnTabMouseClicked(evt);
            }
        });

        tblAll.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAll.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAll.getTableHeader().setReorderingAllowed(false);
        tblAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAll);

        tpnTab.addTab("All", jScrollPane1);

        tblAdmin.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAdmin.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAdmin.getTableHeader().setReorderingAllowed(false);
        tblAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdminMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAdmin);

        tpnTab.addTab("Admin", jScrollPane2);

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUser.getTableHeader().setReorderingAllowed(false);
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUser);

        tpnTab.addTab("User", jScrollPane3);

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSearch.setText("Search By User Name");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        btnSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSearchKeyPressed(evt);
            }
        });

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "User Details", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        lblPhoto.setDisplayedMnemonic('A');
        lblPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnChangePhoto.setText("Change Photo");
        btnChangePhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePhotoActionPerformed(evt);
            }
        });

        btnCreateUser.setText("Create User");
        btnCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUserActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("User ID:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("User Name:");

        txtUserID.setEditable(false);
        txtUserID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtUserName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Phone:");

        cbxRole.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Role:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnChangePassword.setText("Change Password");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChangePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtEmail))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtUserID))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChangePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserID)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserName)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnView.setText("View Promotion List");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnAddToPromotion.setText("Add To Promotion");
        btnAddToPromotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToPromotionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTabLayout = new javax.swing.GroupLayout(pnlTab);
        pnlTab.setLayout(pnlTabLayout);
        pnlTabLayout.setHorizontalGroup(
            pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tpnTab)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabLayout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addGap(18, 18, 18)
                                .addComponent(btnReload))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabLayout.createSequentialGroup()
                                .addComponent(btnAddToPromotion)
                                .addGap(18, 18, 18)
                                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLogout)
                                .addGap(23, 23, 23)))))
                .addContainerGap())
        );
        pnlTabLayout.setVerticalGroup(
            pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpnTab, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(pnlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddToPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tpnTab.getAccessibleContext().setAccessibleName("");

        lblUserID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validData()) {
            return;
        }
        if (!validFormatData()) {
            return;
        }
        int intdexTab = tpnTab.getSelectedIndex();
        if (intdexTab == 0) {
            doUpdate(tblAll, modelAll);
        } else if (intdexTab == 1) {
            doUpdate(tblAdmin, modelAdmin);
        } else if (intdexTab == 2) {
            doUpdate(tblUser, modelUser);
        }
        setDefault();
        if (searchUser == true) {
            String keyword = txtSearch.getText().toUpperCase().trim();
            classifyByRole(userDAO.loadSearchUser(keyword), allSearchList, adminSearchList, userSearchList);
            initTable(allSearchList, adminSearchList, userSearchList);
        } else {
            classifyByRole(userDAO.loadUser(), allTableList, adminTableList, userTableList);
            initTable(allTableList, adminTableList, userTableList);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        UserCreationFrame ucf = new UserCreationFrame(this, true);
        ucf.setVisible(true);
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int intdexTab = tpnTab.getSelectedIndex();
        if (intdexTab == 0) {
            doDelete(tblAll, modelAll);
        } else if (intdexTab == 1) {
            doDelete(tblAdmin, modelAdmin);
        } else if (intdexTab == 2) {
            doDelete(tblUser, modelUser);
        }
        if (searchUser == true) {
            String keyword = txtSearch.getText().toUpperCase().trim();
            classifyByRole(userDAO.loadSearchUser(keyword), allTableList, adminTableList, userTableList);
            initTable(allSearchList, adminSearchList, userSearchList);
        } else {
            classifyByRole(userDAO.loadUser(), allTableList, adminTableList, userTableList);
            initTable(allTableList, adminTableList, userTableList);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllMouseClicked
        clickIntoTable(tblAll, modelAll);
    }//GEN-LAST:event_tblAllMouseClicked

    private void tblAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdminMouseClicked
        clickIntoTable(tblAdmin, modelAdmin);
    }//GEN-LAST:event_tblAdminMouseClicked

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        clickIntoTable(tblUser, modelUser);
    }//GEN-LAST:event_tblUserMouseClicked

    private void tpnTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpnTabMouseClicked
        tblAll.clearSelection();
        tblAdmin.clearSelection();
        tblUser.clearSelection();
    }//GEN-LAST:event_tpnTabMouseClicked

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        if (txtUserID.getText().isEmpty()) {
            return;
        }
        PasswordChangeFrame pcf = new PasswordChangeFrame(this, true, txtUserID.getText());
        pcf.setVisible(true);
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        int ans = JOptionPane.showConfirmDialog(this, " Do you wanna Logout ?", "Logout", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            btnLogoutActionPerformed(null);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchUser = true;
        String keyword = txtSearch.getText().trim().toUpperCase();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(null, " Please input name to Search !");
            searchUser = false;
            return;
        }

        classifyByRole(userDAO.loadSearchUser(keyword), allSearchList, adminSearchList, userSearchList);
        initTable(allSearchList, adminSearchList, userSearchList);

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchActionPerformed(null);
        }
    }//GEN-LAST:event_btnSearchKeyPressed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(null);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnChangePhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePhotoActionPerformed
        if (txtUserID.getText().isEmpty()) {
            return;
        }
        fc = new JFileChooser();
        try {
            int open = fc.showOpenDialog(this);
            if (open == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                photoName = file.getName();
                if (file.getName().endsWith("jpg") || file.getName().endsWith("png")) {
                    loadPhoto();
                } else {
                    JOptionPane.showMessageDialog(this, "Not a photo !");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot select photo");
        }
    }//GEN-LAST:event_btnChangePhotoActionPerformed

    private void btnAddToPromotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToPromotionActionPerformed
        PromotionFrame pf = new PromotionFrame(this, true);
        pf.setVisible(true);
    }//GEN-LAST:event_btnAddToPromotionActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        searchUser = false;
        setDefault();
        classifyByRole(userDAO.loadUser(), allTableList, adminTableList, userTableList);
        initTable(allTableList, adminTableList, userTableList);
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        PromotionDetailFrame pdf = new PromotionDetailFrame(this, true);
        pdf.setVisible(true);
    }//GEN-LAST:event_btnViewActionPerformed

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
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToPromotion;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnChangePhoto;
    private javax.swing.JButton btnCreateUser;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblUserID;
    private javax.swing.JPanel pnlTab;
    private javax.swing.JTable tblAdmin;
    private javax.swing.JTable tblAll;
    private javax.swing.JTable tblUser;
    private javax.swing.JTabbedPane tpnTab;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
