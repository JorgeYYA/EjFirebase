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
import android.widget.EditText;
import android.widget.Spinner;
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
 * {@link FragmentModArticulo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentModArticulo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentModArticulo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText articuloAntiguo;

    EditText nombre, desc, cat, precio;

    Button modArt, eliminar;

    DatabaseReference bbdd, bbdd2;

    String usuario, catego;

    String nombreArticulo;

    Spinner listaArt, categoria;

    boolean userCorrecto;

    ArrayList<String> lista = new ArrayList<String>();

    ArrayList<String> listaCat = new ArrayList<String>();


    private OnFragmentInteractionListener mListener;

    public FragmentModArticulo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentModArticulo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentModArticulo newInstance(String param1, String param2) {
        FragmentModArticulo fragment = new FragmentModArticulo();
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
        View v = inflater.inflate(R.layout.fragment_mod_articulo, container, false);


            articuloAntiguo = (EditText) v.findViewById(R.id.art_anterior);
            nombre = (EditText) v.findViewById(R.id.new_nombre);
            desc = (EditText) v.findViewById(R.id.new_desc);
            cat = (EditText) v.findViewById(R.id.new_cat);
            precio = (EditText) v.findViewById(R.id.new_precio);

            listaArt = (Spinner) v.findViewById(R.id.spinner_nombre_producto);

            modArt = (Button) v.findViewById(R.id.btn_mod);
            eliminar = (Button) v.findViewById(R.id.btn_eliminar);

            bbdd = FirebaseDatabase.getInstance().getReference("articulos_"+MainActivity.uiduser);

            bbdd2 = FirebaseDatabase.getInstance().getReference("articulos");




        categoria = (Spinner) v.findViewById(R.id.spinner_categoria_mod_art);

        ArrayAdapter<String> adaptador;



        listaCat.add("hogar");
        listaCat.add("tecnología");
        listaCat.add("trabajo");

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaCat);

        categoria.setAdapter(adaptador);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                catego = listaCat.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listaArt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(!lista.isEmpty()) {

                    Query q = bbdd.orderByChild("nombre").equalTo(lista.get(position));

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot ds: dataSnapshot.getChildren()){

                                Articulo producto = ds.getValue(Articulo.class);

                                nombre.setText(producto.getNombre());
                                desc.setText(producto.getDescripcion());
                                cat.setText(producto.getCategoria());
                                precio.setText(producto.getPrecio());

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

        bbdd.addValueEventListener(new ValueEventListener() {
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

                    String strNombre =  nombre.getText().toString();
                    String strDesc =  desc.getText().toString();
                    String strCat =  cat.getText().toString();
                    String strPrecio =  precio.getText().toString();


                                        Query q2 = bbdd.orderByChild("nombre").equalTo(nombreArticulo);

                                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                    String clave = ds.getKey();

                                                    Log.d("asd", clave);


                                                    if (!TextUtils.isEmpty(nombre.getText().toString())) {

                                                        bbdd.child(clave).child("nombre").setValue(nombre.getText().toString());
                                                        bbdd2.child(clave).child("nombre").setValue(nombre.getText().toString());

                                                    }

                                                    if (!TextUtils.isEmpty(catego)) {

                                                        bbdd.child(clave).child("categoria").setValue(catego);
                                                        bbdd2.child(clave).child("categoria").setValue(catego);

                                                    }

                                                    if (!TextUtils.isEmpty(desc.getText().toString())) {

                                                        bbdd.child(clave).child("descripcion").setValue(desc.getText().toString());
                                                        bbdd2.child(clave).child("categoria").setValue(cat.getText().toString());


                                                    }

                                                    if (!TextUtils.isEmpty(precio.getText().toString())) {

                                                        bbdd.child(clave).child("precio").setValue(precio.getText().toString());
                                                        bbdd2.child(clave).child("categoria").setValue(cat.getText().toString());

                                                    }


                                                }


                                            }


                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }

            });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query q = bbdd.orderByChild("nombre").equalTo(nombreArticulo);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            String clave = ds.getKey();


                            DatabaseReference dr = bbdd.child(clave);
                            dr.removeValue();

                            DatabaseReference dr2 = bbdd2.child(clave);
                            dr2.removeValue();

                            Toast.makeText(getActivity(), articuloAntiguo+" Ha sido eliminado", Toast.LENGTH_SHORT).show();


                        }
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
