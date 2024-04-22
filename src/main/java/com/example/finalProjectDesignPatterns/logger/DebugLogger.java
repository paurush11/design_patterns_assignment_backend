package com.example.finalProjectDesignPatterns.logger;

public class DebugLogger extends AbstractLogger{
        public DebugLogger(int levels){
            this.levels = levels;
        }

        @Override
        public void display(String msg, LoggerSubject loggerSubject){
            loggerSubject.notifyAllObserver(levels, msg);
        }

}
