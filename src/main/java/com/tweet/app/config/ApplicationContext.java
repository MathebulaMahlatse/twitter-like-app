package com.tweet.app.config;

import com.tweet.app.utils.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
@ComponentScan(basePackages = {"com.tweet.app.service", "com.tweet.app.tweet", "com.tweet.app.utils", "com.tweet.app.dao"})
public class ApplicationContext {
    @Autowired
    @Qualifier("fileUtils")
    private FileUtils fileUtils;

    @Autowired
    private Environment env;

    @Bean(name = "usersFile")
    public File usersFile() {
        return fileUtils.retrieveFile(env.getProperty("mock.users"));
    }

    @Bean(name = "tweetsFile")
    public File tweetsFile() {
        return fileUtils.retrieveFile(env.getProperty("mock.tweets"));
    }
}
