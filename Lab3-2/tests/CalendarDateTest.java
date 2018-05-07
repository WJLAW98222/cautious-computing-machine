import org.junit.Test;

import static org.junit.Assert.*;


public class CalendarDateTest {
    @Test
    public void testGetDayOfWeekTrue() throws Exception {
        CalendarDate date = new CalendarDate(2018, 4, 15);
        int result = date.getDayOfWeek();
        assertEquals(7, result);

    }

    @Test
    public void testGetDayOfWeekFalse() throws Exception {
        CalendarDate date1 = new CalendarDate(2018, 4, 15);
        int result1 = date1.getDayOfWeek();
        assertNotEquals(4, result1);

        CalendarDate date2 = new CalendarDate(2018, 2, 29);
        int result2 = date2.getDayOfWeek();
        assertEquals(-1, result2);
    }

    @Test
    public void testIsTheSameDay() throws Exception {
        CalendarDate date1 = new CalendarDate(2018, 4, 15);
        CalendarDate date2 = new CalendarDate(2018, 4, 15);
        CalendarDate date3 = new CalendarDate(2018, 4, 16);
        assertTrue(date1.isTheSameDay(date2));
        assertFalse(date1.isTheSameDay(date3));
        assertFalse(date1.isTheSameDay(null));
    }

    @Test
    public void change() throws Exception {
        CalendarDate date1 = new CalendarDate(2018, 4, 21);
        DateWithHM expect = new DateWithHM(2018, 4, 21, 0, 0);
        DateWithHM date2 = new DateWithHM(2018, 4, 22, 0, 0);
        DateWithHM actual = date1.change();
        assertTrue(expect.isTheSameDate(actual));
        assertFalse(expect.isTheSameDate(date2));

    }
}