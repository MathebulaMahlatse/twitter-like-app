package com.tweet.app.utils.mapping

import com.tweet.app.model.Tweet
import spock.lang.Specification

class TweetsMapperSpec extends Specification {
    TweetsMapper mapper = new TweetsMapper()

    def 'should map to tweets'() {
        given: 'tweets'
        List<String> tweets = [ ' Alan>Hi', 'Ward> There ']

        when: 'calling mapToTweet'
        List<Tweet> actualTweets = mapper.mapToTweet(tweets)

        then: 'tweets should be'
        actualTweets
        actualTweets.get(0).name == 'Alan'
        actualTweets.get(0).message == 'Hi'

        actualTweets.get(1).name == 'Ward'
        actualTweets.get(1).message == 'There'
    }

    def 'should handle invalid tweets gracefully'() {
        given: 'invalid tweets'
        List<String> tweets = [ '', 'Ward ', 'Alan>', null]

        when: 'calling mapToTweet'
        List<Tweet> actualTweets = mapper.mapToTweet(tweets)

        then: 'tweets should be'
        !actualTweets
    }
}
