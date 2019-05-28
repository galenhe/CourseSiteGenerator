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
public class Instructor {
    private final StringProperty name;
    private final StringProperty room;
    private final StringProperty email;
    private final StringProperty homePage;
    private final StringProperty officeHours;
    
    public Instructor (String initName, String initEmail, String initRoom, String initHomePage){
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        room = new SimpleStringProperty(initRoom);
        homePage = new SimpleStringProperty(initHomePage);
        officeHours = new SimpleStringProperty("");
    }
    
    public String getName(){
        return name.get();
    }
    
    public String getRoom(){
        return room.get();
    }
    
    public String getEmail(){
        return email.get();
    }
    
    public String getHomePage(){
        return  homePage.get();
    }
    
    public String getOfficeHours(){
        return officeHours.get();
    }
    
    public void setName(String initName){
        name.set(initName);
    }
    
    public void setEmail(String initEmail){
        email.set(initEmail);
    }
    
    public void setRoom(String initRoom){
        room.set(initRoom);
    }
    
    public void setHomePage(String initHomePage){
        homePage.set(initHomePage);
    }
    
    public void setOfficeHours(String initOfficeHours){
        officeHours.set(initOfficeHours);
    }
}
