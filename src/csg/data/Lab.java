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
public class Lab {
    private final StringProperty lsection;
    private final StringProperty ldaysAndTime;
    private final StringProperty lroom;
    private final StringProperty lta1;
    private final StringProperty lta2;
    
    public Lab(){
        lsection = new SimpleStringProperty("??");
        ldaysAndTime = new SimpleStringProperty("??");
        lroom = new SimpleStringProperty("??");
        lta1 = new SimpleStringProperty("??");
        lta2 = new SimpleStringProperty("??");
    }
    
    public Lab(String initSection, String initDaysAndTime, String initRoom, String initTA1, String initTA2){
        lsection = new SimpleStringProperty(initSection);
        ldaysAndTime = new SimpleStringProperty(initDaysAndTime);
        lroom = new SimpleStringProperty(initRoom);
        lta1 = new SimpleStringProperty(initTA1);
        lta2 = new SimpleStringProperty(initTA2);
    }
    
    public String getLsection(){
        return lsection.get();
    }
    
    public void setSection(String initSection){
        lsection.set(initSection);
    }
    
    public String getLdaysAndTime(){
        return ldaysAndTime.get();
    }
    
    public void setDaysAndTime(String initDaysAndTime){
        ldaysAndTime.set(initDaysAndTime);
    }
    
    public String getLroom(){
        return lroom.get();
    }
    
    public void setRoom(String initRoom){
        lroom.set(initRoom);
    }
    
    public String getLta1(){
        return lta1.get();
    }
    
    public void setlta1(String initTA1){
        lta1.set(initTA1);
    }
    
    public String getLta2(){
        return lta2.get();
    }
    
    public void setTA2(String initTA2){
        lta2.set(initTA2);
    }
    
    
}
