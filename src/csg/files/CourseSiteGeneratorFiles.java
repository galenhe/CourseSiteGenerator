package csg.files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import csg.CourseSiteGeneratorApp;
import static csg.CourseSiteGeneratorPropertyType.*;
import csg.data.CourseSiteGeneratorData;
import csg.data.Instructor;
import csg.data.Lab;
import csg.data.Lecture;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.data.TimeSlot.DayOfWeek;
import static djf.AppPropertyType.APP_PATH_EXPORT;
import djf.modules.AppGUIModule;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;
        
/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 */
public class CourseSiteGeneratorFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    CourseSiteGeneratorApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    
//Tab 1 - SITE TAB JSON TYPES
    //Banner JSONs
    static final String JSON_BANNER_SUBJECT_OPTIONS = "subject_options";
    static final String JSON_BANNER_SUBJECT_OPTION = "subject_option";
    static final String JSON_BANNER_SUBJECT_SELECTED = "subject";
    static final String JSON_BANNER_SEMESTER_OPTIONS = "semester_options";
    static final String JSON_BANNER_SEMESTER_OPTION = "semester_option";
    static final String JSON_BANNER_SEMESTER_SELECTED = "semester";
    static final String JSON_BANNER_NUMBER_OPTIONS = "number_options";
    static final String JSON_BANNER_NUMBER_OPTION = "number_option";
    static final String JSON_BANNER_NUMBER_SELECTED = "number";
    static final String JSON_BANNER_YEAR_OPTIONS = "year_options";
    static final String JSON_BANNER_YEAR_OPTION = "year_option";
    static final String JSON_BANNER_YEAR_SELECTED = "year";
    static final String JSON_BANNER_TITLE_TEXT = "title";
    static final String JSON_BANNER_EXPORT_DIR_TEXT = "export_dir_text";
    
    //Pages JSONs
    static final String JSON_PAGES = "pages";
    static final String JSON_PAGE = "name";
    static final String JSON_PAGE_LINK = "link";

    //Style JSONs
    static final String JSON_LOGOS = "logos";
    static final String JSON_FAVICON = "favicon";
    static final String JSON_NAVBAR = "navbar";
    static final String JSON_LEFT = "left";
    static final String JSON_RIGHT = "right";
  //**new
    static final String JSON_CSS_PAGE = "css_page";
        

    
    //Instructor JSONs
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_INSTRUCTOR_NAME_TEXT = "name";
    static final String JSON_INSTRUCTOR_EMAIL_TEXT = "email";
    static final String JSON_INSTRUCTOR_ROOM_TEXT = "room";
    static final String JSON_INSTRUCTOR_HOME_PAGE_TEXT = "link";
    static final String JSON_INSTRUCTOR_PHOTO_TEXT = "photo";
    
    static final String JSON_INSTRUCTOR_OFFICE_HOURS_TEXT = "hours";
    
//Tab 2 - SYLLABUS TAB JSON OBJECTS
    static final String JSON_DESCRIPTION_TEXT = "description";
    static final String JSON_TOPICS_TEXT="topics";
    static final String JSON_PREREQUISITES_TEXT="prerequisites";
    static final String JSON_OUTCOMES_TEXT="outcomes";
    static final String JSON_TEXTBOOKS_TEXT="textbooks";
    static final String JSON_GRADED_COMPONENTS_TEXT="gradedComponents";
    static final String JSON_GRADING_NOTE_TEXT="gradingNote";
    static final String JSON_ACADEMIC_DISHONESTY_TEXT="academicDishonesty";
    static final String JSON_SPECIAL_ASSISTANCE_TEXT="specialAssistance";
    
// Tab 3 - MEETING TIMES - LECTURES, RECITATIONS, LABS
    static final String JSON_LECTURES = "lectures";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_LABS = " labs";
    
    static final String JSON_LECTURE = "lecture";
    static final String JSON_LECTURE_SECTION = "section";
    static final String JSON_LECTURE_DAYS = "days";
    static final String JSON_LECTURE_TIME = "time";
    static final String JSON_LECTURE_ROOM = "room";
    
    static final String JSON_RECITATION = "recitation";
    static final String JSON_RECITATION_SECTION = "section";
    static final String JSON_RECITATION_DAYS = "days_time";
    static final String JSON_RECITATION_ROOM = "location";
    static final String JSON_RECITATION_TA1= "ta_1";
    static final String JSON_RECITATION_TA2= "ta_2";
    
    
    static final String JSON_LAB = "lab";
    static final String JSON_LAB_SECTION = "section";
    static final String JSON_LAB_DAYS = "day_time";
    static final String JSON_LAB_ROOM = "location";
    static final String JSON_LAB_TA1= "ta_1";
    static final String JSON_LAB_TA2= "ta_2";
    
    
    
// Tab 4 - TAs Info
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRAD_TAS = "grad_tas";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_TYPE = "type";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_START_TIME = "time";
    static final String JSON_DAY_OF_WEEK = "day";
    static final String JSON_MONDAY = "monday";
    static final String JSON_TUESDAY = "tuesday";
    static final String JSON_WEDNESDAY = "wednesday";
    static final String JSON_THURSDAY = "thursday";
    static final String JSON_FRIDAY = "friday";

    
//**** all new Tab 5 - Schedule Info
    static final String JSON_STARTING_MONDAY = "starting_monday";
    static final String JSON_ENDING_FRIDAY = "ending_frday";
    
    static final String JSON_SCHEDULE_HOLIDAYS = "holidays";
    static final String JSON_SCHEDULE_LECTURES = "slectures";
    static final String JSON_SCHEDULE_HOMEWORKS = "hws";
    static final String JSON_SCHEDULE_RECITATIONS = "srecitations";
    static final String JSON_SCHEDULE_LABS = "slabs";
    static final String JSON_SCHEDULE_REFERENCES = "references";
    
    
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    static final String JSON_SCHEDULE_ITEM = "schedule_item";
    static final String JSON_SCHEDULE_ITEM_TYPE = "type";
    static final String JSON_SCHEDULE_ITEM_DATE = "date";
    static final String JSON_SCHEDULE_ITEM_TITLE = "title";
    static final String JSON_SCHEDULE_ITEM_TOPIC = "topic";
    static final String JSON_SCHEDULE_ITEM_LINK = "link";
    
    static final String JSON_SCHEDULE_ITEM_CURRENT_TITLE = "schedule_item_current_title";
    static final String JSON_SCHEDULE_ITEM_CURRENT_TOPIC = "schedule_item_current_topic";
    static final String JSON_SCHEDULE_ITEM_CURRENT_LINK = "schedule_item_current_link";
    
    
    
    public CourseSiteGeneratorFiles(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData)data;
        dataManager.reset();

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

        

        AppGUIModule gui = app.getGUIModule();
        
//TAB 1 - SITE INFORMATION TO LOAD
    //BANNER INFORMATION
    //SUBJECT COMBO BOX
        ObservableList<String> subjectOptions = FXCollections.observableArrayList();
        JsonArray jsonSubjectArray = json.getJsonArray(JSON_BANNER_SUBJECT_OPTIONS);
        ObservableList<String> alreadySubjects = dataManager.getSubjectOptions();
        for(int i=0; i< jsonSubjectArray.size() ; i++){
            JsonObject jsonOption = jsonSubjectArray.getJsonObject(i);
            if(!alreadySubjects.contains(jsonOption.getString(JSON_BANNER_SUBJECT_OPTION)))
                subjectOptions.add(jsonOption.getString(JSON_BANNER_SUBJECT_OPTION));
        }
        ComboBox subjectOptionsCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        subjectOptionsCB.getItems().addAll(subjectOptions);
        String selected = json.getJsonString(JSON_BANNER_SUBJECT_SELECTED).getString();
        dataManager.setSubjectOptions(subjectOptions);
        dataManager.setCurrentSubject(selected);
        subjectOptionsCB.setValue(selected);
        
        
    //NUMBER COMBO BOX
        ObservableList<String> numberOptions = FXCollections.observableArrayList();
        JsonArray jsonNumberArray = json.getJsonArray(JSON_BANNER_NUMBER_OPTIONS);
        ObservableList<String> alreadyNumbers = dataManager.getNumberOptions();
        for(int i=0; i< jsonNumberArray.size() ; i++){
            JsonObject jsonOption = jsonNumberArray.getJsonObject(i);
            if(!alreadyNumbers.contains(jsonOption.getString(JSON_BANNER_NUMBER_OPTION)))
                numberOptions.add(jsonOption.getString(JSON_BANNER_NUMBER_OPTION));
        }
        ComboBox numberOptionsCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        numberOptionsCB.getItems().addAll(numberOptions);
        String selectedNumber = json.getJsonString(JSON_BANNER_NUMBER_SELECTED).getString();
        numberOptionsCB.setValue(selectedNumber);
        dataManager.setCurrentNumber(selectedNumber);
        
        ComboBox semesterOptionsCB = (ComboBox) gui.getGUINode(CSG_SEMESTER_COMBO_BOX);
        dataManager.setCurrentSemester(semesterOptionsCB.getValue().toString());
        ComboBox yearOptionsCB = (ComboBox) gui.getGUINode(CSG_YEAR_COMBO_BOX);
        dataManager.setCurrentYear(yearOptionsCB.getValue().toString());
    //Title Text Field
        String title = json.getJsonString(JSON_BANNER_TITLE_TEXT).getString();
        dataManager.setTitle(title);
        TextField titleLabel = (TextField) gui.getGUINode(CSG_TITLE_TEXT_FIELD);
        titleLabel.setText(title);
        
    //Export Directory Field
        String exportDir = json.getJsonString(JSON_BANNER_EXPORT_DIR_TEXT).getString();
        dataManager.setExportDir(exportDir);
        Label exportDirLabel = (Label) gui.getGUINode(CSG_EXPORT_DIR_LINK);
        exportDirLabel.setText(exportDir);
    
    //Pages Check Boxes
        CheckBox homeCB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        CheckBox syllabusCB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        CheckBox scheduleCB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        CheckBox homeworkCB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
    
        JsonArray pagesArray = json.getJsonArray(JSON_PAGES);
        for(int i=0; i< pagesArray.size(); i++){
            JsonObject pagesOption = pagesArray.getJsonObject(i);
            String page = pagesOption.getString(JSON_PAGE);
            if(page.equals(homeCB.getText()))
                homeCB.setSelected(true);
            else if(page.equals(syllabusCB.getText()))
                syllabusCB.setSelected(true);
            else if(page.equals(scheduleCB.getText()))
                scheduleCB.setSelected(true);
            else if(page.equals(homeworkCB.getText()))
                homeworkCB.setSelected(true);
            else
                System.out.println("Doesnt match at all");
            String link = pagesOption.getString(JSON_PAGE_LINK);
            System.out.println(link + " is being shown");
        }
        
    //INSTRUCTOR INFORMATION  --  
        JsonObject instructor = json.getJsonObject(JSON_INSTRUCTOR);
    
        TextField inNameTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_NAME_TEXT_FIELD);
        String instructorName = instructor.getString(JSON_INSTRUCTOR_NAME_TEXT);
        dataManager.getInstructor().setName(instructorName);
        inNameTF.setText(instructorName);
        
        TextField inEmailTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD);
        String instructorEmail = instructor.getString(JSON_INSTRUCTOR_EMAIL_TEXT);
        dataManager.getInstructor().setEmail(instructorEmail);
        inEmailTF.setText(instructorEmail);
        
        TextField inRoomTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_ROOM_TEXT_FIELD);
        String instructorRoom = instructor.getString(JSON_INSTRUCTOR_ROOM_TEXT);
        dataManager.getInstructor().setRoom(instructorRoom);
        inRoomTF.setText(instructorRoom);
        
        TextField inHomePageTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD);
        String instructorHomePage = instructor.getString(JSON_INSTRUCTOR_HOME_PAGE_TEXT);
        dataManager.getInstructor().setHomePage(instructorHomePage);
        inHomePageTF.setText(instructorHomePage);
        
        TextArea inOHTF = (TextArea) gui.getGUINode(CSG_INSTRUCTOR_OFFICE_HOURS);
        //String instructorOfficeHours = json.getString(JSON_INSTRUCTOR_OFFICE_HOURS_TEXT);
        JsonArray hours = instructor.getJsonArray(JSON_INSTRUCTOR_OFFICE_HOURS_TEXT);
        String s = hours.toString();
        inOHTF.setText(s);
        dataManager.setInstructorHours(s);
        //dataManager.getInstructor().setOfficeHours(instructorOfficeHours);
        //dataManager.setInstructorHours(instructorOfficeHours);
        //inOHTF.setText(instructorOfficeHours);
        
//Tab 2 Loading
        TextArea descriptionArea = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        String description = json.getString(JSON_DESCRIPTION_TEXT);
        descriptionArea.setText(description);
        dataManager.setDesc(description);
        
        TextArea topicsArea = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        JsonArray topics = json.getJsonArray(JSON_TOPICS_TEXT);
        System.out.println("Here is the string" + topics.toString());
        topicsArea.setText(topics.toString());
        dataManager.setTopics(topics.toString());
        
        TextArea prereqsArea = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        prereqsArea.setText(json.getString(JSON_PREREQUISITES_TEXT));
        dataManager.setPrereqs(json.getString(JSON_PREREQUISITES_TEXT));
        
        TextArea outcomesArea = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        JsonArray outcomes = json.getJsonArray(JSON_OUTCOMES_TEXT);
        outcomesArea.setText(outcomes.toString());
        dataManager.setOutcomes(outcomes.toString());
        
        TextArea textbooksArea = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        JsonArray textBooks = json.getJsonArray(JSON_TEXTBOOKS_TEXT);
        textbooksArea.setText(textBooks.toString());
        dataManager.setTextbooks(textBooks.toString());
        
        TextArea gradedComponentsArea = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        JsonArray gradedComponents = json.getJsonArray(JSON_GRADED_COMPONENTS_TEXT);
        gradedComponentsArea.setText(gradedComponents.toString());
        dataManager.setGradedComponents(gradedComponents.toString());
        
        TextArea gradingNoteArea = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        gradingNoteArea.setText(json.getString(JSON_GRADING_NOTE_TEXT));
        dataManager.setGradingNote(json.getString(JSON_GRADING_NOTE_TEXT));
        
        TextArea academicDishonestyArea = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        academicDishonestyArea.setText(json.getString(JSON_ACADEMIC_DISHONESTY_TEXT));
        dataManager.setAcademicDishonesty(json.getString(JSON_ACADEMIC_DISHONESTY_TEXT));
        
        TextArea specialAssistanceArea = (TextArea) gui.getGUINode(CSG_SPECIAL_ASSISTANCE_TEXT_AREA);
        specialAssistanceArea.setText(json.getString(JSON_SPECIAL_ASSISTANCE_TEXT));
        dataManager.setSpecialAssistance(json.getString(JSON_SPECIAL_ASSISTANCE_TEXT));
        

//Tab 3 - Meeting Times  - Data to Load
        JsonArray jsonLectureArray = json.getJsonArray(JSON_LECTURES);
        for(int i = 0; i< jsonLectureArray.size(); i++){
            JsonObject jsonLecture = jsonLectureArray.getJsonObject(i);
            String section = jsonLecture.getString(JSON_LECTURE_SECTION);
            String days = jsonLecture.getString(JSON_LECTURE_DAYS);
            String time = jsonLecture.getString(JSON_LECTURE_TIME);
            String room = jsonLecture.getString(JSON_LECTURE_ROOM);
            Lecture addThis = new Lecture(section, days, time, room);
            dataManager.addLecture(addThis);
        }
        
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATIONS);
        for(int i = 0; i< jsonRecitationArray.size(); i++){
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_RECITATION_SECTION);
            String days = jsonRecitation.getString(JSON_RECITATION_DAYS);
            String room = jsonRecitation.getString(JSON_RECITATION_ROOM);
            String ta1 = jsonRecitation.getString(JSON_RECITATION_TA1);
            String ta2 = jsonRecitation.getString(JSON_RECITATION_TA2);
            Recitation addThis = new Recitation(section, days, room, ta1, ta2);
            dataManager.addRecitation(addThis);
        }
        
        JsonArray jsonLabArray = json.getJsonArray(JSON_LABS);
        for(int i = 0; i< jsonLabArray.size(); i++){
            JsonObject jsonLab = jsonLabArray.getJsonObject(i);
            String section = jsonLab.getString(JSON_LAB_SECTION);
            String days = jsonLab.getString(JSON_LAB_DAYS);
            String room = jsonLab.getString(JSON_LAB_ROOM);
            String ta1 = jsonLab.getString(JSON_LAB_TA1);
            String ta2 = jsonLab.getString(JSON_LAB_TA2);
            Lab addThis = new Lab(section, days, room, ta1, ta2);
            dataManager.addLab(addThis);
        }
        
        
//Tab 4 - DATA TO LOAD
	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        
        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            String type = jsonTA.getString(JSON_TYPE);
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name, email, type);
            dataManager.addTA(ta);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String startTime = jsonOfficeHours.getString(JSON_START_TIME);
            DayOfWeek dow = DayOfWeek.valueOf(jsonOfficeHours.getString(JSON_DAY_OF_WEEK));
            String name = jsonOfficeHours.getString(JSON_NAME);
            TeachingAssistantPrototype ta = dataManager.getTAWithName(name);
            TimeSlot timeSlot = dataManager.getTimeSlot(startTime);
            timeSlot.toggleTA(dow, ta);
        }
        
        
//Tab 5 - Schedule Items - Info to Load
//        JsonArray jsonScheduleItems = json.getJsonArray(JSON_SCHEDULE_ITEMS);
//        for (int i = 0; i < jsonScheduleItems.size(); i++) {
//            JsonObject jsonScheduleItem = jsonScheduleItems.getJsonObject(i);
//            String type = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TYPE);
//            String date = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_DATE);
//            String scheduleTitle = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TITLE);
//            String topic = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_TOPIC);
//            String link = jsonScheduleItem.getString(JSON_SCHEDULE_ITEM_LINK);
//            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
//            addThis.setLink(link);
//            dataManager.addScheduleItem(addThis);
//        }

        JsonArray jsonHolidays = json.getJsonArray(JSON_SCHEDULE_HOLIDAYS);
        for(int i=0; i< jsonHolidays.size(); i++){
            JsonObject jsonHoliday = jsonHolidays.getJsonObject(i);
            String type = jsonHoliday.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonHoliday.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonHoliday.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonHoliday.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonHoliday.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        JsonArray jsonLectures = json.getJsonArray(JSON_SCHEDULE_LECTURES);
        for(int i=0; i< jsonLectures.size(); i++){
            JsonObject jsonLecture = jsonLectures.getJsonObject(i);
            String type = jsonLecture.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonLecture.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonLecture.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonLecture.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonLecture.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        JsonArray jsonHomeworks = json.getJsonArray(JSON_SCHEDULE_HOMEWORKS);
        for(int i=0; i< jsonHomeworks.size(); i++){
            JsonObject jsonHomework = jsonHomeworks.getJsonObject(i);
            String type = jsonHomework.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonHomework.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonHomework.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonHomework.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonHomework.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        JsonArray jsonRecitations = json.getJsonArray(JSON_SCHEDULE_RECITATIONS);
        for(int i=0; i< jsonRecitations.size(); i++){
            JsonObject jsonRecitation = jsonRecitations.getJsonObject(i);
            String type = jsonRecitation.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonRecitation.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonRecitation.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonRecitation.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonRecitation.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        JsonArray jsonLabs = json.getJsonArray(JSON_SCHEDULE_LABS);
        for(int i=0; i< jsonLabs.size(); i++){
            JsonObject jsonLab = jsonLabs.getJsonObject(i);
            String type = jsonLab.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonLab.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonLab.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonLab.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonLab.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        
        JsonArray jsonReferences = json.getJsonArray(JSON_SCHEDULE_REFERENCES);
        for(int i=0; i< jsonReferences.size(); i++){
            JsonObject jsonReference = jsonReferences.getJsonObject(i);
            String type = jsonReference.getString(JSON_SCHEDULE_ITEM_TYPE);
            String date = jsonReference.getString(JSON_SCHEDULE_ITEM_DATE);
            String scheduleTitle = jsonReference.getString(JSON_SCHEDULE_ITEM_TITLE);
            String topic = jsonReference.getString(JSON_SCHEDULE_ITEM_TOPIC);
            String link = jsonReference.getString(JSON_SCHEDULE_ITEM_LINK);
            ScheduleItem addThis = new ScheduleItem(type, date, scheduleTitle, topic);
            addThis.setLink(link);
            dataManager.addScheduleItem(addThis);
        }
        

    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData)data;

        
        AppGUIModule gui = app.getGUIModule();
// TAB 1 DATA - SITE INFO FIRST
        
    //BANNER INFORMATION  
        //SUBJECT COMBO BOX
        JsonArrayBuilder subjectOptionsArrayBuilder = Json.createArrayBuilder();
        Iterator<String> subjectOptionsIterator = dataManager.subjectIterator();
        while(subjectOptionsIterator.hasNext()){
            JsonObject sjOp = Json.createObjectBuilder()
                    .add(JSON_BANNER_SUBJECT_OPTION, subjectOptionsIterator.next())
                    .build();
            subjectOptionsArrayBuilder.add(sjOp);
        }
        JsonArray subjectOptionsArray = subjectOptionsArrayBuilder.build();
        
        //NUMBER COMBO BOX
        JsonArrayBuilder numberOptionsArrayBuilder = Json.createArrayBuilder();
        Iterator<String> numberOptionsIterator = dataManager.numberIterator();
        while(numberOptionsIterator.hasNext()){
            JsonObject sjOp = Json.createObjectBuilder()
                    .add(JSON_BANNER_NUMBER_OPTION, numberOptionsIterator.next())
                    .build();
            numberOptionsArrayBuilder.add(sjOp);
        }
        JsonArray numberOptionsArray = numberOptionsArrayBuilder.build();
        
        //SEMESTER COMBO BOX
        
        //YEAR COMBO BOX
        
        //COMBO BOX SELECTED ITEMS
        ComboBox subjectCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        String selectedSubject = subjectCB.getValue().toString();
        
        ComboBox numberCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        String selectedNumber = numberCB.getValue().toString();
        
        //TITLE
        String title = dataManager.getTitle();
        
        //Export Dir
        String exportDir = dataManager.getExportDir();
        
        
    //PAGES CHECK BOX INFORMATION
        JsonArrayBuilder pagesSelectedPageBuilder = Json.createArrayBuilder();
        CheckBox homeChB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        CheckBox syllabusChB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        CheckBox scheduleChB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        CheckBox homeworkChB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
        if(homeChB.isSelected()){
            JsonObject homeOb = Json.createObjectBuilder()
                    .add(JSON_PAGE, homeChB.getText())
                    .add(JSON_PAGE_LINK, "home.html")
                    .build();
            pagesSelectedPageBuilder.add(homeOb);
            
            
        }
        if(syllabusChB.isSelected()){
            JsonObject syllabusOb = Json.createObjectBuilder()
                    .add(JSON_PAGE, syllabusChB.getText())
                    .add(JSON_PAGE_LINK, "syllabus.html")
                    .build();
            pagesSelectedPageBuilder.add(syllabusOb);
        }
        if(scheduleChB.isSelected()){
            JsonObject scheduleOb = Json.createObjectBuilder()
                    .add(JSON_PAGE, scheduleChB.getText())
                    .add(JSON_PAGE_LINK, "schedule.html")
                    .build();
            pagesSelectedPageBuilder.add(scheduleOb);
        }
        if(homeworkChB.isSelected()){
            JsonObject homeworkOb = Json.createObjectBuilder()
                    .add(JSON_PAGE, homeworkChB.getText())
                    .add(JSON_PAGE_LINK, "hws.html")
                    .build();
            pagesSelectedPageBuilder.add(homeworkOb);
        }
        
        JsonArray pagesArray = pagesSelectedPageBuilder.build();
        
        
    
    //INSTRUCTOR DATA  -----
        Instructor instructorToSave = dataManager.getInstructor();
        String name = instructorToSave.getName();
        String room = instructorToSave.getRoom();
        String email = instructorToSave.getEmail();
        String homePage = instructorToSave.getHomePage();
        String officeHours = instructorToSave.getOfficeHours();
        
        JsonReader reader = Json.createReader(new StringReader(dataManager.getInstructorHours()));
        JsonArray hours = reader.readArray();
        
        for(JsonValue s : hours){
            System.out.println(s.toString());
        }
        
        
        JsonObject instructor = Json.createObjectBuilder()
                .add(JSON_INSTRUCTOR_NAME_TEXT, name)
                .add(JSON_INSTRUCTOR_EMAIL_TEXT, email)
                .add(JSON_INSTRUCTOR_HOME_PAGE_TEXT, homePage)
                .add(JSON_INSTRUCTOR_ROOM_TEXT, room)
                .add(JSON_INSTRUCTOR_PHOTO_TEXT, "./images/RichardMcKenna.jpg")
                .add(JSON_INSTRUCTOR_OFFICE_HOURS_TEXT, hours)
                .build();
        
// TAB 2 SAVING - what is this cancer? wtf
        JsonArrayBuilder topicsBuilder = Json.createArrayBuilder();
        JsonReader readTopics = Json.createReader(new StringReader(dataManager.getTopics()));
        JsonArray topicsArray= readTopics.readArray();
        
        
        JsonArrayBuilder outcomesBuilder = Json.createArrayBuilder();
        JsonReader readOutcomes = Json.createReader(new StringReader(dataManager.getOutcomes()));
        JsonArray outcomesArray = readOutcomes.readArray();
        
        
        JsonArrayBuilder textBooksBuilder = Json.createArrayBuilder();
        JsonReader readTextBooks = Json.createReader(new StringReader(dataManager.getTextBooks()));
        JsonArray textBooksArray = readTextBooks.readArray();
        
        JsonArrayBuilder gradedComponentsBuilder = Json.createArrayBuilder();
        JsonReader readGradedComponents = Json.createReader(new StringReader(dataManager.getGradedComponents()));
        JsonArray gradedComponentsArray = readGradedComponents.readArray();
        
//Tab 3 - Meeting Times Data to Save
        JsonArrayBuilder lecturesBuilder = Json.createArrayBuilder();
        Iterator<Lecture> lecturesIterator = dataManager.lectureIterator();
        while(lecturesIterator.hasNext()){
            Lecture lec = lecturesIterator.next();
            System.out.println("Lecture's section is: "  + lec.getSection());
            JsonObject lecJson = Json.createObjectBuilder()
                    .add(JSON_LECTURE_SECTION, lec.getSection())
                    .add(JSON_LECTURE_DAYS, lec.getDays())
                    .add(JSON_LECTURE_ROOM, lec.getRoom())
                    .add(JSON_LECTURE_TIME, lec.getTime())
                    .build();
            lecturesBuilder.add(lecJson);
        }
        JsonArray lecturesArray = lecturesBuilder.build();
        for(int i=0; i<lecturesArray.size(); i++){
            JsonObject lec = lecturesArray.getJsonObject(i);
            System.out.println("Section: " +lec.getJsonString(JSON_LECTURE_SECTION));
            System.out.println("Day: " +lec.getJsonString(JSON_LECTURE_DAYS));
            System.out.println("Room: " +lec.getJsonString(JSON_LECTURE_ROOM));
            System.out.println("Time: " +lec.getJsonString(JSON_LECTURE_TIME));
        }
        
        
        JsonArrayBuilder recitationsBuilder = Json.createArrayBuilder();
        Iterator<Recitation> recitationsIterator = dataManager.recitationIterator();
        while(recitationsIterator.hasNext()){
            Recitation rec = recitationsIterator.next();
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, rec.getRsection())
                    .add(JSON_RECITATION_DAYS, rec.getRecDaysAndTime())
                    .add(JSON_RECITATION_ROOM, rec.getRecRoom())
                    .add(JSON_RECITATION_TA1, rec.getRecTA1())
                    .add(JSON_RECITATION_TA2, rec.getRecTA2())
                    .build();
            recitationsBuilder.add(recJson);
        }
        JsonArray recitationsArray = recitationsBuilder.build();
    
        JsonArrayBuilder labsBuilder = Json.createArrayBuilder();
        Iterator<Lab> labsIterator = dataManager.labIterator();
        while(labsIterator.hasNext()){
            Lab lab = labsIterator.next();
            JsonObject labJson = Json.createObjectBuilder()
                    .add(JSON_LAB_SECTION, lab.getLsection())
                    .add(JSON_LAB_DAYS, lab.getLdaysAndTime())
                    .add(JSON_LAB_ROOM, lab.getLroom())
                    .add(JSON_LAB_TA1, lab.getLta1())
                    .add(JSON_LAB_TA2, lab.getLta2())
                    .build();
            labsBuilder.add(labJson);
        }
        JsonArray labsArray = labsBuilder.build();
        
//Tab 4 JSON OBJECTS TO SAVE
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	Iterator<TeachingAssistantPrototype> tasIterator = dataManager.teachingAssistantsIterator();
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype ta = tasIterator.next();
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_TYPE, ta.getType())
                    .build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE OFFICE HOURS JSON OBJCTS TO SAVE
	JsonArrayBuilder officeHoursArrayBuilder = Json.createArrayBuilder();
        Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
        while (timeSlotsIterator.hasNext()) {
            TimeSlot timeSlot = timeSlotsIterator.next();
            for (int i = 0; i < DayOfWeek.values().length; i++) {
                DayOfWeek dow = DayOfWeek.values()[i];
                tasIterator = timeSlot.getTAsIterator(dow);
                while (tasIterator.hasNext()) {
                    TeachingAssistantPrototype ta = tasIterator.next();
                    JsonObject tsJson = Json.createObjectBuilder()
                        .add(JSON_START_TIME, timeSlot.getStartTime().replace(":", "_"))
                        .add(JSON_DAY_OF_WEEK, dow.toString())
                        .add(JSON_NAME, ta.getName())
                            .build();
                    officeHoursArrayBuilder.add(tsJson);
                }
            }
	}
	JsonArray officeHoursArray = officeHoursArrayBuilder.build();
        
//*** ALLL NEWWTab 5 - Schedule data to save
        JsonArrayBuilder scheduleItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder holidayItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder lectureItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder homeworkItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder labItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder referenceItemsBuilder = Json.createArrayBuilder();
        JsonArrayBuilder recitationItemsBuilder = Json.createArrayBuilder();
        
        Iterator<ScheduleItem> scheduleItemsIterator = dataManager.scheduleItemsIterator();
        while(scheduleItemsIterator.hasNext()){
            ScheduleItem scheduleItem = scheduleItemsIterator.next();
            JsonObject scheduleItemJson = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_ITEM_TYPE, scheduleItem.getType())
                    .add(JSON_SCHEDULE_ITEM_DATE, scheduleItem.getDate())
                    .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                    .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                    .add(JSON_SCHEDULE_ITEM_LINK, scheduleItem.getLink())
                    .build();
            scheduleItemsBuilder.add(scheduleItemJson);
            if(scheduleItem.getType().equals("Holiday")){
                holidayItemsBuilder.add(scheduleItemJson);
            }
            else if(scheduleItem.getType().equals("Lecture")){
                lectureItemsBuilder.add(scheduleItemJson);
            }
            else if(scheduleItem.getType().equals("Homework")){
                homeworkItemsBuilder.add(scheduleItemJson);
            }
            else if(scheduleItem.getType().equals("Recitation")){
                recitationItemsBuilder.add(scheduleItemJson);
            }
            else if(scheduleItem.getType().equals("Lab")){
                labItemsBuilder.add(scheduleItemJson);
            }
            else if(scheduleItem.getType().equals("Reference")){
                referenceItemsBuilder.add(scheduleItemJson);
            }
        }
        JsonArray scheduleItems = scheduleItemsBuilder.build();
        JsonArray holidayItems = holidayItemsBuilder.build();
        JsonArray lectureItems = lectureItemsBuilder.build();
        JsonArray homeworkItems = homeworkItemsBuilder.build();
        JsonArray recitationItems = recitationItemsBuilder.build();
        JsonArray labItems = labItemsBuilder.build();
        JsonArray referenceItems = referenceItemsBuilder.build();
        
        
    
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
            //TAB 1 - SITE
                //Banner Objects
                .add(JSON_BANNER_SUBJECT_OPTIONS, subjectOptionsArray)
                .add(JSON_BANNER_SUBJECT_SELECTED, selectedSubject)
                .add(JSON_BANNER_NUMBER_OPTIONS, numberOptionsArray)
                .add(JSON_BANNER_NUMBER_SELECTED, selectedNumber)
                .add(JSON_BANNER_TITLE_TEXT, title)
                .add(JSON_BANNER_EXPORT_DIR_TEXT, exportDir)
                
                //Page Objects
                .add(JSON_PAGES, pagesArray)
                
                //INSTRUCTOR OBJECTS
                .add(JSON_INSTRUCTOR, instructor)
                
            //TAB 2 - SYLLABUS
                .add(JSON_DESCRIPTION_TEXT, dataManager.getDesc())
                .add(JSON_TOPICS_TEXT, topicsArray)
                .add(JSON_PREREQUISITES_TEXT,dataManager.getPrereqs())
                .add(JSON_OUTCOMES_TEXT, outcomesArray)
                .add(JSON_TEXTBOOKS_TEXT, textBooksArray)
                .add(JSON_GRADED_COMPONENTS_TEXT, gradedComponentsArray)
                .add(JSON_GRADING_NOTE_TEXT, dataManager.getGradingNote())
                .add(JSON_ACADEMIC_DISHONESTY_TEXT, dataManager.getAcademicDishonesty())
                .add(JSON_SPECIAL_ASSISTANCE_TEXT, dataManager.getSpecialAssistance())
                
            //Tab 3 - Meeting Times
                
                .add(JSON_LECTURES, lecturesArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_LABS, labsArray)
                
            //Tab 4 - Ta info
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                
                //OFFICE HOURS
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, officeHoursArray)
                
            //Tab 5 - Schedule items
                .add(JSON_SCHEDULE_HOLIDAYS, holidayItems)
                .add(JSON_SCHEDULE_LECTURES, lectureItems)
                .add(JSON_SCHEDULE_HOMEWORKS, homeworkItems)
                .add(JSON_SCHEDULE_RECITATIONS, recitationItems)
                .add(JSON_SCHEDULE_LABS, labItems)
                .add(JSON_SCHEDULE_REFERENCES, referenceItems)
               
                .build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        AppGUIModule gui = app.getGUIModule();
        ComboBox subjects = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        ComboBox numbers = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        ComboBox years = (ComboBox) gui.getGUINode(CSG_YEAR_COMBO_BOX);
        ComboBox semesters = (ComboBox) gui.getGUINode(CSG_SEMESTER_COMBO_BOX);
        String subject = subjects.getValue().toString();
        String number = numbers.getValue().toString();
        String year = years.getValue().toString();
        String semester = semesters.getValue().toString();
        String exportFolder = subject+"_"+number+"_"+semester+"_"+year;
        System.out.println("Export folder is " +exportFolder);
        System.out.println("Export folder path is; .\\export\\" + exportFolder);
        System.out.println("Subject is: " + subject + " and number is: " + number);
        CourseSiteGeneratorData dataManager = (CourseSiteGeneratorData) app.getDataComponent();
        String exportDir = dataManager.getExportDir();
        System.out.println("Export directory is: " + exportDir);
        
        
        //MAKING THE FILE DIRECTORIES
        try{
            boolean success = (
                    new File(".\\export\\" + exportFolder)).mkdir();
            boolean success1 = (
                    new File(exportDir)).mkdir();
            
            if(success && success1){
                System.out.println(exportFolder+ " created");
            }
            else
                System.out.println("One of the initial export folders not created");
        }catch (Exception e){
            System.out.println("error");
        }
        
        File sourceDirectory = new File(".\\template");
        File exportDirectory = new File(exportDir); 
        FileUtils.copyDirectory(sourceDirectory, exportDirectory);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        props.removeProperty(APP_PATH_EXPORT);
        props.addProperty(APP_PATH_EXPORT, exportDir);
        System.out.println("here is the export dir" +APP_PATH_EXPORT +exportDir );
        
 //TAB 1 - PAGE DATA TO EXPORT
        try{
            String newFile = exportDir+"\\js\\PageData.json";
            boolean success = (
                    new File(newFile).createNewFile());
            
            if(success){
                System.out.println("PageData.json"+ " created");
            }
            else
                System.out.println("PageData.json already exists");
            
            FileWriter writer = new FileWriter(newFile);
                
            Instructor instructorToSave = dataManager.getInstructor();
            String name = instructorToSave.getName();
            String room = instructorToSave.getRoom();
            String email = instructorToSave.getEmail();
            String homePage = instructorToSave.getHomePage();
            String officeHours = instructorToSave.getOfficeHours();
        

            JsonReader reader = Json.createReader(new StringReader(dataManager.getInstructorHours()));
            JsonArray hours = reader.readArray();
        
            JsonObject instructorBuilder = Json.createObjectBuilder()
                    .add(JSON_INSTRUCTOR_NAME_TEXT, name)
                    .add(JSON_INSTRUCTOR_HOME_PAGE_TEXT, homePage)
                    .add(JSON_INSTRUCTOR_EMAIL_TEXT, email)
                    .add(JSON_INSTRUCTOR_ROOM_TEXT, room)
                    .add(JSON_INSTRUCTOR_PHOTO_TEXT, "./images/RichardMcKenna.jpg")
                    .add(JSON_INSTRUCTOR_OFFICE_HOURS_TEXT, hours)
                    .build();
            
            
            JsonObjectBuilder logosBuilder = Json.createObjectBuilder();
            JsonObject favicon = Json.createObjectBuilder()
                    .add("href", "./images/SBUShieldFavicon.ico")
                    .build();
            logosBuilder.add("favicon", favicon);
            
            JsonObject navBar = Json.createObjectBuilder()
                    .add("href", "http://www.stonybrook.edu")
                    .add("src", "./images/SBUDarkRedShieldLogo.png")
                    .build();
            logosBuilder.add("navbar", navBar);
            
            JsonObject bottomLeft = Json.createObjectBuilder()
                    .add("href", "http://www.cs.stonybrook.edu")
                    .add("src", "./images/SBUWhiteShieldLogo.jpg")
                    .build();
            logosBuilder.add("bottom_left", bottomLeft);
            
            JsonObject bottomRight = Json.createObjectBuilder()
                    .add("href", "http://www.cs.stonybrook.edu")
                    .add("src", "./images/SBUCSLogo.png")
                    .build();
            logosBuilder.add("bottom_right", bottomRight);
            
            
            JsonObject logos= logosBuilder.build();
            
            JsonArrayBuilder pagesSelectedPageBuilder = Json.createArrayBuilder();
            CheckBox homeChB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
            CheckBox syllabusChB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
            CheckBox scheduleChB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
            CheckBox homeworkChB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
            if(homeChB.isSelected()){
                JsonObject homeOb = Json.createObjectBuilder()
                        .add(JSON_PAGE, homeChB.getText())
                        .add(JSON_PAGE_LINK, "home.html")
                        .build();
                pagesSelectedPageBuilder.add(homeOb);


            }
            if(syllabusChB.isSelected()){
                JsonObject syllabusOb = Json.createObjectBuilder()
                        .add(JSON_PAGE, syllabusChB.getText())
                        .add(JSON_PAGE_LINK, "syllabus.html")
                        .build();
                pagesSelectedPageBuilder.add(syllabusOb);
            }
            if(scheduleChB.isSelected()){
                JsonObject scheduleOb = Json.createObjectBuilder()
                        .add(JSON_PAGE, scheduleChB.getText())
                        .add(JSON_PAGE_LINK, "schedule.html")
                        .build();
                pagesSelectedPageBuilder.add(scheduleOb);
            }
            if(homeworkChB.isSelected()){
                JsonObject homeworkOb = Json.createObjectBuilder()
                        .add(JSON_PAGE, homeworkChB.getText())
                        .add(JSON_PAGE_LINK, "homework.html")
                        .build();
                pagesSelectedPageBuilder.add(homeworkOb);
            }
        
            JsonArray pagesArray = pagesSelectedPageBuilder.build();
        
            JsonObject pageData = Json.createObjectBuilder()
                    .add(JSON_BANNER_SUBJECT_SELECTED, dataManager.getCurrentSubject())
                    .add(JSON_BANNER_NUMBER_SELECTED, dataManager.getCurrentNumber())
                    .add(JSON_BANNER_SEMESTER_SELECTED, dataManager.getCurrentSemester())
                    .add(JSON_BANNER_YEAR_SELECTED, dataManager.getCurrentYear())
                    .add(JSON_BANNER_TITLE_TEXT, dataManager.getTitle())
                    .add(JSON_LOGOS, logos)
                    .add(JSON_INSTRUCTOR, instructorBuilder)
                    .add(JSON_PAGES, pagesArray)
                    .build();
            
            
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(pageData);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(newFile);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(pageData);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(newFile);
	pw.write(prettyPrinted);
	pw.close();
            
            
            
        }catch (Exception e){
            System.out.println("error");
        }
        
        
        
//TAB 2 - SyllabusData.json  -- Syllabus Data to EXPORT
           
        try{
            String newFile = exportDir+"\\js\\SyllabusData.json";
            boolean success = (
                    new File(newFile).createNewFile());
            
            if(success){
                System.out.println(exportFolder+ " created");
            }
            else
                System.out.println("SyllabusData.json already exists");
            
            FileWriter writer = new FileWriter(newFile);
            
            
            JsonReader readTopics = Json.createReader(new StringReader(dataManager.getTopics()));
            JsonArray topicsArray= readTopics.readArray();
        
        
            JsonReader readOutcomes = Json.createReader(new StringReader(dataManager.getOutcomes()));
            JsonArray outcomesArray = readOutcomes.readArray();
        
            JsonReader readTextBooks = Json.createReader(new StringReader(dataManager.getTextBooks()));
            JsonArray textBooksArray = readTextBooks.readArray();
        
            JsonReader readGradedComponents = Json.createReader(new StringReader(dataManager.getGradedComponents()));
            JsonArray gradedComponentsArray = readGradedComponents.readArray();
        
            
            
            JsonObject SyllabusData = Json.createObjectBuilder()
                    .add(JSON_DESCRIPTION_TEXT, dataManager.getDesc())
                    .add(JSON_TOPICS_TEXT, topicsArray)
                    .add(JSON_PREREQUISITES_TEXT,dataManager.getPrereqs())
                    .add(JSON_OUTCOMES_TEXT, outcomesArray)
                    .add(JSON_TEXTBOOKS_TEXT, textBooksArray)
                    .add(JSON_GRADED_COMPONENTS_TEXT, gradedComponentsArray)
                    .add(JSON_GRADING_NOTE_TEXT, dataManager.getGradingNote())
                    .add(JSON_ACADEMIC_DISHONESTY_TEXT, dataManager.getAcademicDishonesty())
                    .add(JSON_SPECIAL_ASSISTANCE_TEXT, dataManager.getSpecialAssistance())
                    .build();
            
            
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(SyllabusData);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(newFile);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(SyllabusData);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(newFile);
	pw.write(prettyPrinted);
	pw.close();
            
            
            
        }catch (Exception e){
            System.out.println("error");
        }
        
        
//Tab 3 - SectionsData.json
        try{
            String newFile = exportDir+"\\js\\SectionsData.json";
            boolean success = (
                    new File(newFile).createNewFile());
            
            if(success){
                System.out.println("SectionsData.json created");
            }
            else
                System.out.println("SectionsData.json already exists");
            
            FileWriter writer = new FileWriter(newFile);
            
            JsonArrayBuilder lecturesBuilder = Json.createArrayBuilder();
            Iterator<Lecture> lecturesIterator = dataManager.lectureIterator();
            while(lecturesIterator.hasNext()){
                Lecture lec = lecturesIterator.next();
                JsonObject lecJson = Json.createObjectBuilder()
                        .add(JSON_LECTURE_SECTION, lec.getSection())
                        .add(JSON_LECTURE_DAYS, lec.getDays())
                        .add(JSON_LECTURE_ROOM, lec.getRoom())
                        .add(JSON_LECTURE_TIME, lec.getTime())
                        .build();
                lecturesBuilder.add(lecJson);
            }
            JsonArray lecturesArray = lecturesBuilder.build();

            JsonArrayBuilder recitationsBuilder = Json.createArrayBuilder();
            Iterator<Recitation> recitationsIterator = dataManager.recitationIterator();
            while(recitationsIterator.hasNext()){
                Recitation rec = recitationsIterator.next();
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_RECITATION_SECTION, rec.getRsection())
                        .add(JSON_RECITATION_DAYS, rec.getRecDaysAndTime())
                        .add(JSON_RECITATION_ROOM, rec.getRecRoom())
                        .add(JSON_RECITATION_TA1, rec.getRecTA1())
                        .add(JSON_RECITATION_TA2, rec.getRecTA2())
                        .build();
                recitationsBuilder.add(recJson);
            }
            JsonArray recitationsArray = recitationsBuilder.build();

            JsonArrayBuilder labsBuilder = Json.createArrayBuilder();
            Iterator<Lab> labsIterator = dataManager.labIterator();
            while(labsIterator.hasNext()){
                Lab lab = labsIterator.next();
                JsonObject labJson = Json.createObjectBuilder()
                        .add(JSON_LAB_SECTION, lab.getLsection())
                        .add(JSON_LAB_DAYS, lab.getLdaysAndTime())
                        .add(JSON_LAB_ROOM, lab.getLroom())
                        .add(JSON_LAB_TA1, lab.getLta1())
                        .add(JSON_LAB_TA2, lab.getLta2())
                        .build();
                labsBuilder.add(labJson);
            }
            JsonArray labsArray = labsBuilder.build();
            
            JsonObject sectionsData = Json.createObjectBuilder()
                    .add(JSON_LECTURES, lecturesArray)
                    .add(JSON_RECITATIONS, recitationsArray)
                    .add(JSON_LABS, labsArray)
                    .build();
            
            
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(sectionsData);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(newFile);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(sectionsData);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(newFile);
	pw.write(prettyPrinted);
	pw.close();
            
            
            
        }catch (Exception e){
            System.out.println("error");
        }
        
        
        
        
        try{
            String newFile = exportDir+"\\js\\OfficeHoursData.json";
            boolean success = (
                    new File(newFile).createNewFile());
            
            if(success){
                System.out.println("OfficeHoursData.json created");
            }
            else
                System.out.println("OfficeHoursData.json already exists");
            
            FileWriter writer = new FileWriter(newFile);
            
            Instructor instructorToSave = dataManager.getInstructor();
            String name = instructorToSave.getName();
            String room = instructorToSave.getRoom();
            String email = instructorToSave.getEmail();
            String homePage = instructorToSave.getHomePage();
            String officeHours = instructorToSave.getOfficeHours();

            JsonReader reader = Json.createReader(new StringReader(dataManager.getInstructorHours()));
            JsonArray hours = reader.readArray();
            
            JsonObject instructorBuilder = Json.createObjectBuilder()
                    .add(JSON_INSTRUCTOR_NAME_TEXT, name)
                    .add(JSON_INSTRUCTOR_HOME_PAGE_TEXT, homePage)
                    .add(JSON_INSTRUCTOR_EMAIL_TEXT, email)
                    .add(JSON_INSTRUCTOR_ROOM_TEXT, room)
                    .add(JSON_INSTRUCTOR_PHOTO_TEXT, "./images/RichardMcKenna.jpg")
                    .add(JSON_INSTRUCTOR_OFFICE_HOURS_TEXT, hours)
                    .build();
            
            
            // NOW BUILD THE TA JSON OBJCTS TO SAVE
            JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
            Iterator<TeachingAssistantPrototype> tasIterator = dataManager.teachingAssistantsIterator();
            while (tasIterator.hasNext()) {
                TeachingAssistantPrototype ta = tasIterator.next();
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .build();
                taArrayBuilder.add(taJson);
            }
            JsonArray undergradTAsArray = taArrayBuilder.build();

            // NOW BUILD THE OFFICE HOURS JSON OBJCTS TO SAVE
            JsonArrayBuilder officeHoursArrayBuilder = Json.createArrayBuilder();
            Iterator<TimeSlot> timeSlotsIterator = dataManager.officeHoursIterator();
            while (timeSlotsIterator.hasNext()) {
                TimeSlot timeSlot = timeSlotsIterator.next();
                for (int i = 0; i < DayOfWeek.values().length; i++) {
                    DayOfWeek dow = DayOfWeek.values()[i];
                    tasIterator = timeSlot.getTAsIterator(dow);
                    while (tasIterator.hasNext()) {
                        TeachingAssistantPrototype ta = tasIterator.next();
                        JsonObject tsJson = Json.createObjectBuilder()
                            .add(JSON_DAY_OF_WEEK, dow.toString())
                            .add(JSON_START_TIME, timeSlot.getStartTime().replace(":", "_"))
                            .add(JSON_NAME, ta.getName())
                                .build();
                        officeHoursArrayBuilder.add(tsJson);
                    }
                }
            }
            JsonArray officeHoursArray = officeHoursArrayBuilder.build();
            
            JsonObject officeHoursData = Json.createObjectBuilder()
                    .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                    .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                    .add(JSON_INSTRUCTOR, instructorBuilder)
                    //OFFICE HOURS
                    .add(JSON_GRAD_TAS, undergradTAsArray)
                    .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                    .add(JSON_OFFICE_HOURS, officeHoursArray)
                    .build();
            
            
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(officeHoursData);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(newFile);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(officeHoursData);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(newFile);
	pw.write(prettyPrinted);
	pw.close();
            
        }catch (Exception e){
            System.out.println("error");
        }
        
        
        try{
            String newFile = exportDir+"\\js\\ScheduleData.json";
            boolean success = (
                    new File(newFile).createNewFile());
            
            if(success){
                System.out.println("ScheduleData.json created");
            }
            else
                System.out.println("ScheduleData.json already exists");
            
            FileWriter writer = new FileWriter(newFile);
            
            
            JsonArrayBuilder scheduleItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder holidayItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder lectureItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder homeworkItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder labItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder referenceItemsBuilder = Json.createArrayBuilder();
            JsonArrayBuilder recitationItemsBuilder = Json.createArrayBuilder();

            Iterator<ScheduleItem> scheduleItemsIterator = dataManager.scheduleItemsIterator();
            while(scheduleItemsIterator.hasNext()){
                ScheduleItem scheduleItem = scheduleItemsIterator.next();
//                JsonObject scheduleItemJson = Json.createObjectBuilder()
//                        .add(JSON_SCHEDULE_ITEM_TYPE, scheduleItem.getType())
//                        .add(JSON_SCHEDULE_ITEM_DATE, scheduleItem.getDate())
//                        .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
//                        .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
//                        .add(JSON_SCHEDULE_ITEM_LINK, scheduleItem.getLink())
//                        .build();
//                scheduleItemsBuilder.add(scheduleItemJson);
                if(scheduleItem.getType().equals("Holiday")){
                    JsonObject holiday = Json.createObjectBuilder()
                            .add("month", "9")
                            .add("day", "1")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .build();
                    holidayItemsBuilder.add(holiday);
                }
                else if(scheduleItem.getType().equals("Lecture")){
                    JsonObject lecture = Json.createObjectBuilder()
                            .add("month", "9")
                            .add("day", "2")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .build();
                    lectureItemsBuilder.add(lecture);
                }
                else if(scheduleItem.getType().equals("Homework")){
                    JsonObject homework = Json.createObjectBuilder()
                            .add("month", "9")
                            .add("day", "3")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .add("time", "")
                            .add("criteria", "none")
                            .build();
                    homeworkItemsBuilder.add(homework);
                }
                else if(scheduleItem.getType().equals("Recitation")){
                    JsonObject recitation = Json.createObjectBuilder()
                            .add("month", "10")
                            .add("day", "17")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .build();
                    recitationItemsBuilder.add(recitation);
                }
                else if(scheduleItem.getType().equals("Lab")){
                    JsonObject lab = Json.createObjectBuilder()
                            .add("month", "11")
                            .add("day", "3")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .build();
                    labItemsBuilder.add(lab);
                }
                else if(scheduleItem.getType().equals("Reference")){
                    JsonObject references = Json.createObjectBuilder()
                            .add("month", "11")
                            .add("day", "16")
                            .add(JSON_SCHEDULE_ITEM_TITLE, scheduleItem.getTitle())
                            .add(JSON_SCHEDULE_ITEM_TOPIC, scheduleItem.getTopic())
                            .add(JSON_SCHEDULE_ITEM_LINK, "none")
                            .build();
                    referenceItemsBuilder.add(references);
                            
                }
            }
            JsonArray scheduleItems = scheduleItemsBuilder.build();
            JsonArray holidayItems = holidayItemsBuilder.build();
            JsonArray lectureItems = lectureItemsBuilder.build();
            JsonArray homeworkItems = homeworkItemsBuilder.build();
            JsonArray recitationItems = recitationItemsBuilder.build();
            JsonArray labItems = labItemsBuilder.build();
            JsonArray referenceItems = referenceItemsBuilder.build();

            JsonObject scheduleData = Json.createObjectBuilder()
                    .add("startingMondayMonth", "8")
                    .add("startingMondayDay", "27")
                    .add("endingFridayMonth", "12")
                    .add("endingFridayDay", "12")
                    .add(JSON_SCHEDULE_HOLIDAYS, holidayItems)
                    .add("lectures", lectureItems)
                    .add(JSON_SCHEDULE_HOMEWORKS, homeworkItems)
                    .add("recitations", recitationItems)
                    .add("labs", labItems)
                    .add(JSON_SCHEDULE_REFERENCES, referenceItems)

                    .build();
            
            
            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(scheduleData);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(newFile);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(scheduleData);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(newFile);
	pw.write(prettyPrinted);
	pw.close();
        
        }catch (Exception e){
            System.out.println("error");
        }
    }
    
}