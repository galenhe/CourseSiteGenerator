/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CourseSiteGeneratorData;
import javafx.scene.control.CheckBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author Galen
 */
public class EditCheckBox_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    boolean isSelected;
    String whichCheckBox;
    CheckBox checkBox;
    
    public EditCheckBox_Transaction(CourseSiteGeneratorData initData, CheckBox initCheckBox, boolean initIsSelected, String initWhichCheckBox){
        data = initData;
        isSelected = initIsSelected;
        whichCheckBox = initWhichCheckBox;
        checkBox = initCheckBox;
    }

    @Override
    public void doTransaction() {
        if(!isSelected){
            data.uncheckBox(checkBox, whichCheckBox);
        }
        else if(isSelected){
            data.checkBox(checkBox, whichCheckBox);
        }
        
    }

    @Override
    public void undoTransaction() {
        if(isSelected){
            data.uncheckBox(checkBox, whichCheckBox);
        }
        else if(!isSelected){
            data.checkBox(checkBox, whichCheckBox);
        }
        
    }
    
    
}
