package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentIntro extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_finish, container, false);

        // Hide Result Layout
        view.findViewById(R.id.ll_result).setVisibility(View.INVISIBLE);

        // Set text to TextViews
        ((TextView) view.findViewById(R.id.tv_quiz_title))
                .setText(getString(R.string.welcome));
        ((TextView) view.findViewById(R.id.tv_quiz_message))
                .setText(getString(R.string.quiz_description));

        return view;
    }
}
