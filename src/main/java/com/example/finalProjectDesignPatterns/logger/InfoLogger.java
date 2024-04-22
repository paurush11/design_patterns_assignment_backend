package com.example.finalProjectDesignPatterns.logger;

public class InfoLogger extends AbstractLogger{

    public InfoLogger(int levels){
        this.levels = levels;
    }

    @Override
    public void display(String msg, LoggerSubject loggerSubject){
        loggerSubject.notifyAllObserver(levels, msg);
    }
}
