package com.example.xmlpullparser;

public class RssItem {
    public String title;
    public String link;
    public String guid;
    public String pubdate;
    public String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
