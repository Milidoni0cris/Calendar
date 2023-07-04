package GUI;
import Model.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class AddReminderController{
    private AddReminderView view;
    private Calendar calendar;


    public AddReminderController(AddReminderView view, Calendar calendar){
        this.calendar = calendar;
        this.view = view;
    }


    public void initialize(){
        view.notifySavePress(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String alarm = view.getAlarm().getText();
                Reminder reminder = null;
                if(view.getAllDayHbox().getChildren().contains(view.getRepetition()))
                    reminder = createEvent();
                else
                    reminder = createTask();


                AlarmEnum alarmEnum = AlarmEnum.stringToEnum(alarm);
                if(reminder != null && alarmEnum != null){

                    if(alarmEnum.addAlarmToReminder(view, reminder)){
                        calendar.addReminder(reminder);
                        calendar.updateNextAlarm(LocalDateTime.now());
                        view.closeStage();
                    }
                }
            }
        });


        view.notifyAllDayCheckBox(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(view.getAllDayHbox().getChildren().contains(view.getRepetition())){
                    allDayCheckBoxEvent();
                }
                else{
                    allDayCheckBoxTask();
                }

            }
        });


        view.handleAlarmItem(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getAlarm().setText(item.getText());
                AlarmEnum alarmEnum = AlarmEnum.stringToEnum(item.getText());
                alarmEnum.setAlarmHbox(view);

             }
        });


        view.handleMenuItems1(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getHour1().setText(item.getText());

            }
        });


        view.handleMenuItems2(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getHour2().setText(item.getText());

            }
        });


        view.handleRepetitionItem1(new EventHandler<>(){
            @Override
            public void handle(ActionEvent actionEvent){
                Label label = view.getRepeatsEveryDayLabel();
                view.getRepetition().setText(label.getText());
            }

        });


        view.handleRepetitionItem2(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HBox hbox = view.getHboxRepetition();
                Label label1 = (Label) hbox.getChildren().get(0);
                TextField textField = (TextField) hbox.getChildren().get(1);
                Label label2 = (Label) hbox.getChildren().get(2);
                view.getRepetition().setText(label1.getText() +" "+ textField.getText() +" "+label2.getText());
            }
        });


        view.handleRepetitionItem3(new EventHandler<>(){
            @Override
            public void handle(ActionEvent actionEvent){
                view.getRepetition().setText("No se repite");
            }

        });


        view.handleTimeFormatItem(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuItem item = (MenuItem) actionEvent.getSource();
                view.getTimeFormat().setText(item.getText());
            }
        });

    }



    private void allDayCheckBoxEvent(){
        boolean allDay = view.getAllDay();
        HBox dateChooser = view.getDateChooser();
        if(allDay){
            int index1 = dateChooser.getChildren().indexOf(view.getHour1());
            dateChooser.getChildren().remove(index1);
            int index2 = dateChooser.getChildren().indexOf(view.getHour2());
            dateChooser.getChildren().remove(index2);
        }
        else{
            view.initializeHourPickers();
            int index = dateChooser.getChildren().indexOf(view.getDatePicker1());
            dateChooser.getChildren().add(index+1, view.getHour1());
            int index2 = dateChooser.getChildren().indexOf(view.getA());
            dateChooser.getChildren().add(index2+1, view.getHour2());
        }
    }

    private void allDayCheckBoxTask(){
        boolean allDay = view.getAllDay();
        HBox dateChooser = view.getDateChooser();
        if(allDay){
            dateChooser.getChildren().remove(view.getHour1());
        }
        else{
            view.initializeHourPickers();
            dateChooser.getChildren().add(view.getHour1());
        }
    }

    private Boolean correctDate(LocalDateTime date1, LocalDateTime date2){
        if(view.getAllDay())
            return (date1.toLocalDate().isBefore(date2.toLocalDate()) || date1.toLocalDate().isEqual(date2.toLocalDate()));

        return (date1.isBefore(date2) || date1.isEqual(date2));
    }

    private Reminder createEvent(){

        LocalDate startdate = view.getDatePicker1().getValue();
        LocalDate enddate = view.getDatePicker2().getValue();
        Boolean completeDay = view.getAllDay();
        String repetition = view.getRepetition().getText();

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        if(!completeDay){
            startDate = startdate.atTime(stringToLocalTime(view.getHour1().getText()));
            endDate =  enddate.atTime(stringToLocalTime(view.getHour2().getText()));
        }
        else {
            startDate = startdate.atStartOfDay();
            endDate =  enddate.atStartOfDay();
        }
        if(!correctDate(startDate, endDate)){
            view.getWarningValidDate().setText("Inserte una fecha y hora válida");
            return null;
        }

        Reminder event = null;
        RepetitionEnum repetitionEnum = RepetitionEnum.stringToEnum(repetition);


        return repetitionEnum.eventConstructor(view, startDate, endDate);
    }

    private Task createTask(){
        String title = view.getEventName().getText();
        String description = view.getDescription();
        Boolean completeDay = view.getAllDay();
        String alarm = view.getAlarm().getText();
        LocalDate expirationdate = view.getDatePicker1().getValue();
        LocalDateTime expirationDate = null;
        if(!completeDay){
            expirationDate = expirationdate.atTime(stringToLocalTime(view.getHour1().getText()));
        }
        else {
            expirationDate = expirationdate.atStartOfDay();
        }
        Task task = new Task(title,description,expirationDate,completeDay,false);

        return task;
    }

    private static LocalTime stringToLocalTime(String time){
        String parsed = null;

        var substring = time.substring(0,2);
        if(time.charAt(5) == 'p'){
            substring = time.substring(0,2);
            Integer a = Integer.parseInt(substring);
            if(!substring.equals("12"))
                 a +=12;

            parsed = a.toString() + time.substring(2,5);
        }
        else{
            if(substring.equals("12")){
                parsed = "00" + time.substring(2,5);
            }
            else
                parsed = time.substring(0,5);
        }

        return LocalTime.parse(parsed);

    }

    private enum AlarmEnum{
        NONE("Ninguna"){
            @Override
            public void setAlarmHbox(AddReminderView view){
                view.getHboxAlarm().getChildren().remove(view.getTimeBefore());
                view.getHboxAlarm().getChildren().remove(view.getTimeFormat());
                view.getHboxAlarm().getChildren().remove(view.getAlarmText());
            }

            @Override
            public boolean addAlarmToReminder(AddReminderView view, Reminder reminder){
                return true;
            }
        },
        NOTIFICATION("Notificación"){
            @Override
            public void setAlarmHbox(AddReminderView view){
                if(!view.getHboxAlarm().getChildren().contains(view.getTimeBefore())) {
                    view.getHboxAlarm().getChildren().add(view.getTimeBefore());
                    view.getHboxAlarm().getChildren().add(view.getTimeFormat());
                    view.getHboxAlarm().getChildren().add(view.getAlarmText());
                }
            }
            @Override
            public boolean addAlarmToReminder(AddReminderView view, Reminder reminder) {
                Alarm alarm = createAlarm(view);
                if (alarm == null){
                    view.getWarningValidDate().setText("Inserte una alarma válida");
                    return false;
                }
                else {
                    alarm.setEffect(new Notification());
                    reminder.addAlarm(alarm);
                    return true;
                }

            }
        };

        protected Alarm createAlarm(AddReminderView view){
            Alarm newAlarm = null;
            Effect effect = null;

            String text = view.getTimeBefore().getText();
            if(text.equals("")){
                return null;
            }

            int timeBefore = Integer.parseInt(text);

            String format = view.getTimeFormat().getText();
            LocalDateTime goOffTime = null;
            LocalTime time = stringToLocalTime(view.getHour1().getText());
            LocalDateTime eventDate = view.getDatePicker1().getValue().atTime(time);

            TimeFormat timeFormat = TimeFormat.stringToEnum(format);
            if (timeFormat == null){
                return  null;
            }
            goOffTime = timeFormat.getDateFromDatePicker(view, eventDate, timeBefore);

            return newAlarm = new Alarm(goOffTime, effect, view.getDescription(), eventDate);
        }
        private String type;

        AlarmEnum(String string){
            this.type = string;
        }

        private String getType(){
            return type;
        }
        public abstract void setAlarmHbox(AddReminderView view);

        public abstract boolean addAlarmToReminder(AddReminderView view, Reminder reminder);

        public static AlarmEnum stringToEnum(String string){
            for (AlarmEnum alarmEnum: AlarmEnum.values()) {
                if (alarmEnum.getType().equals(string)){
                    return alarmEnum;
                }
            }
            return null;
        }

    }


    private enum RepetitionEnum{
        NOREPETITION("No se repite"){
            @Override
            Event eventConstructor(AddReminderView view, LocalDateTime startDate, LocalDateTime endDate){
                return new Event(view.getEventName().getText(), view.getDescription(), startDate,endDate, view.getAllDay());
            }
            
        },
        EVERYDAYREPETITION("Todos los días"){
            @Override
            Event eventConstructor(AddReminderView view, LocalDateTime startDate, LocalDateTime endDate) {
                TextField textField = (TextField) view.getHboxRepetition().getChildren().get(1);

                DailyStrategy frequencyStrategy = new DailyStrategy(1);;
                InfiniteEvent event = new InfiniteEvent(view.getEventName().getText(), view.getDescription(), view.getAllDay(),startDate, endDate);
                event.addFrequency(frequencyStrategy);
                return event;
            }
        },
        INTERVALREPETITION("Repetir cada:"){
            @Override
            Event eventConstructor(AddReminderView view, LocalDateTime startDate, LocalDateTime endDate){
                HBox hbox = view.getHboxRepetition();
                TextField textField = (TextField) hbox.getChildren().get(1);
                if (textField.getText().equals("")){
                    view.getWarningValidDate().setText("Inserte un intervalo de repetición");
                    return null;
                }

                DailyStrategy frequencyStrategy = null;
                
                TextField textField1 = (TextField) view.getHboxRepetition().getChildren().get(1);
                int interval =Integer.parseInt(textField1.getText());
                frequencyStrategy = new DailyStrategy(interval);
                
                InfiniteEvent event = new InfiniteEvent(view.getEventName().getText(), view.getDescription(), view.getAllDay(), startDate, endDate);
                event.addFrequency(frequencyStrategy);
                return  event;

            }
        };
        private String type;

        RepetitionEnum(String string){
            this.type = string;
        }

        private String getRepetition(){
            return type;
        }

        public static RepetitionEnum stringToEnum(String string){
            for (RepetitionEnum repetition: RepetitionEnum.values()) {
                if (string.contains(repetition.getRepetition())){
                    return repetition;
                }
            }
            return null;
        }
        
        abstract Event eventConstructor(AddReminderView view, LocalDateTime starDate, LocalDateTime endDate);

    }


    private enum TimeFormat{
        MINUTES("Minutos"){
            @Override
            LocalDateTime getDateFromDatePicker(AddReminderView view, LocalDateTime eventDate, int timeBefore){
                return eventDate.minusMinutes(timeBefore);
            }
        },
        HOURS("Horas"){
            @Override
            LocalDateTime getDateFromDatePicker(AddReminderView view, LocalDateTime eventDate, int timeBefore){
                return eventDate.minusHours(timeBefore);
            }
        },
        DAYS("Días"){
            @Override
            LocalDateTime getDateFromDatePicker(AddReminderView view, LocalDateTime eventDate, int timeBefore){
                return eventDate.minusDays(timeBefore);
            }
        },
        SEMANAS("Semanas"){
            @Override
            LocalDateTime getDateFromDatePicker(AddReminderView view, LocalDateTime eventDate, int timeBefore){
                return eventDate.minusWeeks(timeBefore);
            }
        };

        private String format;

        TimeFormat(String string){
            this.format = string;
        }

        private String getFormat(){
            return format;
        }

        abstract LocalDateTime getDateFromDatePicker(AddReminderView view, LocalDateTime eventDate, int timeBefore);

        public static TimeFormat stringToEnum(String string){
            for (TimeFormat timeFormat: TimeFormat.values()) {
                if (timeFormat.getFormat().equals(string)){
                    return timeFormat;
                }
            }
            return null;
        }

    }
}
