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
public class AddComboBoxOption_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    ObservableList<String> list;
    String input;
    ComboBox options;
    public AddComboBoxOption_Transaction(CourseSiteGeneratorData initData, ObservableList<String> initList, String initInput, ComboBox initCB){
        data = initData;
        list = initList;
        input = initInput;
        options = initCB;
    }

    @Override
    public void doTransaction() {
        data.addComboOption(list, input, options);
    }

    @Override
    public void undoTransaction() {
        data.removeComboOption(list, input, options);
    }
    
}
