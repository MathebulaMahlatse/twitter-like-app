package com.tweet.app.utils

import spock.lang.Specification

class FileUtilsSpec extends Specification {
    FileUtils fileUtils = new FileUtils()
    def 'should load file successfully'() {
        given: 'file name'
        String fileName = "user.txt"

        when: 'calling retrieveFile'
        File file = fileUtils.retrieveFile(fileName)

        then: 'file should exist'
        file.exists()
    }

    def 'should handle exception gracefully if the file does not exist'() {
        given: 'file name'
        String fileName = 'doesNotExist.txt'

        when: 'calling retrieveFile'
        File file = fileUtils.retrieveFile(fileName)

        then: 'null should have been returned'
        !file
    }
}
