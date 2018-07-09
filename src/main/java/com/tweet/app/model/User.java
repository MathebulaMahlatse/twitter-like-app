package com.tweet.app.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Following> following;
    private ArrayList<Tweet> messages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public ArrayList<Tweet> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Tweet> messages) {
        this.messages = messages;
    }
}
