package ru.dkotik.notesapp.repository.impl;

import android.os.Handler;
import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.repository.Callback;
import ru.dkotik.notesapp.repository.NotesRepository;

@Deprecated
public class InMemoryNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new InMemoryNotesRepository();

    private Executor executor = Executors.newSingleThreadExecutor();
    private List<Note> result;
    private Handler handler = new Handler(Looper.getMainLooper());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());

    private InMemoryNotesRepository() {
        Calendar calendar = Calendar.getInstance();
        result = new ArrayList<>(
                Arrays.asList(
                        new Note(UUID.randomUUID().toString(),"Заголовок 1", timeFormat.format(calendar.getTime()), "Рыба текст 1"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 2", timeFormat.format(calendar.getTime()), "Рыба текст 2"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 3", timeFormat.format(calendar.getTime()), "Рыба текст 3"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 4", timeFormat.format(calendar.getTime()), "Рыба текст 4"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 5", timeFormat.format(calendar.getTime()), "Рыба текст 5"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 6", timeFormat.format(calendar.getTime()), "Рыба текст 6"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 7", timeFormat.format(calendar.getTime()), "Рыба текст 7"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 8", timeFormat.format(calendar.getTime()), "Рыба текст 8"),
                        new Note(UUID.randomUUID().toString(),"Заголовок 9", timeFormat.format(calendar.getTime()), "Рыба текст 9")
                )
        );
    }

    @Override
    public void getAllNotes(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });
    }

    @Override
    public void save(String title, String description, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        Note note = new Note(UUID.randomUUID().toString(), title, timeFormat.format(calendar.getTime()), description);
                        result.add(note);
                        callback.onSuccess(note);
                    }
                });
            }
        });
    }

    @Override
    public void update(Note note, String title, String description, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = 0;

                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).getId().equals(note.getId())) {
                                index = i;
                                break;
                            }
                        }

                        Calendar calendar = Calendar.getInstance();
                        Note editableNote = result.get(index);
                        editableNote.setTitle(title);
                        editableNote.setDescription(description);
                        editableNote.setTime(timeFormat.format(calendar.getTime()));
                        callback.onSuccess(editableNote);
                    }
                });
            }
        });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.remove(note);
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryNotesRepository that = (InMemoryNotesRepository) o;
        return Objects.equals(executor, that.executor) && Objects.equals(result, that.result) && Objects.equals(handler, that.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(executor, result, handler);
    }
}
