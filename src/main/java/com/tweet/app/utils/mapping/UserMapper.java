package com.tweet.app.utils.mapping;

import com.tweet.app.model.Following;
import com.tweet.app.model.Tweet;
import com.tweet.app.model.User;
import com.tweet.app.utils.ApplicationConstants;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class UserMapper {
    public List<User> mapToUsers(LinkedHashMap<String, List<Following>> uniqueUsers) {
        return uniqueUsers.keySet().stream().map(key -> {
            User user = new User();
            user.setName(key);
            user.setFollowing(uniqueUsers.get(key));
            return user;
        }).collect(Collectors.toList());
    }

    public User mapToUser(String[] users) {
        User user = new User();

        if(isUserValid(users)) {
            String[] multipleUsers = users[1].split(ApplicationConstants.FOLLOWING_MULTIPLE_SEPARATOR_INDEX);
            List<Following> following = mapToFollowing(multipleUsers);

            user.setName(users[0].trim());
            user.setFollowing(following);

            return user;
        }

        return null;
    }

    public LinkedHashMap<String, List<Following>> mapToUniqueFollowing(List<User> users) {
        LinkedHashMap<String, List<Following>> uniqueUsers = new LinkedHashMap<>();

        users.forEach(userWithFollows -> {
            if (uniqueUsers.containsKey(userWithFollows.getName())) {
                List<Following> followings = uniqueUsers.get(userWithFollows.getName());

                Set<String> letter = mapToUniqueFollowNames(followings, userWithFollows.getFollowing());
                uniqueUsers.put(userWithFollows.getName(), mapFollowing(letter));

            } else {
                uniqueUsers.put(userWithFollows.getName(), userWithFollows.getFollowing());
            }
        });

        return uniqueUsers;
    }

    public List<String> mapToUniqueUsers(List<String> whoFollowsWho) {
        Set<String> uniqueUsers = new HashSet<>();
        whoFollowsWho.forEach(user -> {
            String[] messageParts = user.split(ApplicationConstants.USER_FOLLOWS_KEY);
            //Assumption: , is used to indicate multiple following
            if(messageParts.length > 1) {
                String[] multipleUsers = messageParts[1].split(ApplicationConstants.FOLLOWING_MULTIPLE_SEPARATOR_INDEX);

                uniqueUsers.add(messageParts[0].trim());
                uniqueUsers.addAll(trimUsers(multipleUsers));
            }
        });

        return new ArrayList<>(uniqueUsers);
    }

    public List<User> mapTweetsToUsers(List<User> users, List<Tweet> tweets) {
        return users.stream().map(user -> {
            tweets.forEach(tweet -> {
                if(tweet.getName().equals(user.getName())) {
                    ArrayList<Tweet> existingTweets = user.getMessages();
                    updateTweetsByUsers(user, existingTweets, tweet);
                } else if(user.getFollowing() != null) {
                    user.getFollowing().forEach(following -> {
                        if(following.getName().equals(tweet.getName())){
                            ArrayList<Tweet> existingTweets = user.getMessages();
                            updateTweetsByUsers(user, existingTweets, tweet);
                        }
                    });
                }
            });
            return user;
        }).collect(Collectors.toList());
    }

    private void updateTweetsByUsers(User user, ArrayList<Tweet> existingTweets, Tweet tweet) {
        if(existingTweets == null) {
            user.setMessages(new ArrayList<Tweet>() { {add(tweet);}});
        } else {
            existingTweets.add(tweet);
            user.setMessages(existingTweets);
        }
    }

    private Set<String> mapToUniqueFollowNames(List<Following> following1, List<Following> following2) {
        Set<String> uniqueNames = new HashSet<>();
        List<Following> combinedFollowing = new ArrayList<>();
        combinedFollowing.addAll(following1);
        combinedFollowing.addAll(following2);

        combinedFollowing.forEach(item -> uniqueNames.add(item.getName()));

        return uniqueNames;
    }

    private List<Following> mapFollowing(Set<String> uniqueNames) {
        return uniqueNames.stream().map(uniqueName -> {
            Following following = new Following();
            following.setName(uniqueName);
            return following;
        }).collect(Collectors.toList());
    }

    private List<Following> mapToFollowing(String[] multipleUsers) {
        return Arrays.stream(multipleUsers).map(local -> {
            Following following1 = new Following();
            following1.setName(local.trim());
            return following1;
        }).collect(Collectors.toList());
    }

    private List<String> trimUsers(String[] users) {
        return Arrays.stream(users).map(String::trim).collect(toList());
    }

    private boolean isUserValid(String[] users) {
        return users[0] != null && !StringUtils.isEmpty(users[0]) && users[1] != null;
    }
}
