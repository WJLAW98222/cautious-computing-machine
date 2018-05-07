import java.util.ArrayList;
import java.util.List;

public class EventHold {
    private List<EventNode> events;

    public EventHold() {
        events = new ArrayList<>();
    }

    //添加成功返回true,否则返回false
    public boolean addEvent(EventNode eventNode) {
        if (eventNode == null) {
            return false;
        } else {
            events.add(eventNode);
            return true;
        }
    }

    //按天查找
    public List<EventNode> getEvent(CalendarDate calendarDate) {
        if (calendarDate == null)
            return null;
        if (events.size() == 0) {
            return null;
        }

        List<EventNode> list = new ArrayList<>();
        int size = events.size();
        for (int i = 0; i < size; i++) {
            if (events.get(i).isExist()) {
                if (events.get(i).getTime() == null && DateUtil.isCalendarDateInList(events.get(i).getTimes(), calendarDate)) {
                    list.add(events.get(i));
                } else if (events.get(i).getTimes() == null && events.get(i).getTime().isTheSameDay(calendarDate)) {
                    list.add(events.get(i));
                }
            }
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    //按时间段查找
    public List<EventNode> getEvents(List<DateWithHM> dates) {
        if (dates == null || dates.size() == 0)
            return null;
        if (events.size() == 0) {
            return null;
        }
        List<EventNode> list = new ArrayList<>();
        int size = events.size();
        for (int i = 0; i < size; i++) {
            if (events.get(i).isExist()) {
                if (events.get(i).getTime() == null && DateUtil.isContained(events.get(i).getTimes(), dates)) {
                    list.add(events.get(i));
                } else if (events.get(i).getTimes() == null && DateUtil.isCalendarDateInList(dates, events.get(i).getTime())) {
                    list.add(events.get(i));
                }
            }
        }
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    //删除节点,成功返回true,否则返回false
    public boolean removeEvent(EventNode eventNode) {
        if (eventNode == null) {
            return false;
        }
        if (events.size() == 0) {
            return false;
        }
        int size = events.size();
        boolean isRemoved = false;
        for (int i = 0; i < size; i++) {
            if (events.get(i).isExist() && events.get(i).isTheSameEvent(eventNode)) {
                events.get(i).setExist(false);
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    //得到所有的事件
    public  List<EventNode> getAllEvents() {
        if (events.size() == 0) {
            return null;
        }
        return events;
    }
}
