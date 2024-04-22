package com.example.finalProjectDesignPatterns.logger;

public class FileLogger implements LogObserver{

    @Override
    public void log(String message){
        System.out.println("File Message: " + message);
    }
}
