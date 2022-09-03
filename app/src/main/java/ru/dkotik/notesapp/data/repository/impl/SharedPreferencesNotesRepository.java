package ru.dkotik.notesapp.data.repository.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.data.repository.Callback;
import ru.dkotik.notesapp.data.repository.NotesRepository;

@Deprecated
public class SharedPreferencesNotesRepository implements NotesRepository {

    @SuppressLint("StaticFieldLeak")
    private static SharedPreferencesNotesRepository INSTANCE;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());

    private static final String KEY_NOTES = "KEY_NOTES";

    public static SharedPreferencesNotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesNotesRepository(context);
        }
        return INSTANCE;
    }

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public SharedPreferencesNotesRepository(Context context) {
        this.context = context.getApplicationContext();
        this.sharedPreferences = context.getApplicationContext().getSharedPreferences("notes", Context.MODE_PRIVATE);
    }

    @Override
    public void getAllNotes(Callback<List<Note>> callback) {
        String data = sharedPreferences.getString(KEY_NOTES, "[]");
        Type type = new TypeToken<List<Note>>() {

        }.getType();

        List<Note> result = gson.fromJson(data, type);

        callback.onSuccess(result);
    }

    @Override
    public void save(String title, String description, Callback<Note> callback) {
        Calendar calendar = Calendar.getInstance();
        String data = sharedPreferences.getString(KEY_NOTES, "[]");
        Type type = new TypeToken<List<Note>>() {

        }.getType();

        List<Note> result = gson.fromJson(data, type);

        Note note = new Note(UUID.randomUUID().toString(), title, description, timeFormat.format(calendar.getTime()));

        result.add(note);

        String toSave = gson.toJson(result, type);

        sharedPreferences.edit().putString(KEY_NOTES, toSave);

        callback.onSuccess(note);
    }

    @Override
    public void update(Note note, String title, String description, Callback<Note> callback) {

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

    }
}
