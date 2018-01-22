package com.example.jorgeyya.ejfirebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorgeyya.ejfirebase.model.Articulo;
import com.example.jorgeyya.ejfirebase.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentModificar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentModificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentModificar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    boolean userCorrecto;

    EditText username, nombre, apellidos, correo, direccion;

    Button modUser;

    DatabaseReference bbdd;

    String usuarioAntiguo;

    private OnFragmentInteractionListener mListener;

    public FragmentModificar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentModificar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentModificar newInstance(String param1, String param2) {
        FragmentModificar fragment = new FragmentModificar();
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
        View v = inflater.inflate(R.layout.fragment_modificar, container, false);


             username = (EditText) v.findViewById(R.id.new_username);
             nombre = (EditText) v.findViewById(R.id.new_nombre);
             apellidos = (EditText) v.findViewById(R.id.new_apellidos);
             correo = (EditText) v.findViewById(R.id.new_correo);
             direccion = (EditText) v.findViewById(R.id.new_direccion);

             modUser = (Button) v.findViewById(R.id.btn_mod);

            bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

            Query q = bbdd.orderByKey().equalTo(MainActivity.uiduser);

            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot ds: dataSnapshot.getChildren()){

                        Usuario usuario = ds.getValue(Usuario.class);

                        username.setText(usuario.getUsername());
                        nombre.setText(usuario.getNombre());
                        apellidos.setText(usuario.getApellidos());
                        correo.setText(usuario.getCorreo());
                        direccion.setText(usuario.getDireccion());
                        usuarioAntiguo = usuario.getUsername();



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            modUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strUsernameAnterior =  usuarioAntiguo;

                    String strUsername =  username.getText().toString();
                    String strNombre =  nombre.getText().toString();
                    String strApellidos =  apellidos.getText().toString();
                    String strCorreo =  correo.getText().toString();
                    String strDireccion =  direccion.getText().toString();





                    Query q = bbdd.orderByKey().equalTo(MainActivity.uiduser);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                String clave = ds.getKey();

                                Log.d("asd", clave);


                                if(!TextUtils.isEmpty(username.getText().toString())){

                                    bbdd.child(clave).child("username").setValue(username.getText().toString());

                                }

                                if(!TextUtils.isEmpty(nombre.getText().toString())){

                                    bbdd.child(clave).child("nombre").setValue(nombre.getText().toString());

                                }

                                if(!TextUtils.isEmpty(apellidos.getText().toString())){

                                    bbdd.child(clave).child("apellidos").setValue(apellidos.getText().toString());

                                }

                                if(!TextUtils.isEmpty(correo.getText().toString())){

                                    bbdd.child(clave).child("correo").setValue(correo.getText().toString());

                                }

                                if(!TextUtils.isEmpty(direccion.getText().toString())){

                                    bbdd.child(clave).child("direccion").setValue(direccion.getText().toString());

                                }

                            }

                            Log.d("asd","asd2: "+MainActivity.uiduser);


                            }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });







                }
            });


        return v;
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
