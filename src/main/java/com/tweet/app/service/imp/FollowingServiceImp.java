package com.tweet.app.service.imp;

import com.tweet.app.model.Following;
import com.tweet.app.model.User;
import com.tweet.app.service.FollowingService;
import com.tweet.app.utils.ApplicationConstants;
import com.tweet.app.utils.mapping.UserMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component("followingService")
public class FollowingServiceImp implements FollowingService {
    private UserMapper userMapper = new UserMapper();

    @Override
    public LinkedHashMap<String, List<Following>> getFollowing(List<User> applicationUsers, List<String> whoFollowsWho) {

        List<User> usersWithFollows = whoFollowsWho.stream().map(user -> {
            String[] parts = user.split(ApplicationConstants.USER_FOLLOWS_KEY);
            return userMapper.mapToUser(parts);
        }).collect(Collectors.toList());

        List<User> allUserWithFollowing = mapToAllUsers(applicationUsers, usersWithFollows);
        return userMapper.mapToUniqueFollowing(allUserWithFollowing);
    }

    private List<User> mapToAllUsers(List<User> allUsers, List<User> usersWithFollows) {
        return allUsers.stream().map(user -> {
            usersWithFollows.forEach(userWithFollow -> {
                if (user.getName().equals(userWithFollow.getName().trim())) {
                    user.setFollowing(userWithFollow.getFollowing());
                }
            });

            return user;
        }).collect(Collectors.toList());
    }
}
