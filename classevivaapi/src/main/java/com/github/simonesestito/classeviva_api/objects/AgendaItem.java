package com.github.simonesestito.classeviva_api.objects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AgendaItem implements Serializable {

    private static final long serialVersionUID = 3253527L;

    public static final String TYPE_HOMEWORK = "esercizi", TYPE_TEST = "verifica", TYPE_INTERROGATION = "interrogazione", TYPE_GENERIC = "nota";

    private String text = "", type = "", teacher = "";
    private Calendar date;

    public String getType() {
        return type;
    }

    public Calendar getDate() {
        return date;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getText() {
        return text;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setTeacher(String materia) {
        this.teacher = materia;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar toSave = Calendar.getInstance();
        toSave.setTime(df.parse(date));
        setDate(toSave);
    }
}
