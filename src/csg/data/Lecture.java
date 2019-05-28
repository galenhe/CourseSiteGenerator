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
public class Lecture {
    private final StringProperty section;
    private final StringProperty days;
    private final StringProperty time;
    private final StringProperty room;
    
    public Lecture(){
        section = new SimpleStringProperty("??");
        days = new SimpleStringProperty("??");
        time = new SimpleStringProperty("??");
        room = new SimpleStringProperty("??");
    }
    
    public Lecture(String initSection, String initDays, String initTime, String initRoom){
        section = new SimpleStringProperty(initSection);
        days = new SimpleStringProperty(initDays);
        time = new SimpleStringProperty(initTime);
        room = new SimpleStringProperty(initRoom);
    }
    
    public String getSection(){
        return section.get();
    }
    
    public void setSection(String initSection){
        this.section.set(initSection);
    }
    
    public String getDays(){
        return days.get();
    }
    
    public void setDays(String initDays){
        this.days.set(initDays);
    }
    
    public String getTime(){
        return time.get();
    }
    
    public void setTime(String initTime){
        this.time.set(initTime);
    }
    
    public String getRoom(){
        return room.get();
    }
    
    public void setRoom(String initRoom){
        this.room.set(initRoom);
    }
}
