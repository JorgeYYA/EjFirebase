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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jorgeyya.ejfirebase.model.Articulo;
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
 * {@link FragmentFav.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFav#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFav extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button modArt;

    DatabaseReference bbdd, bbdd2;


    String nombreArticulo;

    Spinner listaArt;

    CheckBox fav;

    String sNombre, sUser, sCate, sPrecio, sDesc;

    ArrayList<String> lista = new ArrayList<String>();

    ArrayList<String> listaCat = new ArrayList<String>();

    private OnFragmentInteractionListener mListener;

    public FragmentFav() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFav.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFav newInstance(String param1, String param2) {
        FragmentFav fragment = new FragmentFav();
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

        View v = inflater.inflate(R.layout.fragment_fav, container, false);



        listaArt = (Spinner) v.findViewById(R.id.spinner_nombre_producto);

        modArt = (Button) v.findViewById(R.id.btn_mod);


        fav = (CheckBox) v.findViewById(R.id.check_fav);

        bbdd = FirebaseDatabase.getInstance().getReference("articulos_fav"+MainActivity.uiduser);

        bbdd2 = FirebaseDatabase.getInstance().getReference("articulos");



        listaArt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(!lista.isEmpty()) {

                    Query q = bbdd2.orderByChild("nombre").equalTo(lista.get(position));

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot ds: dataSnapshot.getChildren()){

                                Articulo producto = ds.getValue(Articulo.class);

                                sNombre = producto.getNombre();
                                sDesc = producto.getDescripcion();
                               sCate = producto.getCategoria();
                                sPrecio = producto.getPrecio();
                                sUser = producto.getUser();

                                nombreArticulo = producto.getNombre();


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bbdd2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;


                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    Articulo producto = ds.getValue(Articulo.class);

                    String nombre = producto.getNombre();
                    lista.add(nombre);

                    nombreArticulo = nombre;

                }

                adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);

                listaArt.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        modArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String strArticuloAnterior =  articuloAntiguo.getText().toString();

                if (fav.isChecked()) {

                    Articulo art = new Articulo(sUser, sNombre, sDesc, sCate, sPrecio);

                    String clave = bbdd.push().getKey();

                    bbdd.child(clave).setValue(art);


                    Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                }

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
