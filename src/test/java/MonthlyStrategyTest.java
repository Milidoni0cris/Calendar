import Model.MonthlyStrategy;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class MonthlyStrategyTest {

    @Test
    public void getCorrectFirstRepetitionIfDay31(){
        //arrange
        LocalDateTime date1 = LocalDateTime.of(2023,4,10,10,0);
        LocalDateTime date2 = LocalDateTime.of(2023,8,31,10,0);
        LocalDateTime eventDate = LocalDateTime.of(2023,3,31,10,0);
        var test = new MonthlyStrategy();
        LocalDateTime expected = LocalDateTime.of(2023,5,31,10,0);;

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(expected,result);
    }

    @Test
    public void firstRepetitionAfterOnMonthFromStartDate(){
        //assert
        LocalDateTime date1 = LocalDateTime.of(2022,9,3,10,50);
        LocalDateTime date2 = LocalDateTime.of(2025,10,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2022,8,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = eventDate.plusMonths(1);

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(result,expected);

    }

    @Test
    public void startDateIsTheFirstRepetition(){
        //assert
        LocalDateTime date1 = LocalDateTime.of(2023,8,3,10,50);
        LocalDateTime date2 = LocalDateTime.of(2025,10,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2023,9,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = eventDate;

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(expected,result);
    }

    @Test
    public void startDateIsAfterTheTwoDates(){
        //assert
        LocalDateTime date1 = LocalDateTime.of(2023,8,3,10,50);
        LocalDateTime date2 = LocalDateTime.of(2023,10,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2026,9,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = null;

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(expected,result);
    }


    @Test
    public void zeroOcurrences(){
        //assert

        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = eventDate;

        //act
        LocalDateTime result = test.lastDateWithOcurrences(0, eventDate);

        //assert
        assertEquals(expected,result);
    }

    @Test
    public void oneOcurrences(){
        //assert

        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = eventDate.plusMonths(1);

        //act
        LocalDateTime result = test.lastDateWithOcurrences(1, eventDate);

        //assert
        assertEquals(expected,result);
    }


    @Test
    public void manyOcurrences(){
        //assert

        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        MonthlyStrategy test = new MonthlyStrategy();
        LocalDateTime expected = eventDate.plusMonths(4);

        //act
        LocalDateTime result = test.lastDateWithOcurrences(4, eventDate);

        //assert
        assertEquals(expected,result);
    }


}
