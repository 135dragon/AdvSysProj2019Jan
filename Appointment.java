/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import java.util.Date;

/**
 *
 * @author Charles Hulett
 */
public class Appointment 
{
    private String apptTime;
    private Date apptDate;
    //private String apptDate;
    private String result;
    private int docID;
    private int patID;
    
    public Appointment(String apptTime, /*String*/Date apptDate, 
                       String result, int docID, int patID)
    {
        this.apptTime = apptTime;
        this.apptDate = apptDate;
        this.result = result;
        this.docID = docID;
        this.patID = patID;
    }
    
    public void setApptTime(String apptTime) { this.apptTime = apptTime; }
    public void setApptDate(/*String*/Date apptDate) { this.apptDate = apptDate; }
    public void setResult(String result) { this.result = result; }
    public void setDocID(int docID) { this.docID = docID; }
    public void setPatID(int patID) { this.patID = patID; }
    
    public String getApptTime() { return apptTime; }
    public /*String*/Date getApptDate() { return apptDate; }
    public String getResult() { return result; }
    public int getDocID() { return docID; }
    public int getPatID() { return patID; }
}
