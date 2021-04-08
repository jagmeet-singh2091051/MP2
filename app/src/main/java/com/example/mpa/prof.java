package com.example.mpa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class prof extends AppCompatActivity {
    TextView t;
    FirebaseFirestore ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        t=findViewById(R.id.tv);

        ff=FirebaseFirestore.getInstance();
        fetchdata();

    }
    public  void fetchdata()
    {

        DocumentReference d=FirebaseFirestore.getInstance().collection("User").document("0gQzBsh917UtXw50AnjpBtWL1oo2");
        d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
t.setText(documentSnapshot.getString("Location"));
                }
else
                {
                    Toast.makeText(getApplicationContext(),"Fail to feth data",Toast.LENGTH_LONG).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Fail to feth data",Toast.LENGTH_LONG).show();
                    }
                });
    }
}