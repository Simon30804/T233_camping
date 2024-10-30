package es.unizar.eina.notepad.ui;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.notepad.R;
import es.unizar.eina.notepad.database.Parcela;

/** Pantalla principal de la aplicación Camping */
public class Camping extends AppCompatActivity {
    private ParcelaViewModel mParcelaViewModel;
    private ReservaViewModel mReservaViewModel;

    //static final int CREAR_PARCELA_ID = Menu.FIRST;
    //static final int LISTAR_PARCELAs_ID = Menu.FIRST + 1;
    //static final int CREAR_RESERVA_ID = Menu.FIRST + 2;
    //static final int LISTAR_RESERVAS_ID = Menu.FIRST + 3;


    RecyclerView mRecyclerView;

    ParcelaListAdapter mParAdapter;
    ReservaListAdapter mResAdapter;

    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad); //esto me lleva a una plantilla donde se presenta la app, retocarla para adaptarla a lo nuestro
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mParcelaViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mParcelaViewModel.getAllNotes().observe(this, notes -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(notes);
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> createNote());

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_note);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     public boolean onContextItemSelected(MenuItem item) {
        Note current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getTitle(),
                        Toast.LENGTH_LONG).show();
                mParcelaViewModel.delete(current);
                return true;
            case EDIT_ID:
                editNote(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        mStartCreateNote.launch(new Intent(this, NoteEdit.class));
    }

    ActivityResultLauncher<Intent> mStartCreateNote = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Note note) {
            mParcelaViewModel.insert(note);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResult executable) {
        return registerForActivityResult(
                new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Note note = new Note(extras.getString(NoteEdit.NOTE_TITLE),
                                extras.getString(NoteEdit.NOTE_BODY));
                        executable.process(extras, note);
                    }
                });
    }

    private void editNote(Note current) {
        Intent intent = new Intent(this, NoteEdit.class);
        intent.putExtra(NoteEdit.NOTE_TITLE, current.getTitle());
        intent.putExtra(NoteEdit.NOTE_BODY, current.getBody());
        intent.putExtra(NoteEdit.NOTE_ID, current.getId());
        mStartUpdateNote.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateNote = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Note note) {
            int id = extras.getInt(NoteEdit.NOTE_ID);
            note.setId(id);
            mParcelaViewModel.update(parecla);
        }
    });

}

interface ExecuteActivityResult {
    void process(Bundle extras, Note note);
}