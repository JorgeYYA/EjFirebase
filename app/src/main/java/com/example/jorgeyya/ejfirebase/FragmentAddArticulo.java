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
 * {@link FragmentAddArticulo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAddArticulo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddArticulo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> lista = new ArrayList<String>();

    EditText user,nombre,desc,cat,precio;
    Button confirmar;

    DatabaseReference bbddArticulos, bbddUsuarios, bbddArticulos2;

    boolean yaExiste;

    String catego;

    String usuario;

    private OnFragmentInteractionListener mListener;

    Spinner categoria;

    public FragmentAddArticulo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddArticulo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddArticulo newInstance(String param1, String param2) {
        FragmentAddArticulo fragment = new FragmentAddArticulo();
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
        View v = inflater.inflate(R.layout.fragment_add_articulo, container, false);

            user = (EditText) v.findViewById(R.id.usuario_producto);
            nombre = (EditText) v.findViewById(R.id.art_nombre);
            desc = (EditText) v.findViewById(R.id.art_desc);
            cat = (EditText) v.findViewById(R.id.art_cat);
            precio = (EditText) v.findViewById(R.id.art_precio);
            confirmar = (Button) v.findViewById(R.id.btn_add);



        bbddArticulos = FirebaseDatabase.getInstance().getReference("articulos_"+MainActivity.uiduser);

        bbddArticulos2 = FirebaseDatabase.getInstance().getReference("articulos");

        bbddUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");


        categoria = (Spinner) v.findViewById(R.id.spinner_categoria_add);

        ArrayAdapter<String> adaptador;



            lista.add("hogar");
            lista.add("tecnología");
            lista.add("trabajo");

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                catego = lista.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);

        categoria.setAdapter(adaptador);


        Query q = bbddUsuarios.orderByKey().equalTo(MainActivity.uiduser);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    Usuario elUsuario = ds.getValue(Usuario.class);

                    usuario = elUsuario.getUsername();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                            if (TextUtils.isEmpty(nombre.getText().toString()) || TextUtils.isEmpty(desc.getText().toString()) || TextUtils.isEmpty(catego) || TextUtils.isEmpty(precio.getText().toString())){

                                Toast.makeText(getActivity(), "No debe haber campos vacíos", Toast.LENGTH_SHORT).show();

                            }else{


                                Articulo art = new Articulo(usuario, nombre.getText().toString(), desc.getText().toString(), catego, precio.getText().toString());

                                String clave = bbddArticulos.push().getKey();

                                bbddArticulos.child(clave).setValue(art);


                                bbddArticulos2.child(clave).setValue(art);


                                Toast.makeText(getActivity(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();


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
