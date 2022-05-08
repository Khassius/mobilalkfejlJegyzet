package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class CreateNoteActivity extends AppCompatActivity {

    EditText createNoteTitleET;
    EditText createNoteContentET;
    FloatingActionButton saveNoteBtn;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        saveNoteBtn = findViewById(R.id.saveNote);
        createNoteContentET = findViewById(R.id.noteContentCreate);
        createNoteTitleET = findViewById(R.id.titleNoteCreate);

        Toolbar toolbar = findViewById(R.id.createNoteToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = createNoteTitleET.getText().toString();
                String content = createNoteContentET.getText().toString();
                Log.d("what","button clicked");
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Both fields are required", Toast.LENGTH_LONG).show();
                } else {
                    DocumentReference documentReference = firebaseFirestore
                            .collection("notes")
                            .document(firebaseUser.getUid())
                            .collection("myNotes")
                            .document();
                    Map<String,Object> note = new HashMap<>();

                    note.put("title",title);
                    note.put("content",content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Note created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CreateNoteActivity.this, NotesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Note creation failed", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

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
        startActivity(new Intent(CreateNoteActivity.this, NotesActivity.class));
    }
}