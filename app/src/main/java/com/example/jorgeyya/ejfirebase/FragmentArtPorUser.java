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
import android.widget.ListView;
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
 * {@link FragmentArtPorUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentArtPorUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentArtPorUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText nombreUsuario;

    ListView listaProductos;

    Button buscar;

    DatabaseReference bbdd;

    private OnFragmentInteractionListener mListener;

    public FragmentArtPorUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentArtPorUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentArtPorUser newInstance(String param1, String param2) {
        FragmentArtPorUser fragment = new FragmentArtPorUser();
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
        View v = inflater.inflate(R.layout.fragment_art_por_user, container, false);


        nombreUsuario = (EditText) v.findViewById(R.id.nombre_usuario);

        listaProductos = (ListView) v.findViewById(R.id.lista_articulos);

        buscar = (Button) v.findViewById(R.id.btn_buscar);

        bbdd = FirebaseDatabase.getInstance().getReference("articulos");



        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Query q = bbdd.orderByChild("user").equalTo(nombreUsuario.getText().toString());

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adaptador;
                        ArrayList<String> lista = new ArrayList<String>();

                        for(DataSnapshot ds: dataSnapshot.getChildren()){

                            Articulo art = ds.getValue(Articulo.class);

                            String nombre = art.getNombre();
                            lista.add(nombre);

                        }

                        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);

                        listaProductos.setAdapter(adaptador);
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
