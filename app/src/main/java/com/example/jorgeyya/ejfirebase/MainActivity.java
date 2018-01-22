package com.example.jorgeyya.ejfirebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private FragmentTransaction ft;

    private FirebaseAuth fa;

    static String uiduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,FragmentInicio.newInstance(null,null));
        ft.commit();*/

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,FragmentLogin.newInstance(null,null));
        ft.commit();




    }
}
