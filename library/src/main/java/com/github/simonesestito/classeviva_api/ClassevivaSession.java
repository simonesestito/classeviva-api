package com.github.simonesestito.classeviva_api;


import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.simonesestito.classeviva_api.objects.AgendaItem;
import com.github.simonesestito.classeviva_api.objects.Mark;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassevivaSession {

    private final String sharedPreferencesFilename = "classeviva_api_preferences";

    private Context ctx;
    private RequestQueue requestQueue;
    private String sessionKey = null, apiKey;

    private ClassevivaSession(){
        //Private constructor
        //You are not allowed to use this
    }

    public ClassevivaSession(String apiKey, Context ctx){
        //Default constructor
        this.apiKey = apiKey;
        this.ctx = ctx;

        //Create new RequestQueue
        requestQueue = Volley.newRequestQueue(ctx);
    }

    public void login(String username, String password, final OnResultsAvailable<String> onLogin) {
        //Load sharedpreferences
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(sharedPreferencesFilename, Context.MODE_PRIVATE);

        //Check if there's an existing instance
        if ((System.currentTimeMillis() - sharedPreferences.getLong("last_classeviva_session_time", 0) < 3600000 /*1 hour*/)
                && sharedPreferences.getString("last_classeviva_session_key", null) != null) {
            //There's an existing instance and it isn't expired (an instance'll expire in 1 hour)
            sessionKey = sharedPreferences.getString("last_classeviva_session_key", null);
            onLogin.onResultsAvailable(sessionKey, this);
        } else {
            //We need to login
            performLogin(username, password, onLogin, sharedPreferences);
        }
    }

    private void performLogin(String username, String password, final OnResultsAvailable<String> onLogin, SharedPreferences sharedPreferences) {
        //Create params
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        //Create HTTP Request to login
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, apiKey, "https://api.x9d.it/classeviva/v1/login", params, new OnResultsAvailable<JSONArray>() {
            @Override
            public void onResultsAvailable(JSONArray result, ClassevivaSession instance) {
                try {
                    //Get Session Key from JSONArray
                    String obtainedKey = result.getJSONObject(0).getString("session");
                    //Save sessionKey
                    sessionKey = obtainedKey;
                    //Notify success
                    onLogin.onResultsAvailable(obtainedKey, instance);
                } catch (JSONException e){
                    onLogin.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {
                onLogin.onError(e);
            }
        }, this /*ClassevivaSession*/);

        requestQueue.add(request);
    }


    //Get all grades
    public void getMarksList(final OnResultsAvailable<List<Mark>> listener) {

        if (sessionKey == null) {
            listener.onError(new IllegalStateException("You have to login before get grades list"));
            return;
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiKey, "https://api.x9d.it/classeviva/v1/grades/" + sessionKey, null /*No params*/, new OnResultsAvailable<JSONArray>() {
            @Override
            public void onResultsAvailable(JSONArray result, ClassevivaSession instance) {
                try {
                    List<Mark> marks = new ArrayList<>();
                    for (int i = 0; i < result.length(); i++){
                        String subject = result.getJSONObject(i).getString("name");
                        JSONArray marksForSubject = result.getJSONObject(i).getJSONArray("grades");
                        for (int ii = 0; ii < marksForSubject.length(); ii++){
                            //Get each JSONObject from the array, and transform it into Mark object
                            JSONObject mark = marksForSubject.getJSONObject(ii);
                            double value = fromStringToDouble(mark.getString("value"));
                            String date = mark.getString("date");
                            String type = mark.getString("type");
                            marks.add(new Mark(value, subject, type, date));
                        }
                    }

                    //Finally...
                    listener.onResultsAvailable(marks, instance);
                } catch (JSONException e){
                    listener.onError(e);
                }
            }

            private double fromStringToDouble(String value) {
				value = value.replaceAll(",", ".").replaceAll("=", "--");
                double extra = 0.00;
                while (value.contains("+")){
                    value = value.replaceFirst("\\+", "");
                    //Add 0.25 for each "+"
                    extra += 0.25;
                }
                while (value.contains("-")){
                    value = value.replaceFirst("-", "");
                    //Remove 0.25 for each "-"
                    extra -= 0.25;
                }
                while (value.contains("\u00bd")){
                    value = value.replaceFirst("\u00bd", "");
                    //Add 0.50
                    extra += 0.50;
                }
                if (org.apache.commons.lang3.StringUtils.isNumeric(value))
                    return Double.valueOf(value)+extra;
                else
                    return 0;
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }
        }, this /*ClassevivaSession*/);

        requestQueue.add(request);
    }


    //Get agenda
    public void getAgenda(final OnResultsAvailable<List<AgendaItem>> listener) {

        if (sessionKey == null) {
            listener.onError(new IllegalStateException("You have to login before get agenda"));
            return;
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiKey, "https://api.x9d.it/classeviva/v1/agenda/" + sessionKey, null /*No params*/, new OnResultsAvailable<JSONArray>() {
            @Override
            public void onResultsAvailable(JSONArray result, ClassevivaSession instance) {
                try {
                    //Transform each JSONObject in AgendaItem
                    List<AgendaItem> agendaItems = new ArrayList<>();
                    for (int i = 0; i < result.length(); i++){
                        JSONObject jsonItem = result.getJSONObject(i);
                        AgendaItem agendaItem = new AgendaItem();
                        agendaItem.setDate(jsonItem.getString("start"));
                        agendaItem.setText(jsonItem.getString("nota_2"));
                        agendaItem.setType(getType(jsonItem.getString("title")));
                        agendaItems.add(agendaItem);
                    }
                    listener.onResultsAvailable(agendaItems, instance);

                } catch (JSONException | ParseException e){
                    listener.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {
                listener.onError(e);
            }

            private String getType(String text){
                //Determinate type by text
                text = text.toLowerCase();

                if (text.contains("interrogazion"))
                    return AgendaItem.TYPE_INTERROGATION;

                else if (text.contains("verific"))
                    return AgendaItem.TYPE_TEST;

                else if (text.contains("esercizi"))
                    return AgendaItem.TYPE_HOMEWORK;

                else
                    return AgendaItem.TYPE_GENERIC;
            }

        }, this /*ClassevivaSession*/);

        requestQueue.add(request);
    }
}
