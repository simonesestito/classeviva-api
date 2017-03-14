package com.github.simonesestito.classeviva_api.objects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class Mark implements Serializable {

    //Random unique ID
    private static final long serialVersionUID = 576834756L;

    private double value;
    private String subject, type;
    private Calendar date;

    public Mark(double value, String subject, String type, String date){
        this.value = value;
        this.subject = Utils.getInstance().capitalizeFirst(subject);
        this.type = type;

        /*
         * Decode string to Calendar object
         * First and second chars represents the day of month
         * The last two chars represents the month
         */
        List<Integer> schoolMonths = Arrays.asList(8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7);
        Calendar result = Calendar.getInstance();
        result.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0, 2)));
        int month = Integer.valueOf(date.substring(date.length()-2, date.length()))-1;
        result.set(Calendar.MONTH, month);
        if (schoolMonths.indexOf(month) < schoolMonths.indexOf(Calendar.getInstance().get(Calendar.MONTH))
                && month > Calendar.getInstance().get(Calendar.MONTH)) {
            result.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR)-1);
        }
        this.date = result;
    }

    public double getValue() {
        return value;
    }

    public Calendar getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
