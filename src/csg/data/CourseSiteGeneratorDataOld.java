///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package csg.data;
//
//import csg.CourseSiteGeneratorApp;
//import csg.data.TimeSlotOld.DayOfWeek;
//import djf.components.AppDataComponent;
//import djf.modules.AppGUIModule;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javafx.collections.ObservableList;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TableView;
//import static csg.CourseSiteGeneratorPropertyType.*;
//import static csg.data.TAType.Graduate;
//import javafx.collections.FXCollections;
//
///**
// *
// * @author Galen
// */
//public class CourseSiteGeneratorDataOld implements AppDataComponent{
//
//    CourseSiteGeneratorApp app;
//    ObservableList<TeachingAssistantPrototypeOld> teachingAssistants;
//    
//    ObservableList<TimeSlotOld> officeHours;    
//    HashMap<TAType, ArrayList<TeachingAssistantPrototypeOld>> allTAs;
//
//    int startHour;
//    int endHour;
//    public static final int MIN_START_HOUR = 9;
//    public static final int MAX_END_HOUR = 20;
//
//    public CourseSiteGeneratorDataOld(CourseSiteGeneratorApp initApp){
//        
//        app = initApp;
//        //AppGUIModule gui = app.getGUIModule();
//    }
//    
//    public int getStartHour() {
//        return startHour;
//    }
//
//    public int getEndHour() {
//        return endHour;
//    }
//    
//    // PRIVATE HELPER METHODS
//    
//    private void sortTAs() {
//        Collections.sort(teachingAssistants);
//    }
//    
//    private void resetOfficeHours() {
//        //THIS WILL STORE OUR OFFICE HOURS
//        AppGUIModule gui = app.getGUIModule();
//        TableView<TimeSlotOld> officeHoursTableView = (TableView)gui.getGUINode(CSG_OFFICE_HOURS_TABLE_VIEW);
//        officeHours = officeHoursTableView.getItems(); 
//        officeHours.clear();
//        for (int i = startHour; i <= endHour; i++) {
//            TimeSlotOld timeSlot = new TimeSlotOld(   this.getTimeString(i, true),
//                                                this.getTimeString(i, false));
//            officeHours.add(timeSlot);
//            
//            TimeSlotOld halfTimeSlot = new TimeSlotOld(   this.getTimeString(i, false),
//                                                    this.getTimeString(i+1, true));
//            officeHours.add(halfTimeSlot);
//        }
//    }
//    
//    private String getTimeString(int militaryHour, boolean onHour) {
//        String minutesText = "00";
//        if (!onHour) {
//            minutesText = "30";
//        }
//
//        // FIRST THE START AND END CELLS
//        int hour = militaryHour;
//        if (hour > 12) {
//            hour -= 12;
//        }
//        String cellText = "" + hour + ":" + minutesText;
//        if (militaryHour < 12) {
//            cellText += "am";
//        } else {
//            cellText += "pm";
//        }
//        return cellText;
//    }
//    
// //COMMENTED OUT
//    @Override
//    public void reset() {
//        startHour = MIN_START_HOUR;
//        endHour = MAX_END_HOUR;
//        //teachingAssistants.clear();
//        
////        for (TimeSlot timeSlot : officeHours) {
////            timeSlot.reset();
////        }
//    }
//    
//    // SERVICE METHODS
//    
//    public void initHours(String startHourText, String endHourText) {
//        int initStartHour = Integer.parseInt(startHourText);
//        int initEndHour = Integer.parseInt(endHourText);
//        if (initStartHour <= initEndHour) {
//            // THESE ARE VALID HOURS SO KEEP THEM
//            // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
//            startHour = initStartHour;
//            endHour = initEndHour;
//        }
//        resetOfficeHours();
//    }
//    
//    public void addTA(TeachingAssistantPrototypeOld ta) {
////        if (!hasTA(ta)) {
////            TAType taType = TAType.valueOf(ta.getType());
////            ArrayList<TeachingAssistantPrototype> tas = allTAs.get(taType);
////            tas.add(ta);
////            this.updateTAs();
////        }
//        
//        TAType taType = TAType.valueOf(ta.getType());
//        ArrayList<TeachingAssistantPrototypeOld> tas;
//        if(allTAs==null){
//            System.out.println("ITSNULL");
//            tas = new ArrayList<TeachingAssistantPrototypeOld>();
//            tas.add(ta);
//        }
//        else
//            tas = allTAs.get(taType);
//        
//        this.updateTAs();
//    }
//
//    public void addTA(TeachingAssistantPrototypeOld ta, HashMap<TimeSlotOld, ArrayList<DayOfWeek>> officeHours) {
//        addTA(ta);
//        for (TimeSlotOld timeSlot : officeHours.keySet()) {
//            ArrayList<DayOfWeek> days = officeHours.get(timeSlot);
//            for (DayOfWeek dow : days) {
//                timeSlot.addTA(dow, ta);
//            }
//        }
//    }
//    
//    public void removeTA(TeachingAssistantPrototypeOld ta) {
//        // REMOVE THE TA FROM THE LIST OF TAs
//        TAType taType = TAType.valueOf(ta.getType());
//        allTAs.get(taType).remove(ta);
//        
//        // REMOVE THE TA FROM ALL OF THEIR OFFICE HOURS
//        for (TimeSlotOld timeSlot : officeHours) {
//            timeSlot.removeTA(ta);
//        }
//        
//        // AND REFRESH THE TABLES
//        this.updateTAs();
//    }
//
//    public void removeTA(TeachingAssistantPrototypeOld ta, HashMap<TimeSlotOld, ArrayList<DayOfWeek>> officeHours) {
//        removeTA(ta);
//        for (TimeSlotOld timeSlot : officeHours.keySet()) {
//            ArrayList<DayOfWeek> days = officeHours.get(timeSlot);
//            for (DayOfWeek dow : days) {
//                timeSlot.removeTA(dow, ta);
//            }
//        }    
//    }
//    
//    public DayOfWeek getColumnDayOfWeek(int columnNumber) {
//        return TimeSlotOld.DayOfWeek.values()[columnNumber-2];
//    }
//
//    public TeachingAssistantPrototypeOld getTAWithName(String name) {
//        Iterator<TeachingAssistantPrototypeOld> taIterator = teachingAssistants.iterator();
//        while (taIterator.hasNext()) {
//            TeachingAssistantPrototypeOld ta = taIterator.next();
//            if (ta.getName().equals(name))
//                return ta;
//        }
//        return null;
//    }
//
//    public TeachingAssistantPrototypeOld getTAWithEmail(String email) {
//        Iterator<TeachingAssistantPrototypeOld> taIterator = teachingAssistants.iterator();
//        while (taIterator.hasNext()) {
//            TeachingAssistantPrototypeOld ta = taIterator.next();
//            if (ta.getEmail().equals(email))
//                return ta;
//        }
//        return null;
//    }
//
//    public TimeSlotOld getTimeSlot(String startTime) {
//        Iterator<TimeSlotOld> timeSlotsIterator = officeHours.iterator();
//        while (timeSlotsIterator.hasNext()) {
//            TimeSlotOld timeSlot = timeSlotsIterator.next();
//            String timeSlotStartTime = timeSlot.getStartTime().replace(":", "_");
//            if (timeSlotStartTime.equals(startTime))
//                return timeSlot;
//        }
//        return null;
//    }
//
//    public TAType getSelectedType() {
//        RadioButton allRadio = (RadioButton)app.getGUIModule().getGUINode(CSG_ALL_RADIO_BUTTON);
//        if (allRadio.isSelected()){
//            System.out.println("all is selected");
//            return TAType.All;
//        }
//        
//        RadioButton gradRadio = (RadioButton)app.getGUIModule().getGUINode(CSG_GRADUATE_RADIO_BUTTON);
//        if (gradRadio.isSelected())
//            return TAType.Graduate;
//        else
//            return TAType.Undergraduate;
//    }
//
//    public TeachingAssistantPrototypeOld getSelectedTA() {
//        AppGUIModule gui = app.getGUIModule();
//        TableView<TeachingAssistantPrototypeOld> tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
//        return tasTable.getSelectionModel().getSelectedItem();
//    }
//    
//    /**
//     *
//     * @param ta
//     * @return
//     */
//    public HashMap<TimeSlotOld, ArrayList<DayOfWeek>> getTATimeSlots(TeachingAssistantPrototypeOld ta) {
//        HashMap<TimeSlotOld, ArrayList<DayOfWeek>> timeSlots = new HashMap();
//        for (TimeSlotOld timeSlot : officeHours) {
//            if (timeSlot.hasTA(ta)) {
//                ArrayList<DayOfWeek> daysForTA;
//                daysForTA = timeSlot.getDaysForTA(ta);
//                timeSlots.put(timeSlot, daysForTA);
//            }
//        }
//        return timeSlots;
//    }
//    
//    private boolean hasTA(TeachingAssistantPrototypeOld testTA) {
//        
//        return allTAs.get(TAType.Graduate).contains(testTA)
//                ||
//                allTAs.get(TAType.Undergraduate).contains(testTA);
//    }
//    
//    public boolean isTASelected() {
//        AppGUIModule gui = app.getGUIModule();
//        TableView tasTable = (TableView)gui.getGUINode(CSG_TAS_TABLE_VIEW);
//        return tasTable.getSelectionModel().getSelectedItem() != null;
//    }
//
//    public boolean isLegalNewTA(String name, String email) {
//        if ((name.trim().length() > 0)
//                && (email.trim().length() > 0)) {
//            // MAKE SURE NO TA ALREADY HAS THE SAME NAME
//            TAType type = this.getSelectedType();
//            TeachingAssistantPrototypeOld testTA = new TeachingAssistantPrototypeOld(name, email, type);
////            TeachingAssistantPrototype ta = new TeachingAssistantPrototype("Name","email", Graduate);
////            teachingAssistants.add(ta);
//            
//            if (this.teachingAssistants.contains(testTA))
//                return false;
//            if (this.isLegalNewEmail(email)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    public boolean isLegalNewName(String testName) {
//        if (testName.trim().length() > 0) {
//            for (TeachingAssistantPrototypeOld testTA : this.teachingAssistants) {
//                if (testTA.getName().equals(testName))
//                    return false;
//            }
//            return true;
//        }
//        return false;
//    }
//    
//    public boolean isLegalNewEmail(String email) {
//        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
//                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
//        if (matcher.find()) {
//            for (TeachingAssistantPrototypeOld ta : this.teachingAssistants) {
//                if (ta.getEmail().equals(email.trim()))
//                    return false;
//            }
//            return true;
//        }
//        else return false;
//    }
//    
//    public boolean isDayOfWeekColumn(int columnNumber) {
//        return columnNumber >= 2;
//    }
//    
//    public boolean isTATypeSelected() {
//        AppGUIModule gui = app.getGUIModule();
//        RadioButton allRadioButton = (RadioButton)gui.getGUINode(CSG_ALL_RADIO_BUTTON);
//        return !allRadioButton.isSelected();
//    }
//    
//    public boolean isValidTAEdit(TeachingAssistantPrototypeOld taToEdit, String name, String email) {
//        if (!taToEdit.getName().equals(name)) {
//            if (!this.isLegalNewName(name))
//                return false;
//        }
//        if (!taToEdit.getEmail().equals(email)) {
//            if (!this.isLegalNewEmail(email))
//                return false;
//        }
//        return true;
//    }
//
//    public boolean isValidNameEdit(TeachingAssistantPrototypeOld taToEdit, String name) {
//        if (!taToEdit.getName().equals(name)) {
//            if (!this.isLegalNewName(name))
//                return false;
//        }
//        return true;
//    }
//
//    public boolean isValidEmailEdit(TeachingAssistantPrototypeOld taToEdit, String email) {
//        if (!taToEdit.getEmail().equals(email)) {
//            if (!this.isLegalNewEmail(email))
//                return false;
//        }
//        return true;
//    }    
//
//    public void updateTAs() {
//        TAType type = getSelectedType();
//        selectTAs(type);
//    }
//    
//    public void selectTAs(TAType type) {
//        //teachingAssistants.clear();
//        Iterator<TeachingAssistantPrototypeOld> tasIt = this.teachingAssistantsIterator();
//        while (tasIt.hasNext()) {
//            TeachingAssistantPrototypeOld ta = tasIt.next();
//            if (type.equals(TAType.All)) {
//                teachingAssistants.add(ta);
//            }
//            else if (ta.getType().equals(type.toString())) {
//                teachingAssistants.add(ta);
//            }
//        }
//        
//        // SORT THEM BY NAME
//        sortTAs();
//
//        // CLEAR ALL THE OFFICE HOURS
//        Iterator<TimeSlotOld> officeHoursIt = officeHours.iterator();
//        while (officeHoursIt.hasNext()) {
//            TimeSlotOld timeSlot = officeHoursIt.next();
//            timeSlot.filter(type);
//        }
//        
//        app.getFoolproofModule().updateAll();
//    }
//    
//    public Iterator<TimeSlotOld> officeHoursIterator() {
//        return officeHours.iterator();
//    }
//
//    public Iterator<TeachingAssistantPrototypeOld> teachingAssistantsIterator() {
//        return new AllTAsIterator();
//    }
//    
//    private class AllTAsIterator implements Iterator {
//        Iterator gradIt = allTAs.get(TAType.Graduate).iterator();
//        Iterator undergradIt = allTAs.get(TAType.Undergraduate).iterator();
//
//        public AllTAsIterator() {}
//        
//        @Override
//        public boolean hasNext() {
//            if (gradIt.hasNext() || undergradIt.hasNext())
//                return true;
//            else
//                return false;                
//        }
//
//        @Override
//        public Object next() {
//            if (gradIt.hasNext())
//                return gradIt.next();
//            else if (undergradIt.hasNext())
//                return undergradIt.next();
//            else
//                return null;
//        }
//    }
//    
//}
