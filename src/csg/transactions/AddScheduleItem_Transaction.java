/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;


import csg.data.CourseSiteGeneratorData;
import csg.data.ScheduleItem;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;
/**
 *
 * @author Galen
 */
public class AddScheduleItem_Transaction implements jTPS_Transaction {
    CourseSiteGeneratorData data;
    ScheduleItem scheduleItem;
    
    public AddScheduleItem_Transaction(CourseSiteGeneratorData initData, ScheduleItem initItem){
        data = initData; 
        scheduleItem = initItem;
    }
    
    
    
    @Override
    public void doTransaction() {
        data.addScheduleItem(scheduleItem);
    }

    @Override
    public void undoTransaction() {
        data.removeScheduleItem(scheduleItem);
    }
    
}
