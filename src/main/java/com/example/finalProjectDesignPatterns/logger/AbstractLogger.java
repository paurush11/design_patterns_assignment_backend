package com.example.finalProjectDesignPatterns.logger;

public abstract class AbstractLogger {

    int levels;

    private AbstractLogger nextLogger;

    void setNextLogger(AbstractLogger abstractLogger){
        this.nextLogger = abstractLogger;
    }

    void logMessage(int levels, String msg, LoggerSubject loggerSubject){
        if(this.levels == levels){
            display(msg, loggerSubject);
        }
        if(nextLogger != null)
            nextLogger.logMessage(levels, msg, loggerSubject);
    }

    abstract void display(String msg, LoggerSubject loggerSubject);


}
