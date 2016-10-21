package com.infobip.demo.common.ui;

import javafx.scene.layout.Pane;

/**
 * Custom view abstraction extended by the concrete view classes in this application. It holds a reference to
 * instance of a controller of type {@code T} in charge with controlling this view.
 * {@link View} also defines two abstract methods that make working with views easier and standardized.
 *
 * @param <T> concrete type of the controller in change of this view
 */
public abstract class View<T extends Controller> {

    /**
     * Reference to the controller instance in charge of this view.
     */
    protected final T controller;

    /**
     * @param controller instance of the in charge of controlling this view
     */
    protected View(T controller) {
        this.controller = controller;
    }

    /**
     * @return instance in charge of controlling this view
     */
    public T getController() {
        return controller;
    }

    /**
     * Use this method to get {@link Pane} instance that can be added to JavaFX scene graph.
     *
     * @return JavaFX representation of this view
     */
    public abstract Pane getPane();

    /**
     * Use this method to instruct this view to show provided {@code errorMessage} String as an error.
     *
     * @param errorMessage text to be shown with error style
     */
    public abstract void showErrorMessage(String errorMessage);
}
