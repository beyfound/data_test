package com.alvaria.datareuse.service;

import com.alvaria.datareuse.entity.User;
import com.csvreader.CsvReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadCSVService {

    private final static URL PATH = Thread.currentThread().getContextClassLoader().getResource("");

    public List<User> getUserListFromCSV(MultipartFile multipartFile) {
        String line = "";
        String splitBy = ",";
        List<User> userInfos = new ArrayList<>();
        ArrayList<String> strList = null;
        CsvReader reader = null;
        try {
            ArrayList<String[]> arrList = new ArrayList<String[]>();
            reader = new CsvReader(multipartFile.getInputStream(), ',', Charset.forName("UTF-8"));
            while (reader.readRecord()) {
                arrList.add(reader.getValues());
            }

            for (int row = 1; row < arrList.size(); row++) {
                User userInfo = new User();
                userInfo.setEmail(arrList.get(row)[0].substring(0, arrList.get(row)[0].indexOf('@')));
                userInfo.setPassword(arrList.get(row)[1]);
                userInfo.setDisplay(arrList.get(row)[2]);
                userInfo.setFirstName(arrList.get(row)[3]);
                userInfo.setLastName(arrList.get(row)[4]);
                userInfo.setTelephoneNumber(arrList.get(row)[5]);
                userInfo.setTeam(arrList.get(row)[6]);
                userInfo.setRole(arrList.get(row)[7]);
                userInfo.setTZone(arrList.get(row)[9]);
                userInfo.setLanguage(arrList.get(row)[10]);
                userInfo.setManagerOfTeam(arrList.get(row)[11]);
                userInfos.add(userInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        return userInfos;
    }
}
