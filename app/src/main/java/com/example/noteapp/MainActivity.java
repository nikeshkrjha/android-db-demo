package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.noteapp.adapter.NoteListAdapter;
import com.example.noteapp.database.DatabaseHelper;
import com.example.noteapp.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.NotesListInterface{

    private RecyclerView notesRecyclerView = null;
    private NoteListAdapter noteListAdapter = null;
    private DatabaseHelper databaseHelper = null;
    private RecyclerView.LayoutManager layoutManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupNotesListView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, CreateAndViewNoteActivity.class);
                intent.putExtra("isUpdate", 0);
                startActivity(intent);
            }
        });
    }

    private void setupNotesListView() {
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        databaseHelper = new DatabaseHelper(this);
        noteListAdapter = new NoteListAdapter(this,databaseHelper.getAllNotes());
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        notesRecyclerView.setHasFixedSize(true);

        notesRecyclerView.setAdapter(noteListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getSelectedNote(Note selectedNote){
        Intent intent = new Intent(MainActivity.this, CreateAndViewNoteActivity.class);
        intent.putExtra("isUpdate", 1);
        intent.putExtra("NoteID", selectedNote.getId());
//        intent.putExtra("NoteTitle", selectedNote.getTitle());
//        intent.putExtra("NoteDesc", selectedNote.getDetail());

        startActivity(intent);
    }
}
