package ru.dkotik.notesapp.data.repository.impl;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.data.repository.Callback;
import ru.dkotik.notesapp.data.repository.NotesRepository;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault());

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TIME = "time";
    private static final String NOTES = "notes";

    @Override
    public void getAllNotes(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        List<Note> result = new ArrayList<>();
                        for (DocumentSnapshot doc : docs) {
                            String id = doc.getId();
                            String title = doc.getString(KEY_TITLE);
                            String description = doc.getString(KEY_DESCRIPTION);
                            String time = doc.getString(KEY_TIME);
                            Note note = new Note(id, title, time, description);
                            result.add(note);
                        }
                        callback.onSuccess(result);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void save(String title, String description, Callback<Note> callback) {
        Calendar calendar = Calendar.getInstance();
        Map<String, Object> data = new HashMap<>();
        String createdAt = timeFormat.format(calendar.getTime());
        data.put(KEY_TITLE, title);
        data.put(KEY_DESCRIPTION, description);
        data.put(KEY_TIME, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        callback.onSuccess(new Note(id, title, createdAt, description));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void update(Note note, String title, String description, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();

        data.put(KEY_TITLE, title);
        data.put(KEY_DESCRIPTION, description);
        data.put(KEY_TIME, note.getTime());

        db.collection(NOTES)
                .document(note.getId())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        note.setTitle(title);
                        note.setDescription(description);
                        callback.onSuccess(note);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }
}
