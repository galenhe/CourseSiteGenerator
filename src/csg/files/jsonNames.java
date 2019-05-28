/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

/**
 *
 * @author Galen
 */
public class jsonNames {
    //Tab 1 - SITE TAB JSON TYPES
    //Banner JSONs
    static final String JSON_BANNER_SUBJECT_OPTIONS = "subject_options";
    static final String JSON_BANNER_SUBJECT_OPTION = "subject_option";
    static final String JSON_BANNER_SUBJECT_SELECTED = "selected_subject";
    static final String JSON_BANNER_SEMESTER_OPTIONS = "semester_options";
    static final String JSON_BANNER_SEMESTER_OPTION = "semester_option";
    static final String JSON_BANNER_SEMESTER_SELECTED = "selected_semester";
    static final String JSON_BANNER_NUMBER_OPTIONS = "number_options";
    static final String JSON_BANNER_NUMBER_OPTION = "number_option";
    static final String JSON_BANNER_NUMBER_SELECTED = "selected_number";
    static final String JSON_BANNER_YEAR_OPTIONS = "year_options";
    static final String JSON_BANNER_YEAR_OPTION = "year_option";
    static final String JSON_BANNER_YEAR_SELECTED = "selected_year";
    static final String JSON_BANNER_TITLE_TEXT = "title_text";
    static final String JSON_BANNER_EXPORT_DIR_TEXT = "export_dir_text";
    
    //Pages JSONs
    static final String JSON_PAGES = "pages";
    static final String JSON_PAGE = "page_name";
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
    
    static final String JSON_INSTRUCTOR_OFFICE_HOURS_TEXT = "office_hours";
    
//Tab 2 - SYLLABUS TAB JSON OBJECTS
    static final String JSON_DESCRIPTION_TEXT = "description_text";
    static final String JSON_TOPICS_TEXT="topic_text";
    static final String JSON_PREREQUISITES_TEXT="prerequisites_text";
    static final String JSON_OUTCOMES_TEXT="outcomes_text";
    static final String JSON_TEXTBOOKS_TEXT="textbooks_text";
    static final String JSON_GRADED_COMPONENTS_TEXT="graded_components_text";
    static final String JSON_GRADING_NOTE_TEXT="grading_note_text";
    static final String JSON_ACADEMIC_DISHONESTY_TEXT="academic_dishonesty_text";
    static final String JSON_SPECIAL_ASSISTANCE_TEXT="special_assistance_text";
    
//** ALL NEW  Tab 4 - MEETING TIMES - LECTURES, RECITATIONS, LABS
    static final String JSON_LECTURES = "lectures";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_LABS = " labs";
    
    static final String JSON_LECTURE = "lecture";
    static final String JSON_LECTURE_SECTION = "lecture_section";
    static final String JSON_LECTURE_DAYS = "lecture_days";
    static final String JSON_LECTURE_TIME = "lecture_time";
    static final String JSON_LECTURE_ROOM = "lecture_room";
    
    static final String JSON_RECITATION = "recitation";
    static final String JSON_RECITATION_SECTION = "recitation_section";
    static final String JSON_RECITATION_DAYS = "recitation_days";
    static final String JSON_RECITATION_ROOM = "recitation_room";
    static final String JSON_RECITATION_TA1= "recitation_ta1";
    static final String JSON_RECITATION_TA2= "recitation_ta2";
    
    
    static final String JSON_LAB = "lab";
    static final String JSON_LAB_SECTION = "lab_section";
    static final String JSON_LAB_DAYS = "lab_days";
    static final String JSON_LAB_ROOM = "lab_room";
    static final String JSON_LAB_TA1= "lab_ta1";
    static final String JSON_LAB_TA2= "lab_ta2";
    
    
    
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
    
    static final String JSON_SCHEDULE_HOLIDAYS = "schedule_holidays";
    static final String JSON_SCHEDULE_LECTURES = "schedule_lectures";
    static final String JSON_SCHEDULE_HOMEWORKS = "schedule_homeworks";
    static final String JSON_SCHEDULE_RECITATIONS = "schedule_recitations";
    static final String JSON_SCHEDULE_LABS = "schedule_labs";
    static final String JSON_SCHEDULE_REFERENCES = "schedule_references";
    
    
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    static final String JSON_SCHEDULE_ITEM = "schedule_item";
    static final String JSON_SCHEDULE_ITEM_TYPE = "schedule_item_type";
    static final String JSON_SCHEDULE_ITEM_DATE = "schedule_item_date";
    static final String JSON_SCHEDULE_ITEM_TITLE = "schedule_item_title";
    static final String JSON_SCHEDULE_ITEM_TOPIC = "schedule_item_topic";
    static final String JSON_SCHEDULE_ITEM_LINK = "schedule_item_link";
    
    static final String JSON_SCHEDULE_ITEM_CURRENT_TITLE = "schedule_item_current_title";
    static final String JSON_SCHEDULE_ITEM_CURRENT_TOPIC = "schedule_item_current_topic";
    static final String JSON_SCHEDULE_ITEM_CURRENT_LINK = "schedule_item_current_link";
    
    
}
