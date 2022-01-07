package ru.dkotik.notesapp.view.actions;

import android.os.Bundle;

import androidx.annotation.StringRes;

import ru.dkotik.notesapp.model.Note;

public interface AddNoteView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setTitle(String title);

    void setDescription(String description);

    void actionCompleted(String key, Bundle bundle);
}
