package com.ambe.filerecovery.model;

public class FileObject {
    private String name;
    private String path;
    private String date;
    private Boolean isChecked;
    private Boolean isPlaying;

    public FileObject() {
    }

    public FileObject(String name, String path, String date, Boolean isChecked, Boolean isPlaying) {
        this.name = name;
        this.path = path;
        this.date = date;
        this.isChecked = isChecked;
        this.isPlaying = isPlaying;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }
}
