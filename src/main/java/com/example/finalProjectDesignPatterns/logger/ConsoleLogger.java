package com.example.finalProjectDesignPatterns.logger;

public class ConsoleLogger implements LogObserver{

    @Override
    public void log(String message){
        System.out.println("Console Message: " + message);
    }
}
