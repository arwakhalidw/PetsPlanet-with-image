package com.example.petsplanet;

import android.graphics.Bitmap;

public class Post {
    private Bitmap image;

    private int id;
    private String post;



    public Post(Bitmap image, String post, int id) {
        this.image = image;
        this.id = id;
        this.post = post;
    }

    public Bitmap getImage() {
        return image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }



    @Override
    public String toString() {
        return "StudentMod{" +
                "id=" + id +
                ", Post='" + post + '\'' +
                '}';
    }
}
