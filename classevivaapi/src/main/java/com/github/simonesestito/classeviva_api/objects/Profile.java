package com.github.simonesestito.classeviva_api.objects;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Profile implements Serializable {

    //Random unique ID
    private static final long serialVersionUID = 11004756L;

    private String name = "", surname = "", userid = "", type = "", sessionKey = "", json = "";

    private Profile(){}

    public Profile(JSONArray json) throws JSONException {
        JSONObject obj = json.getJSONObject(0);
        if (!obj.getBoolean("success"))
            throw new JSONException("Login failed");
        name = obj.getString("name");
        surname = obj.getString("cognome");
        userid = obj.getString("userid");
        type = obj.getString("type");
        sessionKey = obj.getString("session");
        this.json = json.toString();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUserid() {
        return userid;
    }

    @Override
    public String toString() {
        return json;
    }

    public Profile setJson(String json) throws JSONException {
        return new Profile(new JSONArray(json));
    }
}
