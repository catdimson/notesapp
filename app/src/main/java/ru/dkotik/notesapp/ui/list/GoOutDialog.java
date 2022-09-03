package ru.dkotik.notesapp.ui.list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.dkotik.notesapp.R;

public class GoOutDialog extends DialogFragment {
    public static final String KEY_RESULT = "GoOutDialog";
    public static final String ARG_BUTTON = "ARG_BUTTON";
    private static final String ARG_TITLE = "ARG_TITLE";

    interface ClickListener {
        void onPositiveClicked();
        void onNegativeClicked();
        void onNeutralClicked();
    }

    public static GoOutDialog newInstance(String title) {
        GoOutDialog dialogFragment = new GoOutDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DialogInterface.OnClickListener clickListener = (dialogInterface, i) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_BUTTON, i);
            getParentFragmentManager().setFragmentResult(KEY_RESULT, bundle);
        };

        return new AlertDialog.Builder(requireContext())
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle(requireArguments().getString(ARG_TITLE))
                .setMessage(R.string.message)
                .setPositiveButton(R.string.ok, clickListener)
                .setNegativeButton(R.string.no, clickListener)
                .setCancelable(false)
                .create();
    }
}
