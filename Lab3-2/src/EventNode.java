import java.util.ArrayList;
import java.util.List;

public class EventNode {
    private List<DateWithHM> times = new ArrayList<>();
    private CalendarDate time;
    private String event;
    private boolean exist;


    public EventNode(CalendarDate calendarDate, String event) {
        this.times = null;
        this.time = calendarDate;
        this.event = event;
        this.exist = true;
    }

    public EventNode(List<DateWithHM> times, String event) {
        int size = times.size();
        for (int i = 0; i < size; i++) {
            this.times.add(i, times.get(i));
        }
        this.time = null;
        this.event = event;
        this.exist = true;
    }

    public List<DateWithHM> getTimes() {
        return times;
    }

    public CalendarDate getTime() {
        return time;
    }

    public String getEvent() {
        return event;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    //是否为同一个事件
    public boolean isTheSameEvent(EventNode eventNode) {
        if (eventNode == null) {
            return false;
        }
        boolean isSameTimes;
        if (this.getTimes() == null && eventNode.getTimes() == null) {
            isSameTimes = true;
        } else if (this.getTimes() == null && eventNode.getTimes() != null) {
            isSameTimes = false;
        } else if (this.getTimes() != null && eventNode.getTimes() == null) {
            isSameTimes = false;
        } else {
            isSameTimes = DateUtil.isTheSameList(this.getTimes(), eventNode.getTimes());
        }
        boolean isSameTime;
        if (this.getTime() == null && eventNode.getTime() == null) {
            isSameTime = true;
        } else if (this.getTime() == null && eventNode.getTime() != null) {
            isSameTime = false;
        } else if (this.getTime() != null && eventNode.getTime() == null) {
            isSameTime = false;
        } else {
            isSameTime = this.getTime().isTheSameDay(eventNode.getTime());
        }
        return isSameTimes && isSameTime && this.getEvent().equals(eventNode.getEvent()) && this.isExist() == eventNode.isExist();
    }


}
