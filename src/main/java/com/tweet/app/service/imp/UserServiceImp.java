package com.tweet.app.service.imp;

import com.tweet.app.dao.FileReaderDao;
import com.tweet.app.model.Following;
import com.tweet.app.model.User;
import com.tweet.app.service.FollowingService;
import com.tweet.app.service.UserService;
import com.tweet.app.utils.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component("userService")
public class UserServiceImp implements UserService {

    private UserMapper userMapper = new UserMapper();

    @Autowired
    @Qualifier("followingService")
    private FollowingService followingService;

    @Autowired
    @Qualifier("usersFileReaderDao")
    private FileReaderDao usersFileReaderDao;

    @Override
    public List<User> getUsers() {
        List<String> contents = usersFileReaderDao.getContents();

        List<String> applicationUsers = userMapper.mapToUniqueUsers(contents);

        applicationUsers.sort(String::compareTo);

        LinkedHashMap<String, List<Following>> uniqueUserFollowing = followingService.getFollowing(mapToUsers(applicationUsers), contents);
        return userMapper.mapToUsers(uniqueUserFollowing);
    }

    private List<User> mapToUsers(List<String> applicationUsers) {
        return applicationUsers.stream().map(applicationUser -> {
            User user = new User();
            user.setName(applicationUser);
            return user;
        }).collect(toList());
    }
}
