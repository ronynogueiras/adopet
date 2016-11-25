package br.ufg.inf.adopet.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rony on 24/11/16.
 */

public class Post {

    private int id;
    private String title;
    private String author;
    private String description;
    private String[] images;
    private String shareLink;
    private int interested;

    public Post(int id, String title, String author, String description, String[] images, String shareLink, int interested) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.images = images;
        this.shareLink = shareLink;
        this.interested = interested;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getImages() {
        return images;
    }

    public String getShareLink() {
        return shareLink;
    }

    public int getInterested() {
        return interested;
    }

    public String getAuthor(){
        return author;
    }

    public String toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id",this.id);
        json.put("title",this.title);
        json.put("description",this.description);
        //json.put("images","");
        json.put("share_link",this.shareLink);
        json.put("interested",this.interested);

        return json.toString();
    }
}
