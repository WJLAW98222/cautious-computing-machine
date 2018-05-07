import org.junit.Test;

import static org.junit.Assert.*;


public class DateWithHMTest {
    @Test
    public void change() throws Exception {
        DateWithHM date = new DateWithHM(2018, 4, 15, 15, 36);
        CalendarDate expect = new CalendarDate(2018, 4, 15);
        CalendarDate date1 = new CalendarDate(2018,4,20);
        CalendarDate actual = date.change();
        assertTrue(actual.isTheSameDay(expect));
        assertFalse(actual.isTheSameDay(date1));
    }

    @Test
    public void isTheSameDate() throws Exception {

        DateWithHM date1 = new DateWithHM(2018, 4, 20, 22, 25);
        DateWithHM date2 = new DateWithHM(2018, 4, 20, 22, 25);
        DateWithHM date3 = new DateWithHM(2018, 4, 21, 22, 25);
        assertFalse(date1.isTheSameDate(null));
        assertFalse(date1.isTheSameDate(date3));
        assertTrue(date1.isTheSameDate(date2));
    }
}