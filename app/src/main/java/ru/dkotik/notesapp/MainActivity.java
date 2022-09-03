package ru.dkotik.notesapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.ui.CustomActions;
import ru.dkotik.notesapp.ui.about.AboutFragment;
import ru.dkotik.notesapp.ui.about.NavDrawerHost;
import ru.dkotik.notesapp.ui.detail.NoteDetailFragment;
import ru.dkotik.notesapp.ui.list.GoOutDialog;
import ru.dkotik.notesapp.ui.list.impl.NotesListFragment;

public class MainActivity extends AppCompatActivity implements NavDrawerHost {

    private static final String ARG_NOTE = "ARG_NOTE";
    private static final String ARG_IS_DRAWER = "ARG_IS_DRAWER";

    private boolean isDrawer = false;
    private boolean isGoHomeDialogActive = false;
    private boolean goOut = false;
    private Note selectedNote;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureDrawer(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(ARG_NOTE)) {
                selectedNote = savedInstanceState.getParcelable(ARG_NOTE);
            }

            if (savedInstanceState.containsKey(ARG_IS_DRAWER) && savedInstanceState.getBoolean(ARG_IS_DRAWER)) {
                isDrawer = savedInstanceState.containsKey(ARG_IS_DRAWER);

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    View detailContainer = findViewById(R.id.details_container);
                    detailContainer.setVisibility(View.GONE);
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AboutFragment.newInstance(selectedNote))
                        .commit();
                drawer.closeDrawer(GravityCompat.START);
            } else {
                loadMainActivity(fm, selectedNote);
            }
        }

        fm.setFragmentResultListener(NotesListFragment.RESULT_KEY,this, (requestKey, result) -> {
                selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);
                reloadMainActivityAfterListener(fm, selectedNote);
                isDrawer = false;
            }
        );

        fm.setFragmentResultListener(NoteDetailFragment.KEY_GO_TO_MAIN,this, (requestKey, result) -> {
                selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);
                loadMainActivityPortraitAfterListener(fm);
                isDrawer = false;
            }
        );

        fm.setFragmentResultListener(AboutFragment.IS_DRAWEL,this, (requestKey, result) -> isDrawer = true);

        fm.setFragmentResultListener(AboutFragment.KEY_GO_BACK_FROM_DRAWER,this, (requestKey, result) -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                View detailContainer = findViewById(R.id.details_container);
                detailContainer.setVisibility(View.VISIBLE);
            }
            selectedNote = result.getParcelable(AboutFragment.ARG_NOTE);
            loadMainActivity(fm, selectedNote);
            isDrawer = false;
            }
        );

        fm.setFragmentResultListener(GoOutDialog.KEY_RESULT,this, (requestKey, result) -> {
            if (result.getInt(GoOutDialog.ARG_BUTTON) == -1) {
                goOut = true;
                onBackPressed();
            } else {
                isGoHomeDialogActive = false;
            }
        });
    }

    private void configureDrawer(Bundle savedInstanceState) {
        drawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_about:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, AboutFragment.newInstance(selectedNote))
                            .commit();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        View detailContainer = findViewById(R.id.details_container);
                        detailContainer.setVisibility(View.GONE);
                    }
                    drawer.closeDrawer(GravityCompat.START);
                    this.isDrawer = true;
                    return true;
            }
            return false;
        });
    }

    private void loadMainActivity(FragmentManager fm, Note selectedNote) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction()
                    .replace(R.id.details_container, NoteDetailFragment.newInstance(selectedNote))
                    .replace(R.id.fragment_container, new NotesListFragment())
                    .commit();
        } else {
            if (selectedNote != null) {
                fm.beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailFragment.newInstance(selectedNote))
                        .commit();
            } else {
                fm.beginTransaction()
                        .replace(R.id.fragment_container, new NotesListFragment())
                        .commit();
            }
        }
    }

    private void reloadMainActivityAfterListener(FragmentManager fm, Note selectedNote) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction()
                    .replace(R.id.details_container, NoteDetailFragment.newInstance(selectedNote))
                    .commit();
        } else {
            fm.beginTransaction()
                    .replace(R.id.fragment_container, NoteDetailFragment.newInstance(selectedNote))
                    .commit();
        }
    }

    private void loadMainActivityPortraitAfterListener(FragmentManager fm) {
        fm.beginTransaction()
                .replace(R.id.fragment_container, new NotesListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && isDrawer) {
            View detailContainer = findViewById(R.id.details_container);
            detailContainer.setVisibility(View.VISIBLE);
        }
        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ||
            (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && isDrawer)) {
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);
            if (!(fragment instanceof CustomActions) || !((CustomActions) fragment).onBackPressed()) {
                goOut(fm);
            }
        } else {
            goOut(fm);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
        outState.putBoolean(ARG_IS_DRAWER, isDrawer);
    }

    @Override
    public void supplyToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer_description,
                R.string.navigate_up_description
        );

        drawer.addDrawerListener(toggle);

        toggle.syncState();

    }

    public void goOut(FragmentManager fm) {
        if (!isGoHomeDialogActive) {
            isGoHomeDialogActive = true;
            showDialog(fm);
        } else {
            if (goOut) {
                isGoHomeDialogActive = false;
                super.onBackPressed();
            } else {
                showDialog(fm);
            }
        }
    }

    private void showDialog(FragmentManager fm) {
        new GoOutDialog().newInstance("Внимание!").show(fm, "GoHomeDialog");
    }
}