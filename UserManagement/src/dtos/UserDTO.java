/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Vector;

/**
 *
 * @author Kami.Sureiya
 */
public class UserDTO {
    private String userID;
    private String userName;
    private String roleID;
    private String email;
    private String phone;
    private String photo;
    private boolean status;
    private String password;
    
    public UserDTO() {
    }
    
    public UserDTO(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public UserDTO(String userID, String userName, String roleID, String email, String phone, String photo, boolean status, String password) {
        this.userID = userID;
        this.userName = userName;
        this.roleID = roleID;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.status = status;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vector changeToNormalList(){
        Vector list = new Vector();
        list.add(userID);
        list.add(userName);
        list.add(roleID);
        list.add(email);
        list.add(phone);
        list.add(photo);
        list.add(status);
        list.add(password);
        return list;        
    }
    
}
