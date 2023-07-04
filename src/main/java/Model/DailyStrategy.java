package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DailyStrategy implements FrequencyStrategy{

    private int frequency;

    public int getFrequency(){
        return frequency;
    }

    public DailyStrategy(int frequency){
        this.frequency = frequency;
    }

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate){

        ArrayList<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime repetition = getFirstRepetitionWithinTwoDates(date1, date2, eventDate);
        if(repetition == null)
            return null;

        while (repetition.isBefore(date2)){
            dates.add(repetition);
            repetition = repetition.plusDays(frequency);
        }

        return dates;
    }

    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime startDate){
        LocalDateTime lastDate = null;
        for(int i = 0; i < ocurrences; i++) lastDate = lastDate.plusDays(frequency);

        return lastDate;
    }


    @Override
    public LocalDateTime getFirstRepetitionWithinTwoDates(LocalDateTime date1, LocalDateTime date2, LocalDateTime startDate) {
        if (startDate.isAfter(date2)){

            return null;
        }


        LocalDateTime firstRepetition = startDate;
        int i = 0;
        while(firstRepetition.isBefore(date1)){
            i++;
            firstRepetition = firstRepetition.plusDays(frequency);
        }
        if(firstRepetition.isBefore(date2))
            return firstRepetition;
        else
            return null;
    }



    @Override
    public boolean equals(Object o){
        if(o.getClass() != DailyStrategy.class)
            return false;

        if(o == null)
            return false;

        if(this == o)
            return false;

        boolean isEqual = true;

        if(!(this.frequency == ((DailyStrategy) o).frequency))
            isEqual = false;


        return isEqual;
    }




}
