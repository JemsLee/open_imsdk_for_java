package com.momo.imc.net;


public class IMNetworkUtil {

    public static boolean isNetWorkAvailable() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process pingProcess = runtime.exec("ping -c 1 8.8.8.8");
            int exitCode = pingProcess.waitFor();
            return (exitCode == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}