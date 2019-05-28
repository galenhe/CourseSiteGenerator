/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.foolproof;

import csg.CourseSiteGeneratorApp;
import static csg.CourseSiteGeneratorPropertyType.*;import csg.data.CourseSiteGeneratorData;
import djf.modules.AppGUIModule;
import djf.ui.foolproof.FoolproofDesign;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorFoolproofDesign implements FoolproofDesign{
    CourseSiteGeneratorApp app;
    
    public CourseSiteGeneratorFoolproofDesign(CourseSiteGeneratorApp initApp){
        app = initApp;
    }
    
    @Override
    public void updateControls() {
        updateAddTAFoolproofDesign();
//        updateEditTAFoolproofDesign();
    }
    
    public void updateAddTAFoolproofDesign(){
        AppGUIModule gui = app.getGUIModule();
        TextField nameTF = ((TextField) gui.getGUINode(CSG_TA_NAME_TEXT_FIELD));
        TextField emailTF = ((TextField) gui.getGUINode(CSG_TA_EMAIL_TEXT_FIELD));
        RadioButton allTAButton = ((RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON));
        String name = nameTF.getText();
        String email = emailTF.getText();
        Button addTAButton = ((Button) gui.getGUINode(CSG_ADD_TA_BUTTON));
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        
        boolean isLegal = data.isLegalNewTA(name, email);
        boolean isLegalName = data.isLegalName(name);
        boolean isLegalEmail = data.isLegalNewEmail(email);
        boolean isAllSelected = allTAButton.isSelected();
        if(isLegal){
            nameTF.setOnAction(addTAButton.getOnAction());
            emailTF.setOnAction(addTAButton.getOnAction());
        }
        else{
            nameTF.setOnAction(null);
            emailTF.setOnAction(null);
            nameTF.setStyle("-fx-text-fill: red");
            emailTF.setStyle("-fx-text-fill: red");
        }
        if (isLegalName){
            nameTF.setStyle("-fx-text-fill: black");
        }
        if(isLegalEmail){
            emailTF.setStyle("-fx-text-fill: black");
        }
                
        addTAButton.setDisable(!isLegal);
        if(allTAButton.isSelected()){
            addTAButton.setDisable(true);
        }
    }
    
    
}
