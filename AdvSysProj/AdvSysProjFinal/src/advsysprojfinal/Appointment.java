/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advsysprojfinal;

import java.util.Date;

/**
 *
 * @author aasim
 */
public class Appointment {

    protected int apptID;
    protected int patID;
    protected int docID;
    protected Date date = new Date();
    protected String result;
    protected boolean time7, time8, time9, time10, time11, time12, time13, time14, time15, time16, time17;
    protected Times time;

    Appointment(int IDap, int IDpat, int IDdoc, Date d, Times times, String res) {
        apptID = IDap;
        patID = IDpat;
        docID = IDdoc;
        date = d;
        result = res;
        time = times;
    }

    public int getApptID() {
        return apptID;
    }

    public int getPatID() {
        return apptID;
    }

    public int getDocID() {
        return apptID;
    }

    public Date getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public Times getTime() {
        return time;
    }

    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public void setPatID(int patID) {
        this.patID = patID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimes(Times times) {
        this.time = times;
    }

    public void setResults(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return apptID + " " + patID + " " + docID + " " + date;
    }
}
