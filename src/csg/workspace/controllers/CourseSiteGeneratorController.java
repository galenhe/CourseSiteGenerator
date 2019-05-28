/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controllers;

import csg.CourseSiteGeneratorApp;
import static csg.CourseSiteGeneratorPropertyType.CSG_FOOLPROOF_SETTINGS;
import static csg.CourseSiteGeneratorPropertyType.*;
import csg.data.CourseSiteGeneratorData;
import csg.data.Instructor;
import csg.data.Lab;
import csg.data.Lecture;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TAType;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.data.TimeSlot.DayOfWeek;
import csg.transactions.AddTA_Transaction;
import csg.transactions.*;
import csg.transactions.EditInstructorHours_Transaction;
import csg.transactions.EditTA_Transaction;
import csg.transactions.ToggleOfficeHours_Transaction;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.dialogs.AppDialogsFacade;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.json.JsonArray;

/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorController {
    CourseSiteGeneratorApp app;
    
    public CourseSiteGeneratorController(CourseSiteGeneratorApp initApp){
        app = initApp;
    }
    
//TAB 1 FUCTIONALITY
    
    public void processChangeComboBox(ComboBox options, String whichList, String newString){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        
        ObservableList<String> list= FXCollections.observableArrayList();
        
        String oldString = "";
        
        if(whichList.equals("semester")){
            System.out.println("Semester ObservableList input");
            list = data.getSemesterOptions();
            oldString = data.getCurrentSemester();
        }
        else if(whichList.equals("year")){
            System.out.println("Year ObservableList input");
            list = data.getYearOptions();
            oldString = data.getCurrentYear();
            System.out.println("oldString is " + oldString);
            System.out.println("newString is " + newString);
        }
        
        if(oldString.equals(newString)){
            System.out.println(oldString + " and " + newString + " are equal!!");
        }
        else{
            System.out.println("Transaction begins");
            EditComboBox_Transaction editCB = new EditComboBox_Transaction(data, options, list, newString, oldString, whichList);
            app.processTransaction(editCB);
        }
    }
    
    public void processChangeTitleTextField(){
        AppGUIModule gui = app.getGUIModule();
        TextField titleTF = (TextField) gui.getGUINode(CSG_TITLE_TEXT_FIELD);
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        
        
        
    }
    
    public void processMakeNewSubjectOption(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        ComboBox subjectsCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        
        ObservableList<String> subjectOptions = data.getSubjectOptions();
        String newInput = subjectsCB.getEditor().getText();
        System.out.println("Input is currently: " +newInput);
        if(!subjectOptions.contains(newInput)){
            System.out.println("does not contain new input. adding now");
            AddComboBoxOption_Transaction addCBOption = new AddComboBoxOption_Transaction(data, subjectOptions, newInput, subjectsCB);
            app.processTransaction(addCBOption);
        }
        else
            System.out.println("Already contains, not adding new");
        
    }
    
    public void processMakeNewNumberOption(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        ComboBox numbersCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        
        ObservableList<String> numberOptions = data.getNumberOptions();
        String newInput = numbersCB.getEditor().getText();
        if(!numberOptions.contains(newInput)){
            System.out.println("does not contain new input. adding now");
            AddComboBoxOption_Transaction addCBOption = new AddComboBoxOption_Transaction(data, numberOptions, newInput, numbersCB);
            app.processTransaction(addCBOption);
        }
        else
            System.out.println("Already contains, not adding new");
        
    }
    
    
    
    public void processChangeExportDir(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        ComboBox subjectCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        ComboBox numberCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        ComboBox semesterCB = (ComboBox) gui.getGUINode(CSG_SEMESTER_COMBO_BOX);
        ComboBox yearCB = (ComboBox) gui.getGUINode(CSG_YEAR_COMBO_BOX);
        
        String subject = subjectCB.getEditor().getText();
        String number = numberCB.getEditor().getText();
        String semester = semesterCB.getValue().toString();
        String year = yearCB.getValue().toString();
        
        String exportDir = ".\\export\\";
        exportDir = exportDir + subject + "_" + number + "_" + semester + "_" + year + "\\public_html";
        
        Label exportDirText = (Label) gui.getGUINode(CSG_EXPORT_DIR_LINK);
        exportDirText.setText(exportDir);
        
        data.setExportDir(exportDir);
        
    }
    
    
//Pages Help Methods
    public void processChangeCheckBox(String whichCheckBox){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        CheckBox homeChB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        CheckBox syllabusChB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        CheckBox scheduleChB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        CheckBox homeworkChB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
        boolean hcbSelected = homeChB.isSelected();
        boolean scbSelected = syllabusChB.isSelected();
        boolean sccbSelected = scheduleChB.isSelected();
        boolean hwcbSelected = homeworkChB.isSelected();
        
        if(whichCheckBox.equals("home")){
            System.out.println(hcbSelected);
            EditCheckBox_Transaction editChB = new EditCheckBox_Transaction(data, homeChB, hcbSelected , "home");
            app.processTransaction(editChB);
        }
        else if(whichCheckBox.equals("syllabus")){
            EditCheckBox_Transaction editChB = new EditCheckBox_Transaction(data, syllabusChB, scbSelected , "syllabus");
            app.processTransaction(editChB);
        }
        else if(whichCheckBox.equals("schedule")){
            EditCheckBox_Transaction editChB = new EditCheckBox_Transaction(data, scheduleChB, sccbSelected , "schedule");
            app.processTransaction(editChB);
        }
        else if(whichCheckBox.equals("homework")){
            EditCheckBox_Transaction editChB = new EditCheckBox_Transaction(data, homeworkChB, hwcbSelected , "homework");
            app.processTransaction(editChB);
        }
        else
            System.out.println("Nothing else");
        
        
        
    }
    
//Style Help methods
    public void processOpenFileChooser(String whichButton){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        FileChooser fileChooser = new FileChooser();
        //File selectedFile = fileChooser.showOpenDialog(null);
        File selectedFile = fileChooser.showOpenDialog(app.getGUIModule().getWindow());
        File workingDir = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDir);
        
        fileChooser.getExtensionFilters().addAll(
            //new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        
        if(selectedFile != null){
            //actionStatus.setText(selectedFile.getName());
            String sourcePath = selectedFile.toURI().toString();
            System.out.println("Source Path is: " + sourcePath);
            String whichFile = "file:" + sourcePath.substring(sourcePath.indexOf("images"));
            System.out.println("The file path is: " + whichFile);
            
            
            //ChangeImageView_Transaction trans = new ChangeImageView_Transaction(data, whichFile, whichButton);
            //app.processTransaction(trans);
            ImageView imageViewToChange = (ImageView) gui.getGUINode(CSG_TEST_IMAGE_VIEW);
            Image currentImage = imageViewToChange.getImage();
            System.out.println("This is the toString: " + currentImage.toString());
            Image x = new Image("file:images/favicon.jpg");
            Image y = new Image(whichFile);
            imageViewToChange.setImage(y);
        }
        else{
            System.out.println("No file chosen");
        }
    }
    
    public void processChangeInstructorName(){
        AppGUIModule gui = app.getGUIModule();
        TextField textField = (TextField) gui.getGUINode(CSG_INSTRUCTOR_NAME_TEXT_FIELD);
        
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        Instructor guy = data.getInstructor();
        
        EditInstructorHours_Transaction editHours = new EditInstructorHours_Transaction(data, guy);
        app.processTransaction(editHours);
        
        //Delete this when transaction works
        data.setInstructorName(textField.getText());
        data.getInstructor().setName(textField.getText());
        System.out.println("Guy's Name has been changed");

        
        Iterator<Lecture> lectureIterator = data.lectureIterator();
        while(lectureIterator.hasNext()){
            Lecture lec = lectureIterator.next();
            System.out.println(lec.getDays());
        }
    }
    public void processChangeInstructorEmail(){
        AppGUIModule gui = app.getGUIModule();
        TextField textField = (TextField) gui.getGUINode(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD);
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        Instructor guy = data.getInstructor();
        
        EditInstructorHours_Transaction editHours = new EditInstructorHours_Transaction(data, guy);
        app.processTransaction(editHours);
        
        //Delete this when transaction works
        data.setIntructorEmail(textField.getText());
        data.getInstructor().setEmail(textField.getText());
        System.out.println("Guy's email has been changed");
    }
    public void processChangeInstructorRoom(){
        AppGUIModule gui = app.getGUIModule();
        TextField textField = (TextField) gui.getGUINode(CSG_INSTRUCTOR_ROOM_TEXT_FIELD);
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        Instructor guy = data.getInstructor();
        
        EditInstructorHours_Transaction editHours = new EditInstructorHours_Transaction(data, guy);
        app.processTransaction(editHours);
        
        //Delete this when transaction works
        data.setInstructorRoom(textField.getText());
        data.getInstructor().setRoom(textField.getText());
        System.out.println("Guy's instructor has been changed");
    }
    public void processChangeInstructorHomePage(){
        AppGUIModule gui = app.getGUIModule();
        TextField textField = (TextField) gui.getGUINode(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD);
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        Instructor guy = data.getInstructor();
        
        EditInstructorHours_Transaction editHours = new EditInstructorHours_Transaction(data, guy);
        app.processTransaction(editHours);
        
        //Delete this when transaction works
        data.setInstructorHomePage(textField.getText());
        data.getInstructor().setHomePage(textField.getText());
        System.out.println("Guy's homepage has been changed");
    }
    
    public void processChangeInstructorHours(){
        AppGUIModule gui = app.getGUIModule();
        TextArea textArea = (TextArea) gui.getGUINode(CSG_INSTRUCTOR_OFFICE_HOURS);
        
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        Instructor guy = data.getInstructor();
        
        EditInstructorHours_Transaction editHours = new EditInstructorHours_Transaction(data, guy);
        app.processTransaction(editHours);
        
        //remove this once do and undo get implmeneted.
        data.getInstructor().setOfficeHours(textArea.getText());
        data.setInstructorHours(textArea.getText());
        System.out.println("Guy's office Hours text has been changed");
        
        ComboBox cb = (ComboBox)gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        cb.getValue();
        //System.out.println(cb.getItems().contains("option 1"));
    }
    
    public void processChangeTitle(String classInitials){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextField titleTF = (TextField) gui.getGUINode(CSG_TITLE_TEXT_FIELD);
        ComboBox subjectCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        ComboBox numberCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        
        String number;
        System.out.println("Title name will be:" + classInitials);
//        if(!numberCB.isFocused()){
//            number = numberCB.getEditor().getText();
//        }
//        else
//            number = numberCB.getValue().toString();
        //number = numberCB.getEditor().getText();
        number = data.getCurrentNumber();
        if(classInitials.equals("CSE")){
            titleTF.setText("Computer Science " + number);
        }
        else if(classInitials.equals("MAT")){
            titleTF.setText("Mathematics " + number);
        }
        else if(classInitials.equals("AMS")){
            titleTF.setText("Applied Math " + number);
        }
        else
            titleTF.setText(subjectCB.getEditor().getText() + " " + number);
        
        data.setTitle(subjectCB.getEditor().getText() + " " + number);
    }
    
    
//TAB 2 FUNCTIONALITY 
    public void changeTextBox(String whichBox){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea description = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        TextArea topics = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        TextArea prereqs = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        TextArea outcomes = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        TextArea textbooks = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        TextArea gradedComponents = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        TextArea specialAssistance = (TextArea) gui.getGUINode(CSG_SPECIAL_ASSISTANCE_TEXT_AREA);
        TextArea academicDishonesty = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        TextArea gradingNote = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        
        if(whichBox.equals("description")){
            String oDesc = data.getDesc();
            String nDesc = description.getText();
            System.out.println("Current description is: "+ nDesc + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, description, oDesc, nDesc, "description");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("topics")){
            String oldString = data.getTopics();
            String newString = topics.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, topics, oldString, newString, "topics");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("prereqs")){
            String oldString = data.getPrereqs();
            String newString = prereqs.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, prereqs, oldString, newString, "prereqs");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("outcomes")){
            String oldString = data.getOutcomes();
            String newString = outcomes.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, outcomes, oldString, newString, "outcomes");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("textbooks")){
            String oldString = data.getTextBooks();
            String newString = textbooks.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, textbooks , oldString, newString, "textbooks");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("gc")){
            String oldString = data.getGradedComponents();
            String newString = gradedComponents.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data,gradedComponents , oldString, newString, "gc");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("gn")){
            String oldString = data.getGradingNote();
            String newString = gradingNote.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data,gradingNote, oldString, newString, "gn");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("ad")){
            String oldString = data.getAcademicDishonesty();
            String newString = academicDishonesty.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, academicDishonesty , oldString, newString, "ad");
            app.processTransaction(changeTB);
        }
        else if(whichBox.equals("sa")){
            String oldString = data.getSpecialAssistance();
            String newString = specialAssistance.getText();
            System.out.println("Current description is: "+ newString + " before the transaction begins");
            
            
            ChangeTextBox_Transaction changeTB = new ChangeTextBox_Transaction(data, specialAssistance , oldString, newString, "sa");
            app.processTransaction(changeTB);
        }
        
    }
    
    public void changeDescription(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea description = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        
        data.setDesc(description.getText());
        
    }
    
    public void changeTopics(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea words = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        
        data.setTopics(words.getText());
        
    }
    
    public void changePrereqs(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea words = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        
        data.setPrereqs(words.getText());
        
    }
    
    public void changeOutcomes(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea outcomes = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        
        data.setOutcomes(outcomes.getText());
        
    }
    
    public void changeTextbooks(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea textbooks = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        
        data.setTextbooks(textbooks.getText());
        
    }
    
    public void changeGradedComponents(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea gradedComponents = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        
        data.setGradedComponents(gradedComponents.getText());
        
    }
    
    public void changeGradingNote(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea gradingNote = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        
        data.setGradingNote(gradingNote.getText());
        
    }
    
    public void changeAcademicDishonesty(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea academicDishonesty = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        data.setAcademicDishonesty(academicDishonesty.getText());
        System.out.println(data.getAcademicDishonesty());
    }
    
    public void changeSpecialAssistance(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TextArea specialAssistance = (TextArea) gui.getGUINode(CSG_SPECIAL_ASSISTANCE_TEXT_AREA);
        
        data.setSpecialAssistance(specialAssistance.getText());
        
    }
    
//TAB 3 FUNCTIONALITY - Meeting Times
    public void processAddNewLecture(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        
        System.out.println("Add Lecture Button pressed - Controller");
        Lecture newLecture = new Lecture();
        AddLecture_Transaction addLecTrans = new AddLecture_Transaction(data, newLecture);
        app.processTransaction(addLecTrans);
        
    }
    
    public void processRemoveLecture(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Lecture>lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        
        System.out.println("Remove Lecture Button pressed - Controller");
        if(lecTV.getSelectionModel().getSelectedItem()!=null){
            System.out.println("Not null");
            Lecture removeThis = lecTV.getSelectionModel().getSelectedItem();
            RemoveLecture_Transaction removeLec = new RemoveLecture_Transaction(data, removeThis);
            app.processTransaction(removeLec);
        }
        else{
            System.out.println("No item selected");
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_LECTURE_SELECTED_TITLE, CSG_NO_LECTURE_SELECTED_CONTENT);
        }
    }
    
    public void processAddRecitation(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        
        System.out.println("Add Rectitation Button pressed - Controller");
        Recitation newRec = new Recitation();
        AddRecitation_Transaction addRecTrans = new AddRecitation_Transaction(data, newRec);
        app.processTransaction(addRecTrans);
    }
    
    public void processRemoveRecitation(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Recitation>recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        
        System.out.println("Remove Rec Button pressed - Controller");
        if(recTV.getSelectionModel().getSelectedItem()!=null){
            System.out.println("Not null");
            Recitation removeThis = recTV.getSelectionModel().getSelectedItem();
            RemoveRecitation_Transaction removeTrans = new RemoveRecitation_Transaction(data, removeThis);
            app.processTransaction(removeTrans);
        }
        else{
            System.out.println("No item selected");
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_RECITATION_SELECTED_TITLE, CSG_NO_RECITATION_SELECTED_CONTENT);
        }
    }
    
    public void processAddLab(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        
        System.out.println("Add Lab Button pressed - Controller");
        Lab newLab = new Lab();
        AddLabs_Transaction addLabTrans = new AddLabs_Transaction(data, newLab);
        app.processTransaction(addLabTrans);
        
    }
    
    public void processRemoveLab(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<Lab>labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        
        System.out.println("Remove Lab Button pressed - Controller");
        
        
        if(labTV.getSelectionModel().getSelectedItem()!=null){
            System.out.println("Not null");
            Lab removeThis = labTV.getSelectionModel().getSelectedItem();
            RemoveLabs_Transaction removeTrans = new RemoveLabs_Transaction(data, removeThis);
            app.processTransaction(removeTrans);
        }
        else{
            System.out.println("No item selected");
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_LAB_SELECTED_TITLE, CSG_NO_LAB_SELECTED_CONTENT);
        }
        
    }
    
    public void processEditTableViewCell(String whichTable, String newText, Object obj, int col){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        
        String oldText = "";
        if(whichTable.equals("lecture")){
            Lecture lec = (Lecture) obj;
            if(col == 0){
                oldText = lec.getSection();
            }
            else if(col == 1){
                oldText = lec.getDays();
            }
            else if(col == 2){
                oldText = lec.getTime();
            }
            else if(col == 3){
                oldText = lec.getRoom();
            }
            
        }
        else if(whichTable.equals("recitation")){
            Recitation rec = (Recitation) obj;
            if(col == 0){
                oldText =  rec.getRsection();
            }
            else if(col == 1){
                oldText = rec.getRecDaysAndTime();
            }
            else if(col == 2){
                oldText = rec.getRecRoom();
            }
            else if(col == 3){
                oldText = rec.getRecTA1();
            }
            else if(col ==4){
                oldText = rec.getRecTA2();
            }
        }
        else if(whichTable.equals("lab")){
            Lab lab = (Lab) obj;
            if(col == 0){
                oldText =  lab.getLsection();
            }
            else if(col == 1){
                oldText = lab.getLdaysAndTime();
            }
            else if(col == 2){
                oldText = lab.getLroom();
            }
            else if(col == 3){
                oldText = lab.getLta1();
            }
            else if(col ==4){
                oldText = lab.getLta2();
            }
        }
        else if(whichTable.equals("schedule")){
            System.out.println("Inside schedule 1");
            ScheduleItem si = (ScheduleItem) obj;
            if(col == 0){
                oldText =  si.getType();
            }
            else if(col == 1){
                oldText = si.getDate();
            }
            else if(col == 2){
                oldText = si.getTitle();
            }
            else if(col == 3){
                oldText = si.getTopic();
            }
        }  
        else
            System.out.println("no table type inputed");
            
        TableViewEdit_Transaction addThis = new TableViewEdit_Transaction(data, whichTable, col, obj, oldText, newText);
        app.processTransaction(addThis);
        
    }
    
//TAB 4 FUNCTIONALITY
    public void processAddTA() {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        TableView rightTable = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        RadioButton ugradButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton gradButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        RadioButton allButton = (RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON);
        TextField nameTF = (TextField) gui.getGUINode(CSG_TA_NAME_TEXT_FIELD);
        Button addTAButton = (Button) gui.getGUINode(CSG_ADD_TA_BUTTON);
        String name = nameTF.getText();
        TextField emailTF = (TextField) gui.getGUINode(CSG_TA_EMAIL_TEXT_FIELD);
        String email = emailTF.getText();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();

        if (data.isLegalNewTA(name, email)) {
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name.trim(), email.trim());
            if(ugradButton.isSelected()){
                ta.setType("Undergraduate");
            }
            else if(gradButton.isSelected()){
                ta.setType("Graduate");
            }
            else
                System.out.println("neither valid button is selected");
            
            AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta);
            app.processTransaction(addTATransaction);

            // NOW CLEAR THE TEXT FIELDS
            nameTF.setText("");
            emailTF.setText("");
            nameTF.requestFocus();
        }
        tasTable.refresh();
        rightTable.refresh();
    }
    
    public void processAddTypeTA(String type) {
        AppGUIModule gui = app.getGUIModule();
        TextField nameTF = (TextField) gui.getGUINode(CSG_TA_NAME_TEXT_FIELD);
        String name = nameTF.getText();
        TextField emailTF = (TextField) gui.getGUINode(CSG_TA_EMAIL_TEXT_FIELD);
        String email = emailTF.getText();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        if (data.isLegalNewTA(name, email)) {
            //nameTF.setStyle("-fx-text-fill: blue");
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name.trim(), email.trim(), type.trim());
            AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta);
            app.processTransaction(addTATransaction);

            // NOW CLEAR THE TEXT FIELDS
            nameTF.setText("");
            emailTF.setText("");
            nameTF.requestFocus();
        }
    }
    

    public void processVerifyTA() {

    }

    public void processToggleOfficeHours() {
        AppGUIModule gui = app.getGUIModule();
        TableView<TimeSlot> officeHoursTableView = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        ObservableList<TablePosition> selectedCells = officeHoursTableView.getSelectionModel().getSelectedCells();
        if (selectedCells.size() > 0) {
            TablePosition cell = selectedCells.get(0);
            int cellColumnNumber = cell.getColumn();
            CourseSiteGeneratorData data = (CourseSiteGeneratorData)app.getDataComponent();
            if (data.isDayOfWeekColumn(cellColumnNumber)) {
                DayOfWeek dow = data.getColumnDayOfWeek(cellColumnNumber);
                TableView<TeachingAssistantPrototype> taTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
                TeachingAssistantPrototype ta = taTableView.getSelectionModel().getSelectedItem();
                if (ta != null) {
                    TimeSlot timeSlot = officeHoursTableView.getSelectionModel().getSelectedItem();
                    ToggleOfficeHours_Transaction transaction = new ToggleOfficeHours_Transaction(data, timeSlot, dow, ta);
                    app.processTransaction(transaction);
                }
                else {
                    Stage window = app.getGUIModule().getWindow();
                    AppDialogsFacade.showMessageDialog(window, CSG_NO_TA_SELECTED_TITLE, CSG_NO_TA_SELECTED_CONTENT);
                }
            }
            int row = cell.getRow();
            cell.getTableView().refresh();
        }
        officeHoursTableView.refresh();
    }

    public void processTypeTA() {
        app.getFoolproofModule().updateControls(CSG_FOOLPROOF_SETTINGS);
    }
    
    public void printAllTas(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        ObservableList<TeachingAssistantPrototype> all = dataManager.getTaList();
        for(TeachingAssistantPrototype ta: all){
            System.out.println(ta.getName().toString() + " is of type " + ta.getType());
        }
        System.out.println("ends here \n");
    }
    
    public void processShowTaTable(String type){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<TeachingAssistantPrototype> leftTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        TableView<TimeSlot> rightTable = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        Button addTa = (Button) gui.getGUINode(CSG_ADD_TA_BUTTON);
        
        RadioButton allButton = (RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON);
        RadioButton ugButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton grButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        
        ObservableList<TeachingAssistantPrototype> all = dataManager.getTaList();
        ObservableList<TeachingAssistantPrototype> ug = FXCollections.observableArrayList();
        ObservableList<TeachingAssistantPrototype> gr = FXCollections.observableArrayList();
        //gr = FXCollections.sort();
        if(type.equals("all")){
            addTa.setDisable(ENABLED);
            System.out.println(""); 
            System.out.println("List of all TAs");
            for(TeachingAssistantPrototype ta: all){
                System.out.println(ta.getName());
            }
            
            leftTable.setItems(all);
            
            Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
            while (timeSlotsIterator.hasNext()) {
                TimeSlot timeSlot = timeSlotsIterator.next();
                for (int i = 0; i < DayOfWeek.values().length; i++) {
                    DayOfWeek dow = DayOfWeek.values()[i];
                    if(timeSlot.getDayText().get(dow) != null){
                        StringProperty names = timeSlot.getDayText().get(dow);
                        names.setValue("");
                        ArrayList<TeachingAssistantPrototype> tas = timeSlot.getBackEndTa().get(dow);
                        
                        for(TeachingAssistantPrototype ta: tas){
                            if(all.contains(ta)){
                                names.setValue(names.getValue()+ta.getName());
                            }
                        }
                    }
                    else
                        continue;
                }
            }
            leftTable.refresh();
        }
        else if(type.equals("ug")){
            addTa.setDisable(false);
            processAddTA();
            //for(TeachingAssistantPrototype ta: taTableView.getItems().){
            for(TeachingAssistantPrototype ta: all){
                if(ta.getType().equals("Undergraduate")){
                    System.out.println(ta.getName() + " is Undergraduate");
                    ug.add(ta);
                }
            }
            leftTable.setItems(ug);
            leftTable.refresh();
            
            Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
            while (timeSlotsIterator.hasNext()) {
                TimeSlot timeSlot = timeSlotsIterator.next();
                for (int i = 0; i < DayOfWeek.values().length; i++) {
                    DayOfWeek dow = DayOfWeek.values()[i];
                    if(timeSlot.getDayText().get(dow) != null){
                        StringProperty names = timeSlot.getDayText().get(dow);
                        names.setValue("");
                        ArrayList<TeachingAssistantPrototype> tas = timeSlot.getBackEndTa().get(dow);
                        
                        for(TeachingAssistantPrototype ta: tas){
                            if(ug.contains(ta)){
                                names.setValue(names.getValue()+ta.getName());
                            }
                        }
                    }
                    else
                        continue;
                }
            }
            leftTable.refresh();
            rightTable.refresh();
        }
        else if(type.equals("gr")){
            addTa.setDisable(false);
            processAddTA();
            for(TeachingAssistantPrototype ta: all){
                if(ta.getType().equals("Graduate")){
                    gr.add(ta);
                    System.out.println(ta.getName() + " is Graduate");
                }
            }
            leftTable.setItems(gr);
            leftTable.refresh();
            System.out.println("\n");
            
            
            //FIXING THE RIGHT TABLE SO IT MATCHES WHATEVER IS IN THE RIGHT TABLE
            Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
            while (timeSlotsIterator.hasNext()) {
                TimeSlot timeSlot = timeSlotsIterator.next();
                for (int i = 0; i < DayOfWeek.values().length; i++) {
                    DayOfWeek dow = DayOfWeek.values()[i];
                    if(timeSlot.getDayText().get(dow) != null){
                        StringProperty names = timeSlot.getDayText().get(dow);
                        names.setValue("");
                        ArrayList<TeachingAssistantPrototype> tas = timeSlot.getBackEndTa().get(dow);
                        
                        for(TeachingAssistantPrototype ta: tas){
                            if(gr.contains(ta)){
                                names.setValue(names.getValue()+ta.getName());
                            }
                        }
                    }
                    else
                        continue;
                }
            }
            //gr = FXCollections.sort;
            leftTable.refresh();
        }
    }
    
    public void processHighlightNames(){
        AppGUIModule gui = app.getGUIModule();
        TableView<TeachingAssistantPrototype> leftTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        TeachingAssistantPrototype taInCell = leftTable.getSelectionModel().getSelectedItem();
        
        TableView<TimeSlot> rightTable = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);

        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData)app.getDataComponent();
        //Iterator<TeachingAssistantPrototype> tasIterator = dataManager.teachingAssistantsIterator();
        rightTable.getSelectionModel().clearSelection();
        if (taInCell != null) {
            String taName = taInCell.getName();
            System.out.println(taName);
            
            int timeSlotNumber = 0;
            Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
            while (timeSlotsIterator.hasNext()) {
                TimeSlot timeSlot = timeSlotsIterator.next();
                for (int i = 0; i < DayOfWeek.values().length; i++) {
                    DayOfWeek dow = DayOfWeek.values()[i];
                    if(timeSlot.getDayText().get(dow) != null){
                        StringProperty names = timeSlot.getDayText().get(dow);
                        if(names.get().toLowerCase().matches(taName.toLowerCase())){
                            //System.out.println(dow + "contains");
                            //THIS IS THE CELL THAT SHOULD BE COLLORED
                            //names.setValue("lalala");
                            rightTable.getSelectionModel().select(timeSlotNumber, rightTable.getColumns().get(i+2));
                        }
                        else{
                            //System.out.println(dow + " does not contain " +taName);
                        }
                    }
                    else
                        continue;
                }
                timeSlotNumber++;
            }
        rightTable.refresh();
        }
        else {
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_TA_SELECTED_TITLE, CSG_NO_TA_SELECTED_CONTENT);
        }
    }
    
    
    public void processEdit(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView<TeachingAssistantPrototype> leftTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        TableView<TimeSlot> rightTable = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        TeachingAssistantPrototype taInCell = leftTable.getSelectionModel().getSelectedItem();
        String taName = taInCell.getName();
        String taEmail = taInCell.getEmail();
        String taType = taInCell.getType();
        
        Dialog<ButtonType> editTA = new Dialog<>();
        editTA.setTitle("Edit " + taName);
        editTA.setHeaderText("Edit " +taName);
        
        ButtonType makeChanges = new ButtonType("MAKE CHANGES", ButtonBar.ButtonData.OK_DONE);
        editTA.getDialogPane().getButtonTypes().addAll(makeChanges, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        TextField newName = new TextField();
        newName.setPromptText("Name");
        newName.setText(taName);
        TextField newEmail = new TextField();
        newEmail.setPromptText("Email");
        newEmail.setText(taEmail);
        
        
        final ToggleGroup group = new ToggleGroup();
        RadioButton ug = new RadioButton();
        ug.setText("Undergraduate");
        ug.setToggleGroup(group);
        RadioButton gr = new RadioButton();
        gr.setText("Graduate");
        gr.setToggleGroup(group);
        //RadioButton third = new RadioButton("Example");  // Alternative way of declaring button
        if(taType.equals("Undergraduate")){
            ug.setSelected(ENABLED);
            ug.requestFocus();
        }
        else{
            gr.setSelected(ENABLED);
            gr.requestFocus();
        }

        grid.add(new Label("Name: "),0,0);
        grid.add(newName, 1, 0);
        grid.add(new Label("Email: "),0,1);
        grid.add(newEmail, 1, 1);
        grid.add(new Label("Type: "), 0, 2);
        grid.add(ug, 1, 2);
        grid.add(gr, 2, 2);

       
        //Node editButton = editTA.getDialogPane().lookupButton(ButtonType.CLOSE);
        //editButton.setDisable(true);
        
        editTA.getDialogPane().setContent(grid);

        newName.textProperty().addListener(e ->{
            processTypeTA();
        });
        newEmail.textProperty().addListener(e ->{
            processTypeTA();
        });
        
        /**
         * 
         * ADD RED DESIGN IF NOT VALID NAME AND EMAIL
         */
        
        String type = "";
        Optional<ButtonType> result = editTA.showAndWait();
        if(result.get()==makeChanges){
            System.out.println("Make Changes Selected");
            System.out.println("Name before change: " + taInCell.getName());
            //taInCell.setName(newName.getText());
            System.out.println("Name after: " + taInCell.getName());
            //taInCell.setEmail(newEmail.getText());
            if(ug.isSelected()){
                System.out.println("Undergrad selected");
                //taInCell.setType("Undergraduate");
                type = "Undergraduate";
            }
            else if(gr.isSelected()){
                System.out.println("Grad selected");
                //taInCell.setType("Graduate");
                type = "Graduate";
            }
        }
        else{
            System.out.println("Canceled Changes");
        }
        EditTA_Transaction editTATransaction = new EditTA_Transaction(data, taInCell, newName.getText(), newEmail.getText(), type);
        app.processTransaction(editTATransaction);
        leftTable.refresh();
        rightTable.refresh();
    }
   
    public void processChangeEndTimeOptions(){
        
    }
    
    public void processTimeOptionsFoolProof(){
        
    }
    
    public void processChangeHoursDisplay(){
        AppGUIModule gui = app.getGUIModule();
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        
        ComboBox startTimeCB = (ComboBox) gui.getGUINode(CSG_OFFICE_HOURS_START_TIME_COMBO_BOX);
        ComboBox endTimeCB = (ComboBox) gui.getGUINode(CSG_OFFICE_HOURS_END_TIME_COMBO_BOX);
        TableView<TimeSlot> ohTable = (TableView<TimeSlot>) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        
        ObservableList<TimeSlot> allHours = data.getOfficeHours();
        ObservableList<TimeSlot> willShow = FXCollections.observableArrayList();
        
        String selectedStartTime = startTimeCB.getValue().toString();
        String selectedEndTime = endTimeCB.getValue().toString();
        String[] startTimeParts = selectedStartTime.split(":");
        String[] endTimeParts = selectedEndTime.split(":");
        
        int startTime = Integer.parseInt(startTimeParts[0]);
        int endTime = Integer.parseInt(endTimeParts[0]);
          
        String startHour = startTimeParts[1].substring(startTimeParts[1].length()-2);
        if(startHour.equals("pm"))
            startTime = startTime+12;
        if(endTimeParts[1].substring(endTimeParts[1].length()-2).equals("pm"))
            endTime = endTime+12;
        
        Iterator<TimeSlot> aH = allHours.iterator();
        while(aH.hasNext()){
            TimeSlot ts = aH.next();
            String[] tsComponents = ts.getStartTime().split(":");
            int tsStartTime = Integer.parseInt(tsComponents[0]);
            
            String tsComponentsAmPM = tsComponents[1].substring(tsComponents[1].length()-2);
            if(tsComponentsAmPM.equals("pm")){
                tsStartTime = tsStartTime+12;
            }
            System.out.println("Selected start is: " + startTime + ". Selected end is: " + endTime + ". Timeslot's time is; " + tsStartTime);
            if(startTime<=tsStartTime && tsStartTime<=endTime-1){
                System.out.println("TimeSlot is valid");
                System.out.println(tsStartTime);
                willShow.add(ts);
            }
            else
                System.out.println("Time slot is not valid");
            
        }
        
        data.setSelectedHours(willShow);
        ohTable.setItems(willShow);
    }

    public void processRemoveTA() {
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        AppGUIModule gui = app.getGUIModule();
        
        TableView tasTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTable.getSelectionModel().getSelectedItem();
        RemoveTa_Transaction trans = new RemoveTa_Transaction(data, ta);
        app.processTransaction(trans);
    }

    public void checkSubjectSelection() {
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        System.out.println(data.selectedSubject());
        System.out.println("Hello- made it here");
    }

    
// Tab 5 Functionality
    public void processAddUpdate(){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        AppGUIModule gui = app.getGUIModule();
        
        ComboBox typeCB = (ComboBox) gui.getGUINode(CSG_ADD_EDIT_TYPE_COMBO_BOX);
        DatePicker dateDP = (DatePicker) gui.getGUINode(CSG_ADD_EDIT_DATE_DATE_PICKER);
        TextField titleTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_TITLE_TEXT_FIELD);
        TextField topicTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_TOPIC_TEXT_FIELD);
        TextField linkTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_LINK_TEXT_FIELD);
        
        String type = typeCB.getValue().toString();
        LocalDate date = dateDP.getValue();
        String title = titleTF.getText();
        String topic = topicTF.getText();
        String link = linkTF.getText();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String scheduleDate = month+"/" + day + "/" + year;
        
        ScheduleItem addThis = new ScheduleItem(type, scheduleDate,title,topic);
        
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        Iterator<ScheduleItem> scheduleItems = data.scheduleItemsIterator();
        while(scheduleItems.hasNext()){
            ScheduleItem si = scheduleItems.next();
            System.out.println(si.getTitle());
        }
                    
        ObservableList<ScheduleItem> scheduleList = data.getScheduleItems();
        if(scheduleList.contains(addThis)){
            System.out.println("Already exists");
        }
        else{
            AddScheduleItem_Transaction addSchedule = new AddScheduleItem_Transaction(data, addThis);
            app.processTransaction(addSchedule);
            
            titleTF.setText("");
            topicTF.setText("");
            linkTF.setText("");
            titleTF.requestFocus();
        }
    }
    
    public void processClearTextFields(){
        AppGUIModule gui = app.getGUIModule();
        
        TextField titleTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_TITLE_TEXT_FIELD);
        TextField topicTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_TOPIC_TEXT_FIELD);
        TextField linkTF = (TextField) gui.getGUINode(CSG_ADD_EDIT_LINK_TEXT_FIELD);
        
        titleTF.setText("");
        topicTF.setText("");
        linkTF.setText("");
        titleTF.requestFocus();
    }
    
    public void processRemoveScheduleItem(){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        AppGUIModule gui = app.getGUIModule();
        
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        System.out.println("Remove Schedule Item Buttton Pressed");
        
        if(scheduleTV.getSelectionModel().getSelectedItem() != null){
            ScheduleItem removeItem = scheduleTV.getSelectionModel().getSelectedItem();
            RemoveScheduleItem_Transaction removeScheduleItem = new RemoveScheduleItem_Transaction(data, removeItem);
            app.processTransaction(removeScheduleItem);
        }
        else{
            System.out.println("No item selected");
            Stage window = app.getGUIModule().getWindow();
            AppDialogsFacade.showMessageDialog(window, CSG_NO_SCHEDULE_ITEM_SELECTED_TITLE, CSG_NO_SCHEDULE_ITEM_SELECTED_CONTENT);
        }
        
        
    }
    
}
