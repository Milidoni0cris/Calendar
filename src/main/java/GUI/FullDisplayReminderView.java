package GUI;

import Model.Event;
import Model.InfiniteEvent;
import Model.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FullDisplayReminderView extends ViewVisitor{

    @FXML
    private Label reminderName;
    @FXML
    private CheckBox completed;
    @FXML
    private Label reminderDate;
    @FXML
    private Label reminderRepetition;
    @FXML
    private Label reminderDescription;
    @FXML
    private VBox alarmVbox;
    @FXML
    private VBox mainVbox;

    boolean isCompleted;

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        completed.setDisable(true);
        stage.show();
        return scene;
    }


    public Label getReminderName() {
        return reminderName;
    }
    public Label getReminderDescription() {
        return reminderDescription;
    }
    public VBox getAlarmVbox() {
        return alarmVbox;
    }


    @Override
    public void visitEvent(Event event) {
        if(event.isCompleteDay()){
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", "dd-MMM-yyyy", "     ", event.getStartDate(), event.getEndDate());
        }
        else{
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy  hh:mma", "dd-MMM-yyyy  hh:mma", "     ", event.getStartDate(), event.getEndDate());

        }
        ((Pane)mainVbox.getChildren().get(0)).getChildren().remove(completed);
        reminderRepetition.setText("");
    }


    @Override
    public void visitTask(Task task) {
        completed.setSelected(isCompleted);
        reminderRepetition.setText("");
        if(task.isCompleteDay()){
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", task.getStartDate());
        }
        else{
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy hh:mma", task.getStartDate());
        }
    }

    @Override
    public void visitInfiniteEvent(InfiniteEvent event) {
        if(event.isCompleteDay()){
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy", "dd-MMM-yyyy", "     ", event.getStartDate(), event.getEndDate());
        }
        else{
            setLabelDateWithFormat(reminderDate, "dd-MMM-yyyy  hh:mma", "dd-MMM-yyyy  hh:mma", "     ", event.getStartDate(), event.getEndDate());

        }
        ((Pane)mainVbox.getChildren().get(0)).getChildren().remove(completed);
        reminderRepetition.setText("Se repite diariamente con un intervalo de "+ Integer.toString(event.getFrequency()) +" días");
    }
}
