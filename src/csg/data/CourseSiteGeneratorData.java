/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CourseSiteGeneratorApp;
import csg.data.TimeSlot.DayOfWeek;
import djf.components.AppDataComponent;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import static csg.CourseSiteGeneratorPropertyType.*;
import static csg.data.TAType.Graduate;
import csg.workspace.controllers.CourseSiteGeneratorController;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorData implements AppDataComponent {
    CourseSiteGeneratorApp app;
    
//TAB 1 DATA
    //Banner
    //SubjectOptions
    ObservableList<String> subjectOptions;
    ObservableList<String> numberOptions;
    ObservableList<String> semesterOptions;
    ObservableList<String> yearOptions;
    
    String currentSubject;
    String currentNumber;
    String currentSemester;
    String currentYear;
    
    boolean homeSelected;
    boolean syllabusSelected;
    boolean scheduleSelected;
    boolean hwSelected;
    
    String title;
    String exportDir;
    
    Instructor guy;
    String instructorName;
    String instructorRoom;
    String instructorEmail;
    String instructorHomePage;
    String instructorHours;
    String [] inH;
    
    String description;
    String topics;
    String prerequisites;
    String outcomes;
    String textbooks;
    String gradedComponents;
    String gradingNote;
    String academicDishonesty;
    String specialAssistance;
    
//Tab 3 Data
    ObservableList<Lab> labs;
    ObservableList<Lecture> lectures;
    ObservableList<Recitation> recitations;
    
// Tab 4 Data
    ObservableList<TeachingAssistantPrototype> teachingAssistants;
    ObservableList<TimeSlot> officeHours;
    ObservableList<TimeSlot> selectedOfficeHours;
    ObservableList<TimeSlot> testHours;
    
    
//Tab 5 Data
    ObservableList<ScheduleItem> scheduleItems;
    
    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    /**
     * This constructor will setup the required data structures for
     * use, but will have to wait on the office hours grid, since
     * it receives the StringProperty objects from the Workspace.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public CourseSiteGeneratorData(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        AppGUIModule gui = app.getGUIModule();
         
    //Tab 1 Data
        //Banner Info Items
        ComboBox subject = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        subjectOptions = subject.getItems();
        
        ComboBox number = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        numberOptions = number.getItems();
        
        ComboBox semester = (ComboBox) gui.getGUINode(CSG_SEMESTER_COMBO_BOX);
        semesterOptions = semester.getItems();
        
        
        ComboBox year = (ComboBox) gui.getGUINode(CSG_YEAR_COMBO_BOX);
        yearOptions = FXCollections.observableArrayList();
        for(Object i : year.getItems()){
            String s = Integer.toString((Integer)i);
            //System.out.println(s);
            yearOptions.add(s);
        }
        
        
        currentSubject = subject.getEditor().getText();
        System.out.println(currentSubject+ " is Current Subject");
        currentNumber = number.getEditor().getText();
        //currentSemester = semester.getEditor().getText();
        currentSemester = semester.getSelectionModel().getSelectedItem().toString();
        currentSemester = "Fall";
        //currentYear = year.getEditor().getText();
        currentYear = year.getSelectionModel().getSelectedItem().toString();
        currentYear = "2018";
        
        CheckBox homeChB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        CheckBox syllabusChB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        CheckBox scheduleChB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        CheckBox hwChB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
        
        homeSelected = homeChB.isSelected();
        syllabusSelected = syllabusChB.isSelected();
        scheduleSelected = scheduleChB.isSelected();
        hwSelected = hwChB.isSelected();
        
        TextArea textArea = (TextArea) gui.getGUINode(CSG_INSTRUCTOR_OFFICE_HOURS);
        instructorHours = textArea.getText();
        
        
        TextField nameTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_NAME_TEXT_FIELD);
        TextField emailTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD);
        TextField roomTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_ROOM_TEXT_FIELD);
        TextField homePageTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD);
        
        instructorName = nameTF.getText();
        instructorEmail = emailTF.getText();
        instructorRoom = roomTF.getText();
        instructorHomePage = homePageTF.getText();
        
        guy = new Instructor(nameTF.getText(),emailTF.getText(),roomTF.getText(),homePageTF.getText());
        guy.setOfficeHours(instructorHours);
        
        TextField titleTF = (TextField) gui.getGUINode(CSG_TITLE_TEXT_FIELD);
        //titleTF.setText(title);
        title = titleTF.getText();
        
        Label exportDirLink = (Label) gui.getGUINode(CSG_EXPORT_DIR_LINK);
        exportDir = exportDirLink.getText();
        
    // Tab 2 - SYLLABUS DATA
        TextArea descriptionText = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        description = descriptionText.getText();
        
        TextArea topicsText = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        topics = topicsText.getText();
        
        TextArea prerequisitesText = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        prerequisites = prerequisitesText.getText();
        
        TextArea outcomesText = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        outcomes = outcomesText.getText();
        
        TextArea textbooksText = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        textbooks = textbooksText.getText();
        
        TextArea gradedComponentsText = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        gradedComponents = gradedComponentsText.getText();
        
        TextArea gradingNoteText = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        gradingNote = gradingNoteText.getText();
        
        TextArea academicDishonestyText = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        academicDishonesty = academicDishonestyText.getText();
        
        TextArea specialAssistanceText = (TextArea) gui.getGUINode(CSG_SPECIAL_ASSISTANCE_TEXT_AREA);
        specialAssistance = specialAssistanceText.getText();
        
    //Tab 3 Data
        TableView<Lecture> lecturesTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        this.lectures = lecturesTV.getItems();
        
        TableView<Recitation> recitationsTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        this.recitations = recitationsTV.getItems();
        
        TableView<Lab> labsTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        this.labs = labsTV.getItems();
    
    //Tab 4 Data
    // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        TableView<TeachingAssistantPrototype> taTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        teachingAssistants = taTableView.getItems();
        
        TableView<TimeSlot> ohTableView = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        officeHours = ohTableView.getItems();
        selectedOfficeHours = FXCollections.observableArrayList();
        testHours = FXCollections.observableArrayList();
        
    //Tab 5 Data
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        this.scheduleItems = scheduleTV.getItems();
        
        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        resetOfficeHours();
    }
    
//TAB 1 HELPER METHODS
    public String getCurrentSubject(){
        return this.currentSubject;
    }
    
    public void setCurrentSubject(String initSubject){
        this.currentSubject = initSubject;
    }
    
    public String getCurrentNumber(){
        return this.currentNumber;
    }
    
    public void setCurrentNumber(String initNumber){
        this.currentNumber = initNumber;
    }
    
    public String getCurrentSemester(){
        return this.currentSemester;
    }
    
    public void setCurrentSemester(String initSemester){
        this.currentSemester = initSemester;
    }
    
    public String getCurrentYear(){
        return this.currentYear;
    }
    
    public void setCurrentYear(String initYear){
        this.currentYear = initYear;
    }
    
    public String getInstructorHours(){
        return instructorHours;
    }
    
    public void setInstructorHours(String initHours){
        this.instructorHours = initHours;
    }
    public Instructor getInstructor(){
        return guy;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getExportDir(){
        return this.exportDir;
    }
    
    public void setTitle(String initTitle){
        this.title = initTitle;
    }
    
    public void setExportDir(String initExportDir){
        this.exportDir = initExportDir;
    }
    
    public void setHomeSelected(){
        homeSelected = true;
    }
    
    public boolean isHomeSelected(){
        return homeSelected;
    }
    
    public boolean isSyllabusSelected(){
        return syllabusSelected;
    }
    
    public boolean isScheduleSelected(){
        return scheduleSelected;
    }
    
    public boolean isHwSelected(){
        return hwSelected;
    }
    
    
    public void changeInstructorHours(){
        System.out.println("Process the change instructor hours methods. WORDS HAVE BEEN ADDED");
    }
    
    public void unchangeInstructorHours(){
        System.out.println("Process the UNchange instructor hours methods. DELETE THESE WORDS");
    }
    
    public void changeComboBox(ComboBox options, String newString, String whichList){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        CourseSiteGeneratorController controller = new CourseSiteGeneratorController((CourseSiteGeneratorApp) app);
        
        options.setValue(newString);
        if(whichList.equals("semester")){
            data.setCurrentSemester(newString);
        }
        else if(whichList.equals("year")){
            data.setCurrentYear(newString);
        }
        controller.processChangeExportDir();
        System.out.println("Combo box change");
    }
    
    public void unchangeComboBox(ComboBox options, String oldString, String whichList){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        CourseSiteGeneratorController controller = new CourseSiteGeneratorController((CourseSiteGeneratorApp) app);
        
        options.setValue(oldString);
        if(whichList.equals("semester")){
            data.setCurrentSemester(oldString);
        }
        else if(whichList.equals("year")){
            data.setCurrentYear(oldString);
        }
        controller.processChangeExportDir();
        System.out.println("Undo combo box change");
    }
    
    public void printSubject(){
        System.out.println("Current subject is " + currentSubject);
    }
    
    public void printSemester(){
        System.out.println("Current seemster is " + currentSemester);
    }
    
    
    public void addComboOption(ObservableList<String> list, String newOption, ComboBox options){
        list.add(newOption);
            
        options.setValue(newOption);
        System.out.println("Made it here");
    }
    
    public void removeComboOption(ObservableList<String> list, String newOption, ComboBox options){
        list.remove(newOption);
        if(options.getEditor().getText().equals(newOption)){
            options.getEditor().setText("");
        }
    }
    
    public void changeImageView(String whichButton, String whichFile){
        ImageView x = new ImageView();
        Image a = x.getImage();
    }
    
    public void unchangeImageView(String whichButton, String whichFile){
        
    }
    
    public void checkBox(CheckBox checkBox, String whichTextBox){
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        checkBox.setSelected(true);
        if(whichTextBox.equals("home")){
            homeSelected = true;
        }
        else if(whichTextBox.equals("syllabus")){
            syllabusSelected = true;
        }
        else if(whichTextBox.equals("schedule")){
            scheduleSelected = true;
        }
        else if(whichTextBox.equals("homework")){
            hwSelected = true;
        }
        System.out.println("Check the box");
    }
    
    public void uncheckBox(CheckBox checkBox, String whichTextBox){
        checkBox.setSelected(false);
        if(whichTextBox.equals("home")){
            homeSelected = false;
        }
        else if(whichTextBox.equals("syllabus")){
            syllabusSelected = false;
        }
        else if(whichTextBox.equals("schedule")){
            scheduleSelected = false;
        }
        else if(whichTextBox.equals("homework")){
            hwSelected = false;
        }
        System.out.println("Uncheck the box");
    }
    
    public void setInstructorName(String initName){
        this.instructorName = initName;
    }
    
    public void setIntructorEmail(String initEmail){
        this.instructorEmail = initEmail;
    }
    
    public void setInstructorRoom(String initRoom){
        this.instructorRoom = initRoom;
    }
    
    public void setInstructorHomePage(String initPage){
        this.instructorHomePage = initPage;
    }
    
    public String getInstructorName(){
        return this.instructorName;
    }
    
    public String getInstructorEmail(){
        return this.instructorEmail;
    }
    
    public String getInstructorRoom(){
        return this.instructorRoom;
    }
    
    public String getInstructorLink(){
        return this.instructorHomePage;
    }
    
    
//Tab 2 helper fundtions
    
    public void setDesc(String initString){
        this.description = initString;
    }
    
    public void setTopics(String initString){
        this.topics = initString;
    }
    
    public void setPrereqs(String initString){
        this.prerequisites = initString;
    }
    
    public void setOutcomes(String initString){
        this.outcomes = initString;
    }
    
    public void setTextbooks(String initString){
        this.textbooks = initString;
    }
    
    public void setGradedComponents(String initString){
        this.gradedComponents = initString;
    }
    
    public void setGradingNote(String initString){
        this.gradingNote = initString;
    }
    
    public void setAcademicDishonesty(String initString){
        this.academicDishonesty = initString;
    }
    
    public void setSpecialAssistance(String initString){
        this.specialAssistance = initString;
    }
    
    public String getDesc(){
        return this.description;
    }
    
    public String getTopics(){
        return this.topics;
    }
    
    public String getPrereqs(){
        return this.prerequisites;
    }
    
    public String getOutcomes(){
        return this.outcomes;
    }
    
    public String getTextBooks(){
        return this.textbooks;
    }
    
    public String getGradedComponents(){
        return this.gradedComponents;
    }
    
    public String getGradingNote(){
        return this.gradingNote;
    }
    
    public String getAcademicDishonesty(){
        return this.academicDishonesty;
    }
    
    public String getSpecialAssistance(){
        return this.specialAssistance;
    }
    
    public ObservableList<String> getSubjectOptions(){
        return this.subjectOptions;
    }
    
    public void setSubjectOptions(ObservableList<String> initSubjectOptions){
        this.subjectOptions = initSubjectOptions;
    }
    
    public ObservableList<String> getNumberOptions(){
        return this.numberOptions;
    }
    
    public void setNumberOptions(ObservableList<String> initNumberOptions){
        this.numberOptions = initNumberOptions;
    }
    
    public ObservableList<String> getSemesterOptions(){
        return this.semesterOptions;
    }
    
    public ObservableList<String> getYearOptions(){
        return this.yearOptions;
    }
    
    public void printOptions(ObservableList<String> list){
        for(String s : list){
            System.out.println(s);
        }
    }
    
    public void printYears(ObservableList<Integer> list){
        for(Integer i : list){
            System.out.println(i);
        }
    }
    
    public void changeTextArea(CourseSiteGeneratorData data, TextArea thisTextArea, String newText, String whichBox){
        thisTextArea.setText(newText);
        
        if(whichBox.equals("description")){
            data.setDesc(newText);
        }
        else if(whichBox.equals("topics")){
            data.setTopics(newText);
        }
        else if(whichBox.equals("prereqs")){
            data.setPrereqs(newText);
        }
        else if(whichBox.equals("outcomes")){
            data.setOutcomes(newText);
        }
        else if(whichBox.equals("textbooks")){
            data.setTextbooks(newText);
        }
        else if(whichBox.equals("gc")){
            data.setGradedComponents(newText);
        }
        else if(whichBox.equals("gn")){
           data.setGradingNote(newText);
        }
        else if(whichBox.equals("ad")){
            data.setAcademicDishonesty(newText);
        }
        else if(whichBox.equals("sa")){
            data.setSpecialAssistance(newText);
        }
        
        
        
    }
    
    public void unchangeTextArea(CourseSiteGeneratorData data, TextArea thisTextArea, String oldText, String whichBox){
        thisTextArea.setText(oldText);
        
        if(whichBox.equals("description")){
            data.setDesc(oldText);
        }
        else if(whichBox.equals("topics")){
            data.setTopics(oldText);
        }
        else if(whichBox.equals("prereqs")){
            data.setPrereqs(oldText);
        }
        else if(whichBox.equals("outcomes")){
            data.setOutcomes(oldText);
        }
        else if(whichBox.equals("textbooks")){
            data.setTextbooks(oldText);
        }
        else if(whichBox.equals("gc")){
            data.setGradedComponents(oldText);
        }
        else if(whichBox.equals("gn")){
           data.setGradingNote(oldText);
        }
        else if(whichBox.equals("ad")){
            data.setAcademicDishonesty(oldText);
        }
        else if(whichBox.equals("sa")){
            data.setSpecialAssistance(oldText);
        }
    }
    
    
    
//Tab 3 Helper Methods
    public void addLecture(Lecture addThis){
       AppGUIModule gui = app.getGUIModule();
       TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
       this.lectures.add(addThis);
       lecTV.requestFocus();
       lecTV.getSelectionModel().select(addThis);
       lecTV.scrollTo(addThis);
       lecTV.refresh();
    }
    
    public void removeLecture(Lecture removeThis){
        AppGUIModule gui = app.getGUIModule();
        TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        this.lectures.remove(removeThis);
        lecTV.refresh();
    }
    
    public void addRecitation(Recitation addRec){
        AppGUIModule gui = app.getGUIModule();
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        this.recitations.add(addRec);
        recTV.refresh();
    }
    
    public void removeRecitation(Recitation removeRec){
        AppGUIModule gui = app.getGUIModule();
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        this.recitations.remove(removeRec);
        recTV.refresh();
    }
    
    public void addLab(Lab addLab){
        AppGUIModule gui = app.getGUIModule();
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        this.labs.add(addLab);
        labTV.refresh();
    }
    
    public void removeLab(Lab removeLab){
        AppGUIModule gui = app.getGUIModule();
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        this.labs.remove(removeLab);
        labTV.refresh();
    }
    
    public void editTableViewCell(String newString, String whichTV, Object obj, int col){
        AppGUIModule gui = app.getGUIModule();
        TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        
        
        if(whichTV.equals("lecture")){
            Lecture lec = (Lecture) obj;
            if(col == 0){
                lec.setSection(newString);
            }
            else if(col == 1){
                lec.setDays(newString);
            }
            else if(col == 2){
                lec.setTime(newString);
            }
            else if(col == 3){
                lec.setRoom(newString);
            }
            lecTV.refresh();
        }
        
        if(whichTV.equals("recitation")){
            Recitation rec = (Recitation) obj;
            if(col == 0){
                rec.setRsection(newString);
                 
            }
            else if(col == 1){
                rec.setRecDaysAndTime(newString);
            }
            else if(col == 2){
                rec.setRecRoom(newString);
            }
            else if(col == 3){
                rec.setRecTA1(newString);
            }
            else if(col ==4){
                rec.setRecTA2(newString);
            }
            recTV.refresh();
        }
        
        if(whichTV.equals("lab")){
            Lab lab = (Lab) obj;
            if(col == 0){
                lab.setSection(newString);
            }
            else if(col==1){
                lab.setDaysAndTime(newString);
            }
            else if(col==2){
                lab.setRoom(newString);
            }
            else if(col==3){
                lab.setlta1(newString);
            }
            else if(col==4){
                lab.setTA2(newString);
            }
            labTV.refresh();
        }
        
        if(whichTV.equals("schedule")){
            System.out.println("Inside Schedule 2");
            ScheduleItem si = (ScheduleItem) obj;
            if(col == 0){
                si.setType(newString);
            }
            else if(col == 1){
                si.setDate(newString);
            }
            else if(col == 2){
                System.out.println("Change title");
                si.setTitle(newString);
            }
            else if(col == 3){
                si.setTopic(newString);
                System.out.println("Change topic");
            }
            scheduleTV.refresh();
        }
        
    }
    
    public void uneditTableViewCell(String oldString, String whichTV, Object obj, int col){
        AppGUIModule gui = app.getGUIModule();
        TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        
        
        if(whichTV.equals("lecture")){
            Lecture lec = (Lecture) obj;
            if(col == 0){
                lec.setSection(oldString);
            }
            else if(col == 1){
                lec.setDays(oldString);
            }
            else if(col == 2){
                lec.setTime(oldString);
            }
            else if(col == 3){
                lec.setRoom(oldString);
            }
            lecTV.refresh();
        }
        
        if(whichTV.equals("recitation")){
            Recitation rec = (Recitation) obj;
            if(col == 0){
                rec.setRsection(oldString);
                 
            }
            else if(col == 1){
                rec.setRecDaysAndTime(oldString);
            }
            else if(col == 2){
                rec.setRecRoom(oldString);
            }
            else if(col == 3){
                rec.setRecTA1(oldString);
            }
            else if(col ==4){
                rec.setRecTA2(oldString);
            }
            recTV.refresh();
        }
        
        if(whichTV.equals("lab")){
            Lab lab = (Lab) obj;
            if(col == 0){
                lab.setSection(oldString);
            }
            else if(col==1){
                lab.setDaysAndTime(oldString);
            }
            else if(col==2){
                lab.setRoom(oldString);
            }
            else if(col==3){
                lab.setlta1(oldString);
            }
            else if(col==4){
                lab.setTA2(oldString);
            }
            labTV.refresh();
        }
        
        if(whichTV.equals("schedule")){
            ScheduleItem si = (ScheduleItem) obj;
            if(col == 0){
                si.setType(oldString);
            }
            else if(col == 1){
                si.setDate(oldString);
            }
            else if(col == 2){
                si.setTitle(oldString);
            }
            else if(col == 3){
                si.setTopic(oldString);
            }
            scheduleTV.refresh();
        }
    }
    
//TAB 4 HELPER METHODS   
    public void resetOfficeHours() {
        //THIS WILL STORE OUR OFFICE HOURS
        AppGUIModule gui = app.getGUIModule();
        TableView<TimeSlot> officeHoursTableView = (TableView)gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        officeHours = officeHoursTableView.getItems(); 
        officeHours.clear();
        //allOfficeHours = officeHoursTableView.getItems(); 
        //allOfficeHours.clear();
        for (int i = startHour; i <= endHour; i++) {
            TimeSlot timeSlot = new TimeSlot(   this.getTimeString(i, true),
                                                this.getTimeString(i, false));
            officeHours.add(timeSlot);
            //allOfficeHours.add(timeSlot);
            
            TimeSlot halfTimeSlot = new TimeSlot(   this.getTimeString(i, false),
                                                    this.getTimeString(i+1, true));
            officeHours.add(halfTimeSlot);
            //allOfficeHours.add(halfTimeSlot);
        }
    }
    
    public ObservableList<TeachingAssistantPrototype> getTaList (){
        return this.teachingAssistants;
    }
    
    private String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if (initStartHour <= initEndHour) {
            // THESE ARE VALID HOURS SO KEEP THEM
            // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
            startHour = initStartHour;
            endHour = initEndHour;
        }
        resetOfficeHours();
    }
        
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    
    @Override
    public void reset() {
        
        
        instructorName = "";
        instructorEmail = "";
        guy.setName("");
        //subjectOptions.clear();
        //numberOptions.clear();
        
        //semesterOptions.clear();
        //yearOptions.clear();

        currentSubject = FIRST_SUBJECT.toString();
        currentNumber = "";
        currentSemester = "";
        currentYear= "";

        title = "";
        exportDir= "";

        guy = new Instructor("","","","");
        instructorName = "";
        instructorRoom = "";
        instructorEmail="";
        instructorHomePage="";
        instructorHours="";


        description="";
        topics="";
        prerequisites="";
        outcomes="";
        textbooks="";
        gradedComponents="";
        gradingNote="";
        academicDishonesty="";
        specialAssistance="";

    //Tab 3 Data
        labs.clear();
        lectures.clear();
        recitations.clear();

    // Tab 4 Data
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        
        for (TimeSlot timeSlot : officeHours) {
            timeSlot.reset();
        }
    //Tab 5 Data
        scheduleItems.clear();
        
        
        
        
        AppGUIModule gui = app.getGUIModule();
        CheckBox homeCB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        homeCB.setSelected(false);
        CheckBox syllabusCB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        syllabusCB.setSelected(false);
        CheckBox scheduleCB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        scheduleCB.setSelected(false);
        CheckBox homeworkCB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
        homeworkCB.setSelected(false);
        
        TextField inName = (TextField) gui.getGUINode(CSG_INSTRUCTOR_NAME_TEXT_FIELD);
        inName.clear();
        TextField inEmail = (TextField) gui.getGUINode(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD);
        inEmail.clear();
        TextField inRoom = (TextField) gui.getGUINode(CSG_INSTRUCTOR_ROOM_TEXT_FIELD);
        inRoom.clear();
        TextField inHomePage = (TextField) gui.getGUINode(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD);
        inHomePage.clear();
        
        TextArea inHours = (TextArea) gui.getGUINode(CSG_INSTRUCTOR_OFFICE_HOURS);
        inHours.clear();
        
        TextArea desc = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        desc.clear();
        TextArea topic = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        topic.clear();
        TextArea pre = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        pre.clear();
        TextArea out = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        out.clear();
        TextArea tb = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        tb.clear();
        TextArea gc = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        gc.clear();
        TextArea gn = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        gn.clear();
        TextArea ad = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        ad.clear();
        
        
        
    }
    
// ACCESSOR AND MUTATOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public boolean isTASelected() {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        return tasTable.getSelectionModel().getSelectedItem() != null;
    }
    
    public void addTA(TeachingAssistantPrototype ta) {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        if (!this.teachingAssistants.contains(ta))
            this.teachingAssistants.add(ta);
        tasTable.refresh();
    }
    
    public void removeTA(TeachingAssistantPrototype ta) {
        // REMOVE THE TA FROM THE LIST OF TAs
        this.teachingAssistants.remove(ta);
        
        // AND REMOVE THE TA FROM ALL THEIR OFFICE HOURS
    }
    
    public void cutTA(TeachingAssistantPrototype ta){
        
    }
    
    public void uncutTA(TeachingAssistantPrototype ta){
        
    }

    public boolean isLegalNewTA(String name, String email) {
        if ((name.trim().length() > 0)
                && (email.trim().length() > 0)) {
            // MAKE SURE NO TA ALREADY HAS THE SAME NAME
            TeachingAssistantPrototype testTA = new TeachingAssistantPrototype(name, email);
            
            if (this.teachingAssistants.contains(testTA))
                return false;
            if (this.isLegalNewEmail(email)) {
                //System.out.println("Pass legal TA check");
                return true;
            }
        }
        return false;
    }
    
    public boolean isLegalName(String name){
        if((name.trim().length()> 0)){
            for(TeachingAssistantPrototype ta: this.teachingAssistants){
                if(ta.getName().toLowerCase().equals(name.toLowerCase())){   ///MAKE SURE THIS STRING IS LOWERCASE
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isLegalNewEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        if (matcher.find()) {
            for (TeachingAssistantPrototype ta : this.teachingAssistants) {
                if (ta.getEmail().equals(email.trim()))
                    return false;
            }
            return true;
        }
        else return false;
    }
    
    public boolean isDayOfWeekColumn(int columnNumber) {
        return columnNumber >= 2;
    }
    
    public DayOfWeek getColumnDayOfWeek(int columnNumber) {
        return TimeSlot.DayOfWeek.values()[columnNumber-2];
    }

    public Iterator<TeachingAssistantPrototype> teachingAssistantsIterator() {
        return teachingAssistants.iterator();
    }
    
    public Iterator<TimeSlot> officeHoursIterator() {
        return officeHours.iterator();
    }
    
    public Iterator<TimeSlot> selectedOfficeHoursIterator(){
        return selectedOfficeHours.iterator();
    }
    
    public Iterator<TimeSlot> testIterator(){
        return testHours.iterator();
    }
    
    public void clearOfficeHours(){
        this.officeHours.clear();
    }
    
    public void clearSelectedHours(){
        this.selectedOfficeHours.clear();
    }
    
    public void addTS(TimeSlot ts){
        this.officeHours.add(ts);
    }
    
    public void addSelectedTS(TimeSlot ts){
        this.selectedOfficeHours.add(ts);
    }
    
    public ObservableList<TimeSlot> getOfficeHours(){
        return this.officeHours;
    }
    
    public ObservableList<TimeSlot> getSelectedHours(){
        return this.selectedOfficeHours;
    }
    
    public void setOfficeHours(ObservableList<TimeSlot> initShowingHours){
        this.officeHours = initShowingHours;
    }
    
    public void setTestHours(ObservableList<TimeSlot> initTestHours){
        this.testHours = initTestHours;
    }
    
    public void setSelectedHours(ObservableList<TimeSlot> initSelectedHours){
        this.selectedOfficeHours = initSelectedHours;
    }
    
    public Iterator<String> subjectIterator(){
        return subjectOptions.iterator();
    }

    public Iterator<String> numberIterator(){
        return numberOptions.iterator();
    }

    public Iterator<String> semesterIterator(){
        return semesterOptions.iterator();
    }

    public Iterator<String> yearIterator(){
        return yearOptions.iterator();
    }
    
    public Iterator<Lecture> lectureIterator(){
        return lectures.iterator();
    }
    
    public Iterator<Recitation> recitationIterator(){
        return recitations.iterator();
    }
    
    public Iterator<Lab> labIterator(){
        return labs.iterator();
    }
        
    public String selectedSubject(){
        AppGUIModule gui = app.getGUIModule();
        ComboBox subject = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        return subject.getSelectionModel().getSelectedItem().toString();
    }

    
    public TeachingAssistantPrototype getTAWithName(String name) {
        Iterator<TeachingAssistantPrototype> taIterator = teachingAssistants.iterator();
        while (taIterator.hasNext()) {
            TeachingAssistantPrototype ta = taIterator.next();
            if (ta.getName().equals(name))
                return ta;
        }
        return null;
    }

    public TeachingAssistantPrototype getTAWithEmail(String email) {
        Iterator<TeachingAssistantPrototype> taIterator = teachingAssistants.iterator();
        while (taIterator.hasNext()) {
            TeachingAssistantPrototype ta = taIterator.next();
            if (ta.getEmail().equals(email))
                return ta;
        }
        return null;
    }

    public TimeSlot getTimeSlot(String startTime) {
        Iterator<TimeSlot> timeSlotsIterator = officeHours.iterator();
        while (timeSlotsIterator.hasNext()) {
            TimeSlot timeSlot = timeSlotsIterator.next();
            String timeSlotStartTime = timeSlot.getStartTime().replace(":", "_");
            if (timeSlotStartTime.equals(startTime))
                return timeSlot;
        }
        return null;
    }

    public void editTA(TeachingAssistantPrototype ta, String newName, String newEmail, String newType) {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        tasTable.refresh();
        TeachingAssistantPrototype editTa;
        if (this.teachingAssistants.contains(ta)){
            editTa = teachingAssistants.get(teachingAssistants.indexOf(ta));
            editTa.setName(newName);
            editTa.setEmail(newEmail);
            editTa.setType(newType);
        }
        else
            System.out.println("Failed to edit");
        
    }

    public void uneditTA(TeachingAssistantPrototype ta, String oldName, String oldEmail, String oldType) {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        tasTable.refresh();
        TeachingAssistantPrototype editTa;
        if (this.teachingAssistants.contains(ta)){
            editTa = teachingAssistants.get(teachingAssistants.indexOf(ta));
            editTa.setName(oldName);
            editTa.setEmail(oldEmail);
            editTa.setType(oldType);
        }
        else
            System.out.println("Failed unedit");
    }

    public void editTest(String name) {
        //String name = taToEdit.getName();
        //System.out.println(taToEdit.getEmail());
        System.out.println(name);
    }
    
    public void changeHoursDisplay(){
        
    }
    
    public void unchangeHoursDisplay(){
        
    }
    
    
// Tab 5 Functionality
    public ObservableList<ScheduleItem> getScheduleItems(){
        return this.scheduleItems;
    }
    
    public void addScheduleItem(ScheduleItem addThis){
        AppGUIModule gui = app.getGUIModule();
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        
        this.scheduleItems.add(addThis);
        scheduleTV.requestFocus();
        scheduleTV.getSelectionModel().select(addThis);
        scheduleTV.scrollTo(addThis);
        scheduleTV.refresh();
    }
    
    public void removeScheduleItem(ScheduleItem removeThis){
        AppGUIModule gui = app.getGUIModule();
        TableView<ScheduleItem> scheduleTV = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        
        this.scheduleItems.remove(removeThis);
        scheduleTV.refresh();
    }
    
    public Iterator<ScheduleItem> scheduleItemsIterator() {
        return scheduleItems.iterator();
    }
}
