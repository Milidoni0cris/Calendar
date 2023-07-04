package Model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alarm {
    private final String description;

    private final LocalDateTime goOffTime;
    private final int minBefore;
    private int ID;
    private Effect effect;

    //PRE: goOffTime es anterior a eventDate
    public Alarm(LocalDateTime goOffTime, Effect effect, String description, LocalDateTime eventDate){
        this.description = description;
        this.goOffTime = goOffTime;
        this.effect = effect;
        this.minBefore = calculateMinBefore(eventDate);
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setEffect(Effect effect){
        this.effect = effect;
    }

    public Alarm(int minBefore, Effect effect, String description, LocalDateTime eventDate){
        this.description = description;
        this.minBefore = minBefore;
        this.effect = effect;
        this.goOffTime = calculateGoOffTime(eventDate);
    }

    private int calculateMinBefore(LocalDateTime eventDate){
        return (int)this.goOffTime.until(eventDate, ChronoUnit.MINUTES);
    }

    public int getMinBefore(){
        return minBefore;
    }
    public Alarm cloneAlarm(LocalDateTime eventDate){
        return new Alarm(minBefore, effect, description, eventDate);
    }

    public LocalDateTime getGoOffTime() {
        return goOffTime;
    }

    private LocalDateTime calculateGoOffTime(LocalDateTime eventDate){
        return eventDate.minusMinutes(this.minBefore);
    }

    public boolean shouldTrigger(LocalDateTime date){
        return (date.isAfter(this.goOffTime) && date.isBefore(this.goOffTime.plusNanos( (long) (13 * Math.pow(10,7) / 7))));
    }

    public Effect.typeOfEffect  trigger(){
       return  effect.produceEffect();
    }


}

