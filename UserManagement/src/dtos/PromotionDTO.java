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
public class PromotionDTO {
    private String proID;
    private String proName;
    private String description;

    public PromotionDTO(String proID, String proName, String description) {
        this.proID = proID;
        this.proName = proName;
        this.description = description;
    }

    public PromotionDTO() {
    }
    
    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Vector changeToNormalList(){
        Vector list = new Vector();
        list.add(proID);
        list.add(proName);
        list.add(description);
        return list;        
    }
}
