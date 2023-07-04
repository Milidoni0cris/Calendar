package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class YearlyStrategy implements FrequencyStrategy{

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate){
        if(eventDate.isAfter(date2))
            return null;

        var dates = new ArrayList<LocalDateTime>();

        LocalDateTime repetition  = getFirstRepetitionWithinTwoDates(date1, date2, eventDate);

        if (repetition == null){
            return null;
        }
        while(repetition.isBefore(date2)){
            dates.add(repetition);
            repetition = repetition.plusYears(1);
        }

        return dates;

    }

    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        LocalDateTime lastPossibleDay = starDate;
        for (int i=0; i<ocurrences; i++){
            lastPossibleDay =lastPossibleDay.plusYears(1);
        }
        return lastPossibleDay;
    }

    @Override
    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate) {
        LocalDateTime firstRepetition;

        if (startDate.isAfter(date2)){
            return null;
        }
        else if (startDate.isAfter(date1) && startDate.isBefore(date2))
            return startDate;
        else{
            firstRepetition = startDate;
            long day = startDate.getDayOfMonth();
            while(firstRepetition.isBefore(date1)) {
                if (firstRepetition.plusYears(1).getDayOfMonth() != day){
                    firstRepetition = firstRepetition.plusYears(4);
                    continue;
                }
                firstRepetition = firstRepetition.plusYears(1);
            }

            if (firstRepetition.isBefore(date2))
                return firstRepetition;
            else
                return null;
        }

    }

}
