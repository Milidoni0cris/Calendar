package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.ArrayList;

public class MainView{

    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Button daily;
    @FXML
    private Button weekly;
    @FXML
    private Button monthly;
    @FXML
    private MenuItem addEvent;
    @FXML
    private MenuItem addTask;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label date;
    @FXML
    private Label hour;
    @FXML
    private VBox listOfReminders;

    public Button getDaily() {
        return daily;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDisplayReminderList(){
        displayReminderList = new ArrayList<DisplayReminderView>();
    }

    private ArrayList<DisplayReminderView> displayReminderList;

    private Stage stage;

    public ArrayList<DisplayReminderView> getDisplayReminderList() {
        return displayReminderList;
    }

    public Stage getStage(){
        return stage;
    }

    public Label getDateLabel(){
        return date;
    }

    public Label getHour(){
        return hour;
    }

    public VBox getListOfReminders(){
        return listOfReminders;
    }

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        this.stage = stage;
        stage.setResizable(false);
        stage.show();
        return scene;
    }

    public void notifySelectNewEvent(EventHandler<ActionEvent> eventHandler){
        addEvent.setOnAction(eventHandler);
    }

    public void notifyDeleteReminder(EventHandler<ActionEvent> eventHandler){
        if (displayReminderList == null)
            return;
        for(int i=0; i< displayReminderList.size(); i++){
            displayReminderList.get(i).getDeleteButton().setOnAction(eventHandler);

        }
    }

    public void notifyCheckBoxDisplayOfReminder(EventHandler<ActionEvent> eventHandler){
        if (displayReminderList == null)
            return;

        for(int i=0; i< displayReminderList.size(); i++){
            Pane pane = displayReminderList.get(i).getMainPane();
            if(pane.getChildren().size() > 2) {
                CheckBox checkBox = (CheckBox) pane.getChildren().get(1);
                checkBox.setOnAction(eventHandler);
            }
        }
    }
    
    public void notifyCloseAplication(EventHandler<WindowEvent> eventHandler){
        stage.setOnCloseRequest(eventHandler);
    }

    public void notifySelectNewTask(EventHandler<ActionEvent> eventHandler){
        addTask.setOnAction(eventHandler);
    }

    public void notifySelectDaily(EventHandler<ActionEvent> eventHandler){
        daily.setOnAction(eventHandler);
    }

    public void notifySelectMonthly(EventHandler<ActionEvent> eventHandler){
        monthly.setOnAction(eventHandler);
    }

    public void notifySelectWeekly(EventHandler<ActionEvent> eventHandler){
        weekly.setOnAction(eventHandler);
    }

    public void notifySelectNext(EventHandler<ActionEvent> eventHandler){
        next.setOnAction(eventHandler);
    }

    public void notifySelectPrevious(EventHandler<ActionEvent> eventHandler){
        previous.setOnAction(eventHandler);
    }

    public void notifyDatePickerSelection(EventHandler<ActionEvent> eventHandler){
        datePicker.setOnAction(eventHandler);
    }

}
