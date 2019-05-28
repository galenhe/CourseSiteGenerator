/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import jtps.jTPS_Transaction;
import csg.data.CourseSiteGeneratorData;
import csg.data.Instructor;
/**
 *
 * @author Galen
 */
public class EditInstructorHours_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorData data;
    Instructor guy;
    
    public EditInstructorHours_Transaction(CourseSiteGeneratorData initData, Instructor initInstructor){
        data = initData;
        guy = initInstructor;
    }
    
    @Override
    public void doTransaction(){
        data.changeInstructorHours();
    }
    
    @Override
    public void undoTransaction(){
        data.unchangeInstructorHours();
    }
}
