package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Model.*;


public class MainController{


    private Calendar calendar;
    private MainView mainView;
    private String ruta;
    private StageState stageState;
    private LocalDateTime stateDate1;
    private LocalDateTime stateDate2;


    public MainController(Calendar calendar, MainView mainview, String ruta) {
        this.calendar = calendar;
        this.mainView = mainview;
        this.stageState = StageState.DAILY;
        this.stateDate1 = LocalDate.now().atStartOfDay();
        this.stateDate2 = LocalDate.now().atTime(23, 59,59);
        this.ruta = ruta;
    }


    public void initialize() {

        ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "dd-MMM-yyyy", LocalDateTime.now());
        displayReminderBetweenTwoDates(stateDate1,stateDate2);
        updateDisplays();


        mainView.notifyDeleteReminder(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = (Button) actionEvent.getSource();
                Pane pane = (Pane) button.getParent();
                Pane pane2 = (Pane) ((Button)pane.getChildren().get(0)).getChildrenUnmodifiable().get(0);
                Label id = (Label) pane2.getChildren().get(2);
                id.getText();

                calendar.deleteReminder(Integer.parseInt(id.getText()));
                mainView.getListOfReminders().getChildren().remove(pane);
                calendar.updateNextAlarm(LocalDateTime.now());
                updateDisplays();
            }
        });


       mainView.notifyCheckBoxDisplayOfReminder(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CheckBox checkbox = (CheckBox) actionEvent.getSource();
                boolean state = checkbox.isSelected();
                Pane pane = (Pane) checkbox.getParent();
                Pane pane2 = (Pane) ((Button)pane.getChildren().get(0)).getChildrenUnmodifiable().get(0);
                Label id = (Label) pane2.getChildren().get(2);
                Task task = (Task) calendar.getReminder(Integer.parseInt(id.getText()));
                task.setCompleteTask(state);

            }
        });


        mainView.notifyCloseAplication(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                closeCalendar();
            }
        });



        mainView.getStage().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                updateDisplays();
            }
        });


        mainView.notifyDatePickerSelection(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                mainView.getDaily().fire();
            }
        });


        mainView.notifySelectNext(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                next();
                updateDisplays();
            }
        });


        mainView.notifySelectPrevious(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                previous();
                updateDisplays();
            }
        });


        mainView.notifySelectDaily(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();

                displayReminderBetweenTwoDates(day.atStartOfDay(), day.atTime(23, 59,59));
                stageState = stageState.DAILY;
                stateDate1 = day.atStartOfDay();
                stateDate2 = day.atTime(23, 59,59);
                ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "dd-MMM-yyyy", stateDate1);
            }
        });


        mainView.notifySelectMonthly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();
                int month = day.getMonthValue();
                int year = day.getYear();
                LocalDateTime start = LocalDateTime.of(year, month, 1,0,0);
                displayReminderBetweenTwoDates(start, start.plusMonths(1).minusDays(1));
                mainView.getDatePicker().setValue(null);
                stageState = StageState.MONTHLY;
                stateDate1 = start;
                stateDate2 = start.plusMonths(1);
                ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(),"MMM-yyyy",stateDate1);
            }
        });


        mainView.notifySelectWeekly(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate day = mainView.getDatePicker().getValue();
                if(day == null)
                    day = LocalDate.now();
                int dayOfWeek = day.getDayOfWeek().getValue();
                LocalDateTime start = day.minusDays(dayOfWeek-1).atStartOfDay();
                displayReminderBetweenTwoDates(start, start.plusDays(7));

                mainView.getDatePicker().setValue(null);
                stageState = stageState.WEEKLY;
                stateDate1 = start;
                stateDate2 = start.plusDays(7);
                ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "MMM  dd", "dd", " - ",stateDate1,stateDate2);
            }
        });


        mainView.notifySelectNewEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader addReminderLoader2 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));

                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = (Parent) addReminderLoader2.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                AddReminderView view2 = addReminderLoader2.getController();
                view2.setView(stage, root);
                view2.getDatePicker1().setValue(LocalDate.now());
                view2.getDatePicker2().setValue(LocalDate.now());
                AddReminderController addReminderController = new AddReminderController(view2, calendar);
                addReminderController.initialize();

            }
        });


        mainView.notifySelectNewTask(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader addReminderLoader2 = new FXMLLoader(getClass().getResource("/Fxml/AddReminder.fxml"));
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = (Parent) addReminderLoader2.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                AddReminderView view2 = addReminderLoader2.getController();
                view2.setView(stage, root);

                view2.getEventName().setPromptText("Inserte el nombre de la tarea");

                view2.getAllDayHbox().getChildren().remove(view2.getRepetition());

                HBox dateChooser = view2.getDateChooser();
                dateChooser.getChildren().remove(view2.getA());
                dateChooser.getChildren().remove(view2.getHour2());
                dateChooser.getChildren().remove(view2.getDatePicker2());
                view2.getDatePicker1().setValue(LocalDate.now());
                Text text = new Text("Ingrese la fecha de inicio de la tarea");
                dateChooser.getChildren().add(0, text);
                AddReminderController addReminderController = new AddReminderController(view2, calendar);
                addReminderController.initialize();
            }
        });
    }


    public void next(){
        if(stageState == StageState.DAILY){
            stateDate1 = stateDate1.plusDays(1);
            stateDate2 = stateDate2.plusDays(1);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "dd-MMM-yyyy", stateDate1);
        }
        else if(stageState == stageState.WEEKLY){
            stateDate1 = stateDate1.plusWeeks(1);
            stateDate2 = stateDate2.plusWeeks(1);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "MMM  dd", "dd", " - ", stateDate1, stateDate2);
        }
        else{
            stateDate1 = stateDate1.plusMonths(1);
            stateDate2 = stateDate2.plusMonths(2);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "MMM-yyyy", stateDate1);
        }
    }


    public void previous(){
        if(stageState == StageState.DAILY){
            stateDate1 = stateDate1.minusDays(1);
            stateDate2 = stateDate2.minusDays(1);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "dd-MMM-yyyy", stateDate1);

        }
        else if(stageState == stageState.WEEKLY){
            stateDate1 = stateDate1.minusWeeks(1);
            stateDate2 = stateDate2.minusWeeks(1);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "MMM  dd", "dd", " - ", stateDate1, stateDate2);

        }
        else{
            stateDate1 = stateDate1.minusMonths(1);
            stateDate2 = stateDate2.minusMonths(2);
            ViewVisitor.setLabelDateWithFormat(mainView.getDateLabel(), "MMM-yyyy", stateDate1);
        }
    }

    public void updateDisplays(){
        displayReminderBetweenTwoDates(stateDate1, stateDate2);
    }

    public void displayReminderBetweenTwoDates(LocalDateTime date1, LocalDateTime date2) {
        mainView.getListOfReminders().getChildren().clear();
        List<Reminder> listOfReminders = calendar.remindersBetweenTwoDates(date1, date2);

        for(int i = 0; i < listOfReminders.size(); i++){
            mainView.getListOfReminders().getChildren().add(createDisplay(listOfReminders.get(i)));
        }

    }

    private Pane createDisplay(Reminder reminder) {
        FXMLLoader displayReminderloader = new FXMLLoader(getClass().getResource("/Fxml/DisplayReminder.fxml"));
        try {
            displayReminderloader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DisplayReminderView displayReminderView = displayReminderloader.getController();
        displayReminderView.setID(reminder.getID());
        displayReminderView.getReminderName().setText(reminder.getTitle());
        displayReminderView.getReminderID().setText(Integer.toString(reminder.getID()));

        displayReminderView.getCompleted().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CheckBox checkbox = (CheckBox) actionEvent.getSource();
                boolean state = checkbox.isSelected();
                Pane pane = (Pane) checkbox.getParent();
                Pane pane2 = (Pane) ((Button) pane.getChildren().get(0)).getChildrenUnmodifiable().get(0);
                Label id = (Label) pane2.getChildren().get(2);
                Task task = (Task) calendar.getReminder(Integer.parseInt(id.getText()));

                task.setCompleteTask(state);
            }
        });


        reminder.accept(displayReminderView);


        displayReminderView.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = (Button) actionEvent.getSource();
                Pane pane = (Pane) button.getParent();
                Pane pane2 = (Pane) ((Button)pane.getChildren().get(0)).getChildrenUnmodifiable().get(0);
                Label id = (Label) pane2.getChildren().get(2);


                calendar.deleteReminder(Integer.parseInt(id.getText()));
                mainView.getListOfReminders().getChildren().remove(pane);
                updateDisplays();
            }
        });


        displayReminderView.getButtonDisplay().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader fullDisplayReminderLoader = new FXMLLoader(getClass().getResource("/Fxml/FullDisplayReminder.fxml"));
                Parent root = null;
                try {
                    root = (Parent)fullDisplayReminderLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FullDisplayReminderView fullDisplayReminderView = fullDisplayReminderLoader.getController();

                fullDisplayReminderView.setView(new Stage(), root);
                fullDisplayReminderView.setCompleted(displayReminderView.getCompleted().isSelected());
                Button reminderButton = (Button)actionEvent.getSource();

                Pane pane = (Pane)reminderButton.getChildrenUnmodifiable().get(0);
                Label id = (Label) pane.getChildren().get(2);

                fullDisplayReminderView.getReminderName().setText(reminder.getTitle());
                fullDisplayReminderView.getReminderDescription().setText(reminder.getDescription());

                reminder.accept(fullDisplayReminderView);

                ArrayList<Alarm> alarms = reminder.getAlarms();

                for(int i = 0; i < alarms.size(); i++) {
                    Label alarm = new Label();
                    alarm.setText("Alarma a las:" + alarms.get(i).getGoOffTime().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy  hh:mma")));
                    fullDisplayReminderView.getAlarmVbox().getChildren().add(alarm);
                }


            }
        });


        if(mainView.getDisplayReminderList() == null)
            mainView.setDisplayReminderList();


        mainView.getDisplayReminderList().add(displayReminderView);

        return displayReminderView.getMainPane();
    }


    enum StageState{
        DAILY,
        WEEKLY,
        MONTHLY
    }


    public void closeCalendar(){
        FileWriter file2 = null;
        try {
            file2 = new FileWriter(ruta);
            calendar.writeCalendar(file2);
            file2.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

