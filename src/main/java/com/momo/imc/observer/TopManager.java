package com.momo.imc.observer;

import java.util.HashMap;
import java.util.Map;

public class TopManager {

    private static TopManager topManager;
    Map<String,IMSupperManager> managerMap = new HashMap<>();

    private TopManager() {}

    public static TopManager instance() {
        if (null == topManager) {
            topManager = new TopManager();
        }
        return topManager;
    }

    /**
     * 创建一个管理者
     * @return
     */
    public IMSupperManager createManager(){
        IMSupperManager imSupperManager  = null;
        try {
            imSupperManager = new IMSupperManager();
        }catch (Exception exception){
            imSupperManager  = null;
        }
        return  imSupperManager;
    }

    /**
     * 保存IM管理者
     * @param key
     * @param manager
     */
    public boolean addManager(String key,IMSupperManager manager){
        boolean af = true;
        try {
            managerMap.put(key,manager);
        }catch (Exception e){
            af = false;
        }
        return af;
    }

    /**
     * 获取IM管理者
     * @param key
     * @return
     */
    public IMSupperManager getManager(String key){

        IMSupperManager imManager = null;
        try {
            imManager = managerMap.get(key);
        }catch (Exception e){
            imManager = null;
        }
        return imManager;
    }

    /**
     * 删除IM管理者
     * @param key
     * @return
     */
    public boolean delManager(String key){
        boolean af = true;
        try {
            managerMap.remove(key);
        }catch (Exception e){
            af = false;
        }
        return af;
    }


    /**
     * 清理IM管理者
     * @return
     */
    public boolean clear(){
        boolean af = true;
        try {
            managerMap.clear();
        }catch (Exception e){
            af = false;
        }
        return af;
    }

}
