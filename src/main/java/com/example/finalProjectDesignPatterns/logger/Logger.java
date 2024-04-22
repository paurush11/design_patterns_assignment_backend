package com.example.finalProjectDesignPatterns.logger;

public class Logger {

    private volatile static Logger logger;

    private volatile static AbstractLogger chainOfLogger;

    private volatile static LoggerSubject loggerSubject;

    private Logger(){
        if(logger != null){
            throw new RuntimeException("Use getLogger() method to get the single instance of this class.");
        }
    }

    public static Logger getInstance(){
        if(logger == null){
            synchronized (Logger.class){
                if(logger == null){
                    logger = new Logger();
                    chainOfLogger = doChaining();
                    loggerSubject = addObservers();
                }
            }
        }
        return logger;
    }

    public static AbstractLogger doChaining(){
        AbstractLogger infoLogger = new InfoLogger(1);
        AbstractLogger errorLogger = new ErrorLogger(2);
        AbstractLogger debugLogger = new DebugLogger(3);
        infoLogger.setNextLogger(errorLogger);
        errorLogger.setNextLogger(debugLogger);
        return infoLogger;
    }

    public static LoggerSubject addObservers(){
        LoggerSubject loggerSubject = new LoggerSubject();
        loggerSubject.addObserver(1,new ConsoleLogger());
        loggerSubject.addObserver(2,new ConsoleLogger());
        loggerSubject.addObserver(2,new FileLogger());
        loggerSubject.addObserver(3,new ConsoleLogger());
        return loggerSubject;
    }

    public void info(String message) {
        createLog(1, message);
    }

    public void error(String message) {
        createLog(2, message);
    }

    public void debug(String message) {
        createLog(3, message);
    }

    private void createLog(int level, String message){
        chainOfLogger.logMessage(level, message, loggerSubject);
    }




}
