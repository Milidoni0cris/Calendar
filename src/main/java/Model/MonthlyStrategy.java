package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MonthlyStrategy implements FrequencyStrategy {

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate){

        var dates = new ArrayList<LocalDateTime>();

        LocalDateTime repetition  = getFirstRepetitionWithinTwoDates(date1, date2, eventDate);
        if (repetition == null)
            return null;

        long day = repetition.getDayOfMonth();
        while(repetition.isBefore(date2)){
            dates.add(repetition);
            if(repetition.plusMonths(1).getDayOfMonth() != day){
                repetition = repetition.plusMonths(2);
                continue;
            }
            repetition = repetition.plusMonths(1);
        }

        return dates;

    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        LocalDateTime lastPossibleDay = starDate;
        for (int i=0; i<ocurrences; i++){
            lastPossibleDay =lastPossibleDay.plusMonths(1);
        }
        return lastPossibleDay;
    }

    @Override
    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate) {
        LocalDateTime firstRepetition = null;

        if (startDate.isAfter(date2)){
            return null;
        }
        else if (startDate.isAfter(date1) && startDate.isBefore(date2))
            return startDate;
        else{
            firstRepetition = startDate;
            long day = startDate.getDayOfMonth();
            while(firstRepetition.isBefore(date1)) {
                if (firstRepetition.plusMonths(1).getDayOfMonth() != day){
                    firstRepetition = firstRepetition.plusMonths(2);
                    continue;
                }
                firstRepetition = firstRepetition.plusMonths(1);
            }

            if (firstRepetition.isBefore(date2))
                return firstRepetition;
            else
                return null;
        }

    }

    public int getFrequency(){
        return 1;
    }

}
