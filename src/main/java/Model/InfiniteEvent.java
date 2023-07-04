package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class InfiniteEvent extends Event{
    public FrequencyStrategy getFrequencyStrategy() {
        return frequencyStrategy;
    }

    private FrequencyStrategy frequencyStrategy;

    public int getFrequency (){
        return frequencyStrategy.getFrequency();
    }

    public InfiniteEvent(String title, String description, boolean completeDay, LocalDateTime startDate,LocalDateTime endDate){
        super(title, description, startDate, endDate, completeDay);
        this.frequencyStrategy = null;
        this.isRepeating = true;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != InfiniteEvent.class)
            return false;

        if(o == null)
            return false;

        if(this == o)
            return false;

        boolean isEqual = true;

        if(!(this.title.equals(((InfiniteEvent) o).title)))
            isEqual = false;
        if(!(this.description.equals(((InfiniteEvent) o).description)))
            isEqual = false;
        if(!(this.completeDay == ((InfiniteEvent) o).completeDay))
            isEqual = false;
        if(!(this.startDate.equals(((InfiniteEvent) o).startDate)))
            isEqual = false;
        if(!(this.endDate.equals(((InfiniteEvent) o).endDate)))
            isEqual = false;
        if(!(this.alarms.equals(((InfiniteEvent) o).alarms)))
            isEqual = false;
        if (!(this.frequencyStrategy.equals(((InfiniteEvent) o).frequencyStrategy)))
            isEqual = false;

        return isEqual;
    }

    public Event getFirstRepetitionBetweenTwoDates(LocalDateTime date1, LocalDateTime date2){
        LocalDateTime date = this.frequencyStrategy.getFirstRepetitionWithinTwoDates(date1, date2, startDate);


        return repeatReminder(date);
    }


    @Override
    public InfiniteEvent repeatReminder(LocalDateTime startDate){
        var eventDuration = this.getDuration();
        var eventRepetition = new InfiniteEvent(title, description, isCompleteDay(), startDate, startDate.plusMinutes(eventDuration));

        eventRepetition.addFrequency(getFrequencyStrategy());
        eventRepetition.setID(this.ID);

        for (Alarm alarm : alarms) {
            eventRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }

        eventRepetition.setRepeating(true);
        return eventRepetition;
    }

    @Override
    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }


    @Override
    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        return frequencyStrategy.showDatesOfEvents(date1, date2, startDate);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitInfiniteEvent(this);
    }
}
