package com.github.simonesestito.classeviva_api;


public class Utils {

    //Singleton pattern
    private static Utils instance = null;

    private Utils(){}

    public static Utils getInstance(){
        if (instance == null)
            instance = new Utils();
        return instance;
    }


    public String capitalizeWords(String from){
        String result = "";
        char back = ' ';
        for (char c : from.toCharArray()){
            if (back == ' ')
                result += Character.toUpperCase(c);
            else
                result += Character.toLowerCase(c);
            back = c;
        }
        return result;
    }

    public String capitalizeFirst(String from){
        return Character.toUpperCase(from.charAt(0)) + from.substring(1, from.length()).toLowerCase();
    }
}
