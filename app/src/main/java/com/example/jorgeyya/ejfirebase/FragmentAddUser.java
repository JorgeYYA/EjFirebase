package com.example.jorgeyya.ejfirebase;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorgeyya.ejfirebase.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAddUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAddUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText username, nombre, apellidos, correo, direccion;

    Button addUser;

    DatabaseReference bbdd;

    boolean yaExiste;

    private OnFragmentInteractionListener mListener;

    public FragmentAddUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddUser newInstance(String param1, String param2) {
        FragmentAddUser fragment = new FragmentAddUser();
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
        View v = inflater.inflate(R.layout.fragment_add_user, container, false);

            username = (EditText) v.findViewById(R.id.text_username);
            nombre = (EditText) v.findViewById(R.id.text_nombre);
            apellidos = (EditText) v.findViewById(R.id.text_apellidos);
            correo = (EditText) v.findViewById(R.id.text_correo);
            direccion = (EditText) v.findViewById(R.id.text_direccion);

            addUser = (Button) v.findViewById(R.id.btn_add);

            bbdd = FirebaseDatabase.getInstance().getReference("usuarios");


            addUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    yaExiste = false;

                    Query q = bbdd.orderByChild("username").equalTo(username.getText().toString());

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d("elasd", dataSnapshot+"");

                            String strUsername =  username.getText().toString();
                            String strNombre =  nombre.getText().toString();
                            String strApellidos =  apellidos.getText().toString();
                            String strCorreo =  correo.getText().toString();
                            String strDireccion =  direccion.getText().toString();

                            for(DataSnapshot ds: dataSnapshot.getChildren()){

                                String clave = ds.getKey();

                                //Log.d("asd", clave);



                                    yaExiste = true;



                                Log.d("asd", username.getText().toString()+"  "+yaExiste+" clave: "+clave);
                            }



                            if(!yaExiste) {


                                if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strNombre) || TextUtils.isEmpty(strApellidos) || TextUtils.isEmpty(strCorreo) || TextUtils.isEmpty(strDireccion)) {

                                    Toast.makeText(getContext(), "No debe haber campos vacíos", Toast.LENGTH_SHORT).show();

                                } else {

                                    Usuario u = new Usuario(strUsername, strCorreo, strDireccion, strNombre, strApellidos);

                                    String clave = bbdd.push().getKey();

                                    bbdd.child(clave).setValue(u);

                                    Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                                }

                            }else{Toast.makeText(getActivity(), "Nombre de usuario ya utilizado", Toast.LENGTH_SHORT).show();}


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
