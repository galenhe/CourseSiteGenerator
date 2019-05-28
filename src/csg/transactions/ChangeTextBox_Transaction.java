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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;
/**
 *
 * @author Galen
 */
public class ChangeTextBox_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    TextArea textField;
    String oldText;
    String newText;
    String whichBox;

    public ChangeTextBox_Transaction(CourseSiteGeneratorData initData, TextArea initTF, String initOldText, String initNewText, String initWhich){
        data = initData;
        textField = initTF;
        oldText = initOldText;
        newText = initNewText;
        whichBox = initWhich;
    }
    
    @Override
    public void doTransaction() {
        data.changeTextArea(data, textField, newText, whichBox);
    }

    @Override
    public void undoTransaction() {
        data.unchangeTextArea(data, textField, oldText, whichBox);
    }
    
    
}
