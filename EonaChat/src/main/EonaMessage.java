package main;

import java.util.ArrayList;

/**
 * Created by Bloodrayne on 02.02.2016.
 */
public class EonaMessage {

    private String from = "";
    private ArrayList<String> to = new ArrayList<String>();
    private String message = "";


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<String> getTo() {
        return to;
    }

}
