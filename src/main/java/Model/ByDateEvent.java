package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ByDateEvent extends Event{
    private FrequencyStrategy frequencyStrategy;
    private LocalDateTime expirationDate;

    public ByDateEvent(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay, LocalDateTime expirationDate){
        super(title, description, startDate, endDate, completeDay);
        this.expirationDate = expirationDate;
    }

    @Override
    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        var dates = new ArrayList<LocalDateTime>();
        if(expirationDate.isBefore(date1))
            return null;

        LocalDateTime endDate = (expirationDate.isBefore(date2)) ? expirationDate : date2;
        dates = frequencyStrategy.showDatesOfEvents(date1, endDate, this.startDate);
        return dates;
    }
}

