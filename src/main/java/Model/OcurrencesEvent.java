package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OcurrencesEvent extends Event{
    private FrequencyStrategy frequencyStrategy;


    private int ocurrences;
    public OcurrencesEvent(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay, int ocurrences){
        super(title, description, startDate, endDate, completeDay);
        this.ocurrences = ocurrences;
    }


    @Override
    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        LocalDateTime lastPossibleDay= frequencyStrategy.lastDateWithOcurrences(ocurrences, this.startDate);
        if (lastPossibleDay.isBefore((date2)))
            return frequencyStrategy.showDatesOfEvents(date1, lastPossibleDay, startDate);
        else
            return frequencyStrategy.showDatesOfEvents(date1, date2, startDate);

    }

}
