package ru.dkotik.notesapp.data.repository;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable error);
}
