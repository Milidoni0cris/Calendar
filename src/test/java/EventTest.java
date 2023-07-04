import Model.Event;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void createEventWithTwoDates(){
        //arrange
        LocalDateTime date1 = LocalDateTime.of(2023,4,21,10,0);
        LocalDateTime date2 =LocalDateTime.of(2023,5,22,10,0);

        //act
        var event = new Event("test1", "test2", date1, date2, false);
        var eventStartDate = event.getStartDate();
        var eventEndDate = event.getEndDate();


        //assert
        assertEquals(eventStartDate, date1);
        assertEquals(eventEndDate, date2);

    }

    @Test
    public void createEventWithCompleteDay(){
        //arrange
        LocalDateTime date1 = LocalDateTime.of(2023,4,21,10,0);

        //act
        var event1 = new Event("test1", "test1",date1, null, true);
        var event1EndDate = event1.getEndDate();
        var dateExpected = LocalDateTime.of(2023,4,22,0,0);


        //assert
        assertEquals(event1EndDate, dateExpected);
    }

    @Test
    public void repeatTestHasTheCorrectDates(){
        //Arrange
        LocalDateTime date1 =LocalDateTime.of(2019,7,19,1,0);
        var event1 = new Event("test1", "test1", date1, date1.plusDays(1), true);
        LocalDateTime date2 = LocalDateTime.of(2019,7,27,1,0);
        LocalDateTime date3 = date2.plusDays(1);

        //act
        var event2 =  event1.repeatReminder(date2);
        var event2StartDate = event2.getStartDate();
        LocalDateTime event2EndDate = event2.getEndDate();

        //assert
        assertEquals(date2, event2StartDate);
        assertEquals(date3, event2EndDate);
    }


    @Test
    public void repeatTestFromACompleteDayEvent(){ //repeatEvent no siempre nos da dos eventos con la misma duracion
        //arrange
        LocalDateTime date3 = LocalDateTime.of(2023,4,21,10,0);
        LocalDateTime date2 = date3.plusHours(24);
        LocalDateTime date1 =LocalDateTime.of(2019,7,19,1,0);
        Event event1 = new Event("test1", "test1",date1, null, true);
        var event1Duration = event1.getDuration();

        //act
        var event2 = event1.repeatReminder(date3);
        int event2Duration = (int)date3.until(event2.getStartDate(), ChronoUnit.MINUTES);

        boolean sameDuration = event2Duration == event1Duration;
        //assert
        assertEquals(sameDuration, false);
    }

    @Test
    public void repeatTestFromANotCompleteDayEvent(){ //el repeatEvent de dias no completos conserva la duracion
        //arrange
        LocalDateTime date3 = LocalDateTime.of(2023,4,21,10,0);
        LocalDateTime date2 = date3.plusHours(24);
        LocalDateTime date1 =LocalDateTime.of(2019,7,19,1,0);
        var event1 = new Event("test1", "test1",date1, date2, true);
        var event1Duration = event1.getDuration();

        //act
        var event2 = event1.repeatReminder(date3);
        var event2Duration = date3.until(event2.getEndDate(), ChronoUnit.MINUTES);

        //assert
        assertEquals(event1Duration, event2Duration);
    }

    @Test
    public void completeDayEventDurationIs1440(){
        //assert
        var date1 = LocalDateTime.of(2023,4,21,10,30);
        var event1 = new Event("test1","test1",date1,null, true);
        int expectedDuration = 1440;

        //act
        var event1Duration= event1.getDuration();

        //assert
        assertEquals(event1Duration,expectedDuration);
    }

    @Test
    public void eventDurationWithinDays(){
        //arrage
        var date1 = LocalDateTime.of(2023,4,21,10,30);
        var date2 = LocalDateTime.of(2023,4,24,10,29);
        var event1 = new Event("test1","test1",date1, date2, false);
        int expectedDuration = 3*60*24-1;

        //act
        var event1Duration = event1.getDuration();

        //assert
        assertEquals(event1Duration, expectedDuration);
    }

    @Test
    public void eventDurationWithinWeeks(){
        //arrage
        var date1 = LocalDateTime.of(2023,4,10,10,30);
        var date2 = LocalDateTime.of(2023,4,24,10,30);
        var event1 = new Event("test1","test1",date1, date2, false);
        int expectedDuration = 2*7*24*60;

        //act
        var event1Duration = event1.getDuration();

        //assert
        assertEquals(event1Duration, expectedDuration);
    }

    @Test
    public void eventDurationWithinMonths(){
        //arrage
        var date1 = LocalDateTime.of(2023,4,10,10,30);
        var date2 = LocalDateTime.of(2023,5,10,10,30);
        var event1 = new Event("test1","test1",date1, date2, false);
        int expectedDuration = (int)date1.until(date2, ChronoUnit.MINUTES);

        //act
        var event1Duration = event1.getDuration();

        //assert
        assertEquals(event1Duration, expectedDuration);
    }


}