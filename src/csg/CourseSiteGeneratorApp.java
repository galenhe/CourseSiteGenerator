/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import djf.AppTemplate;
import djf.components.AppClipboardComponent;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import java.util.Locale;
import csg.data.CourseSiteGeneratorData;
import csg.files.CourseSiteGeneratorFiles;
import csg.clipboard.CourseSiteGeneratorClipboard;
import csg.workspace.CourseSiteGeneratorWorkspace;
import static javafx.application.Application.launch;

/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorApp extends AppTemplate {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
    @Override
    public AppClipboardComponent buildClipboardComponent(AppTemplate app) {
        return new CourseSiteGeneratorClipboard(this);
    }

    @Override
    public AppDataComponent buildDataComponent(AppTemplate app) {
        return new CourseSiteGeneratorData(this);
    }

    @Override
    public AppFileComponent buildFileComponent() {
        return new CourseSiteGeneratorFiles(this);
    }

    @Override
    public AppWorkspaceComponent buildWorkspaceComponent(AppTemplate app) {
        return new CourseSiteGeneratorWorkspace(this);        
    }
    
}
