package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Reminder {

    protected String title;
    protected boolean isRepeating;
    protected int ID;
    protected String description;
    protected boolean completeDay;
    protected final ArrayList<Alarm> alarms;
    protected LocalDateTime startDate;


    protected Reminder(String title, String description, boolean completeDay, LocalDateTime startDate) {
        if(title == "")
            this.title = "No Title";
        else
            this.title = title;
        this.description = description;
        this.completeDay = completeDay;
        this.alarms = new ArrayList<>();
        this.startDate = startDate;

    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public boolean isRepeating() {
        return isRepeating;
    }
    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean isCompleteDay() {
        return completeDay;
    }

    public ArrayList<Alarm> getAlarms(){
        return this.alarms;
    }

    public void removeAlarm(Alarm alarm){
        this.alarms.remove(alarm);
    }

    public void addAlarm(Alarm alarm){
        alarm.setID(this.ID);
        this.alarms.add(alarm);
    }

    public void setTitle(String title) {
        if(title == "")
            this.title = "No Title";
        else
            this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleteDay(boolean completeDay) {
        this.completeDay = completeDay;
    }

    public void setStartDate(LocalDateTime endDate){
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }


    abstract public void makeReminderCompleteDay();

    abstract public void changeCompleteDay(LocalDateTime endDate);

    abstract public  Reminder repeatReminder(LocalDateTime startDate);

    abstract public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2);

    abstract public void accept(Visitor visitor);

}

