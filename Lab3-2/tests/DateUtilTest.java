import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class DateUtilTest {

    @Test
    public void getDaysInMonth() throws Exception {
        assertNull(DateUtil.getDaysInMonth(null));

        CalendarDate date = new CalendarDate(2018, 4, 15);
        List<CalendarDate> actualList = DateUtil.getDaysInMonth(date);
        List<CalendarDate> expectList = new ArrayList<>();
        CalendarDate temp;
        for (int i = 0; i < 30; i++) {
            temp = new CalendarDate(2018, 4, i + 1);
            expectList.add(temp);
        }
        assertNotNull(actualList);
        assertEquals(actualList.size(), expectList.size());
        for (int i = 0; i < expectList.size(); i++) {
            assertTrue(actualList.get(i).isTheSameDay(expectList.get(i)));
        }

        CalendarDate date1 = new CalendarDate(2018, 2, 29);
        List<CalendarDate> actualList1 = DateUtil.getDaysInMonth(date1);
        assertNull(actualList1);

        CalendarDate date2 = new CalendarDate(2018, 22, 29);
        List<CalendarDate> actualList2 = DateUtil.getDaysInMonth(date2);
        assertNull(actualList2);

        CalendarDate date3 = new CalendarDate(2018, 2, 100);
        List<CalendarDate> actualList3 = DateUtil.getDaysInMonth(date3);
        assertNull(actualList3);

        CalendarDate date4 = new CalendarDate(2018, 22, 54);
        List<CalendarDate> actualList4 = DateUtil.getDaysInMonth(date4);
        assertNull(actualList4);

    }

    @Test
    public void testIntegration() {
        List<List<CalendarDate>> generatedCalendar = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            List<CalendarDate> monthCalendar = new ArrayList<>();
            for (int j = 1; j <= 31; j++) {
                CalendarDate temp = new CalendarDate(2018, i, j);
                monthCalendar.add(temp);
            }
            generatedCalendar.add(monthCalendar);
        }

        for (int i = 1; i <= 20; i++) {
            List<CalendarDate> tempCalendar = generatedCalendar.get(i - 1);
            int length = tempCalendar.size();
            CalendarDate temp = tempCalendar.get(length - 1);
            if (DateUtil.isValid(temp)) {
                for (int j = 0; j < length; j++) {
                    assertTrue(tempCalendar.get(j).isTheSameDay(DateUtil.getDaysInMonth(temp).get(j)));
                }
            } else {
                assertNull(DateUtil.getDaysInMonth(temp));
            }
        }
    }

    @Test
    public void isValid() throws Exception {
        assertFalse(DateUtil.isValid(null));

        CalendarDate date1 = new CalendarDate(2018, 4, 15);
        assertTrue(DateUtil.isValid(date1));

        CalendarDate date2 = new CalendarDate(1900, 2, 29);
        assertFalse(DateUtil.isValid(date2));
    }

    @Test
    public void isFormatted() throws Exception {
        assertFalse(DateUtil.isFormatted(null));

        String s1 = "2018-2-9";
        assertTrue(DateUtil.isFormatted(s1));

        String s2 = "2018-02-9";
        assertTrue(DateUtil.isFormatted(s2));

        String s3 = "2018-2-09";
        assertTrue(DateUtil.isFormatted(s3));

        String s4 = "2018-02-09";
        assertTrue(DateUtil.isFormatted(s4));

        String s5 = "2018-22-9";
        assertTrue(DateUtil.isFormatted(s5));

        String s6 = "2018-2-99";
        assertTrue(DateUtil.isFormatted(s6));

        String s7 = "20009-2-29";
        assertFalse(DateUtil.isFormatted(s7));

        String s8 = "2009-222-29";
        assertFalse(DateUtil.isFormatted(s8));

        String s9 = "2009-2-299";
        assertFalse(DateUtil.isFormatted(s9));

        String s10 = "2009-0-29";
        assertFalse(DateUtil.isFormatted(s10));

        String s11 = "2009-00-29";
        assertFalse(DateUtil.isFormatted(s11));

        String s12 = "2009-2-0";
        assertFalse(DateUtil.isFormatted(s12));

        String s13 = "2009-2-00";
        assertFalse(DateUtil.isFormatted(s13));

        String s14 = "209-2-29";
        assertTrue(DateUtil.isFormatted(s14));

        String s15 = "sss2018-1-1";
        assertFalse(DateUtil.isFormatted(s15));

        String s16 = "2018-1-1sss";
        assertFalse(DateUtil.isFormatted(s16));

        String s17 = "2018/2-1";
        assertFalse(DateUtil.isFormatted(s17));

    }

    @Test
    public void isLeapYear() throws Exception {
        assertTrue(DateUtil.isLeapYear(2000));

        assertTrue(DateUtil.isLeapYear(2008));

        assertFalse(DateUtil.isLeapYear(1900));

        assertFalse(DateUtil.isLeapYear(2018));
    }

    @Test
    public void getDaysOfMonth() throws Exception {
        assertEquals(-1, DateUtil.getDaysOfMonth(2018, -3));
        assertEquals(-1, DateUtil.getDaysOfMonth(2018, 22));
        assertEquals(-1, DateUtil.getDaysOfMonth(-1, 12));

        assertEquals(29, DateUtil.getDaysOfMonth(2000, 2));
        assertEquals(31, DateUtil.getDaysOfMonth(2018, 1));
        assertEquals(30, DateUtil.getDaysOfMonth(2018, 4));

        assertNotEquals(29, DateUtil.getDaysOfMonth(2018, 2));

    }

    @Test
    public void getDaysFromTimes() throws Exception {
        DateWithHM date1 = new DateWithHM(2018, 2, 26, 23, 59);
        assertNull(DateUtil.getDaysFromTimes(null, null));
        assertNull(DateUtil.getDaysFromTimes(null, date1));
        assertNull(DateUtil.getDaysFromTimes(date1, null));
        DateWithHM date2 = new DateWithHM(2018, 2, 27, 0, 0);
        DateWithHM date3 = new DateWithHM(2018, 2, 28, 0, 0);
        DateWithHM date4 = new DateWithHM(2018, 3, 1, 0, 0);
        DateWithHM date5 = new DateWithHM(2018, 3, 2, 13, 23);
        List<DateWithHM> actual = DateUtil.getDaysFromTimes(date1, date5);
        List<DateWithHM> expect = new ArrayList<>();
        expect.add(date1);
        expect.add(date2);
        expect.add(date3);
        expect.add(date4);
        expect.add(date5);
        assertNotNull(actual);
        int size = actual.size();
        assertEquals(actual.size(), expect.size());
        for (int i = 0; i < size; i++) {
            assertTrue(actual.get(i).isTheSameDate(expect.get(i)));
        }
    }

    @Test
    public void isFormattedTime() throws Exception {

        assertFalse(DateUtil.isFormattedTime(null));

        String s1 = "2018-4-15-14:26";
        assertTrue(DateUtil.isFormattedTime(s1));

        String s2 = "2018-04-15-14:26";
        assertTrue(DateUtil.isFormattedTime(s2));

        String s3 = "2018-4-05-14:26";
        assertTrue(DateUtil.isFormattedTime(s3));

        String s4 = "2018-04-05-14:26";
        assertTrue(DateUtil.isFormattedTime(s4));

        String s5 = "2018-4-15-04:06";
        assertTrue(DateUtil.isFormattedTime(s5));

        String s6 = "2018-4-15-44:66";
        assertTrue(DateUtil.isFormattedTime(s6));

        String s7 = "20118-4-15-14:26";
        assertFalse(DateUtil.isFormattedTime(s7));

        String s8 = "2018-124-15-14:26";
        assertFalse(DateUtil.isFormattedTime(s8));

        String s9 = "2018-4-155-14:26";
        assertFalse(DateUtil.isFormattedTime(s9));

        String s10 = "2018-4-15-114:26";
        assertFalse(DateUtil.isFormattedTime(s10));

        String s11 = "20118-4-15-14:226";
        assertFalse(DateUtil.isFormattedTime(s11));

        String s12 = "sss2018-4-15-14:26";
        assertFalse(DateUtil.isFormattedTime(s12));

        String s13 = "2018-4-15-14:26sss";
        assertFalse(DateUtil.isFormattedTime(s13));

        String s14 = "2018/4-15-14:26";
        assertFalse(DateUtil.isFormattedTime(s14));

    }

    @Test
    public void isValidTime() throws Exception {
        assertFalse(DateUtil.isValidTime(null));

        DateWithHM date1 = new DateWithHM(2018, 2, 28, 23, 59);
        assertTrue(DateUtil.isValidTime(date1));

        DateWithHM date2 = new DateWithHM(2018, 2, 29, 23, 59);
        assertFalse(DateUtil.isValidTime(date2));

        DateWithHM date3 = new DateWithHM(2018, 22, 15, 23, 59);
        assertFalse(DateUtil.isValidTime(date3));

        DateWithHM date4 = new DateWithHM(2018, 4, 34, 23, 59);
        assertFalse(DateUtil.isValidTime(date4));

        DateWithHM date6 = new DateWithHM(2018, 4, 29, 25, 59);
        assertFalse(DateUtil.isValidTime(date6));

        DateWithHM date7 = new DateWithHM(2018, 4, 29, 23, 64);
        assertFalse(DateUtil.isValidTime(date7));


    }

    @Test
    public void isProperTime() throws Exception {
        DateWithHM date1 = new DateWithHM(2018, 2, 28, 23, 59);
        assertNull(DateUtil.getDaysFromTimes(null, null));
        assertNull(DateUtil.getDaysFromTimes(null, date1));
        assertNull(DateUtil.getDaysFromTimes(date1, null));

        DateWithHM date2 = new DateWithHM(2018, 3, 1, 13, 24);
        assertTrue(DateUtil.isProperTime(date1, date2));

        DateWithHM date3 = new DateWithHM(2018, 2, 1, 13, 24);
        assertFalse(DateUtil.isProperTime(date1, date3));

        DateWithHM date4 = new DateWithHM(2018, 2, 28, 23, 59);
        assertTrue(DateUtil.isProperTime(date1, date4));

        DateWithHM date5 = new DateWithHM(2018, 2, 28, 13, 24);
        assertFalse(DateUtil.isProperTime(date1, date5));
        assertTrue(DateUtil.isProperTime(date5, date1));

    }

    @Test
    public void isCalendarDateInList() throws Exception {
        assertFalse(DateUtil.isCalendarDateInList(null, null));
        DateWithHM date1 = new DateWithHM(2018, 4, 20, 1, 23);
        DateWithHM date2 = new DateWithHM(2018, 4, 21, 1, 23);
        DateWithHM date3 = new DateWithHM(2018, 4, 22, 1, 23);
        CalendarDate date4 = new CalendarDate(2018, 4, 20);

        List<DateWithHM> list = new ArrayList<>();
        list.add(date1);
        list.add(date2);
        list.add(date3);
        assertFalse(DateUtil.isCalendarDateInList(list, null));
        assertFalse(DateUtil.isCalendarDateInList(null, date4));
        assertTrue(DateUtil.isCalendarDateInList(list, date4));
        list.remove(date1);
        assertFalse(DateUtil.isCalendarDateInList(list, date4));

    }

    @Test
    public void isContained() throws Exception {
        DateWithHM date1 = new DateWithHM(2018, 4, 20, 12, 20);
        DateWithHM date2 = new DateWithHM(2018, 4, 22, 13, 20);
        DateWithHM date3 = new DateWithHM(2018, 4, 24, 14, 20);
        DateWithHM date4 = new DateWithHM(2018, 4, 26, 15, 20);

        List<DateWithHM> list1 = DateUtil.getDaysFromTimes(date1, date2);
        List<DateWithHM> list2 = DateUtil.getDaysFromTimes(date3, date4);
        List<DateWithHM> list3 = DateUtil.getDaysFromTimes(date1, date4);
        List<DateWithHM> list4 = DateUtil.getDaysFromTimes(date2, date3);
        List<DateWithHM> list10 = DateUtil.getDaysFromTimes(date1, date3);
        List<DateWithHM> list11 = DateUtil.getDaysFromTimes(date2, date4);

        assertFalse(DateUtil.isContained(list1, list2));
        assertFalse(DateUtil.isContained(list2, list1));
        assertTrue(DateUtil.isContained(list3, list4));
        assertTrue(DateUtil.isContained(list4, list3));
        assertTrue(DateUtil.isContained(list10, list11));
        assertTrue(DateUtil.isContained(list11, list10));

        DateWithHM date5 = new DateWithHM(2018, 4, 22, 11, 20);
        List<DateWithHM> list5 = DateUtil.getDaysFromTimes(date5, date3);

        assertTrue(DateUtil.isContained(list1, list5));
        assertTrue(DateUtil.isContained(list5, list1));

        DateWithHM date6 = new DateWithHM(2018, 4, 20, 13, 20);
        DateWithHM date7 = new DateWithHM(2018, 4, 20, 14, 20);
        DateWithHM date8 = new DateWithHM(2018, 4, 20, 15, 20);

        List<DateWithHM> list6 = DateUtil.getDaysFromTimes(date1, date6);
        List<DateWithHM> list7 = DateUtil.getDaysFromTimes(date7, date8);
        List<DateWithHM> list8 = DateUtil.getDaysFromTimes(date1, date7);
        List<DateWithHM> list9 = DateUtil.getDaysFromTimes(date6, date8);
        List<DateWithHM> list12 = DateUtil.getDaysFromTimes(date1, date8);
        List<DateWithHM> list13 = DateUtil.getDaysFromTimes(date6, date7);

        assertFalse(DateUtil.isContained(list6, list7));
        assertFalse(DateUtil.isContained(list7, list6));
        assertTrue(DateUtil.isContained(list8, list9));
        assertTrue(DateUtil.isContained(list9, list8));
        assertTrue(DateUtil.isContained(list12, list13));
        assertTrue(DateUtil.isContained(list13, list12));

    }

    @Test
    public void isTheSameList() throws Exception {
        DateWithHM date1 = new DateWithHM(2018, 4, 20, 12, 20);
        DateWithHM date2 = new DateWithHM(2018, 4, 22, 13, 20);
        DateWithHM date3 = new DateWithHM(2018, 4, 24, 14, 20);
        DateWithHM date4 = new DateWithHM(2018, 4, 26, 15, 20);
        DateWithHM date5 = new DateWithHM(2018, 4, 22, 11, 20);
        DateWithHM date6 = new DateWithHM(2018, 4, 20, 13, 20);
        DateWithHM date7 = new DateWithHM(2018, 4, 20, 14, 20);
        DateWithHM date8 = new DateWithHM(2018, 4, 20, 15, 20);

        List<DateWithHM> list1 = new ArrayList<>();
        List<DateWithHM> list2 = new ArrayList<>();
        List<DateWithHM> list3 = new ArrayList<>();
        List<DateWithHM> list4 = new ArrayList<>();

        list1.add(date1);
        list1.add(date2);
        list1.add(date3);
        list1.add(date4);

        list2.add(date5);
        list2.add(date6);
        list2.add(date7);
        list2.add(date8);

        list3.add(date1);
        list3.add(date2);
        list3.add(date3);
        list3.add(date4);

        list4.add(date1);
        list4.add(date2);
        list4.add(date3);
        list4.add(date4);
        list4.add(date5);

        assertFalse(DateUtil.isTheSameList(null, null));
        assertFalse(DateUtil.isTheSameList(list1, null));
        assertFalse(DateUtil.isTheSameList(null, list1));
        assertFalse(DateUtil.isTheSameList(list1, list2));
        assertFalse(DateUtil.isTheSameList(list1, list4));
        assertTrue(DateUtil.isTheSameList(list1, list3));

    }

    @Test
    public void getDays() throws Exception {
        assertNull(DateUtil.getDays(null));
        CalendarDate date1 = new CalendarDate(2018, 5, 32);
        assertNull(DateUtil.getDays(date1));
        CalendarDate date = new CalendarDate(2018, 5, 5);
        String[][] actual = DateUtil.getDays(date);
        int[][] expectInt = {
                {0, 0, 1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24, 25, 26},
                {27, 28, 29, 30, 31, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };
        String[][] expect = new String[6][7];
        for (int i = 0; i < expect.length; i++) {
            for (int j = 0; j < expect[i].length; j++) {
                if (expectInt[i][j] == 0) {
                    expect[i][j] = "  ";
                } else {
                    expect[i][j] = String.valueOf(expectInt[i][j]);
                }
                assertEquals(actual[i][j], expect[i][j]);
            }
        }

    }

    @Test
    //将List<EventNode> 拆分成一个List<CalendarDate>，并且无重复
    public void getCalendarDateFromEvents() throws Exception {
        assertNull(DateUtil.getCalendarDateFromEvents(null));

        //date1和date5和date6是同一天，date3和date8是同一天
        CalendarDate date1 = new CalendarDate(2018, 4, 20);
        CalendarDate date2 = new CalendarDate(2018, 4, 21);
        CalendarDate date3 = new CalendarDate(2018, 4, 22);
        CalendarDate date4 = new CalendarDate(2018, 4, 23);
        CalendarDate date5 = new CalendarDate(2018, 4, 20);

        DateWithHM date6 = new DateWithHM(2018, 4, 20, 12, 35);
        DateWithHM date7 = new DateWithHM(2018, 4, 11, 12, 35);
        DateWithHM date8 = new DateWithHM(2018, 4, 22, 12, 35);
        DateWithHM date9 = new DateWithHM(2018, 4, 13, 12, 35);
        DateWithHM date10 = new DateWithHM(2018, 4, 25, 12, 35);

        String s1 = "s1";
        String s2 = "s2";
        String s3 = "s3";
        String s4 = "s4";
        String s5 = "s5";
        String s6 = "s6";
        String s7 = "s7";

        List<DateWithHM> list1 = new ArrayList<>();
        List<DateWithHM> list2 = new ArrayList<>();

        list1.add(date6);
        list1.add(date7);
        list1.add(date8);
        list1.add(date9);

        list2.add(date6);
        list2.add(date7);
        list2.add(date9);
        list2.add(date10);

        EventNode eventNode1 = new EventNode(date1, s1);
        EventNode eventNode2 = new EventNode(date2, s2);
        EventNode eventNode3 = new EventNode(date3, s3);
        EventNode eventNode4 = new EventNode(date4, s4);
        EventNode eventNode5 = new EventNode(date5, s5);
        EventNode eventNode6 = new EventNode(list1, s6);
        EventNode eventNode7 = new EventNode(list2, s7);

        List<EventNode> eventList = new ArrayList<>();
        eventList.add(eventNode1);
        eventList.add(eventNode2);
        eventList.add(eventNode3);
        eventList.add(eventNode4);
        eventList.add(eventNode5);
        eventList.add(eventNode6);
        eventList.add(eventNode7);

        List<CalendarDate> expect = new ArrayList<>();
        expect.add(date1);
        expect.add(date2);
        expect.add(date3);
        expect.add(date4);
        expect.add(new CalendarDate(2018, 4, 11));
        expect.add(new CalendarDate(2018, 4, 13));
        expect.add(new CalendarDate(2018, 4, 25));

        List<CalendarDate> actual = DateUtil.getCalendarDateFromEvents(eventList);

        assertNotNull(actual);
        assertEquals(expect.size(), actual.size());

        int count = getCount(expect, actual);
        assertEquals(count, expect.size());
    }

    @Test
    //去除链表中的重复元素
    public void removeRepeat() throws Exception {
        assertNull(DateUtil.removeRepeat(null));

        CalendarDate date1 = new CalendarDate(2018, 4, 20);
        CalendarDate date2 = new CalendarDate(2018, 4, 21);
        CalendarDate date3 = new CalendarDate(2018, 4, 22);
        CalendarDate date4 = new CalendarDate(2018, 4, 23);
        CalendarDate date5 = new CalendarDate(2018, 4, 20);
        CalendarDate date6 = new CalendarDate(2018, 4, 22);
        CalendarDate date7 = new CalendarDate(2018, 4, 20);

        List<CalendarDate> list = new ArrayList<>();
        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
        list.add(date5);
        list.add(date6);
        list.add(date7);

        List<CalendarDate> expectList = new ArrayList<>();
        expectList.add(date1);
        expectList.add(date2);
        expectList.add(date3);
        expectList.add(date4);

        List<CalendarDate> actualList = DateUtil.removeRepeat(list);

        assertNotNull(actualList);
        assertEquals(expectList.size(), actualList.size());

        int count = getCount(expectList, actualList);
        assertEquals(count, expectList.size());

    }

    @Test
    //把DateWithHM转换为String类型
    public void changeDateWithHMToString() throws Exception {
        assertNull(DateUtil.changeDateWithHMToString(null));

        DateWithHM dateWithHM1 = new DateWithHM(2018, 4, 20, 22, 7);
        String expect1 = "2018" + "-" + "4" + "-" + "20" + "-" + "22" + ":" + "07";
        String actual1 = DateUtil.changeDateWithHMToString(dateWithHM1);
        assertEquals(expect1, actual1);
        DateWithHM dateWithHM2 = new DateWithHM(2018, 4, 20, 3, 14);
        String expect2 = "2018" + "-" + "4" + "-" + "20" + "-" + "03" + ":" + "14";
        String actual2 = DateUtil.changeDateWithHMToString(dateWithHM2);
        assertEquals(expect2, actual2);

        DateWithHM dateWithHM3 = new DateWithHM(2018, 4, 20, 13, 14);
        String expect3 = "2018" + "-" + "4" + "-" + "20" + "-" + "13" + ":" + "14";
        String actual3 = DateUtil.changeDateWithHMToString(dateWithHM3);
        assertEquals(expect3, actual3);

        DateWithHM date4 = new DateWithHM(2018, 4, 32, 22, 56);
        assertNull(DateUtil.changeDateWithHMToString(date4));

    }

    @Test
    //把CalendarDate转换为String类型
    public void changeCalendarDateToString() throws Exception {
        assertNull(DateUtil.changeCalendarDateToString(null));

        CalendarDate date1 = new CalendarDate(2018, 4, 20);
        String expect = "2018" + "-" + "4" + "-" + "20";
        String actual = DateUtil.changeCalendarDateToString(date1);
        assertEquals(expect, actual);

        CalendarDate date2 = new CalendarDate(2018, 4, 32);
        assertNull(DateUtil.changeCalendarDateToString(date2));

    }


    private int getCount(List<CalendarDate> expect, List<CalendarDate> actual) {
        int count = 0;
        for (int i = 0; i < actual.size(); i++) {
            for (int j = 0; j < expect.size(); j++) {
                boolean isSame = false;
                if (actual.get(i).isTheSameDay(expect.get(j))) {
                    isSame = true;
                }
                if (isSame) {
                    count++;
                }
            }
        }
        return count;
    }
}