package com.droid.udacitynewsapp;

/**
 * Created by nickkrause on 5/11/17.
 */
public class Story {

    String caption;
    String author;
    String url;
    String date;
    String section;

    public Story(String caption, String author, String url, String date, String section) {
        this.caption = caption;
        this.author = author;
        this.url = url;
        this.date = date;
        this.section = section;
    }

    public String getTitle() {
        return caption;
    }

    public void setTitle(String title) {
        this.caption = caption;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "Story{" +
                "aside_title='" + caption + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
