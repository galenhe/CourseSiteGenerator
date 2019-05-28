/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;


import csg.CourseSiteGeneratorApp;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppNodesBuilder;
import csg.clipboard.CourseSiteGeneratorClipboard;
import java.awt.Rectangle;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;
import djf.components.AppWorkspaceComponent;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import csg.CourseSiteGeneratorApp;
import csg.CourseSiteGeneratorPropertyType;
import static csg.CourseSiteGeneratorPropertyType.*;
import csg.data.CourseSiteGeneratorData;
import csg.data.Lab;
import csg.data.Lecture;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.workspace.controllers.CourseSiteGeneratorController;
import csg.workspace.foolproof.CourseSiteGeneratorFoolproofDesign;
import static csg.workspace.style.OHStyle.*;
import static djf.AppPropertyType.COPY_BUTTON;
import static djf.AppPropertyType.CUT_BUTTON;
import static djf.AppPropertyType.PASTE_BUTTON;
import static djf.AppTemplate.PATH_WORK;
import djf.components.AppClipboardComponent;
import static djf.modules.AppGUIModule.ENABLED;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.swing.JFileChooser;



/**
 *
 * @author Galen
 */
public class CourseSiteGeneratorWorkspace extends AppWorkspaceComponent{
    
    public CourseSiteGeneratorWorkspace(CourseSiteGeneratorApp app){
        super(app);
        
        initLayout();
        
        initControllers();
        
        initFoolproofDesign();
        
        initDialogs();
    }

    private void initLayout(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        
        Insets myInsets = new Insets(50,50,50,50);
        
//SITE TAB  -  FIRST 
        Tab site = new Tab();
        site.setText("Site");
        
        ScrollPane siteScroll = new ScrollPane();
        VBox sitePane = csgBuilder.buildVBox(CSG_SITE_PANE, null, CLASS_CSG_FIRST_PANE, ENABLED);
        VBox.setVgrow(sitePane, Priority.ALWAYS);
        siteScroll.setContent(sitePane);
        GridPane banner = csgBuilder.buildGridPane(CSG_BANNER_PANE, sitePane, CLASS_CSG_SECOND_PANE, ENABLED);
        csgBuilder.buildLabel(CSG_BANNER_HEADER, banner, 0, 0, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        banner.setHgap(10);
        banner.setVgap(10);
        
        ArrayList<String> numbers = new ArrayList<>();
        ObservableList<String> options = FXCollections.observableArrayList(
            "Option 1",
            "Option 2",
            "Option 3"
        );
        
    //BANNER PANE
        csgBuilder.buildLabel(CSG_SUBJECT_LABEL, banner, 0, 1, 2, 2, CLASS_CSG_LABEL, ENABLED);
        ComboBox subject = csgBuilder.buildComboBox(CSG_SUBJECT_COMBO_BOX, banner, 2, 1, 2, 2, CLASS_CSG_COMBO_BOX, ENABLED, GLOBAL_SUBJECT_OPTIONS, FIRST_SUBJECT);
        //subject.setValue("");
        subject.setEditable(ENABLED);
        
        //Image faviconImage = new Image("file:images/favicon.jpg");
//        File globalSubjects = new File("file:work/global_subject.txt");
//        String filePath= PATH_WORK + "global_subject.txt"; 
//        
//        ObservableList<String> subjectOptions;
//        subjectOptions = FXCollections.observableArrayList();
//        try{
//            File file = new File(filePath); 
//            Scanner sc = new Scanner(file); 
//            
//            
//            while (sc.hasNextLine()) {
//                subjectOptions.add(sc.nextLine());
//                //System.out.println(sc.nextLine());
//            }
//        }
//        catch (FileNotFoundException e){
//            System.out.println("File Not found");
//        }
//        
//        subject.getItems().addAll(subjectOptions);
//        
        
        csgBuilder.buildLabel(CSG_NUMBER_LABEL, banner, 4, 1, 2, 2, CLASS_CSG_LABEL, ENABLED);
        ComboBox number = csgBuilder.buildComboBox(CSG_NUMBER_COMBO_BOX, banner, 6, 1, 2, 2, CLASS_CSG_COMBO_BOX, ENABLED, GLOBAL_NUMBER_OPTIONS, FIRST_NUMBER);
        number.setEditable(ENABLED);
        
        File globalNumbers = new File("file:work/global_number.txt");

        
        csgBuilder.buildLabel(CSG_SEMESTER_LABEL, banner, 0, 3, 2, 2, CLASS_CSG_LABEL, ENABLED);
        ComboBox semester = csgBuilder.buildComboBox(CSG_SEMESTER_COMBO_BOX, banner, 2, 3, 2, 2, CLASS_CSG_COMBO_BOX, ENABLED, GLOBAL_SEMESTER_OPTIONS, FIRST_SEMESTER);
        semester.setEditable(false);
        
        csgBuilder.buildLabel(CSG_YEAR_LABEL, banner, 4, 3, 2, 2, CLASS_CSG_LABEL, ENABLED);
        Calendar now = Calendar.getInstance();
        int thisYear = (now.get(Calendar.YEAR));
        int nextYear = (now.get(Calendar.YEAR)+1);
        ComboBox year = csgBuilder.buildComboBox(CSG_YEAR_COMBO_BOX, banner, 6, 3, 2, 2, CLASS_CSG_COMBO_BOX, ENABLED, options, thisYear);
        
        
        year.getItems().addAll(thisYear, nextYear);
        year.setValue(thisYear);
        year.setEditable(false);
        
        csgBuilder.buildLabel(CSG_TITLE_LABEL,banner, 0, 5, 2, 1, CLASS_CSG_LABEL, ENABLED);
        TextField titleTF = csgBuilder.buildTextField(CSG_TITLE_TEXT_FIELD, banner, 2, 5, 1, 1, CLASS_CSG_TEXT_FIELD, ENABLED);
        titleTF.setText("");
        
        csgBuilder.buildLabel(CSG_EXPORT_DIR_LABEL, banner, 0,6,2,1, CLASS_CSG_LABEL, ENABLED);
        Label exportDir = csgBuilder.buildLabel(CSG_EXPORT_DIR_LINK, banner, 2, 6, 2, 1, CLASS_CSG_LABEL, ENABLED);
        exportDir.setText(".\\export\\");
        banner.setPadding(new Insets(10,10,10,10)); // add this to each grid pane
        //sitePane.setMargin(banner, new Insets(10,10,10,10)); // do this for each grid pane ,changing the first parameter
        
    //PAGES PANE
        HBox pages = csgBuilder.buildHBox(CSG_PAGES_PANE, sitePane, CLASS_CSG_SECOND_PANE, ENABLED);
        pages.setPadding(new Insets(10,10,10,10)); // add this to each grid pane
        pages.setSpacing(10);
        sitePane.setMargin(pages, new Insets(10,10,10,10)); // do this for each grid pane ,changing the first parameter
        
        csgBuilder.buildLabel(CSG_PAGES_HEADER, pages, CLASS_CSG_HEADER_LABEL, ENABLED);
        CheckBox homeCB = csgBuilder.buildCheckBox(CSG_HOME_CHECK_BOX, pages, CLASS_CSG_CHECK_BOX, ENABLED);
        homeCB.setText("Home");
        //csgBuilder.buildLabel(CSG_PAGES_HOME_LABEL, pages, CLASS_CSG_LABEL, ENABLED);
        
        CheckBox syllabusCB = csgBuilder.buildCheckBox(CSG_SYLLABUS_CHECK_BOX, pages, CLASS_CSG_CHECK_BOX, ENABLED);
        syllabusCB.setText("Syllabus");
        //csgBuilder.buildLabel(CSG_PAGES_SYLLABUS_LABEL, pages, CLASS_CSG_LABEL, ENABLED);
        CheckBox scheduleCB = csgBuilder.buildCheckBox(CSG_SCHEDULE_CHECK_BOX, pages, CLASS_CSG_CHECK_BOX, ENABLED);
        scheduleCB.setText("Schedule");
        //csgBuilder.buildLabel(CSG_PAGES_SCHEDULE_LABEL, pages, CLASS_CSG_LABEL, ENABLED);
        CheckBox homeworkCB = csgBuilder.buildCheckBox(CSG_HOMEWORK_CHECK_BOX, pages, CLASS_CSG_CHECK_BOX, ENABLED);
        homeworkCB.setText("Homework");
        //csgBuilder.buildLabel(CSG_PAGES_HOMEWORK_LABEL, pages, CLASS_CSG_LABEL, ENABLED);
        
    //STLYE PANE
        GridPane style = csgBuilder.buildGridPane(CSG_STYLE_PANE, sitePane, CLASS_CSG_SECOND_PANE, ENABLED);
        style.setHgap(10);
        style.setVgap(10);
        //csgBuilder.buildLabel(CSG_STYLE_HEADER, style, CLASS_CSG_HEADER_LABEL, ENABLED);
        csgBuilder.buildLabel(CSG_STYLE_HEADER, style, 0, 0, 2, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        style.setPadding(new Insets(10,10,10,10)); // add this to each grid pane
        sitePane.setMargin(style, new Insets(10,10,10,10)); // do this for each grid pane ,changing the first parameter
        
        Button favicon = csgBuilder.buildTextButton(CSG_FAVICON_ICON_BUTTON, style, 0, 1, 2, 1, CLASS_CSG_SITE_STYLE_BUTTON, ENABLED);
        Button navbar = csgBuilder.buildTextButton(CSG_NAVBAR_ICON_BUTTON, style,0,2,2,1, CLASS_CSG_SITE_STYLE_BUTTON, ENABLED);
        Button leftFooter= csgBuilder.buildTextButton(CSG_LEFT_FOOTER_ICON_BUTTON, style, 0,3,2,1, CLASS_CSG_SITE_STYLE_BUTTON, ENABLED);
        Button rightFooter= csgBuilder.buildTextButton(CSG_RIGHT_FOOTER_ICON_BUTTON, style, 0,4,2,1, CLASS_CSG_SITE_STYLE_BUTTON, ENABLED);

        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"),
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
            new ExtensionFilter("All Files", "*.*"));
//        File selectedFile = fileChooser.showOpenDialog(mainStage);
//        if (selectedFile != null) {
//           mainStage.display(selectedFile);
//        }
        //style.add(fileChooser, 1, 1);
        
        final JFileChooser fc = new JFileChooser();
        //style.add(fc,1,1);
        
        System.out.println(System.getProperty("user.dir"));
        //System.out.println(System.getProperty("user.dir").getParentFile().getName());
        
        Image faviconImage = new Image("file:images/favicon.jpg");
        Image navbarImage = new Image("file:images/SBUDarkRedShieldLogo.png");
        Image leftFooterImage = new Image("file:images/SBUDarkRedShieldLogo.png");
        Image rightFooterImage = new Image("file:images/SBUCSLogo.png");
        
        ImageView pic1 = new ImageView(faviconImage);
        ImageView pic2 = new ImageView(navbarImage);
        ImageView pic3 = new ImageView(leftFooterImage);
        ImageView pic4 = new ImageView(rightFooterImage);
        
        
        
        style.add(pic1, 1, 1, 2, 1);
        style.add(pic2, 1, 2, 2, 1);
        style.add(pic3, 1, 3, 2, 1);
        style.add(pic4, 1, 4, 2, 1);
        
        
        csgBuilder.buildLabel(CSG_FONTS_AND_COLORS_LABEL, style, 0, 5, 1, 1, CLASS_CSG_LABEL, ENABLED);
        ComboBox fontsColors = csgBuilder.buildComboBox(CSG_FONTS_AND_COLORS_COMBO_BOX, style, 1, 5, 1, 1, CLASS_CSG_COMBO_BOX, ENABLED, options, options.get(0));
        ObservableList<String> styleSelection = FXCollections.observableArrayList();
        styleSelection.add("galaxy.css");
        styleSelection.add("sea_wolf.css");
        fontsColors.getItems().addAll(styleSelection);
        fontsColors.setValue("galaxy.css");
        File folder = new File("file:work/*");
        File[] files = folder.listFiles();
        System.out.println("Here is files: " +files);
//        for(int i=0; i < files.length ; i++){
//            System.out.println(files[i].getName());
//        }
//        
        
        csgBuilder.buildLabel(CSG_STYLE_NOTE, style, 0, 6, 1, 1, CLASS_CSG_LABEL, ENABLED);
        
        ImageView x = csgBuilder.buildImageView(CSG_TEST_IMAGE_VIEW, leftFooterImage, style, 1, 7, 2, 1, CLASS_CSG_IMAGE_VIEW, ENABLED);
        
    //Instructtor Pane
        GridPane instructor = csgBuilder.buildGridPane(CSG_INSTRUCTOR_PANE, sitePane, CLASS_CSG_SECOND_PANE, ENABLED);
        instructor.setHgap(10);
        instructor.setVgap(10);
        csgBuilder.buildLabel(CSG_INSTRUCTOR_HEADER, instructor, 0, 0, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        instructor.setPadding(new Insets(10,10,10,10)); // add this to each grid pane
        sitePane.setMargin(instructor, new Insets(10,10,10,10)); // do this for each grid pane ,changing the first parameter

        
        Label nameLabel = csgBuilder.buildLabel(CSG_INSTRUCTOR_NAME_TEXT_FIELD_HEADER, instructor, 0, 1, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        TextField nameTF = csgBuilder.buildTextField(CSG_INSTRUCTOR_NAME_TEXT_FIELD, instructor, 1, 1, 2, 1, CLASS_CSG_TEXT_FIELD, ENABLED);
        nameLabel.prefWidthProperty().bind(instructor.widthProperty().multiply(1.0 / 8.0));
        nameTF.prefWidthProperty().bind(instructor.widthProperty().multiply(3.0 / 8.0));
        nameTF.setText("hello");
        
        Label roomLabel = csgBuilder.buildLabel(CSG_INSTRUCTOR_ROOM_TEXT_FIELD_HEADER, instructor, 3, 1, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        TextField roomTF = csgBuilder.buildTextField(CSG_INSTRUCTOR_ROOM_TEXT_FIELD, instructor, 4, 1, 1, 1, CLASS_CSG_TEXT_FIELD, ENABLED);
        roomLabel.prefWidthProperty().bind(instructor.widthProperty().multiply(1.0 / 8.0));
        roomTF.prefWidthProperty().bind(instructor.widthProperty().multiply(3.0 / 8.0));
        
        Label emailLabel = csgBuilder.buildLabel(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD_HEADER, instructor, 0, 2, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        TextField emailTF = csgBuilder.buildTextField(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD, instructor, 1, 2, 1, 1, CLASS_CSG_TEXT_FIELD, ENABLED);
        emailLabel.prefWidthProperty().bind(instructor.widthProperty().multiply(1.0 / 8.0));
        emailTF.prefWidthProperty().bind(instructor.widthProperty().multiply(3.0 / 8.0));
        
        
        Label homeLabel = csgBuilder.buildLabel(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD_HEADER, instructor, 3, 2, 1, 1, CLASS_CSG_HEADER_LABEL, ENABLED);
        TextField homeTF = csgBuilder.buildTextField(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD, instructor, 4, 2, 1, 1, CLASS_CSG_TEXT_FIELD, ENABLED);
        homeLabel.prefWidthProperty().bind(instructor.widthProperty().multiply(1.0 / 8.0));
        homeTF.prefWidthProperty().bind(instructor.widthProperty().multiply(3.0 / 8.0));
        
        
        TextArea text = csgBuilder.buildTextArea(CSG_INSTRUCTOR_OFFICE_HOURS, null, CLASS_CSG_TEXT_AREA, ENABLED);
        text.setPromptText("Instructor Office Hours");
        //text.setText("{\n {day  } \n {day   } \n {day  } \n}");
        //TextArea dayText = new TextArea("{\n {day  } \n {day   } \n {day  } \n}");
        HBox titledPaneBox = csgBuilder.buildHBox(CSG_INSTRUCTOR_OFFICE_HOURS_BOX, instructor,0,4,5,1, CLASS_CSG_PANE, ENABLED);
        
        TitledPane expand = new TitledPane("Office Hours", text);
        expand.setExpanded(true);
        titledPaneBox.getChildren().add(expand);
        expand.prefWidthProperty().bind(instructor.widthProperty());
                

        
//SYLLABUS TAB - SECOND
        Tab syllabus = new Tab();
        syllabus.setText("Syllabus Tab");
        
        VBox syllabusPane = csgBuilder.buildVBox(CSG_SYLLABUS_PANE, null, CLASS_CSG_FIRST_PANE, ENABLED);
        VBox.setVgrow(syllabusPane, Priority.ALWAYS);
        syllabusPane.setMargin(banner, new Insets(10,10,10,10));
        syllabusPane.setSpacing(10);
        
        VBox descriptionPane = csgBuilder.buildVBox(CSG_DESCRIPTION_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox topicsPane = csgBuilder.buildVBox(CSG_TOPICS_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox prerequisitesPane = csgBuilder.buildVBox(CSG_PREREQUISITES_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox outcomesPane = csgBuilder.buildVBox(CSG_OUTCOMES_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox textbooksPane = csgBuilder.buildVBox(CSG_TEXTBOOKS_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox gradedComponentsPane = csgBuilder.buildVBox(CSG_GRADED_COMPONENTS_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox gradingNotePane = csgBuilder.buildVBox(CSG_GRADING_NOTE_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox academicDishonestyPane = csgBuilder.buildVBox(CSG_ACADEMIC_DISHONESTY_PANE, syllabusPane, CLASS_CSG_TITLED_PANE, ENABLED);
        VBox specialAssistancePane = csgBuilder.buildVBox(CSG_SPECIAL_ASSISTANCE_PANE, syllabusPane, CLASS_CSG_SECOND_PANE, ENABLED);
        
        TextArea description = csgBuilder.buildTextArea(CSG_DESCRIPTION_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        description.setPadding(new Insets(10,10,10,10));
        TitledPane descriptionExpand = new TitledPane("Description", description);
        descriptionPane.getChildren().add(descriptionExpand);
        //description.setText("Here is a description");
        
        TextArea topics = csgBuilder.buildTextArea(CSG_TOPICS_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        topics.setPadding(new Insets(10,10,10,10));
        TitledPane topicsExpand = new TitledPane("Topics", topics); 
        topicsPane.getChildren().add(topicsExpand);
        topicsExpand.setExpanded(true);
        
        TextArea prerequisites = csgBuilder.buildTextArea(CSG_PREREQUISITES_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        prerequisites.setPadding(new Insets(10,10,10,10));
        TitledPane prerequisitesExpand = new TitledPane("Prerequisites", prerequisites); 
        prerequisitesPane.getChildren().add(prerequisitesExpand);
        prerequisitesExpand.setExpanded(true);
        
        TextArea outcomes = csgBuilder.buildTextArea(CSG_OUTCOMES_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        outcomes.setPadding(new Insets(10,10,10,10));
        TitledPane outcomesExpand = new TitledPane("Outcomes", outcomes); 
        outcomesPane.getChildren().add(outcomesExpand);
        outcomesExpand.setExpanded(false);
        
        TextArea textbooks = csgBuilder.buildTextArea(CSG_TEXTBOOKS_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        textbooks.setPadding(new Insets(10,10,10,10));
        TitledPane textbooksExpand = new TitledPane("Textbooks",textbooks);
        textbooksPane.getChildren().add(textbooksExpand);
        textbooksExpand.setExpanded(false);
        
        TextArea gradedComponents = csgBuilder.buildTextArea(CSG_GRADED_COMPONENTS_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        gradedComponents.setPadding(new Insets(10,10,10,10));
        TitledPane gradedComponentsExpand = new TitledPane("Graded Components", gradedComponents);
        gradedComponentsPane.getChildren().add(gradedComponentsExpand);
        gradedComponentsExpand.setExpanded(false);
        
        TextArea gradingNote = csgBuilder.buildTextArea(CSG_GRADING_NOTE_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        gradingNote.setPadding(new Insets(10,10,10,10));
        TitledPane gradingNoteExpand = new TitledPane("GradingNote", gradingNote);
        gradingNotePane.getChildren().add(gradingNoteExpand);
        gradingNoteExpand.setExpanded(false);
        
        TextArea academicDishonesty = csgBuilder.buildTextArea(CSG_ACADEMIC_DISHONESTY_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        academicDishonesty.setPadding(new Insets(10,10,10,10));
        TitledPane academicDishonestyExpand = new TitledPane("Academic Dishonest", academicDishonesty);
        academicDishonestyPane.getChildren().add(academicDishonestyExpand);
        academicDishonestyExpand.setExpanded(false);
        
        TextArea specialAssistance = csgBuilder.buildTextArea(CSG_SPECIAL_ASSISTANCE_TEXT_AREA, descriptionPane, CLASS_CSG_TEXT_AREA, ENABLED);
        specialAssistance.setPadding(new Insets(10,10,10,10));
        TitledPane specialAssistanceExpand = new TitledPane("Special Assistance", specialAssistance);
        specialAssistancePane.getChildren().add(specialAssistanceExpand);
        specialAssistanceExpand.setExpanded(false);
        
//MEETING TIMES TAB - THIRD
        Tab meetingTimes = new Tab();
        meetingTimes.setText("Meeting Times");
        
        VBox meetingTimesPane = csgBuilder.buildVBox(CSG_MEETING_TIMES_PANE, null, CLASS_CSG_FIRST_PANE, ENABLED);
        VBox.setVgrow(meetingTimesPane, Priority.ALWAYS);
        VBox lecturesPane = csgBuilder.buildVBox(CSG_LECTURES_PANE, meetingTimesPane, CLASS_CSG_SECOND_PANE, ENABLED);
        VBox recitationsPane = csgBuilder.buildVBox(CSG_RECITATIONS_PANE, meetingTimesPane, CLASS_CSG_SECOND_PANE, ENABLED);
        VBox labsPane = csgBuilder.buildVBox(CSG_LABS_PANE, meetingTimesPane, CLASS_CSG_SECOND_PANE, ENABLED);
        
        VBox inLecturesT = csgBuilder.buildVBox(CSG_INLECTUREST, null, CLASS_CSG_PANE, ENABLED);
        
        HBox lectures3 = csgBuilder.buildHBox(CSG_LECTURES_H_BOX, inLecturesT, CLASS_CSG_PANE, ENABLED);
        lectures3.setSpacing(10);
        lectures3.setPadding(new Insets(10,10,10,10));
        csgBuilder.buildLabel(CSG_LECTURES_HEADER, lectures3, CLASS_CSG_LABEL, ENABLED);
        Button addLecture = csgBuilder.buildTextButton(CSG_ADD_LECTURE_BUTTON, lectures3, CLASS_CSG_BUTTON, ENABLED);
        Button removeLecture = csgBuilder.buildTextButton(CSG_REMOVE_LECTURE_BUTTON, lectures3, CLASS_CSG_BUTTON, ENABLED);
        addLecture.setText("Add Lec");
        removeLecture.setText("Remove Lec");
        
        TableView<Lecture> lecturesTable = csgBuilder.buildTableView(CSG_LECTURES_TABLE_VIEW, inLecturesT, CLASS_CSG_TABLE_VIEW, ENABLED);
        lecturesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lecturesTable.setEditable(true);
        TableColumn sectionColumn = csgBuilder.buildTableColumn(CSG_esSECTION_TABLE_COLUMN, lecturesTable, CLASS_CSG_COLUMN);
        sectionColumn.setEditable(ENABLED);
        TableColumn daysColumn = csgBuilder.buildTableColumn(CSG_DAYS_TABLE_COLUMN, lecturesTable, CLASS_CSG_COLUMN);
        TableColumn timeColumn = csgBuilder.buildTableColumn(CSG_TIME_TABLE_COLUMN, lecturesTable, CLASS_CSG_CENTERED_COLUMN);
        TableColumn roomColumn = csgBuilder.buildTableColumn(CSG_ROOM_TABLE_COLUMN, lecturesTable, CLASS_CSG_COLUMN);
        sectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<String, String>("days"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("time"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        sectionColumn.prefWidthProperty().bind(lecturesTable.widthProperty().multiply(1.0 / 4.0));
        daysColumn.prefWidthProperty().bind(lecturesTable.widthProperty().multiply(1.0 / 4.0));
        timeColumn.prefWidthProperty().bind(lecturesTable.widthProperty().multiply(1.0 / 4.0));
        roomColumn.prefWidthProperty().bind(lecturesTable.widthProperty().multiply(1.0 / 4.0));
        sectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        daysColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
        
        
        VBox inRecitationsT = csgBuilder.buildVBox(CSG_INRECITATIONST, null, CLASS_CSG_PANE, ENABLED);
        HBox recitations3 = csgBuilder.buildHBox(CSG_LECTURES_H_BOX, inRecitationsT, CLASS_CSG_PANE, ENABLED);
        recitations3.setSpacing(10);
        recitations3.setPadding(new Insets(10,10,10,10));
        csgBuilder.buildLabel(CSG_RECITATIONS_HEADER, recitations3, CLASS_CSG_LABEL, ENABLED);
        Button addRec = csgBuilder.buildTextButton(CSG_ADD_RECITATION_BUTTON, recitations3, CLASS_CSG_BUTTON, ENABLED);
        Button removeRec = csgBuilder.buildTextButton(CSG_REMOVE_RECITATION_BUTTON, recitations3, CLASS_CSG_BUTTON, ENABLED);
        addRec.setText("Add Lec");
        removeRec.setText("Remove Lec");
        
        TableView<Recitation> recitationsTable = csgBuilder.buildTableView(CSG_RECITATIONS_TABLE_VIEW, inRecitationsT , CLASS_CSG_TABLE_VIEW, ENABLED);
        recitationsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        recitationsTable.setEditable(ENABLED);
        TableColumn rsectionColumn = csgBuilder.buildTableColumn(CSG_RSECTION_TABLE_COLUMN, recitationsTable, CLASS_CSG_COLUMN);
        TableColumn rdaysTimeColumn = csgBuilder.buildTableColumn(CSG_RDAYS_TIME_TABLE_COLUMN, recitationsTable, CLASS_CSG_COLUMN);
        TableColumn rroomColumn = csgBuilder.buildTableColumn(CSG_RROOM_TABLE_COLUMN, recitationsTable, CLASS_CSG_CENTERED_COLUMN);
        TableColumn rTA1Column = csgBuilder.buildTableColumn(CSG_RTA1_TABLE_COLUMN, recitationsTable, CLASS_CSG_COLUMN);
        TableColumn rTA2Column = csgBuilder.buildTableColumn(CSG_RTA2_TABLE_COLUMN, recitationsTable, CLASS_CSG_COLUMN);
        rsectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("rsection"));
        rdaysTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("recDaysAndTime"));
        rroomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("recRoom"));
        rTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("recTA1"));
        rTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("recTA2"));
        rsectionColumn.prefWidthProperty().bind(recitationsTable.widthProperty().multiply(1.0 / 5.0));
        rdaysTimeColumn.prefWidthProperty().bind(recitationsTable.widthProperty().multiply(1.0 / 5.0));
        rroomColumn.prefWidthProperty().bind(recitationsTable.widthProperty().multiply(1.0 / 5.0));
        rTA1Column.prefWidthProperty().bind(recitationsTable.widthProperty().multiply(1.0 / 5.0));
        rTA2Column.prefWidthProperty().bind(recitationsTable.widthProperty().multiply(1.0 / 5.0));
        rsectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rdaysTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rTA1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        rTA2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        rroomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
       
        VBox inLabsT = csgBuilder.buildVBox(CSG_INLABST, null, CLASS_CSG_PANE, ENABLED);
        HBox labs3 = csgBuilder.buildHBox(CSG_LABS_H_BOX, inLabsT, CLASS_CSG_PANE, ENABLED);
        labs3.setSpacing(10);
        labs3.setPadding(new Insets(10,10,10,10));
        csgBuilder.buildLabel(CSG_LABS_HEADER, labs3, CLASS_CSG_LABEL, ENABLED);
        Button addLab = csgBuilder.buildTextButton(CSG_ADD_LAB_BUTTON, labs3, CLASS_CSG_BUTTON, ENABLED);
        Button removeLab = csgBuilder.buildTextButton(CSG_REMOVE_LAB_BUTTON, labs3, CLASS_CSG_BUTTON, ENABLED);
        addLab.setText("Add Lab");
        removeLab.setText("Remove Lab");
        
        
        //csgBuilder.buildLabel(CSG_LABS_HEADER, labsPane, CLASS_CSG_LABEL, ENABLED);
        
        TableView<Lab> labsTable = csgBuilder.buildTableView(CSG_LABS_TABLE_VIEW, inLabsT, CLASS_CSG_TABLE_VIEW, ENABLED);
        labsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        labsTable.setEditable(ENABLED);
        TableColumn lsectionColumn = csgBuilder.buildTableColumn(CSG_LSECTION_TABLE_COLUMN, labsTable, CLASS_CSG_COLUMN);
        TableColumn ldaysTimeColumn = csgBuilder.buildTableColumn(CSG_LDAYS_TIME_TABLE_COLUMN, labsTable, CLASS_CSG_COLUMN);
        TableColumn lroomColumn = csgBuilder.buildTableColumn(CSG_LROOM_TABLE_COLUMN, labsTable, CLASS_CSG_CENTERED_COLUMN);
        TableColumn lTA1Column = csgBuilder.buildTableColumn(CSG_LTA1_TABLE_COLUMN, labsTable, CLASS_CSG_COLUMN);
        TableColumn lTA2Column = csgBuilder.buildTableColumn(CSG_LTA2_TABLE_COLUMN, labsTable, CLASS_CSG_COLUMN);
        lsectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lsection"));
        ldaysTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("ldaysAndTime"));
        lroomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("lroom"));
        lTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("lta1"));
        lTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("lta2"));
        lsectionColumn.prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0 / 5.0));
        ldaysTimeColumn.prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0 / 5.0));
        lroomColumn.prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0 / 5.0));
        lTA1Column.prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0 / 5.0));
        lTA2Column.prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0 / 5.0));
        lsectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ldaysTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lTA1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        lTA2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        lroomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
       
        TitledPane lecturesExpand = new TitledPane("Lectures", inLecturesT);
        lecturesPane.getChildren().add(lecturesExpand);
        
        TitledPane recitationsExpand = new TitledPane("Recitations", inRecitationsT);
        recitationsPane.getChildren().add(recitationsExpand);
        recitationsExpand.setExpanded(true);
        
        TitledPane labsExpand = new TitledPane("Labs", inLabsT);
        labsPane.getChildren().add(labsExpand);
        labsExpand.setExpanded(false);
        
        
        
//OFFICE HOURS TAB - FOURTH
        Tab officeHours = new Tab();
        officeHours.setText("Office Hours");
        
        VBox officeHoursPane = csgBuilder.buildVBox(CSG_OFFICE_HOURS_PANE, null, CLASS_CSG_FIRST_PANE, ENABLED);
        VBox.setVgrow(officeHoursPane, Priority.ALWAYS);
        officeHoursPane.setSpacing(10);
        
        GridPane tasPane = csgBuilder.buildGridPane(CSG_TAS_PANE, officeHoursPane, CLASS_CSG_SECOND_PANE,ENABLED);
        tasPane.setPadding(new Insets(10,10,10,10));
        tasPane.setHgap(10);
        tasPane.setVgap(10);
        
        HBox tasType = csgBuilder.buildHBox(CSG_TAS, tasPane, 0, 0, 1, 1, CLASS_CSG_PANE, ENABLED);
        //HBox tasType = csgBuilder.buildHBox(CSG_TAS, tasPane, CLASS_CSG_SECOND_PANE, ENABLED);
        tasType.setSpacing(10);
        officeHoursPane.setMargin(tasType, new Insets(10,10,10,10));
        
        Button removeTA = csgBuilder.buildTextButton(CSG_REMOVE_TA_BUTTON, tasType, CLASS_CSG_BUTTON, ENABLED);
        csgBuilder.buildLabel(CSG_TAs_HEADER , tasType, CLASS_CSG_HEADER_LABEL, ENABLED);
        ToggleGroup type = new ToggleGroup();
        RadioButton all = csgBuilder.buildRadioButton(CSG_ALL_RADIO_BUTTON, tasType, CLASS_CSG_RADIO_BUTTON, ENABLED, type, ENABLED);
        RadioButton under= csgBuilder.buildRadioButton(CSG_UNDERGRADUATE_RADIO_BUTTON, tasType, CLASS_CSG_RADIO_BUTTON, ENABLED, type, ENABLED);
        csgBuilder.buildRadioButton(CSG_GRADUATE_RADIO_BUTTON, tasType, CLASS_CSG_RADIO_BUTTON, ENABLED, type, ENABLED);
        all.setSelected(true);
        
        TableView<TeachingAssistantPrototype> tasTable = csgBuilder.buildTableView(CSG_TAS_TABLE_VIEW, tasPane, 0, 1, 5, 1, CLASS_CSG_TABLE_VIEW, ENABLED);
        tasTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn taNameColumn = csgBuilder.buildTableColumn(CSG_TA_NAME_TABLE_COLUMN, tasTable, CLASS_CSG_COLUMN);
        TableColumn taEmailColumn = csgBuilder.buildTableColumn(CSG_TA_EMAIL_TABLE_COLUMN, tasTable, CLASS_CSG_COLUMN);
        TableColumn taTimeSlotsColumn = csgBuilder.buildTableColumn(CSG_TA_TIME_SLOTS_TABLE_COLUMN, tasTable, CLASS_CSG_CENTERED_COLUMN);
        TableColumn taTypeColumn = csgBuilder.buildTableColumn(CSG_TA_TYPE_TABLE_COLUMN, tasTable, CLASS_CSG_COLUMN);
        taNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        taEmailColumn.setCellValueFactory(new PropertyValueFactory<String, String>("email"));
        taTimeSlotsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("slots"));
        taTypeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        taNameColumn.prefWidthProperty().bind(tasTable.widthProperty().multiply(1.0 / 4.0));
        taEmailColumn.prefWidthProperty().bind(tasTable.widthProperty().multiply(1.0 / 4.0));
        taTimeSlotsColumn.prefWidthProperty().bind(tasTable.widthProperty().multiply(1.0 / 4.0));
        taTypeColumn.prefWidthProperty().bind(tasTable.widthProperty().multiply(1.0 / 4.0));
        tasTable.prefWidthProperty().bind(tasPane.widthProperty());

        VBox.setVgrow(tasTable, Priority.ALWAYS);
        
        HBox addTA = csgBuilder.buildHBox(CSG_ADD_TA_PANE, tasPane, 0,2 , 1, 1, CLASS_CSG_PANE, ENABLED);
        addTA.setSpacing(10);
        //ADDED DISABLE
        csgBuilder.buildTextField(CSG_TA_NAME_TEXT_FIELD, addTA, CLASS_CSG_TEXT_FIELD, !ENABLED);
        csgBuilder.buildTextField(CSG_TA_EMAIL_TEXT_FIELD, addTA, CLASS_CSG_TEXT_FIELD, !ENABLED);
        csgBuilder.buildTextButton(CSG_ADD_TA_BUTTON, addTA, CLASS_CSG_BUTTON, !ENABLED);
        
            
        
        GridPane officeHoursTablePane = csgBuilder.buildGridPane(CSG_OFFICE_HOURS_TABLE_PANE, officeHoursPane, CLASS_CSG_SECOND_PANE, ENABLED);
        officeHoursTablePane.setVgap(10);
        officeHoursTablePane.setHgap(10);
        officeHoursTablePane.setPadding(new Insets(10,10,10,10));
        
        csgBuilder.buildLabel(CSG_OFFICE_HOURS_HEADER, officeHoursTablePane, 0, 0, 1, 1, CLASS_CSG_LABEL, ENABLED);
        csgBuilder.buildLabel(CSG_OFFICE_HOURS_START_TIME_LABEL, officeHoursTablePane, 4, 0, 1, 1, CLASS_CSG_LABEL, ENABLED);
        ComboBox startTime = csgBuilder.buildComboBox(CSG_OFFICE_HOURS_START_TIME_COMBO_BOX, officeHoursTablePane, 5, 0, 1, 1, CLASS_CSG_COMBO_BOX, ENABLED, START_TIME_OPTIONS, DEFAULT_START_TIME);
        csgBuilder.buildLabel(CSG_OFFICE_HOURS_END_TIME_LABEL, officeHoursTablePane, 7, 0, 1, 1, CLASS_CSG_LABEL, ENABLED);
        ComboBox endTime = csgBuilder.buildComboBox(CSG_OFFICE_HOURS_END_TIME_COMBO_BOX, officeHoursTablePane, 8, 0, 1, 1, CLASS_CSG_COMBO_BOX, ENABLED, END_TIME_OPTIONS, DEFAULT_END_TIME);
        
        TableView<TimeSlot> officeHoursTable = csgBuilder.buildTableView(CSG_OFFICE_HOURS_TABLE_VIEW, officeHoursTablePane, 0,1,20,20, CLASS_CSG_OFFICE_HOURS_TABLE_VIEW, ENABLED);
        
        setupOfficeHoursColumn(CSG_START_TIME_TABLE_COLUMN, officeHoursTable, CLASS_CSG_TIME_COLUMN, "startTime");
        setupOfficeHoursColumn(CSG_END_TIME_TABLE_COLUMN, officeHoursTable, CLASS_CSG_TIME_COLUMN, "endTime");
        setupOfficeHoursColumn(CSG_MONDAY_TABLE_COLUMN, officeHoursTable, CLASS_CSG_DAY_OF_WEEK_COLUMN, "monday");
        setupOfficeHoursColumn(CSG_TUESDAY_TABLE_COLUMN, officeHoursTable, CLASS_CSG_DAY_OF_WEEK_COLUMN, "tuesday");
        setupOfficeHoursColumn(CSG_WEDNESDAY_TABLE_COLUMN, officeHoursTable, CLASS_CSG_DAY_OF_WEEK_COLUMN, "wednesday");
        setupOfficeHoursColumn(CSG_THURSDAY_TABLE_COLUMN, officeHoursTable, CLASS_CSG_DAY_OF_WEEK_COLUMN, "thursday");
        setupOfficeHoursColumn(CSG_FRIDAY_TABLE_COLUMN, officeHoursTable, CLASS_CSG_DAY_OF_WEEK_COLUMN, "friday");
        officeHoursTable.prefWidthProperty().bind(officeHoursTablePane.widthProperty());
        VBox.setVgrow(officeHoursTable, Priority.ALWAYS);

        
//SCHEDULE TAB - FIFTH
        Tab schedule = new Tab();
        schedule.setText("Schedule");
        
        VBox schedulePane = csgBuilder.buildVBox(CSG_SCHEDULE_PANE, null, CLASS_CSG_FIRST_PANE, ENABLED);
        VBox.setVgrow(schedulePane, Priority.ALWAYS);
        
        GridPane calendarBoundariesPane = csgBuilder.buildGridPane(CSG_CALENDAR_BOUNDARIES_PANE, schedulePane, CLASS_CSG_SECOND_PANE, ENABLED);
        csgBuilder.buildLabel(CSG_CALENDAR_BOUNDARIES_HEADER, calendarBoundariesPane, 0,0,1,1, CLASS_CSG_HEADER_LABEL,ENABLED);
        schedulePane.setMargin(calendarBoundariesPane, new Insets(10,10,10,10));
        calendarBoundariesPane.setPadding(new Insets(10,10,10,10));
        
        
        csgBuilder.buildLabel(CSG_CALENDAR_BOUNDARIES_START_DAY, calendarBoundariesPane, 0,1,1,1, CLASS_CSG_LABEL, ENABLED);
        DatePicker starting = new DatePicker();
        calendarBoundariesPane.add(starting, 1, 1, 3, 1);
        
        csgBuilder.buildLabel(CSG_CALENDAR_BOUNDARIES_END_DAY, calendarBoundariesPane, 6,1,1,1, CLASS_CSG_LABEL, ENABLED);
        DatePicker ending = new DatePicker();
        calendarBoundariesPane.add(ending, 7,1,1,1);
        
        starting.setValue(LocalDate.of(2018,10,1));
        ending.setValue(LocalDate.of(2018,12,29));
        
        VBox scheduleItemsPane = csgBuilder.buildVBox(CSG_SCHEDULE_ITEMS_PANE, schedulePane, CLASS_CSG_SECOND_PANE, ENABLED);
        VBox.setVgrow(scheduleItemsPane, Priority.ALWAYS);
        schedulePane.setMargin(scheduleItemsPane, new Insets(10,10,10,10));
        scheduleItemsPane.setPadding(new Insets(10,10,10,10));
        
        
        Button deleteScheduleItem = csgBuilder.buildTextButton(CSG_DELETE_SCHEDULE_ITEM_BUTTON, scheduleItemsPane, CLASS_CSG_BUTTON, ENABLED);
        csgBuilder.buildLabel(CSG_SCHEDULE_ITEMS_HEADER, scheduleItemsPane, CLASS_CSG_LABEL, ENABLED);
        
        TableView<ScheduleItem> scheduleTable = csgBuilder.buildTableView(CSG_SCHEDULE_ITEMS_TABLE_VIEW, scheduleItemsPane , CLASS_CSG_TABLE_VIEW, ENABLED);
        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        scheduleTable.setEditable(ENABLED);
        
        TableColumn typeColumn = csgBuilder.buildTableColumn(CSG_TYPE_TABLE_COLUMN, scheduleTable, CLASS_CSG_COLUMN);
        TableColumn dateColumn = csgBuilder.buildTableColumn(CSG_DATE_TABLE_COLUMN, scheduleTable, CLASS_CSG_COLUMN);
        TableColumn titleColumn = csgBuilder.buildTableColumn(CSG_TITLE_TABLE_COLUMN, scheduleTable, CLASS_CSG_CENTERED_COLUMN);
        TableColumn topicColumn = csgBuilder.buildTableColumn(CSG_TOPIC_TABLE_COLUMN, scheduleTable, CLASS_CSG_COLUMN);
        typeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<String, String>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<String, String>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<String, String>("topic"));
        typeColumn.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(1.0 / 4.0));
        dateColumn.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(1.0 / 4.0));
        titleColumn.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(1.0 / 4.0));
        topicColumn.prefWidthProperty().bind(scheduleTable.widthProperty().multiply(1.0 / 4.0));
        scheduleTable.prefWidthProperty().bind(scheduleItemsPane.widthProperty().subtract(20.0));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        topicColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        VBox addEditPane = csgBuilder.buildVBox(CSG_ADD_EDIT_PANE, schedulePane, CLASS_CSG_SECOND_PANE, ENABLED);
        VBox.setVgrow(addEditPane, Priority.ALWAYS);
        schedulePane.setMargin(addEditPane, new Insets(10,10,10,10));
        addEditPane.setPadding(new Insets(10,10,10,10));
        addEditPane.setSpacing(15);
        
        
        csgBuilder.buildLabel(CSG_ADD_EDIT_HEADER, addEditPane, CLASS_CSG_HEADER_LABEL, ENABLED);
        
        HBox addEditTypePane = csgBuilder.buildHBox(CSG_ADD_EDIT_TYPE_PANE, addEditPane, CLASS_CSG_PANE, ENABLED);
        Label typeLabel = csgBuilder.buildLabel(CSG_ADD_EDIT_TYPE_HEADER, addEditTypePane, CLASS_CSG_HEADER_LABEL, ENABLED);
        ComboBox typeCB = csgBuilder.buildComboBox(CSG_ADD_EDIT_TYPE_COMBO_BOX, options, options.get(0), addEditTypePane, CLASS_CSG_COMBO_BOX, ENABLED);
        typeCB.getItems().addAll("Homework", "Lecture", "Lab","Recitation", "Holiday", "Reference");
        typeCB.setValue("Homework");
        typeLabel.setPrefWidth(80);
        
        HBox addEditDatePane = csgBuilder.buildHBox(CSG_ADD_EDIT_TYPE_PANE, addEditPane, CLASS_CSG_PANE, ENABLED);
        Label dateLabel = csgBuilder.buildLabel(CSG_ADD_EDIT_DATE_HEADER, addEditDatePane, CLASS_CSG_HEADER_LABEL, ENABLED);
        DatePicker dateDP = csgBuilder.buildDatePicker(CSG_ADD_EDIT_DATE_DATE_PICKER, addEditDatePane, CLASS_CSG_DATE_PICKER, ENABLED);
        LocalDate today = LocalDate.now();
        dateDP.setValue(today);
        dateLabel.setPrefWidth(80);
        
        HBox addEditTitlePane = csgBuilder.buildHBox(CSG_ADD_EDIT_TITLE_PANE, addEditPane, CLASS_CSG_PANE, ENABLED);
        Label titleLabel = csgBuilder.buildLabel(CSG_ADD_EDIT_TITLE_HEADER, addEditTitlePane, CLASS_CSG_HEADER_LABEL, ENABLED);
        csgBuilder.buildTextField(CSG_ADD_EDIT_TITLE_TEXT_FIELD, addEditTitlePane, CLASS_CSG_TEXT_FIELD, ENABLED);
        titleLabel.setPrefWidth(80);
        
        HBox addEditTopicPane = csgBuilder.buildHBox(CSG_ADD_EDIT_TOPIC_PANE, addEditPane, CLASS_CSG_PANE, ENABLED);
        Label topicLabel = csgBuilder.buildLabel(CSG_ADD_EDIT_TOPIC_HEADER, addEditTopicPane, CLASS_CSG_HEADER_LABEL, ENABLED);
        csgBuilder.buildTextField(CSG_ADD_EDIT_TOPIC_TEXT_FIELD, addEditTopicPane, CLASS_CSG_TEXT_FIELD, ENABLED);
        topicLabel.setPrefWidth(80);
        
        HBox addEditLinkPane = csgBuilder.buildHBox(CSG_ADD_EDIT_LINK_PANE, addEditPane, CLASS_CSG_PANE, ENABLED);
        Label linkLabel = csgBuilder.buildLabel(CSG_ADD_EDIT_LINK_HEADER, addEditLinkPane, CLASS_CSG_HEADER_LABEL, ENABLED);
        csgBuilder.buildTextField(CSG_ADD_EDIT_LINK_TEXT_FIELD, addEditLinkPane, CLASS_CSG_TEXT_FIELD, ENABLED);
        linkLabel.setPrefWidth(80);
        
        HBox twoButtonsPane = csgBuilder.buildHBox(CSG_ADD_EDIT_TWO_BUTTONS_PANE,addEditPane,CLASS_CSG_PANE,ENABLED);
        Button addUpdate = csgBuilder.buildTextButton(CSG_ADD_EDIT_UPDATE_BUTTON, twoButtonsPane, CLASS_CSG_BUTTON, ENABLED);
        csgBuilder.buildTextButton(CSG_ADD_EDIT_CLEAR_BUTTON, twoButtonsPane, CLASS_CSG_PANE, ENABLED);
        twoButtonsPane.setSpacing(30);
        
        addEditTypePane.setSpacing(20);
        addEditDatePane.setSpacing(20);
        addEditTitlePane.setSpacing(20);
        addEditTopicPane.setSpacing(20);
        addEditLinkPane.setSpacing(20);
        
        //ALL TABS TOGETHER
        ScrollPane syllabusScroll = new ScrollPane();
        syllabusScroll.setMaxWidth(Double.MAX_VALUE);
        syllabusScroll.setContent(syllabusPane);
        ScrollPane meetingTimesScroll = new ScrollPane();
        meetingTimesScroll.setContent(meetingTimesPane);
        ScrollPane officeHoursScroll = new ScrollPane();
        officeHoursScroll.setContent(officeHoursPane);
        ScrollPane scheduleScroll = new ScrollPane();
        scheduleScroll.setContent(schedulePane);
        
        
        TabPane tabs = new TabPane();
        
        site.setContent(siteScroll);
        
        tabs.getTabs().add(site);
        syllabus.setContent(syllabusScroll);
        
        tabs.getTabs().add(syllabus);
        meetingTimes.setContent(meetingTimesScroll);
        tabs.getTabs().add(meetingTimes);
        officeHours.setContent(officeHoursScroll);
        tabs.getTabs().add(officeHours);
        schedule.setContent(scheduleScroll);
        tabs.getTabs().add(schedule);
        
        //NOW ALL IN WORKSAPCE
        //Pane everything = new Pane();
        Pane everything = csgBuilder.buildVBox(CSG_EVERYTHING_PANE, null, CLASS_CSG_PANE, ENABLED);
        everything.getChildren().add(tabs);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(everything);
        tabs.tabMinWidthProperty().bind(workspace.widthProperty().divide(5).subtract(20));
        //expand.prefWidthProperty().bind(workspace.widthProperty());
        
        //lecturesExpand.prefHeightProperty().bind(workspace.heightProperty());
        //tabs.prefWidthProperty().bind(workspace.widthProperty());
        //tabs.setStyle("-fx-pagging: 0 -1 -1 -1 -1");
        
        everything.prefWidthProperty().bind(workspace.widthProperty());
        sitePane.prefWidthProperty().bind(workspace.widthProperty());
        syllabusPane.prefWidthProperty().bind(workspace.widthProperty());
        meetingTimesPane.prefWidthProperty().bind(workspace.widthProperty());
        officeHoursPane.prefWidthProperty().bind(workspace.widthProperty());
        schedulePane.prefWidthProperty().bind(workspace.widthProperty());
        
//        everything.prefHeightProperty().bind(workspace.widthProperty());
//        sitePane.prefHeightProperty().bind(workspace.widthProperty());
//        syllabusPane.prefHeightProperty().bind(workspace.widthProperty());
//        meetingTimesPane.prefHeightProperty().bind(workspace.widthProperty());
//        officeHoursPane.prefHeightProperty().bind(workspace.widthProperty());
//        schedulePane.prefHeightProperty().bind(workspace.widthProperty());
//        
//        
        
            
    }
    
    private void setupOfficeHoursColumn(Object columnId, TableView tableView, String styleClass, String columnDataProperty) {
        AppNodesBuilder builder = app.getGUIModule().getNodesBuilder();
        TableColumn<TeachingAssistantPrototype, String> column = builder.buildTableColumn(columnId, tableView, styleClass);
        column.setCellValueFactory(new PropertyValueFactory<TeachingAssistantPrototype, String>(columnDataProperty));
        column.prefWidthProperty().bind(tableView.widthProperty().multiply(1.0 / 7.0));
        column.setCellFactory(col -> {
            return new TableCell<TeachingAssistantPrototype, String>() {
                @Override
                protected void updateItem(String text, boolean empty) {
                    super.updateItem(text, empty);
                    if (text == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // CHECK TO SEE IF text CONTAINS THE NAME OF
                        // THE CURRENTLY SELECTED TA
                        setText(text);
                        TableView<TeachingAssistantPrototype> tasTableView = (TableView) app.getGUIModule().getGUINode(CSG_TAS_TABLE_VIEW);
                        TeachingAssistantPrototype selectedTA = tasTableView.getSelectionModel().getSelectedItem();
                        if (selectedTA == null) {
                            setStyle("");
                        } else if (text.contains(selectedTA.getName())) {
                            setStyle("-fx-background-color: red");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });
    }
    
    
    
    private void initControllers(){
        CourseSiteGeneratorController controller = new CourseSiteGeneratorController((CourseSiteGeneratorApp) app);
        //CourseSiteGeneratorClipboard clipboard = new CourseSiteGeneratorClipboard ((CourseSiteGeneratorApp) app);
        AppGUIModule gui = app.getGUIModule();
        
        CourseSiteGeneratorData data = (CourseSiteGeneratorData) app.getDataComponent();
        TableView officeHoursTableView = (TableView) gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
        TableView tasTable = (TableView) gui.getGUINode(CSG_TAS_TABLE_VIEW);
        final ToggleGroup group = new ToggleGroup();
        RadioButton ugradButton = (RadioButton) gui.getGUINode(CSG_UNDERGRADUATE_RADIO_BUTTON);
        RadioButton gradButton = (RadioButton) gui.getGUINode(CSG_GRADUATE_RADIO_BUTTON);
        RadioButton allButton = (RadioButton) gui.getGUINode(CSG_ALL_RADIO_BUTTON);
        ugradButton.setToggleGroup(group);
        ugradButton.setUserData("ug");
        gradButton.setToggleGroup(group);
        gradButton.setUserData("gr");
        allButton.setToggleGroup(group);
        allButton.setUserData("all");
        allButton.setSelected(ENABLED);
        allButton.requestFocus();
        
        
//Tab 1 - Site 
    //Banner Listeners
        ComboBox subjectsCB = (ComboBox) gui.getGUINode(CSG_SUBJECT_COMBO_BOX);
        ComboBox numbersCB = (ComboBox) gui.getGUINode(CSG_NUMBER_COMBO_BOX);
        ComboBox semesterCB = (ComboBox) gui.getGUINode(CSG_SEMESTER_COMBO_BOX);
        ComboBox yearCB = (ComboBox) gui.getGUINode(CSG_YEAR_COMBO_BOX);
        TextField titleTF = (TextField) gui.getGUINode(CSG_TITLE_TEXT_FIELD);
        
        
        //ORIGINAL ENTER BUTTON
//        subjectsCB.addEventHandler(KeyEvent.KEY_PRESSED, ev->{
//            if(ev.getCode() == KeyCode.ENTER){
//                System.out.println("Enter has been pressed");
//                controller.processMakeNewSubjectOption();
//                controller.processChangeExportDir();
//                controller.processChangeComboBox();
//                controller.processChangeTitle(subjectsCB.getValue().toString());
//            
//            }
//            else if(ev.getCode() == KeyCode.ALPHANUMERIC){
//                System.out.println("alphanumberic");
//            }
//            else if(ev.getCode() == KeyCode.getKeyCode("a"))
//                System.out.println("a has been pressed");
//            else
//                System.out.println("This has been processed");
//        });
        

        subjectsCB.setOnKeyReleased(e->{
            if(e.getText().matches("^[a-zA-Z0-9]$")){
                System.out.println("alphanum typed");
                controller.processChangeTitle(subjectsCB.getEditor().getText());
                controller.processChangeExportDir();
            }
            else if(e.getCode() == KeyCode.ENTER){
                System.out.println("Enter pressed");
                controller.processMakeNewSubjectOption();
            }
            else if(e.getCode() == KeyCode.BACK_SPACE){
                controller.processChangeTitle(subjectsCB.getEditor().getText());
                controller.processChangeExportDir();
            }
            else
                System.out.println("That button has no functionality");
            
        });
        
//        subjectsCB.setOnAction(e->{
//            System.out.println("option chosen");
//        });
//    

//        numbersCB.addEventHandler(KeyEvent.KEY_PRESSED, ev->{
//            if(ev.getCode() == KeyCode.ENTER){
//                controller.processMakeNewNumberOption();
//                controller.processChangeExportDir();
//                controller.processChangeComboBox();
//                controller.processChangeTitle(subjectsCB.getValue().toString());
//            }
//            else
//                System.out.println("something pressed");
//        });
        
        numbersCB.setOnKeyReleased(e->{
            if(e.getText().matches("^[a-zA-Z0-9]$")){
                System.out.println("alphanum typed");
                controller.processChangeTitle(subjectsCB.getEditor().getText());
                controller.processChangeExportDir();
            }
            else if(e.getCode() == KeyCode.ENTER){
                System.out.println("Enter pressed");
                controller.processMakeNewNumberOption();
            }
            else if(e.getCode() == KeyCode.BACK_SPACE){
                controller.processChangeTitle(subjectsCB.getEditor().getText());
                controller.processChangeExportDir();
            }
            else
                System.out.println("That button has no functionality");
        });
        

        semesterCB.focusedProperty().addListener(e->{
            if(!semesterCB.isFocused()){
                
                String newSelectedSemester = semesterCB.getValue().toString();
                System.out.println("newly selected semester is: " + newSelectedSemester);
                controller.processChangeComboBox(semesterCB, "semester", newSelectedSemester);
                controller.processChangeExportDir();
            }
        });
        
        yearCB.focusedProperty().addListener(e->{
            if(!yearCB.isFocused()){
                controller.processChangeExportDir();
                String newSelectedYear = yearCB.getValue().toString();
                controller.processChangeComboBox(yearCB, "year", newSelectedYear);
            }
        });
        
        //3x LOOPING SETONACTION
//        yearCB.setOnAction(e->{
//            controller.processChangeExportDir();
//            String newSelectedYear = yearCB.getValue().toString();
//            controller.processChangeComboBox(yearCB, "year", newSelectedYear);
//        });

//        yearCB.setOnAction(e->{
//            String newSelectedYear = yearCB.getValue().toString();
//            controller.processChangeComboBox(yearCB, "year", newSelectedYear);
//            controller.processChangeExportDir();
//        });

        
        titleTF.setOnAction(e->{
            controller.processChangeTitleTextField();
        });
        
    //CHECK BOXES - Pages Lisgteners
        CheckBox homeCB = (CheckBox) gui.getGUINode(CSG_HOME_CHECK_BOX);
        CheckBox syllabusCB = (CheckBox) gui.getGUINode(CSG_SYLLABUS_CHECK_BOX);
        CheckBox scheduleCB = (CheckBox) gui.getGUINode(CSG_SCHEDULE_CHECK_BOX);
        CheckBox homeworksCB = (CheckBox) gui.getGUINode(CSG_HOMEWORK_CHECK_BOX);
        
        
        //to do/undo this, just check if the checkbox is selected. The undo will just be the opposite of isSelected()
        homeCB.setOnAction(e->{
            System.out.println("Home selected/deselected");
            controller.processChangeCheckBox("home");
        });
        syllabusCB.setOnAction(e->{
            System.out.println("Syllabus selected/deselected");
            controller.processChangeCheckBox("syllabus");
        });
        scheduleCB.setOnAction(e->{
            System.out.println("Schedule selected/deselected");
            controller.processChangeCheckBox("schedule");
        });
        homeworksCB.setOnAction(e->{
            System.out.println("Homeworks selected/deselected");
            controller.processChangeCheckBox("homework");
        });
        
    //Style Node listeners
        Button faviconButton = (Button) gui.getGUINode(CSG_FAVICON_ICON_BUTTON);
        Button navbarButton = (Button) gui.getGUINode(CSG_NAVBAR_ICON_BUTTON);
        Button leftFooterButton = (Button) gui.getGUINode(CSG_LEFT_FOOTER_ICON_BUTTON);
        Button rightFooterButton = (Button) gui.getGUINode(CSG_RIGHT_FOOTER_ICON_BUTTON);
        
        faviconButton.setOnMouseClicked(e->{
            System.out.println("presed the favicon Button");
            controller.processOpenFileChooser("favicon");
        });
        
        navbarButton.setOnMouseClicked(e->{
            controller.processOpenFileChooser("navbar");
        });
        
        leftFooterButton.setOnMouseClicked(e->{
            controller.processOpenFileChooser("leftfooter");
        });
        rightFooterButton.setOnMouseClicked(e->{
            controller.processOpenFileChooser("rightfooter");
        });
        
    
    //INSTRUCTOR NODE LISTENERS
        TextField inNameTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_NAME_TEXT_FIELD);
        inNameTF.setOnKeyReleased(e->{
            controller.processChangeInstructorName();
        });
        
        TextField inEmailTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_EMAIL_TEXT_FIELD);
        inEmailTF.setOnKeyReleased(e->{
            controller.processChangeInstructorEmail();
        });
        
        TextField inRoomTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_ROOM_TEXT_FIELD);
        inRoomTF.setOnKeyReleased(e->{
            controller.processChangeInstructorRoom();
        });
        
        TextField inHomePageTF = (TextField) gui.getGUINode(CSG_INSTRUCTOR_HOME_PAGE_TEXT_FIELD);
        inHomePageTF.setOnKeyReleased(e->{
            controller.processChangeInstructorHomePage();
        });
        

        TextArea textArea = (TextArea) gui.getGUINode(CSG_INSTRUCTOR_OFFICE_HOURS);
        textArea.setOnKeyReleased(e -> {
            controller.processChangeInstructorHours();
        });
        
        
        
//TAB 2 - SYLLABUS TAB LISTENERS
        //Description Listener
        TextArea descT = (TextArea) gui.getGUINode(CSG_DESCRIPTION_TEXT_AREA);
        TextArea topicsT = (TextArea) gui.getGUINode(CSG_TOPICS_TEXT_AREA);
        TextArea prereqsT = (TextArea) gui.getGUINode(CSG_PREREQUISITES_TEXT_AREA);
        TextArea outcomesT = (TextArea) gui.getGUINode(CSG_OUTCOMES_TEXT_AREA);
        TextArea textbooksT = (TextArea) gui.getGUINode(CSG_TEXTBOOKS_TEXT_AREA);
        TextArea gradedComponentsT = (TextArea) gui.getGUINode(CSG_GRADED_COMPONENTS_TEXT_AREA);
        TextArea gradingNoteT = (TextArea) gui.getGUINode(CSG_GRADING_NOTE_TEXT_AREA);
        TextArea academicDishonestyT = (TextArea) gui.getGUINode(CSG_ACADEMIC_DISHONESTY_TEXT_AREA);
        TextArea specialAssistanceT = (TextArea) gui.getGUINode(CSG_SPECIAL_ASSISTANCE_TEXT_AREA);
        
        descT.focusedProperty().addListener(e ->{
            if(!descT.isFocused()){
                controller.changeTextBox("description");
            }
            
        });
        
        topicsT.focusedProperty().addListener(e ->{
            if(!topicsT.isFocused()){
               controller.changeTextBox("topics"); 
            }
            
        });
        
        prereqsT.focusedProperty().addListener(e ->{
            if(!prereqsT.isFocused()){
               controller.changeTextBox("prereqs");
            }
            
        
        });
        
        outcomesT.focusedProperty().addListener(e ->{
            if(!outcomesT.isFocused()){
               controller.changeTextBox("outcomes");
            }
            
        
        });
        
        textbooksT.focusedProperty().addListener(e ->{
            if(!textbooksT.isFocused()){
               controller.changeTextBox("textbooks");
            }
            
        
        });
        
        gradedComponentsT.focusedProperty().addListener(e ->{
            if(!gradedComponentsT.isFocused()){
               controller.changeTextBox("gc");
            }
            
        
        });
        
        gradingNoteT.focusedProperty().addListener(e ->{
            if(!gradingNoteT.isFocused()){
               controller.changeTextBox("gn");
            }
            
        
        });
        
        academicDishonestyT.focusedProperty().addListener(e ->{
            if(!academicDishonestyT.isFocused()){
               controller.changeTextBox("ad");
            }
            
        
        });
        
        specialAssistanceT.focusedProperty().addListener(e ->{
            if(!specialAssistanceT.isFocused()){
               controller.changeTextBox("sa");
            }
            
        });
        
//Tab 3 - Meeting Times 
        //Recitations Functionality
        
        Button addLec = (Button) gui.getGUINode(CSG_ADD_LECTURE_BUTTON);
        addLec.setOnMousePressed(e->{
            controller.processAddNewLecture();
        });
        
        Button removeLect = (Button) gui.getGUINode(CSG_REMOVE_LECTURE_BUTTON);
        removeLect.setOnMousePressed(e->{
            controller.processRemoveLecture();
        });
        
        //Recitations Functionality
        Button addRec = (Button) gui.getGUINode(CSG_ADD_RECITATION_BUTTON);
        addRec.setOnMousePressed(e->{
            controller.processAddRecitation();
        });
        
        Button removeRec = (Button) gui.getGUINode(CSG_REMOVE_RECITATION_BUTTON);
        removeRec.setOnMousePressed(e->{
            controller.processRemoveRecitation();
        });
        
        //Labs Functionality
        Button addLab = (Button) gui.getGUINode(CSG_ADD_LAB_BUTTON);
        addLab.setOnMousePressed(e->{
            controller.processAddLab();
        });
        
        Button removeLab = (Button)  gui.getGUINode(CSG_REMOVE_LAB_BUTTON);
        removeLab.setOnMousePressed(e->{
            controller.processRemoveLab();
        });
        
        TableView<Lecture> lecTV = (TableView<Lecture>) gui.getGUINode(CSG_LECTURES_TABLE_VIEW);

        for(TableColumn tc : lecTV.getColumns()){
            tc.setOnEditCommit(
                    new EventHandler<CellEditEvent<Lecture, String>>() {
                    @Override
                    public void handle(CellEditEvent<Lecture, String> t) {
                        Lecture changeThis = lecTV.getSelectionModel().getSelectedItem();
                        ObservableList<TablePosition> selectedCells = lecTV.getSelectionModel().getSelectedCells();
                        for(TablePosition tp : selectedCells){
                            System.out.print(tp.getRow() + " ");
                            System.out.println(tp.getColumn());
                        }
                        
                        System.out.println("New Value is: " + t.getNewValue());
                        TablePosition cell = lecTV.getSelectionModel().getSelectedCells().get(0);
                        int column = cell.getColumn();
                        int row = cell.getRow();
                        String newText = t.getNewValue();
                        
                        controller.processEditTableViewCell("lecture", newText, changeThis, column);
                        
                    }
                });
        }
        
        TableView<Recitation> recTV = (TableView<Recitation>) gui.getGUINode(CSG_RECITATIONS_TABLE_VIEW);
        for(TableColumn tc : recTV.getColumns()){
            tc.setOnEditCommit(
                    new EventHandler<CellEditEvent<Recitation, String>>() {
                    @Override
                    public void handle(CellEditEvent<Recitation, String> t) {
                        Recitation changeThis = recTV.getSelectionModel().getSelectedItem();
                        ObservableList<TablePosition> selectedCells = recTV.getSelectionModel().getSelectedCells();
                        for(TablePosition tp : selectedCells){
                            System.out.print(tp.getRow() + " ");
                            System.out.println(tp.getColumn());
                        }
                        
                        System.out.println("New Value is: " + t.getNewValue());
                        TablePosition cell = recTV.getSelectionModel().getSelectedCells().get(0);
                        int column = cell.getColumn();
                        int row = cell.getRow();
                        String newText = t.getNewValue();
                        
                        controller.processEditTableViewCell("recitation", newText, changeThis, column);
                        
                    }
                });
        }
        
        
        TableView<Lab> labTV = (TableView<Lab>) gui.getGUINode(CSG_LABS_TABLE_VIEW);
        for(TableColumn tc : labTV.getColumns()){
            tc.setOnEditCommit(
                    new EventHandler<CellEditEvent<Lab, String>>() {
                    @Override
                    public void handle(CellEditEvent<Lab, String> t) {
                        Lab changeThis = labTV.getSelectionModel().getSelectedItem();
                        ObservableList<TablePosition> selectedCells = labTV.getSelectionModel().getSelectedCells();
                        for(TablePosition tp : selectedCells){
                            System.out.print(tp.getRow() + " ");
                            System.out.println(tp.getColumn());
                        }
                        
                        System.out.println("New Value is: " + t.getNewValue());
                        TablePosition cell = labTV.getSelectionModel().getSelectedCells().get(0);
                        int column = cell.getColumn();
                        int row = cell.getRow();
                        String newText = t.getNewValue();
                        
                        controller.processEditTableViewCell("lab", newText, changeThis, column);
                        
                    }
                });
        }
        
//Tab 4 - Teaching Assistants and Office Hours
        
        // FOOLPROOF DESIGN STUFF
        TextField nameTextField = ((TextField) gui.getGUINode(CSG_TA_NAME_TEXT_FIELD));
        TextField emailTextField = ((TextField) gui.getGUINode(CSG_TA_EMAIL_TEXT_FIELD));
        

        nameTextField.textProperty().addListener(e -> {
            controller.processTypeTA();
        });
        emailTextField.textProperty().addListener(e -> {
            controller.processTypeTA();
        });

        //Adding TA process
        nameTextField.setOnAction(e -> {
            controller.processAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.processAddTA();
        });
        ((Button) gui.getGUINode(CSG_ADD_TA_BUTTON)).setOnAction(e -> {
            controller.processAddTA();
            //System.out.println(group.getSelectedToggle().getUserData().toString());
            controller.processShowTaTable(group.getSelectedToggle().getUserData().toString());
        });
        
        
        //Adding office hours for a given TA
        officeHoursTableView.getSelectionModel().setCellSelectionEnabled(true);
        officeHoursTableView.setOnMouseClicked(e -> {
            controller.processToggleOfficeHours();
        });

        
        //Toggling the TA table based on which Radio button (undergrad, grad, all) is pressed. 
        Button cutButton = (Button)gui.getGUINode(CUT_BUTTON);
        Button copyButton = (Button)gui.getGUINode(COPY_BUTTON);
        Button pasteButton = (Button)gui.getGUINode(PASTE_BUTTON);
        
        ugradButton.setOnMousePressed(e -> {
            //controller.printAllTas();
            pasteButton.setDisable(true);
            nameTextField.setDisable(false);
            emailTextField.setDisable(false);
            controller.processShowTaTable("ug");
        });
        gradButton.setOnMousePressed(e -> {
            nameTextField.setDisable(false);
            emailTextField.setDisable(false);
            pasteButton.setDisable(true);
            //controller.printAllTas();
            controller.processShowTaTable("gr");
        });
        allButton.setOnMousePressed(e -> {
            nameTextField.setDisable(true);
            emailTextField.setDisable(true);
            AppClipboardComponent cb = app.getClipboardComponent();
            controller.checkSubjectSelection();
            controller.processShowTaTable("all");
            //controller.processAddTA();
        });
        
        // Attempt to highlight the TA in the OH right table based on the click on the left
        tasTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount()==1){
                        Button cutButton = (Button)gui.getGUINode(CUT_BUTTON);
                        cutButton.setDisable(false);
                        Button copyButton = (Button)gui.getGUINode(COPY_BUTTON);
                        copyButton.setDisable(false);
                        System.out.print("Highlighted Ta: ");
                        System.out.println("");
                        controller.processHighlightNames();
                    }
                    if(mouseEvent.getClickCount()==2){
                        controller.processEdit();
                        controller.processAddTA();
                    }
                }
            }
        });
        
        ComboBox startTimeCB = (ComboBox) gui.getGUINode(CSG_OFFICE_HOURS_START_TIME_COMBO_BOX);
        ComboBox endTimeCB = (ComboBox) gui.getGUINode(CSG_OFFICE_HOURS_END_TIME_COMBO_BOX);
        startTimeCB.focusedProperty().addListener(e->{
            if(!startTimeCB.isFocused()){
                
                controller.processTimeOptionsFoolProof();
                
                controller.processChangeHoursDisplay();
                
            }
        });
        
        endTimeCB.focusedProperty().addListener(e-> {
            if(!endTimeCB.isFocused()){
                
                controller.processTimeOptionsFoolProof();
                controller.processChangeHoursDisplay();
            }
        });
        
        //Tab 4 Function - Remove TA
        
        Button removeTAButton = ((Button) gui.getGUINode(CSG_REMOVE_TA_BUTTON));
        removeTAButton.setOnMouseClicked(e -> {
            controller.processRemoveTA();
        });
        
        cutButton.setOnMouseClicked(e -> {
            pasteButton.setDisable(false);
        });
        copyButton.setOnMouseClicked(e -> {
            pasteButton.setDisable(false);
        });
        pasteButton.setOnMouseClicked(e -> {
        });
        
        
// Tab 5 Functionality
        
        

        Button addUpdateButton = (Button) gui.getGUINode(CSG_ADD_EDIT_UPDATE_BUTTON);
        addUpdateButton.setOnMouseReleased(e->{
            controller.processAddUpdate();
        });
        
        Button clearButton = (Button) gui.getGUINode(CSG_ADD_EDIT_CLEAR_BUTTON);
        clearButton.setOnMouseReleased(e->{
            controller.processClearTextFields();
        });
        
        TableView<ScheduleItem> scheduleTableView = (TableView<ScheduleItem>) gui.getGUINode(CSG_SCHEDULE_ITEMS_TABLE_VIEW);
        //setoneditcommit
//        scheduleTableView.setOnMouseReleased(e->{
//            if(e.getButton().equals(MouseButton.PRIMARY)){
//                if(e.getClickCount()==1){
//                    
//                }
//                else if(e.getClickCount()==2){
//                    
//                }
//            }
//        });
        
        for(TableColumn tc : scheduleTableView.getColumns()){
            tc.setOnEditCommit(
                    new EventHandler<CellEditEvent<ScheduleItem, String>>() {
                    @Override
                    public void handle(CellEditEvent<ScheduleItem, String> t) {
                        ScheduleItem changeThis = scheduleTableView.getSelectionModel().getSelectedItem();
                        ObservableList<TablePosition> selectedCells = scheduleTableView.getSelectionModel().getSelectedCells();
                        for(TablePosition tp : selectedCells){
                            System.out.print(tp.getRow() + " ");
                            System.out.println(tp.getColumn());
                        }
                        
                        System.out.println("New Value is: " + t.getNewValue());
                        TablePosition cell = scheduleTableView.getSelectionModel().getSelectedCells().get(0);
                        int column = cell.getColumn();
                        int row = cell.getRow();
                        String newText = t.getNewValue();
                        
                        controller.processEditTableViewCell("schedule", newText, changeThis, column);
                        
                    }
                });
        }
        
        Button removeScheduleItem = (Button) gui.getGUINode(CSG_DELETE_SCHEDULE_ITEM_BUTTON);
        removeScheduleItem.setOnMouseReleased(e->{
            controller.processRemoveScheduleItem();
        });
        
        
        
        // DON'T LET ANYONE SORT THE TABLES
        TableView tasTableView = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
        for (int i = 0; i < officeHoursTableView.getColumns().size(); i++) {
            ((TableColumn)officeHoursTableView.getColumns().get(i)).setSortable(false);
        }
        for (int i = 0; i < tasTableView.getColumns().size(); i++) {
            ((TableColumn)tasTableView.getColumns().get(i)).setSortable(false);
        }
        
    }
    
    
    private void initFoolproofDesign(){
        AppGUIModule gui = app.getGUIModule();
        AppFoolproofModule foolproofSettings = app.getFoolproofModule();
        foolproofSettings.registerModeSettings(CSG_FOOLPROOF_SETTINGS,
                new CourseSiteGeneratorFoolproofDesign((CourseSiteGeneratorApp) app));
    
    }
    
    private void initDialogs(){
        
    }
    
    
    @Override
    public void showNewDialog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
