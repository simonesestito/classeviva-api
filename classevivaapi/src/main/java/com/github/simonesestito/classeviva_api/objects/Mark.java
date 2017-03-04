package com.github.simonesestito.classeviva_api.objects;

import java.io.Serializable;
import java.util.Calendar;


public class Mark implements Serializable {

    //Random unique ID
    private static final long serialVersionUID = 576834756L;

    private double value;
    private String subject, type;
    private Calendar date;

    public Mark(double value, String subject, String type, String date){
        this.value = value;
        this.subject = subject;
        this.type = type;

        /*
         * Decode string to Calendar object
         * First and second chars represents the day of month
         * The last two chars represents the month
         */
        Calendar result = Calendar.getInstance();
        result.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0, 2)));
        int month = Integer.valueOf(date.substring(date.length()-2, date.length()));
        result.set(Calendar.MONTH, month);
        if (month < Calendar.getInstance().get(Calendar.MONTH))
            result.set(Calendar.YEAR, result.get(Calendar.YEAR)-1);
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
