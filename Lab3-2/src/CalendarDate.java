/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate {
    private int year;
    private int month;
    private int day;

    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * a constructor that can return a CalendarDate object through the given string.
     *
     * @param dateString format: 2018-3-18
     */
    public CalendarDate(String dateString) throws DateCreateException{
        if (dateString == null || !DateUtil.isFormatted(dateString)) {
            throw new DateCreateException();
        } else {
            String[] date = dateString.split("-");
            this.year = Integer.parseInt(date[0]);
            this.month = Integer.parseInt(date[1]);
            this.day = Integer.parseInt(date[2]);
        }
    }

    public int getYear() {
        return year;
    }


    public int getMonth() {
        return month;
    }


    public int getDay() {
        return day;
    }


    /**
     * Get index of the day in a week for one date.
     * <p>
     * Don't use the existing implement like Calendar.setTime(),
     * try to implement your own algorithm.
     *
     * @return 1-7, 1 stands for Monday and 7 stands for Sunday
     */
    public int getDayOfWeek() {
        if (!DateUtil.isValid(this)) {
            return -1;
        } else {
            //用蔡勒公式计算星期，c表示世纪
            int y = year;
            int m = month;
            int d = day;
            int c;
            if (m == 1 || m == 2) {
                y--;
                m += 12;
            }
            c = y / 100;
            y = y - c * 100;
            int week = y + y / 4 + (c / 4) - 2 * c + (26 * (m + 1) / 10) + d - 1;
            while (week < 0) {
                week += 7;
            }
            week %= 7;
            return (week == 0) ? 7 : week;
        }


    }


    public boolean isTheSameDay(CalendarDate calendarDate) {
        if (calendarDate == null)
            return false;
        return this.getYear() == calendarDate.getYear() && this.getMonth() == calendarDate.getMonth() && this.getDay() == calendarDate.getDay();
    }


    public DateWithHM change() {
        return new DateWithHM(this.getYear(), this.getMonth(), this.getDay(), 0, 0);
    }
}
