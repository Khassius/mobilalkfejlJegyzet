package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class NoteDetails extends AppCompatActivity {

    TextView noteTitleDetailTV;
    TextView noteContentDetailTV;
    FloatingActionButton goToEditNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        noteTitleDetailTV = findViewById(R.id.noteTitleDetail);
        noteContentDetailTV = findViewById(R.id.noteDetailContent);
        goToEditNote = findViewById(R.id.goToEditNote);
        Toolbar toolbar = findViewById(R.id.noteDetailToolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        goToEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteId", data.getStringExtra("noteId"));
                view.getContext().startActivity(intent);
            }
        });

        noteContentDetailTV.setText(data.getStringExtra("content"));
        noteTitleDetailTV.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            //startActivity(new Intent(NoteDetails.this, NotesActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NoteDetails.this, NotesActivity.class));
    }
}