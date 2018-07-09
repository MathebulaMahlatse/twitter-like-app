package com.tweet.app.utils.mapping

import com.tweet.app.model.Following
import com.tweet.app.model.Tweet
import com.tweet.app.model.User
import spock.lang.Specification
import spock.util.mop.Use

class UserMapperSpec extends Specification {
    UserMapper mapper = new UserMapper()
    def 'mapToUsers: should map unique users to users as list'() {
        given: 'uniqueUsers'
        LinkedHashMap<String, List<Following>> uniqueUsers  = new LinkedHashMap<String, List<Following>>() {
            {
                put('Alan', [new Following(name: 'Ward')])
                put('Ward', [])
            }
        }

        when: 'calling mapToUsers'
        List<User> users = mapper.mapToUsers(uniqueUsers)

        then: 'users should be'
        users
        users.get(0).name == 'Alan'
        users.get(0).following.get(0).name == 'Ward'
        users.get(1).name == 'Ward'
        !users.get(1).following
    }

    def 'mapToUser: should map to user'() {
        given: 'users'
        String[] users = ['Alan', 'Ward']

        when: 'calling mapToUser'
        User user = mapper.mapToUser(users)

        then: 'users should be'
        user
        user.name == 'Alan'
        user.following.get(0).name == 'Ward'
    }

    def 'mapToUser: should handle multiple follow'() {
        given: 'users'
        String[] users = ['Alan', 'Ward, Mark, Thabo, Macaka']

        when: 'calling mapToUser'
        User user = mapper.mapToUser(users)

        then: 'users should be'
        user
        user.name == 'Alan'
        user.following.get(0).name == 'Ward'
        user.following.get(1).name == 'Mark'
        user.following.get(2).name == 'Thabo'
        user.following.get(3).name == 'Macaka'
    }

    def 'mapToUser: should handle errors'() {
        given: 'users'
        String[] users = ['', null]

        when: 'calling mapToUser'
        User user = mapper.mapToUser(users)

        then: 'users should be'
        !user
    }

    def 'mapToUniqueFollowing: should map to unique following'() {
        given: 'users'
        List<User> users = [ new User(name: 'Alan', following: [ new Following(name: 'Mark')]),
                             new User(name: 'Alan', following: [ new Following(name: 'Mark')])]

        when: 'calling mapToUniqueFollowing'
        LinkedHashMap<String, List<Following>> actualUniqueFollowing = mapper.mapToUniqueFollowing(users)

        then: 'users should be'
        actualUniqueFollowing
        actualUniqueFollowing.size() == 1
        actualUniqueFollowing.get('Alan').size() == 1
        actualUniqueFollowing.get('Alan').get(0).name == 'Mark'
    }

    def 'mapToUniqueUsers: should map to unique users'() {
        given: 'whoFollowsWho'
        List<String> whoFollowsWho = ['Alan follows Mark', 'Alan follows Mark, Thabo']

        when: 'calling mapToUniqueUsers'
        List<String> actualUniqueUsers = mapper.mapToUniqueUsers(whoFollowsWho)

        then: 'actualUniqueUsers should be'
        actualUniqueUsers
        actualUniqueUsers.size() == 3
        actualUniqueUsers.get(0) == 'Alan'
        actualUniqueUsers.get(1) == 'Mark'
        actualUniqueUsers.get(2) == 'Thabo'
    }

    def 'mapToUniqueUsers: should handle error properly'() {
        given: 'whoFollowsWho'
        List<String> whoFollowsWho = ['Alan follows', 'Alan']

        when: 'calling mapToUniqueUsers'
        List<String> actualUniqueUsers = mapper.mapToUniqueUsers(whoFollowsWho)

        then: 'actualUniqueUsers should be'
        !actualUniqueUsers
    }

    def 'mapTweetsToUsers: should map tweets to users'() {
        given: 'users and tweets'
        List<User> users = [new User(name: 'Alan', following: [new Following(name: 'Thabo')])]
        List<Tweet> tweets = [new Tweet(name: 'Alan', message: 'Unit Testing Rocks!!!'),
                              new Tweet(name: 'Thabo', message: 'I repeat, Unit Testing Rocks !!!')]

        when: 'calling mapTweetsToUsers'
        List<User> actualUsers = mapper.mapTweetsToUsers(users, tweets)

        then: 'actual users should be'
        actualUsers
        actualUsers.get(0).name == 'Alan'
        actualUsers.get(0).messages.get(0).name == 'Alan'
        actualUsers.get(0).messages.get(0).message == 'Unit Testing Rocks!!!'
        actualUsers.get(0).messages.get(1).name == 'Thabo'
        actualUsers.get(0).messages.get(1).message == 'I repeat, Unit Testing Rocks !!!'
    }

}
