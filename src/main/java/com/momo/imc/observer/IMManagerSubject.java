package com.momo.imc.observer;

import java.util.ArrayList;
import java.util.List;

public class IMManagerSubject implements IMSubject {
    //存放订阅者
    private List<IMObserver> observers=new ArrayList<IMObserver>();

    @Override
    public void addObserver(IMObserver obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(IMObserver obj) {
        int i = observers.indexOf(obj);
        if(i>=0){
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver(String receiveMessage) {
        for(int i=0;i<observers.size();i++){
            IMObserver o=(IMObserver)observers.get(i);
            o.onIMMessage(receiveMessage);
        }
    }

    @Override
    public void notifyErrorObserver(String errorMessage) {
        for(int i=0;i<observers.size();i++){
            IMObserver o=(IMObserver)observers.get(i);
            o.onIMError(errorMessage);
        }
    }

    public void publish(String message){
        notifyObserver(message);
    }

    public void publishError(String message){
        notifyErrorObserver(message);
    }
}

