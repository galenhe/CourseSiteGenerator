/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CourseSiteGeneratorData;
import csg.data.Lecture;
import jtps.jTPS_Transaction;

/**
 *
 * @author Galen
 */
public class AddLecture_Transaction implements jTPS_Transaction {
    CourseSiteGeneratorData data;
    Lecture lecture;
    
    public AddLecture_Transaction(CourseSiteGeneratorData initData, Lecture initLecture){
        data = initData;
        lecture = initLecture;
    }
    
    @Override
    public void doTransaction(){
        data.addLecture(lecture);
    }
    
    @Override
    public void undoTransaction(){
        data.removeLecture(lecture);
    }

}

