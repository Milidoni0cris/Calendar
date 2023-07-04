package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface FrequencyStrategy {

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime finalDate, LocalDateTime eventDate); //finaldate es date2 o la ultima repeticion del evento en su defecto

    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate);

    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate);

    public int getFrequency();
}
