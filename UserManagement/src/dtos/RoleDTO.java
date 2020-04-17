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
public class RoleDTO {
    private String roleID;
    private String roleName;

    public RoleDTO() {
    }

    public RoleDTO(String roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public Vector changeToNormalList(){
        Vector list = new Vector();
        list.add(roleID);
        list.add(roleName);
        return list;        
    }
}
