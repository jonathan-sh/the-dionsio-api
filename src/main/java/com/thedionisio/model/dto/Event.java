package com.thedionisio.model.dto;

import java.util.List;

/**
 * Created by jonathan on 3/6/17.
 */
public class Event {
    @Override
    public String toString() {
        return "Event{" +
                "_id=" + _id +
                ", _idCompany=" + _idCompany +
                ", _idPlace=" + _idPlace +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", genres=" + genres +
                ", urlBanners=" + urlBanners +
                ", batches=" + batches +
                ", tickets=" + tickets +
                ", isActive=" + isActive +
                '}';
    }

    public Object _id;
    public Object _idCompany;
    public Object _idPlace;
    public String name;
    public String description;
    public Date date;
    public List<String> genres;
    public List<String> urlBanners;
    public List<Batch> batches;
    public List<Ticket> tickets;
    public Boolean isActive;


    public String isRequered(){
        return " < _idCompany, _idPlace, name >";
    }

    public Integer createValidation(){

        if(this.name!=null){
            return 1;
        }
        return 0;
    }

}
