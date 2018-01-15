package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.myquizapp.QuestionHelper;
import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentQuestion extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        int question = getArguments().getInt(QuestionHelper.ARG_QUESTION, 0);
        invalidateQuestion(view, question);

        return view;
    }

    /**
     * TODO Prepare question image and text to show
     * @param view
     * @param question
     */
    private void invalidateQuestion(View view, int question) {
        ImageView questionImageView = view.findViewById(R.id.iv_question_image);
        TextView questionTextView = view.findViewById(R.id.tv_question_text);
        switch (question) {
            case QuestionHelper.Q_CABO_DA_ROCA:
                questionImageView.setImageResource(R.drawable.cabo_da_roca_square);
                questionTextView.setText(getString(R.string.question_cabo_da_roca));
                break;
            default:
                questionImageView.setImageResource(R.drawable.yarmiychuk);
                questionTextView.setText(getString(R.string.error_question));
                break;
        }

    }

}
