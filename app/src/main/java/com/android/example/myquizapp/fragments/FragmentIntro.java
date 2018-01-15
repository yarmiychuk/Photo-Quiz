package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentIntro extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }
}
