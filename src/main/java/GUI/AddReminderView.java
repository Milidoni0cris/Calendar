package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AddReminderView {

    private Stage stage;

    @FXML
    private Label warningValidDate;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private TextField eventName;

    @FXML
    private CheckBox allDay;

    @FXML
    private MenuButton repetition;

    @FXML
    private TextArea description;

    @FXML
    private MenuButton hour1;

    @FXML
    private MenuButton hour2;

    @FXML
    private MenuButton alarm;

    @FXML
    private TextField timeBefore;

    @FXML
    private MenuButton timeFormat;

    @FXML
    private Button save;

    @FXML
    private HBox chooseDateHbox;

    @FXML
    private  HBox allDayHbox;

    @FXML
    private Text a;

    @FXML
    private HBox hboxRepetition;

    @FXML
    private HBox hboxAlarm;

    @FXML
    private Text alarmText;

    @FXML
    private Label repeatsEveryDayLabel;




    public HBox getHboxRepetition() {
        return hboxRepetition;
    }

    public HBox getHboxAlarm() {
        return hboxAlarm;
    }

    public Text getAlarmText(){
        return alarmText;
    }

    public Label getRepeatsEveryDayLabel(){
        return repeatsEveryDayLabel;
    }

    public HBox getAllDayHbox(){
        return this.allDayHbox;
    }

    public Label getWarningValidDate(){
        return this.warningValidDate;
    }

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        hboxAlarm.getChildren().remove(timeBefore);
        hboxAlarm.getChildren().remove(timeFormat);
        hboxAlarm.getChildren().remove(alarmText);
        stage.show();
        this.stage = stage;

        return scene;
    }



    public void closeStage(){
        if(this.stage != null){
            stage.close();
        }
    }
    public HBox getDateChooser(){
        return chooseDateHbox;
    }

    public DatePicker getDatePicker1() {
        return datePicker1;
    }

    public DatePicker getDatePicker2() {
        return datePicker2;
    }

    public TextField getEventName() {
        return eventName;
    }

    public boolean getAllDay() {
        return allDay.isSelected();
    }

    public MenuButton getRepetition() {
        return repetition;
    }

    public String getDescription() {
        return description.getText();
    }

    public MenuButton getHour1() {
        return hour1;
    }

    public MenuButton getHour2() {
        return hour2;
    }

    public MenuButton getAlarm() {
        return alarm;
    }

    public TextField getTimeBefore() {
        return timeBefore;
    }

    public MenuButton getTimeFormat() {
        return timeFormat;
    }

    public void notifySavePress(EventHandler<ActionEvent> eventHandler){
        save.setOnAction(eventHandler);
    }

    public void notifyAllDayCheckBox(EventHandler<ActionEvent> eventHandler){
        allDay.setOnAction(eventHandler);
    }

    public void handleTimeFormatItem(EventHandler<ActionEvent> eventHandler){
        for(int i = 0; i < timeFormat.getItems().size();i++){
            timeFormat.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public void handleMenuItems1(EventHandler<ActionEvent> eventHandler){
        for(int i=0; i< hour1.getItems().size(); i++){
            hour1.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public void initializeHourPickers(){
        hour1.setText("05:00pm");
        hour2.setText("06:00pm");
    }

    public void handleMenuItems2(EventHandler<ActionEvent> eventHandler){
        for(int i=0; i< hour1.getItems().size(); i++){
            hour2.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public void handleRepetitionItem1(EventHandler<ActionEvent> eventHandler) {
        repetition.getItems().get(1).setOnAction(eventHandler);
    }
    public void handleRepetitionItem2(EventHandler<ActionEvent> eventHandler) {
        repetition.getItems().get(2).setOnAction(eventHandler);
    }

    public void handleRepetitionItem3(EventHandler<ActionEvent> eventHandler){
        repetition.getItems().get(0).setOnAction(eventHandler);
    }

    public void handleAlarmItem(EventHandler<ActionEvent> eventHandler){
        for(int i = 0; i < alarm.getItems().size(); i++){
            alarm.getItems().get(i).setOnAction(eventHandler);
        }
    }

    public javafx.scene.text.Text getA(){
        return a;
    }

}




