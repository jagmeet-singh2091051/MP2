package com.example.mpa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class register extends Fragment {

    EditText em,pw,nm,g,bd,cty;
    Button register;

private  FirebaseAuth auth;
private FirebaseFirestore firebase;
private NavController navController;

    public register() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseFirestore.getInstance();

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        em = view.findViewById(R.id.email);
        em = view.findViewById(R.id.email);
        pw = view.findViewById(R.id.password);
        nm = view.findViewById(R.id.name);
        g=view.findViewById(R.id.gender);
        bd = view.findViewById(R.id.dob);
        cty = view.findViewById(R.id.location);
        register = view.findViewById(R.id.signup_btn);
        navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
        register.setOnClickListener(view1 -> {


            if (!checkEmptyFields()) {
                if (pw.getText().length() < 6) {
                    pw.setError("Invalid Password, Password Should be at least 6 characters");
                    pw.requestFocus();
                } else {
                    String mailid = em.getText().toString();
                    String password = pw.getText().toString();
                    String fname = nm.getText().toString();
                    String gen=g.getText().toString();
                    String bdate = bd.getText().toString();
                    String loc = cty.getText().toString();

                    Person person= new Person(mailid, password, fname, gen , bdate, loc);

                    createUser(person);

                }
            }


        });
    }
    public boolean checkEmptyFields()
    {
        if(TextUtils.isEmpty(em.getText().toString()))
        {
            em.setError("Email cannot be empty!");
            em.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(pw.getText().toString()))
        {
            pw.setError("Password cannot be empty!");
            pw.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(nm.getText().toString()))
        {
            nm.setError("Name cannot be empty!");
            nm.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(g.getText().toString()))
        {
            g.setError("Gender cannot be empty!");
            g.requestFocus();
            return true;
        }
        else if (TextUtils.isEmpty(bd.getText().toString()))
        {
            bd.setError("Birth date cannot be empty!");
            bd.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(cty.getText().toString()))
        {
            cty.setError("Location cannot be empty!");
            cty.requestFocus();
            return true;
        }

        return false;
    }

    public void createUser(Person person)
    {
        auth.createUserWithEmailAndPassword(person.getEmail(),person.getPassword())
                .addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        writeFireStore(person, firebaseUser);
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(),"Registration Error!",Toast.LENGTH_LONG).show();
                    }

                });
    }

    public void writeFireStore(Person person, FirebaseUser firebaseUser)
    {
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("Name",person.getName());
        userMap.put("Email",person.getEmail());
        userMap.put("Dob",person.getDob());
        userMap.put("Gender",person.getGen());
        userMap.put("Location",person.getLocation());

        firebase.collection("User").document(firebaseUser.getUid())
                .set(userMap).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful())
            {
                Toast.makeText(getActivity().getApplicationContext(),"Registration Success!",Toast.LENGTH_LONG).show();
                navController.navigate(R.id.login);
            }else
            {
                Toast.makeText(getActivity().getApplicationContext(),"FireStore Error!",Toast.LENGTH_LONG).show();
            }
        });

    }


}
