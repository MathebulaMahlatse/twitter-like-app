package com.tweet.app.dao.imp

import com.tweet.app.dao.FileReaderDao
import com.tweet.app.utils.FileUtils
import spock.lang.Specification
import spock.lang.Subject

@Subject(UsersFileReaderDaoImp)
class UsersFileReaderDaoImpSpec extends Specification {
    FileUtils fileUtils = Mock()
    File usersFile = Mock()
    FileReaderDao fileReaderDao = new UsersFileReaderDaoImp(fileUtils: fileUtils, usersFile: usersFile)

    List<String> expectedContents =  ['Content']
    def 'get contents of file'() {
        when: 'calling  getContents'
        List<String>  actualContents = fileReaderDao.getContents()

        then: 'asString should be called'
        1 * fileUtils.asString({
            File file -> file == usersFile
        }) >> expectedContents

        and: 'actual contents should be'
        actualContents == expectedContents
    }
}
