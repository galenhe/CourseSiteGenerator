/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import jtps.jTPS_Transaction;
import csg.data.CourseSiteGeneratorData;
import csg.data.TeachingAssistantPrototype;

/**
 *
 * @author Galen
 */
public class Cut_Transaction implements jTPS_Transaction {
    CourseSiteGeneratorData data;
    TeachingAssistantPrototype ta;
    
    public Cut_Transaction(CourseSiteGeneratorData initData, TeachingAssistantPrototype initTA){
        data = initData;
        ta = initTA;
    }
    
    @Override
    public void doTransaction() {
        data.removeTA(ta);        
    }

    @Override
    public void undoTransaction() {
        data.addTA(ta);
    }
}
