package com.github.simonesestito.classeviva_api.objects;

import com.github.simonesestito.classeviva_api.Utils;

import java.io.Serializable;


public class Subject implements Serializable {

    //Random unique ID
    private static final long serialVersionUID = 45736534756L;

    private int id;
    private String name;

    public Subject(int id, String name){
        this.id = id;
        this.name = Utils.getInstance().capitalizeFirst(name);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}