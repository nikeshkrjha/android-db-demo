package com.example.noteapp.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Note {
    private String title;
    private String detail;
    private Date createdDate;
    private int id;

    public Note(){

    }

    public Note(String title, String detail, Date createdDate, int id) {
        this.title = title;
        this.detail = detail;
        this.createdDate = createdDate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateInString(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(this.createdDate);
        return strDate;
    }

    public void setDateInString(String strDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {

            Date date = dateFormat.parse(strDate);
            System.out.println(date);
            this.createdDate = date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
