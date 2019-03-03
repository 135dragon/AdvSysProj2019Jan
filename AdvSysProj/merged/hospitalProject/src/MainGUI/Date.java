/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainGUI;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 *
 * @author aasim
 */
public class Date {

    private int year;   //
    private int month;  //
    private String monthName;
    private int day;   //
    private int maxDay; //
    private int docID;
    private String patName;
    private String currDate;

    Date(int m, int y, int d) {
        setMonth(m);
        setYear(y);
        checkMaxDay();
        setMonthName();
        setDay(d);

    }

    Date(String x) {
        setCurrentDate();
        String[] a = x.split(" ");
        String[] b = a[3].split("-");
        docID = Integer.parseInt(a[0]);
        patName = a[1] + " " + a[2];
        year = Integer.parseInt(b[0]);
        month = Integer.parseInt(b[1]);
        day = Integer.parseInt(b[2]);

    }

    public void setCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        currDate = now.toString();
    }

    public void checkMaxDay() {

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            maxDay = 31;
        } else if (month == 2) {
            maxDay = 28;
            if (year % 4 == 0) {
                maxDay = 29;
            }
        } else {
            maxDay = 30;
        }

    }

    public void setMonth(int m) {
        if (m >= 1 && m <= 12) {
            month = m;
        }
    }

    public void setYear(int y) {
        if (y > 2012) {
            year = y;
        }
    }

    public void setDay(int d) {
        if (d > 0 && d <= maxDay) {
            day = d;
        }
    }

    public void setMonthName() {
        if (month == 1) {
            monthName = "January";
        }
        if (month == 2) {
            monthName = "Febuary";
        }
        if (month == 3) {
            monthName = "March";
        }
        if (month == 4) {
            monthName = "April";
        }
        if (month == 5) {
            monthName = "May";
        }
        if (month == 6) {
            monthName = "June";
        }
        if (month == 7) {
            monthName = "July";
        }
        if (month == 8) {
            monthName = "August";
        }
        if (month == 9) {
            monthName = "September";
        }
        if (month == 10) {
            monthName = "October";
        }
        if (month == 11) {
            monthName = "November";
        }
        if (month == 12) {
            monthName = "December";
        }
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public String getMonthName() {
        return monthName;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public String getCurrDate() {
        return currDate;
    }

    public int compareTo(Date x) {
        // - 1 means X comes after this event
        // 0 means the events are on the same day
        // 1 means x comes before this event

        if (x.getYear() < this.getYear()) {
            return 1;
        } else if (x.getYear() > this.getYear()) {
            return -1;
        } else if (x.getMonth() > this.getMonth()) {
            return -1;
        } else if (x.getMonth() < this.getMonth()) {
            return 1;
        } else if (x.getDay() < this.getDay()) {
            return 1;
        } else if (x.getDay() > this.getDay()) {
            return -1;
        }
        return 0;
    }
}
