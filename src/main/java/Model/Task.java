package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Reminder{

    private boolean completed;

    public Task(String title, String description, LocalDateTime startDate, boolean completeDay, boolean completed){
        super(title, description, completeDay, startDate);
        this.completed = completed;
        this.isRepeating = false;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void setCompleteTask(boolean completeTask){
        completed = completeTask;
    }

    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        if ((startDate.isAfter(date1) && startDate.isBefore(date2)) || (startDate.isBefore(date2) && startDate.equals(date1)))
            dates.add(startDate);

        return dates;
    }

    public void changeCompleteDay(LocalDateTime startDate){
        completeDay = false;
        this.startDate = startDate;
    }

    public void makeReminderCompleteDay(){
        completeDay = true;
    }

    public Reminder repeatReminder(LocalDateTime startDate){
        var taskRepetition = new Task(title, description, startDate, isCompleteDay(),isCompleted());
        for (Alarm alarm : alarms) {
            taskRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        taskRepetition.setID(this.ID);
        return taskRepetition;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != Task.class)
            return false;

        if(this == o)
            return true;

        if(o == null){
            return false;
        }

        boolean isEqual = true;

        if(!(this.completed ==((Task) o).completed))
            isEqual = false;
        if(!(this.title.equals(((Task) o).title)))
            isEqual = false;
        if(!(this.description.equals(((Task) o).description)))
            isEqual = false;
        if(!(this.completeDay == ((Task) o).completeDay))
            isEqual = false;
        if(!(this.startDate.equals(((Task) o).startDate)))
            isEqual = false;
        if(!(this.alarms.equals(((Task) o).alarms)))
            isEqual = false;

        return isEqual;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitTask(this);
    }


}
