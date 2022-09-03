package ru.dkotik.notesapp.presenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.InOrderImpl;

import java.util.List;

import ru.dkotik.notesapp.data.repository.Callback;
import ru.dkotik.notesapp.data.repository.NotesRepository;
import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.ui.list.NotesListView;

public class NotesListPresenterTest {

    private NotesListPresenter presenter;

    @Mock
    private NotesListView view;

    @Mock
    private NotesRepository repository;

    @Mock
    private Note note;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new NotesListPresenter(view, repository);
    }

    @Test
    public void noteListPresenter_getAll() {
        presenter.getAllNotes();

        InOrder order = Mockito.inOrder(view, repository);
        order.verify(view).showProgress();
        order.verify(repository).getAllNotes(any());
        verify(view, times(1)).showProgress();
        verify(repository, times(1)).getAllNotes(any());
    }

    @Test
    public void noteListPresenter_create() {
        presenter.createNote(note);

        verify(view, times(1)).onNoteAdded(note);
    }

    @Test
    public void noteListPresenter_update() {
        presenter.updateNote(note);

        verify(view, times(1)).onNoteUpdated(note);
    }

    @Test
    public void noteListPresenter_delete() {
        presenter.removeNote(note);

        InOrder order = Mockito.inOrder(view, repository);
        order.verify(view).showProgress();
        order.verify(repository).delete(eq(note), any());
        verify(view, times(1)).showProgress();
        verify(repository, times(1)).delete(eq(note), any());
    }
}
