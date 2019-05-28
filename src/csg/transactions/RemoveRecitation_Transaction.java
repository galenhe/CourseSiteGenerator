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
public class RemoveRecitation_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    Recitation rec;
    public RemoveRecitation_Transaction(CourseSiteGeneratorData initData, Recitation initRec){
        this.data = initData;
        this.rec= initRec;
    }
    
    @Override
    public void doTransaction(){
        data.removeRecitation(rec);
    }
    
    @Override
    public void undoTransaction(){
        data.addRecitation(rec);
    }
}
