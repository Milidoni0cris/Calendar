import Model.*;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AlarmTest {

    //CalculateMinBefore: clases de equivalencia
    // 0, 30, 61, 3600, 3601

    @Test
    public void zeroMinBefore(){
        //arrange
        Effect effect = null;
        LocalDateTime test = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        var alarm = new Alarm(test, effect, "Test", test);

        //act
        int minBefore = alarm.getMinBefore();

        //assert
        assertEquals(minBefore, 0);
    }

    @Test
    public void minBefore(){
        //arrange
        Effect effect = null;
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        LocalDateTime before = LocalDateTime.of(2023, 4, 16, 20, 30, 0);
        var alarm = new Alarm(before, effect, "Test", eventDate);

        //act
        int minBefore = alarm.getMinBefore();

        //assert
        assertEquals(minBefore, 30);
    }

    @Test
    public void hourBefore(){
        Effect effect = null;
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        LocalDateTime before = LocalDateTime.of(2023, 4, 16, 19, 59, 0);
        var alarm = new Alarm(before, effect, "Test", eventDate);

        //act
        int minBefore = alarm.getMinBefore();

        //assert
        assertEquals(minBefore, 61);
    }

    @Test
    public void dayBefore(){
        Effect effect = null;
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        LocalDateTime before = LocalDateTime.of(2023, 4, 15, 21, 0, 0);
        var alarm = new Alarm(before, effect, "Test", eventDate);

        //act
        int minBefore = alarm.getMinBefore();

        //assert
        assertEquals(1440, minBefore);
    }

    //CalculateGoOffTime: clases de equivalencias
    //0 min, 60 min
    @Test
    public void goOffTime(){
        Effect effect = null;
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        LocalDateTime eventDateMinusSixty = LocalDateTime.of(2023, 4, 16, 20, 0, 0);
        var alarm1 = new Alarm(0, effect, "Test",eventDate);
        var alarm2 = new Alarm(60, effect, "Test", eventDate);

        LocalDateTime Test1 = alarm1.getGoOffTime();
        LocalDateTime Test2 = alarm2.getGoOffTime();

        assertEquals(eventDate, Test1);
        assertEquals(eventDateMinusSixty, Test2);
    }

    //ShouldTrigger: clases de equivalencias
    //suena, no suena
    @Test
    public void shouldTrigger(){
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        Effect effect = null;
        var alarm1 = new Alarm(0, effect, "Test",eventDate);

        boolean test1 = alarm1.shouldTrigger(eventDate);
        boolean test2 = alarm1.shouldTrigger(eventDate.minusDays(3));
        assertNotEquals(true, test1);
        assertEquals(false, test2);
    }

    @Test
    public void itSounds(){
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        Effect effect = new Sound();
        var alarm1 = new Alarm(0, effect, "Test",eventDate);
        assertEquals(alarm1.trigger(), Effect.typeOfEffect.SOUND);

    }


    @Test
    public void receiveEmail(){
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        Effect effect = new EMail();
        var alarm1 = new Alarm(0, effect, "Test",eventDate);
        assertEquals(alarm1.trigger(), Effect.typeOfEffect.EMAIL);

    }

    @Test
    public void itNotifies(){
        LocalDateTime eventDate = LocalDateTime.of(2023, 4, 16, 21, 0, 0);
        Effect effect = new Notification();
        var alarm1 = new Alarm(0, effect, "Test",eventDate);
        assertEquals(alarm1.trigger(), Effect.typeOfEffect.NOTIFICATION);

    }

}