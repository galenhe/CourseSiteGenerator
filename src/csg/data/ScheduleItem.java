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
public class ScheduleItem {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    
    public ScheduleItem(String initType, String initDate, String initTitle, String initTopic){
        type = new SimpleStringProperty(initType);
        date = new SimpleStringProperty(initDate);
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
        link = new SimpleStringProperty("");
    }
    
    public void setType(String initType){
        this.type.set(initType);
    }
    
    public String getType(){
        return this.type.get();
    }
    
    public void setDate(String initDate){
        this.date.set(initDate);
    }
    
    public String getDate(){
        return this.date.get();
    }
    
    public void setTitle(String initTitle){
        this.title.set(initTitle);
    }
    
    public String getTitle(){
        return this.title.get();
    }
    
    public void setTopic(String initTopic){
        this.topic.set(initTopic);
    }
    
    public String getTopic(){
        return this.topic.get();
    }
    
    public void setLink(String initLink){
        this.link.set(initLink);
    }
    
    public String getLink(){
        return this.link.get();
    }
}
