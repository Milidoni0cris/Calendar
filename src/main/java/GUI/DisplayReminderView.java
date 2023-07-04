package GUI;

import Model.InfiniteEvent;
import Model.Task;
import Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.CheckBox;


public class DisplayReminderView extends ViewVisitor{

    @FXML
    private Pane mainPane;
    @FXML
    private Label reminderName;
    @FXML
    private Label reminderDate;
    @FXML
    private CheckBox completed;
    @FXML
    private Button delete;
    @FXML
    private Button buttonDisplay;
    @FXML
    private Label reminderID;


    public Pane getMainPane() {
        return mainPane;
    }
    public Label getReminderID(){
        return reminderID;
    }
    public Button getButtonDisplay() {
        return buttonDisplay;
    }
    public Button getDeleteButton(){
        return delete;
    }
    public void setID(int ID){
        this.reminderID.setText(Integer.toString(ID));
    }
    public Label getReminderName() {
        return reminderName;
    }
    public CheckBox getCompleted() {
        return completed;
    }

    @Override
    public void visitEvent(Event event){
        mainPane.getChildren().remove(completed);
        if(event.isCompleteDay())
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", event.getStartDate());

        else{
            setLabelDateWithFormat(reminderDate,"dd-MMM-yyyy hh:mma" ,"dd-MMM-yyyy hh:mma", "  -  ", event.getStartDate(), event.getEndDate());
        }
    }

    @Override
    public void visitInfiniteEvent(InfiniteEvent event){
        mainPane.getChildren().remove(completed);
        if(event.isCompleteDay())
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", event.getStartDate());

        else{
            setLabelDateWithFormat(reminderDate,"dd-MMM-yyyy hh:mma" ,"dd-MMM-yyyy hh:mma", "  -  ", event.getStartDate(), event.getEndDate());
        }
    }

    @Override
    public void visitTask(Task task){
        if(task.isCompleteDay())
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", task.getStartDate());
        else {
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy hh:mma", task.getStartDate());

        }
        completed.setSelected(task.isCompleted());

    }
}
