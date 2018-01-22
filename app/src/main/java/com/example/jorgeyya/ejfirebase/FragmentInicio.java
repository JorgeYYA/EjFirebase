package com.example.jorgeyya.ejfirebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInicio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button addUser, verUser, modUser,addArticulo,verArticuloUser,verArticuloCat,modArticulo,btnFav,verFav;

    private FragmentManager fm;
    private FragmentTransaction ft;

    public FragmentInicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicio.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicio newInstance(String param1, String param2) {
        FragmentInicio fragment = new FragmentInicio();
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
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);


            addUser = (Button) v.findViewById(R.id.add_user);
            verUser = (Button) v.findViewById(R.id.ver_user);
            modUser = (Button) v.findViewById(R.id.mod_user);

            addArticulo = (Button) v.findViewById(R.id.add_articulo);

            verArticuloUser = (Button) v.findViewById(R.id.ver_art_por_user);
            verArticuloCat = (Button) v.findViewById(R.id.ver_art_por_cat);
            modArticulo = (Button) v.findViewById(R.id.mod_art);

            btnFav = (Button) v.findViewById(R.id.button_fav);
            verFav = (Button) v.findViewById(R.id.ver_fav);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentAddUser.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        verUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentListaUsuarios.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        modUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentModificar.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        addArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentAddArticulo.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        verArticuloUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentArtPorUser.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        verArticuloCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentArtPorCat.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        modArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentModArticulo.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentFav.newInstance(null,null)).addToBackStack(null);
                ft.commit();

            }
        });

        verFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,FragmentVerFav.newInstance(null,null)).addToBackStack(null);
                ft.commit();

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
