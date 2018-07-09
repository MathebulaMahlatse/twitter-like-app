package com.tweet.app.service.imp

import com.tweet.app.model.Tweet
import com.tweet.app.model.TwitterFeed
import com.tweet.app.model.User
import com.tweet.app.service.TweetsService
import com.tweet.app.service.TwitterFeedService
import com.tweet.app.service.UserService
import com.tweet.app.utils.mapping.UserMapper
import spock.lang.Specification

class TwitterFeedServiceImpSpec extends Specification {
    UserService userService = Mock()
    TweetsService tweetsService = Mock()
    UserMapper userMapper = Mock()
    TwitterFeedService twitterFeedServiceImp = new TwitterFeedServiceImp(userService: userService,
            tweetsService: tweetsService, userMapper: userMapper)

    List<User> expectedUsers = [new User(name: 'Alan'), new User(name: 'Test User')]
    List<Tweet> expectedTweets = [new Tweet(name: 'by', message: 'Hi'), new Tweet(name: 'Dev', message: 'There')]
    List<User> expectedUsersWithTweets = [new User(name: 'Alan', messages: [new Tweet(name: 'by', message: 'here')]),
                                          new User(name: 'Test User', messages: [new Tweet(name: 'the', message: 'hii')])]

    def 'should get Tweet Feed'() {
        when: 'calling getTweetFeed'
        TwitterFeed twitterFeed = twitterFeedServiceImp.getTweetFeed()

        then: 'getUsers should be called'
        1 * userService.getUsers() >> expectedUsers

        and: 'getTweets should be called'
        1 * tweetsService.getTweets() >> expectedTweets

        and: 'mapTweetsToUsers to be called'
        1 * userMapper.mapTweetsToUsers({
            List<User> users -> users == expectedUsers
        }, {
            List<Tweet> tweets -> tweets == expectedTweets
        }) >> expectedUsersWithTweets

        and: 'tweet feed should be'
        twitterFeed
        twitterFeed.getUsers() == expectedUsersWithTweets

    }

    def 'mapTweetsToUsers: should not map users if list is empty'() {
        when: 'calling getTweetFeed'
        TwitterFeed twitterFeed = twitterFeedServiceImp.getTweetFeed()

        then: 'getUsers should be called'
        1 * userService.getUsers() >> []

        and: 'getTweets should be called'
        0 * tweetsService.getTweets()

        and: 'mapTweetsToUsers to be called'
        0 * userMapper.mapTweetsToUsers(_, _)

        and: 'tweet feed should be'
        twitterFeed

    }

    def 'mapTweetsToUsers: should not map tweets if the list is empty'() {
        when: 'calling getTweetFeed'
        TwitterFeed twitterFeed = twitterFeedServiceImp.getTweetFeed()

        then: 'getUsers should be called'
        1 * userService.getUsers() >> expectedUsers

        and: 'getTweets should be called'
        1 * tweetsService.getTweets() >> null

        and: 'mapTweetsToUsers to be called'
        0 * userMapper.mapTweetsToUsers(_, _)

        and: 'tweet feed should be'
        twitterFeed

    }
}
