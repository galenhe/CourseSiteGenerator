/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.clipboard;

import csg.CourseSiteGeneratorApp;
import static csg.CourseSiteGeneratorPropertyType.*;
import csg.data.CourseSiteGeneratorData;
import static djf.AppPropertyType.PASTE_BUTTON;
import djf.components.AppClipboardComponent;
import djf.modules.AppGUIModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import csg.data.TeachingAssistantPrototype;
import csg.transactions.AddTA_Transaction;
import csg.transactions.Cut_Transaction;

/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorClipboard implements AppClipboardComponent {
    CourseSiteGeneratorApp app;
    ArrayList<TeachingAssistantPrototype> clipboardCutItems;
    ArrayList<TeachingAssistantPrototype> clipboardCopiedItems;
    
    public CourseSiteGeneratorClipboard(CourseSiteGeneratorApp initApp) {
        app = initApp;
//        clipboardCutItems = null;
//        clipboardCopiedItems = null;
        clipboardCutItems = new ArrayList<TeachingAssistantPrototype>();
        clipboardCopiedItems = new ArrayList<TeachingAssistantPrototype>();
    }
    
    @Override
    public void cut() {
        System.out.println("Cut Selected");
        AppGUIModule gui = app.getGUIModule();
        TableView tasTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        
        RadioButton ugradButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton gradButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        RadioButton allButton = (RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON);
        Button pasteButton = (Button) gui.getGUINode(PASTE_BUTTON);
        if(ugradButton.isSelected() || gradButton.isSelected()){
            pasteButton.setDisable(true);
        }
        else if(allButton.isSelected()){
            pasteButton.setDisable(false);
        }
        
        
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        ObservableList<TeachingAssistantPrototype> all = dataManager.getTaList();
        
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTableView.getSelectionModel().getSelectedItem();
        System.out.println(ta.getName() + " has been cut");
        
        
        if (ta != null) {
            if(this.clipboardCutItems.isEmpty()){
                System.out.println("Cut clipboard is empty");
            }
            else{
                System.out.println("Cut clipboard not empty. Emptying now");
                clipboardCutItems.remove(0);
            }
            if(!this.clipboardCopiedItems.isEmpty()){
                System.out.println("Other Clipboard not empty. Removing first item in it");
                clipboardCopiedItems.remove(0);
            }
            else
                System.out.println("Other Clipboard is empty. Do not need to empty it");
            
            this.clipboardCutItems.add(ta);
            
            //CLIPBOARD EDIT FOR CUT DATA TRASNACTION;; MAKE IT THE CUT TA TRANSACTION BUT WHISCH THE ADD AND REMOVE TA METHODS SO IT IS OPPOSE OF THE ADDTATRACTION CLASS
            all.remove(ta);
            //dataManager.removeTA(ta);
            Cut_Transaction addCutTransaction = new Cut_Transaction(dataManager, ta);
            app.processTransaction(addCutTransaction);
            
            
            System.out.println("TA in clipboard is: " + clipboardCutItems.get(0).getName());
            tasTableView.refresh();
        }
        else {
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_TA_SELECTED_TITLE, CSG_NO_TA_SELECTED_CONTENT);
        }
        System.out.println("");
    }

    @Override
    public void copy() {
        System.out.println("Copy Selected");
        AppGUIModule gui = app.getGUIModule();
        TableView tasTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        
        RadioButton ugradButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton gradButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        RadioButton allButton = (RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON);
        Button pasteButton = (Button) gui.getGUINode(PASTE_BUTTON);
        if(ugradButton.isSelected() || gradButton.isSelected()){
            pasteButton.setDisable(true);
        }
        else if (allButton.isSelected()){
            pasteButton.setDisable(false);
        }
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        ObservableList<TeachingAssistantPrototype> all = dataManager.getTaList();
        
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTableView.getSelectionModel().getSelectedItem();
        System.out.println(ta.getName() + "WILL BE COPIED");
        
        if (ta != null) {
            if(this.clipboardCopiedItems.isEmpty()){
                System.out.println("Copy clipboard is empty");
            }
            else{
                System.out.println("Copy clipboard not empty. Emptying now");
                clipboardCopiedItems.remove(0);
            }
            
            if(!this.clipboardCutItems.isEmpty()){
                System.out.println("Other Clipboard not empty. Removing first item in it");
                clipboardCutItems.remove(0);
            }
            else
                System.out.println("Other Clipboard is empty. Do not need to empty it");
            
            this.clipboardCopiedItems.add(ta);
            System.out.println("TA in clipboard is: " + clipboardCopiedItems.get(0).getName());
            
            tasTableView.refresh();
        }
        else {
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_TA_SELECTED_TITLE, CSG_NO_TA_SELECTED_CONTENT);
        }
        
        System.out.println("");
    }
    
    @Override
    public void paste() {
        System.out.println("Paste selected");
        AppGUIModule gui = app.getGUIModule();
        TableView tasTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        ObservableList<TeachingAssistantPrototype> all = dataManager.getTaList();
        
        RadioButton ugradButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton gradButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        
        if(hasSomethingToPaste()){
            System.out.println("has something to paste");
            String name = "";
            String email = "";
            String type = "";
            
            if(!clipboardCopiedItems.isEmpty()){
                System.out.println("Made it to A");
                TeachingAssistantPrototype ta = clipboardCopiedItems.get(0);
                name = ta.getName();
                email = ta.getEmail();
                type = ta.getType();
            }
            else if(!clipboardCutItems.isEmpty()){
                System.out.println("Made it to B");
                TeachingAssistantPrototype ta = clipboardCutItems.get(0);
                name = ta.getName();
                email = ta.getEmail();
                type = ta.getType();
            }
        
            CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
            if (data.isLegalNewTA(name, email)) {
                System.out.println("legal");
                TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name.trim(), email.trim());
                ta.setType(type);
                if(ugradButton.isSelected()){
                    System.out.println("Undergraduate button is selected. Pasted TA is type undergraduate");
                    ta.setType("Undergraduate");
                }
                else if(gradButton.isSelected()){
                    System.out.println("Graduate button is selected. Pasted TA is type graduate");
                    ta.setType("Graduate");
                }
                else
                    System.out.println("buttons: grad/ugrad not selected. All selected");
                
                AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta);
                app.processTransaction(addTATransaction);
                System.out.println("Should have been added");
            }
            else{
                int multiplePaste = 1;
                System.out.println("Invalid new TA.");
                boolean legal = false;
                String originalName = new String();
                originalName = name;
                String originalEmail = new String();
                originalEmail = email;
                String endName = "";
                String incrementedEmail = "";
                while(legal == false){
                    System.out.println("Already have same name and email. Incrementing to: " +multiplePaste+1);
                    endName = originalName + "." + multiplePaste;
                    String parts[] = originalEmail.split("@");
                    for(int i=0; i<parts.length; i++){
                        System.out.println(parts[i]);
                    }
                    String changeEmail = parts[0] + "." + multiplePaste;
                    parts[0] = changeEmail;
                    incrementedEmail = String.join("@", parts);
                    
                    if(data.isLegalName(endName) && data.isLegalNewEmail(incrementedEmail)){
                        legal = true; 
                    }
                    else
                        multiplePaste += 1; 
                    System.out.println("");
                }
                
                TeachingAssistantPrototype ta = new TeachingAssistantPrototype(endName.trim(), incrementedEmail.trim());
                ta.setType(type);
                
                AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta);
                app.processTransaction(addTATransaction);
            }
            tasTableView.refresh();
        }
        else 
        {
            System.out.println("neither selected. Check agian");
            Button pasteButton = (Button) gui.getGUINode(PASTE_BUTTON);
            pasteButton.setDisable(true);
        }
        
        //all.sort((a, b) -> TeachingAssistantPrototype.compare(b.getName(), a.getName()));

        System.out.println("");
    }    


    @Override
    public boolean hasSomethingToCut() {
        return ((CourseSiteGeneratorData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToCopy() {
        return ((CourseSiteGeneratorData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToPaste() {
        if ((clipboardCutItems != null) && (!clipboardCutItems.isEmpty()))
            return true;
        else if ((clipboardCopiedItems != null) && (!clipboardCopiedItems.isEmpty()))
            return true;
        else
            return false;
    }
    
}
