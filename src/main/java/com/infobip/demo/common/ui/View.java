package com.infobip.demo.common.ui;

import javafx.scene.layout.Pane;

public abstract class View<T> {

    protected final T controller;

    protected View(T controller) {
        this.controller = controller;
    }

    public T getController() {
        return controller;
    }

    public abstract Pane getPane();

    public abstract void showErrorMessage(String errorMessage);
}
