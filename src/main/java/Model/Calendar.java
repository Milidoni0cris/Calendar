package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


public class Calendar {

    private final HashMap<Integer, Reminder> reminders;
    private int lastID;
    private Alarm nextAlarm;


    public Calendar(){
        this.reminders = new HashMap<>();
        lastID = 0;
        this.nextAlarm = null;
    }

    public boolean addReminder(Reminder reminder){
        if (reminder == null){
            return false;
        }
        reminder.setID(lastID + 1);
        setAlarmIDs(reminder.getAlarms(), lastID+1);
        this.lastID++;
        return reminders.putIfAbsent(lastID, reminder) == null;
    }

    private void setAlarmIDs(ArrayList<Alarm> alarms, int ID){
        for (Alarm alarm: alarms) {
            alarm.setID(ID);
        }
    }

    public Alarm getNextAlarm(){
        return this.nextAlarm;
    }

    public void deleteReminder(int ID){
        reminders.remove(ID);
        if(reminders.size() == 0) {
            lastID = 0;
            nextAlarm = null;
        }
    }

    private Alarm calculateNextAlarm(LocalDateTime actual){
        Alarm alarm;
        Alarm nextAlarm = this.nextAlarm;
        for (Reminder reminder : reminders.values()){
            if(reminder.isRepeating())
                reminder = ((InfiniteEvent) reminder).getFirstRepetitionBetweenTwoDates(actual, actual.plusDays(((InfiniteEvent)reminder).getFrequency()));

            for (int j = 0; j < reminder.getAlarms().size(); j++) {

                alarm = reminder.getAlarms().get(j);
                if (isTheNewNextAlarm(actual, alarm, nextAlarm))
                    nextAlarm = alarm;
            }
        }
        return nextAlarm;
    }

    public void updateNextAlarm(LocalDateTime actual){
        this.nextAlarm = calculateNextAlarm(actual);
    }




    private boolean isTheNewNextAlarm(LocalDateTime actual, Alarm alarm, Alarm nextAlarm){
        if(nextAlarm == null){
            return true;
        }
        if(alarm.getID() == nextAlarm.getID())
            return false;

        if((alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime())) && alarm.getGoOffTime().isAfter(actual)) {  //cuando creas una nueva que suena antes de la que ya estaba
            return true;
        }
        if(nextAlarm.getGoOffTime().isBefore(actual))//ya sono
            return true;


        return false;
    }



    public ArrayList<Reminder> getListOfReminder(){
        return reminders.values().stream().collect(Collectors.toCollection(ArrayList::new));
    }

    public Reminder getReminder(int ID){
        return reminders.get(ID);
    }

    public int getID(Reminder reminder){
        return reminder.getID();
    }

    public void addAlarmToExistentReminder(int ID, Alarm alarm){
        var reminder = getReminder(ID);
        reminder.addAlarm(alarm);
    }

    private void addAlarms(int ID, ArrayList<Alarm> alarms){
        var reminder =  getReminder(ID);

        for (Alarm alarm : alarms) {

            reminder.addAlarm(alarm);
        }

    }

    private void replaceReminder(Reminder newReminder,int ID){
        reminders.replace(ID, newReminder);
        var alarms = newReminder.getAlarms();
        addAlarms(ID, alarms);
    }

    //PRE: El ID debe ser de un evento
    public Event addRepetitionByDateToExistentEvent(int ID, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        var searchedReminder =  (Event) getReminder(ID);
        if( searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addRepetitionByDate(expirationDate, frequencyStrategy);
        replaceReminder(newReminder, ID);
        return newReminder;
    }

    public Event addOcurrencesRepetitionToExistentEvent(int ID, int ocurrences, FrequencyStrategy frequencyStrategy){
        var searchedReminder = (Event) getReminder(ID);
        if(searchedReminder == null)
            return null;
        var newReminder = searchedReminder.addOcurrencesRepetition(ocurrences, frequencyStrategy);
        replaceReminder(newReminder, ID);

        return newReminder;
    }

    public Event addInfiniteRepetitionToExistentEvent(int ID, FrequencyStrategy frequencyStrategy) {
        var searchedReminder = (Event) getReminder(ID);
        if(searchedReminder == null)
            return null;

        var newReminder = searchedReminder.addInfiniteRepetition(frequencyStrategy);

        replaceReminder(newReminder, ID);

        return newReminder;
    }


    public ArrayList<Reminder> remindersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2){
        var toReturn = new ArrayList<Reminder>();
        var dates = new ArrayList<LocalDateTime>();

        for(Reminder reminder : reminders.values()){
            dates = reminder.showDatesOfReminder(date1, date2);
            if (dates == null)
                continue;
            for (LocalDateTime date : dates) {
                toReturn.add(reminder.repeatReminder(date));
            }
        }

        return toReturn;
    }

    public String writeCalendar(Writer out) throws IOException {
        var gsonBuilder = setBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        final String representationJson = gson.toJson(this);
        out.write(representationJson);
        return representationJson;
    }


    public static Calendar readCalendar(Reader reader) throws RuntimeException{
        var gsonBuilder = setBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.fromJson(reader,Calendar.class);

    }

    private static GsonBuilder setBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(Reminder.class, new ReminderAdapter());
        gsonBuilder.registerTypeAdapter(FrequencyStrategy.class, new FrequencyAdapter());
        gsonBuilder.registerTypeAdapter(Effect.class, new EffectAdapter());
        return gsonBuilder;
    }
}

















