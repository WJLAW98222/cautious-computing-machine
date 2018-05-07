
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtil {

    //用于得到今天的CalendarDate对象
    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    //用于得到某一天所在月的所有天数的List<CalendarDate>
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (!isValid(date)) {
            return null;
        } else {
            int year = date.getYear();
            int month = date.getMonth();
            int days = getDaysOfMonth(year, month);
            List<CalendarDate> dateOfMonth = new ArrayList<>();
            for (int i = 1; i <= days; i++) {
                dateOfMonth.add(new CalendarDate(year, month, i));
            }
            return dateOfMonth;
        }
    }

    //用于判断某个日期是否合法有效，例如“2018-2-29”就是无效的
    public static boolean isValid(CalendarDate date) {
        if (date == null) {
            return false;
        }
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        return day <= getDaysOfMonth(year, month);
    }

    //用于判断年月日格式是否正确，“yyyy-MM-dd”，例“2018-04-20”或“2018-4-20”
    public static boolean isFormatted(String dateString) {
        String regex = "^\\d{1,4}-\\d{1,2}-\\d{1,2}$";
        if (dateString != null && dateString.matches(regex)) {
            String[] array = dateString.split("-");
            return !(array[1].equals("00") || array[1].equals("0") || array[2].equals("00") || array[2].equals("0"));
        } else return false;
    }

    //用于判断某年是否是闰年
    public static boolean isLeapYear(int year) {
        /*
         能被100乘除，能被400整除的是闰年；
         不能被100整除，能被4整除的是闰年；
         其他的不是闰年
        */
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

    //该方法用于返回某年某月的最大天数
    public static int getDaysOfMonth(int year, int month) {
        if (year < 0 || month <= 0 || month > 12) {
            return -1;
        }
        int day;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
            day = 31;
        } else if (month == 2 && isLeapYear(year)) {
            day = 29;
        } else if (month == 2 && !isLeapYear(year)) {
            day = 28;
        } else {
            day = 30;
        }
        return day;
    }

    //用于返回给定时间段的天的数组
    public static List<DateWithHM> getDaysFromTimes(DateWithHM start, DateWithHM end) throws ParseException {
        if (!(isValidTime(start) || isValidTime(end)))
            return null;
        else if (!isProperTime(start, end)) {
            return null;
        }
        List<Calendar> list = new ArrayList<>();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.set(Calendar.YEAR, start.getYear());
        startCal.set(Calendar.MONTH, start.getMonth() - 1);
        startCal.set(Calendar.DAY_OF_MONTH, start.getDay());
        endCal.set(Calendar.YEAR, end.getYear());
        endCal.set(Calendar.MONTH, end.getMonth() - 1);
        endCal.set(Calendar.DAY_OF_MONTH, end.getDay());

        list.add(startCal);
        while (endCal.after(startCal)) {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            int year = startCal.get(Calendar.YEAR);
            int month = startCal.get(Calendar.MONTH);
            int day = startCal.get(Calendar.DAY_OF_MONTH);
            Calendar temp = Calendar.getInstance();
            temp.set(year, month, day);
            list.add(temp);
        }
        List<DateWithHM> result = new ArrayList<>();
        result.add(start);
        list.remove(0);
        if (list.size() != 0) {
            list.remove(list.size() - 1);
        }
        while (list.size() != 0) {
            result.add(new DateWithHM(list.get(0).get(Calendar.YEAR), list.get(0).get(Calendar.MONTH) + 1,
                    list.get(0).get(Calendar.DAY_OF_MONTH), 0, 0));
            list.remove(0);
        }
        result.add(end);
        return result;
    }

    //用于判断输入的时间（年-月-日-小时：分钟）（2018-4-14-23:54）的格式
    public static boolean isFormattedTime(String time) {
        String regex1 = "^\\d{1,4}-\\d{1,2}-\\d{1,2}-\\d{2}:\\d{2}";
        if (time != null && time.matches(regex1)) {
            String[] array = time.split("-");
            if (isFormatted(array[0] + "-" + array[1] + "-" + array[2])) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    //用于判断输入的时间是否有效
    public static boolean isValidTime(DateWithHM date) {
        if (date == null) {
            return false;
        }
        CalendarDate calendarDate = new CalendarDate(date.getYear(), date.getMonth(), date.getDay());
        if (isValid(calendarDate) && date.getHour() < 24 && date.getMinute() < 60) {
            return true;
        }
        return false;
    }

    //用于判断输入的两个时间是否前者早于后者
    public static boolean isProperTime(DateWithHM start, DateWithHM end) {
        if (start == null || end == null) {
            return false;
        }
        if (start.getYear() < end.getYear())
            return true;
        else if (start.getYear() == end.getYear()) {
            if (start.getMonth() < end.getMonth())
                return true;
            else if (start.getMonth() == end.getMonth()) {
                if (start.getDay() < end.getDay())
                    return true;
                else if (start.getDay() == end.getDay()) {
                    if (start.getHour() < end.getHour())
                        return true;
                    else if (start.getHour() == end.getHour() && start.getMinute() <= end.getMinute())
                        return true;
                    else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    //用于查找List<DateWithHM>中有无某个指定CalendarDate
    public static boolean isCalendarDateInList(List<DateWithHM> list, CalendarDate calendarDate) {
        if (list == null || calendarDate == null) {
            return false;
        }
        int size = list.size();
        boolean isExist = false;
        for (int i = 0; i < size; i++) {
            CalendarDate date = new CalendarDate(list.get(i).getYear(), list.get(i).getMonth(), list.get(i).getDay());
            isExist = date.isTheSameDay(calendarDate);
            if (isExist)
                break;
        }
        return isExist;
    }

    //用于查找List<DateWithHM>是否包含另外一个List<DateWithHM>（两个list都是顺序的），或者是否有交叉,list是被查找的链表，searchList是查找内容的链表
    public static boolean isContained(List<DateWithHM> list, List<DateWithHM> searchList) {
        if (list == null || searchList == null) {
            return false;
        }
        //考虑是否有天交叉
        int size1 = list.size();
        int size2 = searchList.size();
        boolean isExist = false;
        for (int i = 0; i < size1; i++) {
            CalendarDate date1 = new CalendarDate(list.get(i).getYear(), list.get(i).getMonth(), list.get(i).getDay());
            for (int j = 0; j < size2; j++) {
                CalendarDate date2 = new CalendarDate(searchList.get(j).getYear(), searchList.get(j).getMonth(), searchList.get(j).getDay());
                isExist = date1.isTheSameDay(date2);
                //list的最后一天与searchList的第一天相同，考虑是否一个在前一个在后，list的比searchList的早就不包含
                //list的第一天与searchList的最后一天相同，考虑是否一个在前一个在后，searchList的比list的早就不包含
                if (isExist && i == size1 - 1 && j == 0) {
                    if (DateUtil.isProperTime(list.get(i), searchList.get(j))) {
                        isExist = false;
                    }
                }
                if (isExist && i == 0 && j == size2 - 1) {
                    if (DateUtil.isProperTime(searchList.get(j), list.get(i))) {
                        isExist = false;
                    }
                }
                if (isExist)
                    break;
            }
            if (isExist)
                break;
        }

        /*考虑如果这两个list都在同一天，那么可能存在6种情况，
        *list包含searchList
        * searchList包含list
        * 不包含不相交，list在searchList前
        * 不包含不相交，searchList在list前
        * 相交，list在searchList前
        * 相交，searchList在list前
        */
        if (size1 == size2 && size1 == 2) {
            CalendarDate date1 = new CalendarDate(list.get(0).getYear(), list.get(0).getMonth(), list.get(0).getDay());
            CalendarDate date2 = new CalendarDate(list.get(1).getYear(), list.get(1).getMonth(), list.get(1).getDay());
            CalendarDate date3 = new CalendarDate(searchList.get(0).getYear(), searchList.get(0).getMonth(), searchList.get(0).getDay());
            CalendarDate date4 = new CalendarDate(searchList.get(1).getYear(), searchList.get(1).getMonth(), searchList.get(1).getDay());
            if (date1.isTheSameDay(date2) && date3.isTheSameDay(date4) && date1.isTheSameDay(date3)) {
                DateWithHM date5 = list.get(0);
                DateWithHM date6 = list.get(1);
                DateWithHM date7 = searchList.get(0);
                DateWithHM date8 = searchList.get(1);
                if (DateUtil.isProperTime(date5, date7) && DateUtil.isProperTime(date8, date6)) {
                    isExist = true;
                } else if (DateUtil.isProperTime(date7, date5) && DateUtil.isProperTime(date6, date8)) {
                    isExist = true;
                } else if (DateUtil.isProperTime(date6, date7)) {
                    isExist = false;
                } else if (DateUtil.isProperTime(date8, date5)) {
                    isExist = false;
                } else if (DateUtil.isProperTime(date5, date7) && DateUtil.isProperTime(date7, date6) && DateUtil.isProperTime(date6, date8)) {
                    isExist = true;
                } else if (DateUtil.isProperTime(date7, date5) && DateUtil.isProperTime(date5, date8) && DateUtil.isProperTime(date8, date6)) {
                    isExist = true;
                }
            }
        }

        return isExist;
    }

    //比较两个list<DateWithHM>链表是否相同
    public static boolean isTheSameList(List<DateWithHM> list1, List<DateWithHM> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        int size1 = list1.size();
        int size2 = list2.size();
        boolean isTheSame = false;
        if (size1 != size2) {
            return false;
        } else {
            for (int i = 0; i < size1; i++) {
                isTheSame = list1.get(i).isTheSameDate(list2.get(i));
            }
            return isTheSame;
        }
    }

    //用于创建根据某个日期的二维数组日历
    public static String[][] getDays(CalendarDate date) {
        if (date == null)
            return null;
        if (!DateUtil.isValid(date)) {
            return null;
        }
        String[][] days = new String[6][7];
        List<CalendarDate> daysOfAMonth = DateUtil.getDaysInMonth(date);
        assert daysOfAMonth != null;
        int size = daysOfAMonth.size();
        CalendarDate firstDay = new CalendarDate(date.getYear(), date.getMonth(), 1);
        int weekdayOfFirstDay = firstDay.getDayOfWeek();
        if (weekdayOfFirstDay == 7) {
            weekdayOfFirstDay = 0;
        }
        int countTool = 1;
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                days[i][weekdayOfFirstDay] = String.valueOf(countTool);
                weekdayOfFirstDay++;
                countTool++;
                if (weekdayOfFirstDay > 6) {
                    weekdayOfFirstDay = 0;
                    break;
                }
                if (countTool > size)
                    break;
            }
            if (countTool > size)
                break;
        }
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (days[i][j] == null) {
                    days[i][j] = "  ";
                }
            }
        }
        return days;
    }

    //将List<EventNode> 拆分成一个List<CalendarDate>，并且无重复
    public static List<CalendarDate> getCalendarDateFromEvents(List<EventNode> list) {
        if (list == null || list.size() == 0)
            return null;

        List<CalendarDate> actualList = new ArrayList<>();
        List<CalendarDate> tempList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).isExist()) {
                if (list.get(i).getTime() == null) {
                    int size1 = list.get(i).getTimes().size();
                    for (int j = 0; j < size1; j++) {
                        DateWithHM dateWithHM = list.get(i).getTimes().get(j);
                        tempList.add(new CalendarDate(dateWithHM.getYear(), dateWithHM.getMonth(), dateWithHM.getDay()));
                    }
                }
                if (list.get(i).getTimes() == null) {
                    tempList.add(list.get(i).getTime());
                }
            }
        }
        actualList = removeRepeat(tempList);
        return actualList;
    }

    //去除链表中的重复元素
    public static List<CalendarDate> removeRepeat(List<CalendarDate> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<CalendarDate> actualList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            boolean isTrue = true;
            for (int j = i + 1; j < size; j++) {
                if (list.get(i).isTheSameDay(list.get(j))) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                actualList.add(list.get(i));
            }
        }
        if (actualList.size() == 0) {
            return null;
        }
        return actualList;
    }

    //把DateWithHM转换为String类型
    public static String changeDateWithHMToString(DateWithHM date) {
        if (date == null) {
            return null;
        }
        if (!DateUtil.isValidTime(date)) {
            return null;
        }
        String hour = String.valueOf(date.getHour());
        String minute = String.valueOf(date.getMinute());
        String regex = "^\\d$";
        if (hour.matches(regex)) {
            hour = "0" + hour;
        }
        if (minute.matches(regex)) {
            minute = "0" + minute;
        }
        String dateString = String.valueOf(date.getYear()) + "-" + String.valueOf(date.getMonth()) + "-" + String.valueOf(date.getDay())
                + "-" + hour + ":" + minute;
        return dateString;
    }

    //把CalendarDate转换为String类型
    public static String changeCalendarDateToString(CalendarDate date) {
        if (date == null) {
            return null;
        }
        if (!DateUtil.isValid(date)) {
            return null;
        }
        String dateString = String.valueOf(date.getYear()) + "-" + String.valueOf(date.getMonth()) + "-" + String.valueOf(date.getDay());
        return dateString;
    }


}

