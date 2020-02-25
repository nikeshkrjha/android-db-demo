package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.noteapp.database.DatabaseHelper;
import com.example.noteapp.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class CreateAndViewNoteActivity extends AppCompatActivity {
    private Button saveBtn;
    private DatabaseHelper databaseHelper;
    private EditText noteTitleEditText;
    private EditText noteDescEditText;
    private int isUpdate = 0;
    private Note selectedNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_view_note2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        isUpdate = intent.getIntExtra("isUpdate", 0);
        initializeViewAndDbHelper();

        if(isUpdate == 1){
            populateValues();
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdate == 1) {
                    selectedNote.setTitle(noteTitleEditText.getText().toString());
                    selectedNote.setDetail(noteDescEditText.getText().toString());
                    int rowsAffected = databaseHelper.updateNote(selectedNote);
                    if (rowsAffected != -1) {
                        Toast.makeText(CreateAndViewNoteActivity.this, "Update Successfull",
                                Toast.LENGTH_SHORT);
                    }
                } else {
                    Note note = new Note();
                    note.setTitle(noteTitleEditText.getText().toString());
                    note.setDetail(noteDescEditText.getText().toString());
                    note.setCreatedDate(new Date());
                    long newRowID = databaseHelper.createNewNote(note);
                    if (newRowID != 0) {
                        Toast.makeText(CreateAndViewNoteActivity.this, "Insert Successfull",
                                Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }


    /***
     *  Initialize the view components and DbHelper objects
     * **/
    private void initializeViewAndDbHelper() {
        saveBtn = findViewById(R.id.saveBtn);
        noteDescEditText = findViewById(R.id.noteDetailsEdtTxt);
        noteTitleEditText = findViewById(R.id.noteTitleEditText);
        databaseHelper = new DatabaseHelper(this);
    }


    /***
     *  Populate the values of selected note into the form
     * **/
    private void populateValues() {
        Intent intent = getIntent();
        int selectedNoteID = intent.getIntExtra("NoteID", 0);
        if(selectedNoteID != 0){
            selectedNote = databaseHelper.getNote(selectedNoteID);

//        String noteTitle = intent.getStringExtra("NoteTitle");
//        String noteDesc = intent.getStringExtra("NoteDesc");
//        selectedNoteID = intent.getIntExtra("NoteID", 0);
            this.noteTitleEditText.setText(selectedNote.getTitle());
            this.noteDescEditText.setText(selectedNote.getDetail());
        }else{
            Toast.makeText(CreateAndViewNoteActivity.this, "Couldn't fetch note !!!",
                    Toast.LENGTH_SHORT);
        }
    }
}
