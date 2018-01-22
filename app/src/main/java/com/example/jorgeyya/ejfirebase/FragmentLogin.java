package com.example.jorgeyya.ejfirebase;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jorgeyya.ejfirebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText corr,pass;

    String uid;

    DatabaseReference bbdd;

    boolean yaExiste;

    EditText username, nombre, apellidos, correo, direccion;

    Button entrar, logearse;

    private FirebaseAuth mAuth;

    private FragmentManager fm;
    private FragmentTransaction ft;


    private OnFragmentInteractionListener mListener;



    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        corr = (EditText) v.findViewById(R.id.text_mail);
        pass = (EditText) v.findViewById(R.id.text_pass);
        entrar = (Button) v.findViewById(R.id.btn_entrar);
        logearse = (Button) v.findViewById(R.id.btn_logearse);


        username = (EditText) v.findViewById(R.id.text_username);
        nombre = (EditText) v.findViewById(R.id.text_nombre);
        apellidos = (EditText) v.findViewById(R.id.text_apellidos);
        correo = (EditText) v.findViewById(R.id.text_correo);
        direccion = (EditText) v.findViewById(R.id.text_direccion);

        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = corr.getText().toString();
                String password = pass.getText().toString();

                Query q = bbdd.orderByChild("username").equalTo(username.getText().toString());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d("elasd", dataSnapshot+"");

                        String strUsername =  username.getText().toString();
                        String strNombre =  nombre.getText().toString();
                        String strApellidos =  apellidos.getText().toString();
                        String strCorreo =  corr.getText().toString();
                        String strDireccion =  direccion.getText().toString();

                        for(DataSnapshot ds: dataSnapshot.getChildren()){

                            String clave = uid;

                            //Log.d("asd", clave);



                            yaExiste = true;



                            Log.d("asd", username.getText().toString()+"  "+yaExiste+" clave: "+clave);
                        }



                        if(!yaExiste) {


                            if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strNombre) || TextUtils.isEmpty(strApellidos) || TextUtils.isEmpty(strCorreo) || TextUtils.isEmpty(strDireccion)) {

                                Toast.makeText(getContext(), "No debe haber campos vacíos", Toast.LENGTH_SHORT).show();

                            } else {

                                registrar (corr.getText().toString(), pass.getText().toString());

                               // Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                            }

                        }else{Toast.makeText(getActivity(), "Nombre de usuario ya utilizado", Toast.LENGTH_SHORT).show();}


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }
        });


        logearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = corr.getText().toString();
                String password = pass.getText().toString();

                login (mail, password);

            }
        });

        return v;
    }


    private void registrar (String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information



                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            Toast.makeText(getActivity(), "Authentication success."+user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            Usuario u = new Usuario(username.getText().toString(), corr.getText().toString(), direccion.getText().toString(), nombre.getText().toString(), apellidos.getText().toString());

                            FirebaseUser usuario = mAuth.getCurrentUser();
                            String clave =  usuario.getUid();

                            MainActivity.uiduser = user.getUid();


                            bbdd.child(clave).setValue(u);


                            fm = getFragmentManager();
                            ft = fm.beginTransaction();
                            ft.replace(R.id.frame_layout,FragmentInicio.newInstance(null,null));
                            ft.commit();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("apetecan","apetecan: "+task.getException().getMessage());
                        }
                    }
        });


        yaExiste = false;








    }






    private void login (String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getActivity(), "Authentication success."+user.getUid(),
                            Toast.LENGTH_SHORT).show();

                    MainActivity.uiduser = user.getUid();

                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.frame_layout,FragmentInicio.newInstance(null,null));
                    ft.commit();



                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(getActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
