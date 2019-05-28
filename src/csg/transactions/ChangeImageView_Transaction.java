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
public class ChangeImageView_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    String whichFile;
    String whichButton;
    public ChangeImageView_Transaction(CourseSiteGeneratorData initData, String initWhichFile, String initWhichButton){
        data = initData;
        whichFile = initWhichFile;
        whichButton = initWhichButton;
    }
    
    @Override
    public void doTransaction() {
        data.changeImageView(whichButton, whichFile);
    }

    @Override
    public void undoTransaction() {
        data.unchangeImageView(whichButton, whichFile);
    }
    
}
