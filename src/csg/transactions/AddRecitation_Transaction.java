/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CourseSiteGeneratorData;
import csg.data.Recitation;
import jtps.jTPS_Transaction;
/**
 *
 * @author Galen
 */
public class AddRecitation_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    Recitation recitation;
    
    public AddRecitation_Transaction(CourseSiteGeneratorData initData, Recitation initRec){
        data = initData;
        recitation = initRec;
    }
    
    @Override
    public void doTransaction(){
        data.addRecitation(recitation);
    }
    
    @Override
    public void undoTransaction(){
        data.removeRecitation(recitation);
    }
}
