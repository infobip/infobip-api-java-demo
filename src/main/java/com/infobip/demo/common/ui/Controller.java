package com.infobip.demo.common.ui;

public abstract class Controller<T> {

    protected T view;

    public void registerView(T view) {
        this.view = view;
    }

}
