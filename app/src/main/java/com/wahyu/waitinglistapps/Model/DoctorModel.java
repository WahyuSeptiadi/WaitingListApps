package com.wahyu.waitinglistapps.Model;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class DoctorModel {
    private String id;
    private String name;
    private String imageURL;
    private String spesialis;
    private String workday;
    private String worktimestart;
    private String worktimefinish;
    private String limit;

    public DoctorModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public String getWorktimestart() {
        return worktimestart;
    }

    public void setWorktimestart(String worktimestart) {
        this.worktimestart = worktimestart;
    }

    public String getWorktimefinish() {
        return worktimefinish;
    }

    public void setWorktimefinish(String worktimefinish) {
        this.worktimefinish = worktimefinish;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
