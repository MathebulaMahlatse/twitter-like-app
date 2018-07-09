package com.tweet.app.service.imp;

import com.tweet.app.model.Tweet;
import com.tweet.app.model.TwitterFeed;
import com.tweet.app.model.User;
import com.tweet.app.service.UserService;
import com.tweet.app.service.TweetsService;
import com.tweet.app.service.TwitterFeedService;
import com.tweet.app.utils.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("twitterFeedService")
public class TwitterFeedServiceImp implements TwitterFeedService {
    private UserMapper userMapper = new UserMapper();

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("TweetsService")
    private TweetsService tweetsService;

    @Override
    public TwitterFeed getTweetFeed() {
        TwitterFeed twitterFeed = new TwitterFeed();

        List<User> users = userService.getUsers();
        List<Tweet> tweets = tweetsService.getTweets();

        List<User> withUpdatedMessages = userMapper.mapTweetsToUsers(users, tweets);
        twitterFeed.setUsers(withUpdatedMessages);

        return twitterFeed;
    }
}
