package com.momo.imc.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IMLogger {

    private static IMLogger IMLogger;

    private IMLogger() {
    }
    public static IMLogger getInstance() {
        if (null == IMLogger) {
            IMLogger = new IMLogger();
        }
        return IMLogger;
    }

    public void info(String message){
        System.out.println("Momo IM Server INFO-"+ LocalDateTime.now() + ":"+message);
    }

    public void error(String message){
        System.out.println("Momo IM Server ERROR-"+ LocalDate.now() + ":"+message);
    }
}
