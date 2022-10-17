package com.momo.imc.observer;

public interface IMObserver {
    public void onIMMessage(String message);
    public void onIMError(String message);
}
