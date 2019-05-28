/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;
import csg.data.CourseSiteGeneratorData;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author Galen
 */
public class ChangeHoursDisplay_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    String newTime;
    public ChangeHoursDisplay_Transaction(CourseSiteGeneratorData initData, String initNewTime){
        data = initData;
        newTime  = initNewTime;
    }
    @Override
    public void doTransaction() {
        data.changeHoursDisplay();
    }

    @Override
    public void undoTransaction() {
        data.unchangeHoursDisplay();
    }
    
}
