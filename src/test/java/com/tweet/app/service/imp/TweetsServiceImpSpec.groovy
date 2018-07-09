package com.tweet.app.service.imp

import com.tweet.app.dao.FileReaderDao
import com.tweet.app.model.Tweet
import com.tweet.app.service.TweetsService
import com.tweet.app.utils.mapping.TweetsMapper
import spock.lang.Specification

class TweetsServiceImpSpec extends Specification {
    TweetsMapper tweetsMapper = Mock()
    FileReaderDao tweetsFileReaderDao = Mock()
    TweetsService tweetsServiceImp = new TweetsServiceImp(tweetsMapper: tweetsMapper,
            tweetsFileReaderDao: tweetsFileReaderDao)

    List<String> expectedTweetsAsStrings = ['Alan> Hi There']
    List<Tweet> expectedTweets = [new Tweet(name: 'by', message: 'message here')]


    def 'should get tweets'() {
        when: 'calling getTweets'
        List<Tweet> tweets = tweetsServiceImp.getTweets()

        then: 'asString should be called'
        1 * tweetsFileReaderDao.getContents() >> expectedTweetsAsStrings

        and: 'mapToTweet should be called'
        1 * tweetsMapper.mapToTweet({
            List<String> tweetsAsString -> tweetsAsString == expectedTweetsAsStrings
        }) >> expectedTweets

        and: 'actual users should be'
        tweets == expectedTweets
    }
}
