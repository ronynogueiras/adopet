package br.ufg.inf.adopet.model;

import java.util.List;

/**
 * Created by rony on 24/11/16.
 */

public class Account {
    private int id;
    private String name;
    private String picture;
    private List<Post> posts;

    public Account(int id, String name, String picture, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
