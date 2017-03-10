package com.github.simonesestito.classeviva_api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class JsonArrayRequest extends StringRequest {

    private Map<String, String> params;
    private int method;
    private String apiKey;

    public JsonArrayRequest(int method, String apiKey, String url, Map<String, String> params, final OnResultsAvailable<JSONArray> listener, final ClassevivaSession instance) {
        super(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.wtf("ClasseViva Response", response);
                try {
                    //Transform String to JSONArray
                    listener.onResultsAvailable(new JSONArray(response), instance);
                } catch (JSONException e) {
                    listener.onError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.wtf("Bad Internet response", new String(error.networkResponse.data));
                } catch (Exception e){
                    e.printStackTrace();
                }
                listener.onError(error);
            }
        });

        //Assign fields value
        this.params = params;
        this.method = method;
        this.apiKey = apiKey;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //Set your API Key in Headers
        Map<String, String> head = new HashMap<>();
        head.put("X-API-KEY", apiKey);
        return head;
    }

    @Override
    public int getMethod() {
        //Set custom method
        return method;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        //Set custom parameters
        return params;
    }
}
