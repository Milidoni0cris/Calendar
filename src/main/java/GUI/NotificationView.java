package GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotificationView{
    @FXML
    private Label reminderName;

    @FXML
    private Label hourReminder;

    @FXML
    private Label dateReminder;


    public Label getReminderName(){
        return reminderName;
    }

    public Label getHourReminder(){
        return hourReminder;
    }

    public  Label getDateReminder(){
        return dateReminder;
    }

    public Scene setView(Stage stage, Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        return scene;
    }

}
