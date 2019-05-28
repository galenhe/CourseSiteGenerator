package csg.transactions;

import static csg.CourseSiteGeneratorPropertyType.CSG_TAS_TABLE_VIEW;
import csg.data.CourseSiteGeneratorData;
import java.time.LocalDate;
import jtps.jTPS_Transaction;
import csg.data.TeachingAssistantPrototype;
import djf.modules.AppGUIModule;
import javafx.scene.control.TableView;

/**
 *
 * @author McKillaGorilla
 */
public class EditTA_Transaction implements jTPS_Transaction {
    CourseSiteGeneratorData data;
    TeachingAssistantPrototype taToEdit;
    String Name, newName;
    String Email, newEmail;
    String Type, newType;
    
    public EditTA_Transaction(CourseSiteGeneratorData initData, TeachingAssistantPrototype initTAToEdit, 
            String name, String email, String type) {
        this.data = initData;
        taToEdit = initTAToEdit;
        Name = initTAToEdit.getName();
        Email = initTAToEdit.getEmail();
        Type = initTAToEdit.getType();
        newName = name;
        newEmail = email;
        newType = type;
    }


    @Override
    public void doTransaction() {
        data.editTA(taToEdit, newName, newEmail, newType);
        //data.editTest(this.Name);
    }

    @Override
    public void undoTransaction() {
        data.uneditTA(taToEdit, Name, Email, Type);
    }
}