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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends Fragment {

    NavController navController;
    EditText mail_login, pass_login;
    Button btn_login;

    TextView reg;
    private FirebaseAuth fireAuth;

    FirebaseUser currentUser;


    public login() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail_login = view.findViewById(R.id.email);
        pass_login = view.findViewById(R.id.password);

        btn_login = view.findViewById(R.id.login_btn);
        reg = view.findViewById(R.id.Register_txt);

        navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
        reg.setOnClickListener(view1 -> {

            navController.navigate(R.id.register);
        });


        btn_login.setOnClickListener(view2 -> {

            if (!checkEmptyFields()) {
                String email = mail_login.getText().toString();
                String pass = pass_login.getText().toString();
                loginUser(email, pass);
            }

        });
    }

        public boolean checkEmptyFields ()
        {
            if (TextUtils.isEmpty(mail_login.getText().toString())) {
                mail_login.setError("Email cannot be empty!");
                mail_login.requestFocus();
                return true;
            } else if (TextUtils.isEmpty(pass_login.getText().toString())) {
                pass_login.setError("Password cannot be empty!");
                pass_login.requestFocus();
                return true;
            }
            return false;
        }

    public void updateUI(FirebaseUser user)
    {
        Bundle b = new Bundle();
        b.putParcelable("user",user);
        navController.navigate(R.id.dashboard,b);
    }
    public void loginUser(String email, String pass)
    {
        fireAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Login Success!", Toast.LENGTH_SHORT).show();
                        currentUser = fireAuth.getCurrentUser();
                        updateUI(currentUser);
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(),"Authenticate Failed!", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    }
