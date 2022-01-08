package ru.dkotik.notesapp.repository;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable error);
}
