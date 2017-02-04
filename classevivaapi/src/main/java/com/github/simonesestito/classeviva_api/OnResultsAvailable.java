package com.github.simonesestito.classeviva_api;


public interface OnResultsAvailable<T> {

    void onResultsAvailable(T result, ClassevivaSession instance);

    void onError(Exception e);
}
