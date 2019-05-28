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
public class Syllabus {
    private final StringProperty description;
    private final StringProperty topics;
//    private final StringProperty prerequisites;
//    private final StringProperty outcomes;
//    private final StringProperty textbooks;
//    private final StringProperty gradedComponents;
//    private final StringProperty gradingNotes;
//    private final StringProperty academicDishonesty;
//    private final StringProperty specialAssistance;
    
    public Syllabus (String initDescription, String initTopics){
        description = new SimpleStringProperty(initDescription);
        topics = new SimpleStringProperty(initTopics);
    }
    
    private String getDesc(){
        return description.get();
    }
    
    private void setDesc(String initDesc){
        description.set(initDesc);
    }
}
