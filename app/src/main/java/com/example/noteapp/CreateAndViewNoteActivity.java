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

import java.util.Date;

public class CreateAndViewNoteActivity extends AppCompatActivity {
    private Button saveBtn;
    private DatabaseHelper databaseHelper;
    private EditText noteTitleEditText;
    private EditText noteDescEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_view_note2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveBtn = findViewById(R.id.saveBtn);
        noteDescEditText = findViewById(R.id.noteDetailsEdtTxt);
        noteTitleEditText = findViewById(R.id.noteTitleEditText);
        databaseHelper = new DatabaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                note.setTitle(noteTitleEditText.getText().toString());
                note.setDetail(noteDescEditText.getText().toString());
                note.setCreatedDate(new Date());
                databaseHelper.createNewNote(note);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
