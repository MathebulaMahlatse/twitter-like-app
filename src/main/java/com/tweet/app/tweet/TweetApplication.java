package com.tweet.app.tweet;

import com.tweet.app.model.Tweet;
import com.tweet.app.model.TwitterFeed;
import com.tweet.app.model.User;
import com.tweet.app.service.TwitterFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TweetApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("twitterFeedService")
	private TwitterFeedService twitterFeedService;

	public static void main(String[] args) {
		try {
			SpringApplication.run(TweetApplication.class, args);
		} catch (Exception ex) {
			System.out.println("Could not start the application");
			System.out.println(ex);
		}
	}

	@Override
	public void run(String [] strings) {
		// Assumption: The files will be located in the resource folder
		TwitterFeed twitterFeed = twitterFeedService.getTweetFeed();

		if(twitterFeed != null && twitterFeed.getUsers() != null) {
			for (User user : twitterFeed.getUsers()) {
				System.out.printf("%s%n", user.getName());
				if(user.getMessages() != null) {
					for (Tweet tweet : user.getMessages()) {
						System.out.printf("@%s:%s%n", tweet.getName(), tweet.getMessage());
					}
				}
			}
		} else {
			System.out.println("There is nothing to display");
		}
	}
}
