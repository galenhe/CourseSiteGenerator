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
public class TableViewEdit_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    int column;
    String oldString;
    String newString;
    String whichTV;
    Object obj;
    
    public TableViewEdit_Transaction(CourseSiteGeneratorData initData, String initWhichTV, int initColumn, Object initObj, String initOldString, String initNewString){
        data = initData;
        column = initColumn;
        oldString = initOldString;
        newString = initNewString;
        whichTV = initWhichTV;
        obj = initObj;
    }

    @Override
    public void doTransaction() {
        data.editTableViewCell(newString, whichTV, obj, column);
    }

    @Override
    public void undoTransaction() {
        data.uneditTableViewCell(oldString, whichTV, obj,column);
    }
    
}
