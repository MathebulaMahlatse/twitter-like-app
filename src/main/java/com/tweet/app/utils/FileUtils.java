package com.tweet.app.utils;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component("fileUtils")
public class FileUtils {
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class);

    public File retrieveFile(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        try {
            return classPathResource.getFile();
        } catch (Exception ex) {
            LOGGER.error("Error loading file", ex);
            return null;
        }
    }

    public List<String> asString(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String> users = new ArrayList<>();

            String line = br.readLine();

            while (line != null) {
                users.add(line);
                line = br.readLine();
            }

            return users;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}