package com.example.mpa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class dashboard extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    Button logout;
    FirebaseUser user;
    FirebaseFirestore fireStore;
    NavController navController;
    TextView txt;
    NavigationView n;
    private DrawerLayout d;


    public dashboard() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user = getArguments().getParcelable("user");
        fireStore = FirebaseFirestore.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout = view.findViewById(R.id.logout_btn);
        txt = view.findViewById(R.id.textname);
        d = view.findViewById(R.id.drawer);
        n = view.findViewById(R.id.navi_v);
        n.setNavigationItemSelectedListener(this);


        navController = Navigation.findNavController(getActivity(), R.id.host_fragment);

        readFireStore();

        logout.setOnClickListener(view1 -> {


            FirebaseAuth.getInstance().signOut();
            navController.navigate(R.id.login);

        });
    }

    public void readFireStore() {
        DocumentReference docRef = fireStore.collection("User").document(user.getUid());

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists()) {
                    Log.d("DashboardFragment", doc.getData().toString());

                    txt.setText("Hello  " + doc.get("Name") + " ! You are signed in with " + doc.get("Email"));


                }

            }
        });
    }


    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:

             /*   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new uprofile()).commit();*/
                navController.navigate(R.id.uprofile);

                Toast.makeText(getActivity().getApplicationContext(), "Profile", Toast.LENGTH_LONG).show();

                break;


            case R.id.dashboard:
                Toast.makeText(getActivity().getApplicationContext(), "Dashboard", Toast.LENGTH_LONG).show();

                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.login);

               Toast.makeText(getActivity().getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();

                break;
        }

        d.closeDrawer(GravityCompat.START);
        return true;

    }
}
