package com.tweet.app.service.imp

import com.tweet.app.dao.FileReaderDao
import com.tweet.app.model.Following
import com.tweet.app.model.Tweet
import com.tweet.app.model.User
import com.tweet.app.service.FollowingService
import com.tweet.app.service.UserService
import com.tweet.app.utils.mapping.UserMapper
import spock.lang.Specification

class UserServiceImpSpec extends Specification {
    UserMapper userMapper = Mock()
    FileReaderDao usersFileReaderDao = Mock()
    FollowingService followingService = Mock()
    UserService userService = new UserServiceImp(userMapper: userMapper, usersFileReaderDao: usersFileReaderDao,
            followingService: followingService)

    def 'should get users with following'() {
        setup:
        List<User> expectedUsers = [new User(name: 'Alan', following: [new Following(name: 'Ward')],
                messages: [new Tweet(name: 'Alan', message: 'Hi There')])]

        List<String> expectedFollows = ['Ward follows Alan', 'Alan follows Martin', 'Ward follows Martin, Alan']
        when: 'calling getUsers'
        List<User> users = userService.getUsers()

        then: 'file as string should be called'
        1 * usersFileReaderDao.getContents() >> expectedFollows

        and: 'mapToUniqueUsers should be called'
        1 * userMapper.mapToUniqueUsers({
            List<String> toHaveBeenCalledWith -> toHaveBeenCalledWith == expectedFollows
        }) >> expectedFollows

        and: 'getFollowing should be called'
        1 * followingService.getFollowing(_, {
            List<String> toHaveBeenCalledWith -> toHaveBeenCalledWith == expectedFollows
        }) >> new LinkedHashMap<String, List<Following>> () {
            {

            }
        }

        and: 'mapToUsers should be called'
        1 * userMapper.mapToUsers(_) >> [ expectedUsers ]

        and: 'users should be'
        users
        users.get(0) == expectedUsers
    }
}
