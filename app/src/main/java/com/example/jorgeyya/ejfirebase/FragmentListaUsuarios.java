package com.example.jorgeyya.ejfirebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jorgeyya.ejfirebase.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListaUsuarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListaUsuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaUsuarios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listaUsers;

    DatabaseReference bbdd;

    private OnFragmentInteractionListener mListener;

    public FragmentListaUsuarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListaUsuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaUsuarios newInstance(String param1, String param2) {
        FragmentListaUsuarios fragment = new FragmentListaUsuarios();
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
        View v = inflater.inflate(R.layout.fragment_lista_usuarios, container, false);

            listaUsers = (ListView) v.findViewById(R.id.lista_users);

            bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayAdapter<String> adaptador;
                    ArrayList<String> lista = new ArrayList<String>();

                    for(DataSnapshot ds: dataSnapshot.getChildren()){

                        Usuario usuario = ds.getValue(Usuario.class);

                        String username = usuario.getUsername();
                        lista.add(username);

                    }

                    adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);

                    listaUsers.setAdapter(adaptador);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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
