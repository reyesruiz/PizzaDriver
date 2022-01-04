package com.digitalruiz.pizzadriver;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOrderNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrderNumberFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "SelectedDate";


    // TODO: Rename and change types of parameters
    private String mParam1;


    public AddOrderNumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment AddOrderNumber.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrderNumberFragment newInstance( String param1) {
        AddOrderNumberFragment fragment = new AddOrderNumberFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order_number, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String SelectedDate = mParam1;

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText orderNumberText = view.findViewById(R.id.orderNumber);

        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        orderNumberText.requestFocus();


        orderNumberText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                int orderNumber =  Integer.parseInt(orderNumberText.getText().toString());
                Log.d("TAG", "onViewCreated: " + orderNumber);
                Bundle addOrderBundle = new Bundle();
                addOrderBundle.putInt("orderNumber", orderNumber);
                addOrderBundle.putString("SelectedDate", mParam1);
                NavHostFragment.findNavController(AddOrderNumberFragment.this)
                        .navigate(R.id.action_addOrderNumber_to_addOrderFragment, addOrderBundle);
            }
            return false;
        });

    };

}