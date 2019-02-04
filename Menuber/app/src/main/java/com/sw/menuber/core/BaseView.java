package com.sw.menuber.core;

/**
 * Esta interface contiene todos los metodos
 * que tienen en comun las view
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showMessage(String msg);

    void showErrorMessage(String msg);

}
