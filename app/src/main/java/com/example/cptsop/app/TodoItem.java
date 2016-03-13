package com.example.cptsop.app;

import org.joda.time.DateTimeComparator;

import java.util.Date;

/**
 * Created by omer on 10/03/2016.
 */
public class TodoItem {
    Date due;
    String task;

    TodoItem(Date due, String task) {
        this.due = due;
        this.task = task;
    }

    //returns true iff the ttodo is overdue
    public boolean isOverdue() {
        return DateTimeComparator.getDateOnlyInstance().compare(new Date(), due) > 0;
    }

//    private static Date zeroHour(Date original){//if joda isn't approved.
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(original);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//
//        return cal.getTime();
//
//    }
}
