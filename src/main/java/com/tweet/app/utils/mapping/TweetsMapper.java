package com.tweet.app.utils.mapping;

import com.tweet.app.model.Tweet;
import com.tweet.app.utils.ApplicationConstants;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TweetsMapper {

    public List<Tweet> mapToTweet(List<String> tweets) {
        List<String> validTweets = validTweets(tweets);
        return validTweets.stream().map(tweet -> {
            String[] parts = tweet.split(ApplicationConstants.MESSAGE_SEPARATOR);

            // Assumption There is only be one '>' in the message
            return mapTweet(parts[ApplicationConstants.TWEET_NAME_INDEX], parts[ApplicationConstants.TWEET_MESSAGE_INDEX]);
        }).collect(Collectors.toList());
    }

    private Tweet mapTweet(String name, String message) {
        Tweet tweet = new Tweet();
        tweet.setName(name.trim());
        tweet.setMessage(message.trim());

        return tweet;
    }

    private List<String> validTweets (List<String> tweets) {
        return tweets.stream().filter(tweet -> isTweetValid(tweet) && isSplitTweetValid(tweet)).collect(Collectors.toList());
    }

    private boolean isTweetValid(String tweet) {
        return tweet != null && !StringUtils.isEmpty(tweet);
    }

    private boolean isSplitTweetValid(String tweet) {
        String[] tweetParts = tweet.split(ApplicationConstants.MESSAGE_SEPARATOR);

        return tweetParts.length > 1;

    }
}
