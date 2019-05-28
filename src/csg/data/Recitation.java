/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Galen
 */
public class Recitation {
    private final StringProperty rsection;
    private final StringProperty recDaysAndTime;
    private final StringProperty recRoom;
    private final StringProperty recTA1;
    private final StringProperty recTA2;
    
    public Recitation(){
        rsection = new SimpleStringProperty("??");
        recDaysAndTime = new SimpleStringProperty("??");
        recRoom = new SimpleStringProperty("??");
        recTA1 = new SimpleStringProperty("??");
        recTA2 = new SimpleStringProperty("??");
    }
    
    public Recitation(String initSection, String initDaysAndTime, String initRoom, String initTA1, String initTA2){
        rsection = new SimpleStringProperty(initSection);
        recDaysAndTime = new SimpleStringProperty(initDaysAndTime);
        recRoom = new SimpleStringProperty(initRoom);
        recTA1 = new SimpleStringProperty(initTA1);
        recTA2 = new SimpleStringProperty(initTA2);
    }
    
    public String getRsection(){
        return rsection.get();
    }
    
    public void setRsection(String initSection){
        rsection.set(initSection);
    }
    
    public String getRecDaysAndTime(){
        return recDaysAndTime.get();
    }
    
    public void setRecDaysAndTime(String initDaysAndTime){
        recDaysAndTime.set(initDaysAndTime);
    }
    
    public String getRecRoom(){
        return recRoom.get();
    }
    
    public void setRecRoom(String initRoom){
        recRoom.set(initRoom);
    }
    
    public String getRecTA1(){
        return recTA1.get();
    }
    
    public void setRecTA1(String initTA1){
        recTA1.set(initTA1);
    }
    
    public String getRecTA2(){
        return recTA2.get();
    }
    
    public void setRecTA2(String initTA2){
        recTA2.set(initTA2);
    }
}
