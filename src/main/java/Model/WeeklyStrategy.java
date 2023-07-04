package Model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class WeeklyStrategy implements FrequencyStrategy{

    private Set<DayOfWeek> weekDays;

    public WeeklyStrategy( Set<DayOfWeek> weekDays){
        this.weekDays = weekDays;
    }

    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate) {

        var dates = new ArrayList<LocalDateTime>();

        LocalDateTime repetition  = getFirstRepetitionWithinTwoDates(date1, date2, eventDate);
        if (repetition == null){
            return null;
        }

        while(repetition.isBefore(date2)){
            if(weekDays.contains(repetition.getDayOfWeek()))
                dates.add(repetition);
                repetition = repetition.plusDays(1);
        }

        return dates;
    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        LocalDateTime lastPossibleDay = starDate;
        int i = ocurrences;
        while(i > 0){
            if(weekDays.contains(lastPossibleDay.getDayOfWeek())){
                i--;
            }
            lastPossibleDay.plusDays(1);
        }
        return lastPossibleDay;
    }


    @Override
    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate) {
        LocalDateTime firstRepetition = date1;

        if(startDate.isAfter(date2))
            return null;

        while(!weekDays.contains(firstRepetition.getDayOfWeek()))
            firstRepetition = firstRepetition.plusDays(1);

        return firstRepetition;

    }

}
