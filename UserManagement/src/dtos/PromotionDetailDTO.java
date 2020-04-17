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
public class PromotionDetailDTO {
    private String userID;
    private String proID;
    private int rank;
    private String date;

    
    
    public PromotionDetailDTO(String userID, String proID, int rank, String date) {
        this.userID = userID;
        this.proID = proID;
        this.rank = rank;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    
    public Vector changeToNormalList(){
        Vector list = new Vector();
        list.add(userID);
        list.add(proID);
        list.add(rank);
        list.add(date);
        return list;        
    }
}
