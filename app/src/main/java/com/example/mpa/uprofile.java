
package com.example.mpa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;


public class uprofile extends Fragment {
    TextView t,t1,t2,t3,t4,t5;
    FirebaseFirestore ff;
    FirebaseUser u;
FirebaseAuth a;
String uid;
    public uprofile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //  user1 = getArguments().getParcelable("user1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_uprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getActivity().setTitle("Profile");
        t=view.findViewById(R.id.tv);
        t1=view.findViewById(R.id.tv1);
        t2=view.findViewById(R.id.tv2);
        t3=view.findViewById(R.id.tv3);
        t4=view.findViewById(R.id.tv4);
        t5=view.findViewById(R.id.tv5);
a=FirebaseAuth.getInstance();
uid=a.getCurrentUser().getUid();

        ff=FirebaseFirestore.getInstance();
        fetchdata();
    }

    public  void fetchdata()
    {

        DocumentReference d=FirebaseFirestore.getInstance().collection("User").document(uid);
        d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    t1.setText(documentSnapshot.getString(" Email"));
                    t2.setText(documentSnapshot.getString("Name"));
                    t3.setText(documentSnapshot.getString("Dob"));
                    t4.setText(documentSnapshot.getString("Gender"));
                    t5.setText(documentSnapshot.getString("Location"));

                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Fail to feth data",Toast.LENGTH_LONG).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Fail to feth data",Toast.LENGTH_LONG).show();
                    }
                });
    }
}