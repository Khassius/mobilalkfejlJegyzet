package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    Intent data;
    EditText noteTitleEdit;
    EditText noteContentEdit;
    FloatingActionButton noteEditSave;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        noteTitleEdit = findViewById(R.id.noteTitleEdit);
        noteContentEdit = findViewById(R.id.noteContentEdit);
        noteEditSave = findViewById(R.id.saveEditNote);

        data = getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar= findViewById(R.id.editNoteToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Save button clicked", Toast.LENGTH_LONG).show();
                String newTitle = noteTitleEdit.getText().toString();
                String newContent = noteContentEdit.getText().toString();

                if(newTitle.isEmpty() || newContent.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Something is empty", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    DocumentReference documentReference = firebaseFirestore
                            .collection("notes")
                            .document(firebaseUser.getUid())
                            .collection("myNotes")
                            .document(data.getStringExtra("noteId"));
                    Map<String, Object> note = new HashMap<>();
                    note.put("title",newTitle);
                    note.put("content", newContent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Note updated", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to update", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");
        noteContentEdit.setText(noteContent);
        noteTitleEdit.setText(noteTitle);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditNoteActivity.this, NotesActivity.class));
    }
}