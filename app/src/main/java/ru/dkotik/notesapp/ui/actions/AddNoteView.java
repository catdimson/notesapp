package ru.dkotik.notesapp.ui.actions;

import android.os.Bundle;

import androidx.annotation.StringRes;

public interface AddNoteView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setTitle(String title);

    void setDescription(String description);

    void actionCompleted(String key, Bundle bundle);
}
