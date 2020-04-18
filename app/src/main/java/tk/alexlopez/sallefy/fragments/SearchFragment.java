package tk.alexlopez.sallefy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.SignupActivity;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.network.manager.MeManager;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;


public class SearchFragment extends Fragment {

    public static final String TAG = SearchFragment.class.getName();
    private EditText input_search;



    public static SearchFragment getInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(v);
        getData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initViews(View v) {

        input_search = (EditText) v.findViewById(R.id.input_search);
        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG, "HAY CAMBIOOOOOOOOOOOOOOOOOOOOO " );

                // you can call or do what you want with your EditText here

                // yourEditText...
            }
        });
    }

    private void getData() {

    }





}
