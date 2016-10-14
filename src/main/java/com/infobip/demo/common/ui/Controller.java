package com.infobip.demo.common.ui;

/**
 * Custom controller abstraction extended by the concrete controller classes in this application. It holds the
 * reference to instance of view of type {@param <T>} that this controller is in charge of and defines a single
 * method for setting the said view.
 *
 * @param <T> concrete type of the view that this controller is in charge of
 */
public abstract class Controller<T extends View> {

    /**
     * Reference to the view this controller is in charge of.
     */
    protected T view;

    /**
     * A view instance must be passed to the controller before it can start processing events and sending responses
     * to it. It is recommended to set is as soon as possible in the application flow.
     *
     * @param view instance of the view that this controller is in charge of
     */
    public void registerView(T view) {
        this.view = view;
    }

}
