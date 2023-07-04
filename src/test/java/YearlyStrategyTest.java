import Model.YearlyStrategy;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class YearlyStrategyTest {

    @Test
    public void noRepetitionBetweenTheTwoDates(){
        //assert
        LocalDateTime date1 = LocalDateTime.of(2023,10,3,10,50);
        LocalDateTime date2 = LocalDateTime.of(2024,2,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        YearlyStrategy test = new YearlyStrategy();
        LocalDateTime expected = null;

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(expected,result);
    }


    @Test
    public void firstRepetitionAfterOnMonthFromStartDate(){
        //assert
        LocalDateTime date1 = LocalDateTime.of(2023,8,3,10,50);
        LocalDateTime date2 = LocalDateTime.of(2025,10,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        YearlyStrategy test = new YearlyStrategy();
        LocalDateTime expected = eventDate.plusYears(1);

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
        YearlyStrategy test = new YearlyStrategy();
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
        LocalDateTime date2 = LocalDateTime.of(2025,10,3,10,50);
        LocalDateTime eventDate = LocalDateTime.of(2026,9,3,10,50);
        YearlyStrategy test = new YearlyStrategy();
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
        YearlyStrategy test = new YearlyStrategy();
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
        YearlyStrategy test = new YearlyStrategy();
        LocalDateTime expected = eventDate.plusYears(1);

        //act
        LocalDateTime result = test.lastDateWithOcurrences(1, eventDate);

        //assert
        assertEquals(expected,result);
    }


    @Test
    public void manyOcurrences(){
        //assert

        LocalDateTime eventDate = LocalDateTime.of(2022,9,3,10,50);
        YearlyStrategy test = new YearlyStrategy();
        LocalDateTime expected = eventDate.plusYears(4);

        //act
        LocalDateTime result = test.lastDateWithOcurrences(4, eventDate);

        //assert
        assertEquals(expected,result);
    }

    @Test
    public void getCorrectFirstRepetitionIfDay29(){
        //arrange
        LocalDateTime date1 = LocalDateTime.of(2020,3,1,10,0);
        LocalDateTime date2 = LocalDateTime.of(2025,8,31,10,0);
        LocalDateTime eventDate = LocalDateTime.of(2020,2,29,10,0);
        var test = new YearlyStrategy();
        LocalDateTime expected = LocalDateTime.of(2024,2,29,10,0);;

        //act
        LocalDateTime result = test.getFirstRepetitionWithinTwoDates(date1,date2,eventDate);

        //assert
        assertEquals(expected,result);
    }

}
