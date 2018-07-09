package com.tweet.app.dao.imp;

import com.tweet.app.dao.FileReaderDao;
import com.tweet.app.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository("usersFileReaderDao")
public class UsersFileReaderDaoImp implements FileReaderDao {
    @Autowired
    @Qualifier("fileUtils")
    private FileUtils fileUtils;

    @Autowired
    @Qualifier("usersFile")
    private File usersFile;

    @Override
    public List<String> getContents() {
        return fileUtils.asString(usersFile);
    }
}
