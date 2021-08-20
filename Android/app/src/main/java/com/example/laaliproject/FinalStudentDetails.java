package com.example.laaliproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laaliproject.StudentScreens.StudentPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class FinalStudentDetails extends AppCompatActivity {
    TextView nameView, ageView, assStatus, assLink, sessionattend, sessionfeedback;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    DatabaseReference mUserDatabaseReference;
    String userId;
    Button grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_student_details);
        nameView = findViewById(R.id.nameView);
        ageView = findViewById(R.id.ageView);
        assStatus = findViewById(R.id.assStatus);
        assLink = findViewById(R.id.asslink);
        sessionattend = findViewById(R.id.sessattendance);
        sessionfeedback = findViewById(R.id.sessfeedback);
        grade=findViewById(R.id.grade);
        grade.setVisibility(View.GONE);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        if (getIntent() != null) {
            userId = getIntent().getStringExtra("UserID");
        }

        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ProgramManager").child("Mentor").child("Students").child(userId);
        mUserDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                nameView.setText(name);
                String age=snapshot.child("age").getValue().toString();
                ageView.setText(age);
                String assstatus=snapshot.child("StudentAssignment").child("status").getValue().toString();
                assStatus.setText(assstatus);
                if(assstatus.equals("Submitted")){
                    String link=snapshot.child("StudentAssignment").child("link").getValue().toString();
                    assLink.setText(link);
                    grade.setVisibility(View.VISIBLE);
                    grade.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FinalStudentDetails.this);
                            builder.setTitle("Enter grade");

                            final EditText input = new EditText(FinalStudentDetails.this);
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String m_Text = input.getText().toString();
                                    mUserDatabaseReference.child("StudentAssignment").child("grade").setValue(m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(FinalStudentDetails.this,"Grade submitted",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    });
                }
                String sessattend=snapshot.child("Session").child("attendance").getValue().toString();
                sessionattend.setText(sessattend);
                if(sessattend.equals("Attended")){
                    String feedback=snapshot.child("Session").child("feedback").getValue().toString();
                    sessionfeedback.setText("Feedback: "+feedback);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}

