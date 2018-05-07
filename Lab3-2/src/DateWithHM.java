
public class DateWithHM {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public DateWithHM(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public DateWithHM(String dateString) throws DateCreateException {
        if (dateString == null && !DateUtil.isFormattedTime(dateString)) {
            throw new DateCreateException();
        } else {
            String[] array = dateString.split("-");
            this.year = Integer.parseInt(array[0]);
            this.month = Integer.parseInt(array[1]);
            this.day = Integer.parseInt(array[2]);
            String[] hourMin = array[3].split(":");
            this.hour = Integer.parseInt(hourMin[0]);
            this.minute = Integer.parseInt(hourMin[1]);
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

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    //将DateWithHM转换为CalendarDate
    public CalendarDate change() {
        return new CalendarDate(this.getYear(), this.getMonth(), this.getDay());
    }

    public boolean isTheSameDate(DateWithHM date) {
        if (date == null)
            return false;
        return this.getYear() == date.getYear() && this.getMonth() == date.getMonth()
                && this.getDay() == date.getDay() && this.getHour() == date.getHour() && this.getMinute() == date.getMinute();
    }

}
