package com.example.project;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.xml.parsers.ParserConfigurationException;

public class APIEntity implements Runnable{
    private static ArrayList<LocalStatistics> localList;
    private static NationStatistics nation;
    private final Lock lock;

    public APIEntity(Lock lock) {
        this.lock = lock;
    }

    public static NationStatistics getNation() {
        return nation;
    }
    public static ArrayList<LocalStatistics> getLocalList() {
        return localList;
    }

    @Override
    public void run() {
        API api = new API();
        lock.lock();
        try{
            try {
                localList = api.localAPI();
            } catch (IOException | ParseException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                nation = api.nationAPI(localList);
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
