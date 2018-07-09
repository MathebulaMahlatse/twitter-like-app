package com.tweet.app.service.imp;

import com.tweet.app.dao.FileReaderDao;
import com.tweet.app.model.Tweet;
import com.tweet.app.service.TweetsService;
import com.tweet.app.utils.mapping.TweetsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("TweetsService")
public class TweetsServiceImp implements TweetsService {
    private TweetsMapper tweetsMapper = new TweetsMapper();

    @Autowired
    @Qualifier("tweetsFileReaderDao")
    private FileReaderDao tweetsFileReaderDao;

    @Override
    public List<Tweet> getTweets() {
        List<String> contents = tweetsFileReaderDao.getContents();
        return tweetsMapper.mapToTweet(contents);
    }
}
