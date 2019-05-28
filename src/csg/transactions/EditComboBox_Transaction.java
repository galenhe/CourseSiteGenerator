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
public class EditComboBox_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data; 
    ComboBox options;
    ObservableList<String> list;
    String oldString;
    String newString;
    String whichList;
    
    public EditComboBox_Transaction(CourseSiteGeneratorData initData, ComboBox initOptions, ObservableList<String> initList, String initNew, String initOld, String initWhichList){
        data = initData;
        options = initOptions;
        list = initList;
        oldString = initOld;
        newString = initNew;
        whichList = initWhichList;
    }
    
    
    @Override 
    public void doTransaction(){
        data.changeComboBox(options, newString, whichList);
    }
    
    @Override
    public void undoTransaction(){
        data.unchangeComboBox(options, oldString, whichList);
    }
}
