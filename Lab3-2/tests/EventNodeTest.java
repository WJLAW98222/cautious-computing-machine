import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class EventNodeTest {
    @Test
    public void isTheSameEvent() throws Exception {
        CalendarDate date1 = new CalendarDate(2018,4,20);
        CalendarDate date2 = new CalendarDate(2018,4,21);
        CalendarDate date3 = new CalendarDate(2018,4,20);

        DateWithHM date4 = new DateWithHM(2018,4,20,22,42);
        DateWithHM date5 = new DateWithHM(2018,4,21,22,42);
        DateWithHM date6 = new DateWithHM(2018,4,22,22,42);

        List<DateWithHM> list1 = new ArrayList<>();
        List<DateWithHM> list2 = new ArrayList<>();
        List<DateWithHM> list3 = new ArrayList<>();

        list1.add(date4);
        list1.add(date5);

        list2.add(date5);
        list2.add(date6);

        list3.add(date4);
        list3.add(date5);

        String s1 = "s1";
        String s2 = "s2";
        String s3 = "s3";
        String s4 = "s4";
        String s5 = "s5";
        String s6 = "s6";

        EventNode eventNode1 = new EventNode(date1,s1);
        EventNode eventNode2 = new EventNode(date2,s2);
        EventNode eventNode3 = new EventNode(date3,s3);
        EventNode eventNode4 = new EventNode(date3,s1);
        EventNode eventNode5 = new EventNode(list1,s4);
        EventNode eventNode6 = new EventNode(list2,s5);
        EventNode eventNode7 = new EventNode(list3,s6);
        EventNode eventNode8 = new EventNode(list3,s4);

        assertFalse(eventNode1.isTheSameEvent(null));
        assertFalse(eventNode1.isTheSameEvent(eventNode2));
        assertFalse(eventNode1.isTheSameEvent(eventNode3));
        assertFalse(eventNode1.isTheSameEvent(eventNode5));
        assertFalse(eventNode5.isTheSameEvent(eventNode6));
        assertFalse(eventNode5.isTheSameEvent(eventNode7));
        assertTrue(eventNode1.isTheSameEvent(eventNode4));
        assertTrue(eventNode5.isTheSameEvent(eventNode8));


    }

}