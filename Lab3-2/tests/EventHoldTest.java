import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class EventHoldTest {

    @Test
    public void addEvent() throws Exception {

        CalendarDate calendarDate = new CalendarDate(2018, 4, 15);
        String s1 = "s1";
        EventNode eventNode = new EventNode(calendarDate, s1);
        EventHold eventHold = new EventHold();
        assertFalse(eventHold.addEvent(null));
        assertTrue(eventHold.addEvent(eventNode));
    }

    @Test
    public void getEvent() throws Exception {
        CalendarDate calendarDate = new CalendarDate(2018, 4, 15);
        String s1 = "s1";
        EventNode eventNode = new EventNode(calendarDate, s1);
        CalendarDate calendarDate2 = new CalendarDate(2018, 4, 15);
        String s2 = "s2";
        EventNode eventNode2 = new EventNode(calendarDate2, s2);
        CalendarDate calendarDate3 = new CalendarDate(2018, 4, 21);
        String s3 = "s2";
        EventNode eventNode3 = new EventNode(calendarDate3, s3);

        EventHold eventHold = new EventHold();

        assertNull(eventHold.getEvent(null));
        assertNull(eventHold.getEvent(calendarDate));

        eventHold.addEvent(eventNode);
        eventHold.addEvent(eventNode2);
        eventHold.addEvent(eventNode3);

        List<EventNode> expect = new ArrayList<>();
        expect.add(eventNode);
        expect.add(eventNode2);
        List<EventNode> actual = eventHold.getEvent(calendarDate);

        assertNotNull(actual);
        assertEquals(actual.size(), expect.size());

        int count = getCount(actual, expect);

        assertEquals(count, expect.size());

    }

    @Test
    public void getEvents() throws Exception {
        DateWithHM date1 = new DateWithHM(2018, 4, 15, 13, 14);
        DateWithHM date2 = new DateWithHM(2018, 4, 16, 13, 24);
        List<DateWithHM> list = DateUtil.getDaysFromTimes(date1, date2);
        DateWithHM date3 = new DateWithHM(2018, 4, 21, 12, 13);
        DateWithHM date4 = new DateWithHM(2018, 4, 22, 16, 46);
        List<DateWithHM> list2 = DateUtil.getDaysFromTimes(date3, date4);
        DateWithHM date5 = new DateWithHM(2018, 4, 15, 20, 13);
        DateWithHM date6 = new DateWithHM(2018, 4, 22, 12, 46);
        List<DateWithHM> list3 = DateUtil.getDaysFromTimes(date5, date6);

        EventNode eventNode = new EventNode(list, "s");
        EventNode eventNode2 = new EventNode(list2, "s2");
        EventNode eventNode3 = new EventNode(list3, "s3");
        EventHold eventHold = new EventHold();

        assertNull(eventHold.getEvents(null));
        assertNull(eventHold.getEvents(list));

        eventHold.addEvent(eventNode);

        assertNotNull(eventHold.getEvents(list));
        assertNull(eventHold.getEvents(list2));

        eventHold.addEvent(eventNode2);
        eventHold.addEvent(eventNode3);

        List<EventNode> actual = eventHold.getEvents(list3);
        List<EventNode> expect = new ArrayList<>();
        expect.add(eventNode);
        expect.add(eventNode2);
        expect.add(eventNode3);

        int count = getCount(actual, expect);

        assertEquals(count, expect.size());

    }

    @Test
    public void removeEvent() throws Exception {
        CalendarDate calendarDate = new CalendarDate(2018, 4, 15);
        String s1 = "s1";
        EventNode eventNode = new EventNode(calendarDate, s1);
        EventHold eventHold = new EventHold();
        assertFalse(eventHold.removeEvent(null));
        assertFalse(eventHold.removeEvent(eventNode));
        eventHold.addEvent(eventNode);
        assertTrue(eventHold.removeEvent(eventNode));
        assertFalse(eventHold.removeEvent(eventNode));
    }

    @Test
    public void getAllEvents() throws Exception {
        CalendarDate date1 = new CalendarDate(2018, 4, 20);
        CalendarDate date2 = new CalendarDate(2018, 4, 21);

        DateWithHM date3 = new DateWithHM(2018, 4, 20, 22, 33);
        DateWithHM date4 = new DateWithHM(2018, 4, 21, 22, 33);

        List<DateWithHM> list = new ArrayList<>();
        list.add(date3);
        list.add(date4);

        String s1 = "s1";
        String s2 = "s2";
        String s3 = "s3";

        EventNode eventNode1 = new EventNode(date1, s1);
        EventNode eventNode2 = new EventNode(date2, s2);
        EventNode eventNode3 = new EventNode(list, s3);

        EventHold eventHold = new EventHold();
        assertNull(eventHold.getAllEvents());
        eventHold.addEvent(eventNode1);
        eventHold.addEvent(eventNode2);
        eventHold.addEvent(eventNode3);

        List<EventNode> expect = new ArrayList<>();
        expect.add(eventNode1);
        expect.add(eventNode2);
        expect.add(eventNode3);

        List<EventNode> actual = eventHold.getAllEvents();

        assertNotNull(actual);
        assertEquals(actual.size(), expect.size());

        int count = getCount(actual, expect);

        assertEquals(count, expect.size());
    }

    private int getCount(List<EventNode> actual, List<EventNode> expect) {
        int count = 0;
        for (int i = 0; i < expect.size(); i++) {
            for (int j = 0; j < actual.size(); j++) {
                boolean isSame = false;
                if (expect.get(i).isTheSameEvent(actual.get(j))) {
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