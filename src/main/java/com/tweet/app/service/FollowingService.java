package com.tweet.app.service;

import com.tweet.app.model.Following;
import com.tweet.app.model.User;

import java.util.LinkedHashMap;
import java.util.List;

public interface FollowingService {
    LinkedHashMap<String, List<Following>> getFollowing(List<User> applicationUsers, List<String> whoFollowsWho);
}
