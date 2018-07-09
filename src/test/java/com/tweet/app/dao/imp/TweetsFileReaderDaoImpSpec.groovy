package com.tweet.app.dao.imp

import com.tweet.app.dao.FileReaderDao
import com.tweet.app.utils.FileUtils
import spock.lang.Specification
import spock.lang.Subject

@Subject(TweetsFileReaderDaoImp)
class TweetsFileReaderDaoImpSpec extends Specification {
    FileUtils fileUtils = Mock()
    File tweetsFile = Mock()
    FileReaderDao fileReaderDao = new TweetsFileReaderDaoImp(fileUtils: fileUtils, tweetsFile: tweetsFile)

    List<String> expectedContents =  ['Content']
    def 'get contents of file'() {
        when: 'calling  getContents'
        List<String>  actualContents = fileReaderDao.getContents()

        then: 'asString should be called'
        1 * fileUtils.asString({
            File file -> file == tweetsFile
        }) >> expectedContents

        and: 'actual contents should be'
        actualContents == expectedContents
    }
}
