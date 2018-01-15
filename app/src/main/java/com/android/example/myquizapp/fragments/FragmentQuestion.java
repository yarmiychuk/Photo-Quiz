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

import org.jetbrains.annotations.Contract;

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
     * Prepare question image and text to show
     * @param view - Root view of fragment
     * @param question - number of current question
     */
    private void invalidateQuestion(View view, int question) {
        // Set image
        ((ImageView) view.findViewById(R.id.iv_question_image))
                .setImageResource(getQuestionImage(question));
        // Set text
        ((TextView) view.findViewById(R.id.tv_question_text))
                .setText(getQuestionText(question));
    }

    /**
     * TODO Get image for question
     * @param question - number of question
     * @return image for question
     */
    @Contract(pure = true)
    private int getQuestionImage(int question) {
        switch (question) {
            case QuestionHelper.Q_ACTINIA:
                return R.drawable.actinia_square;
            case QuestionHelper.Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_square;
            case QuestionHelper.Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_square;
            case QuestionHelper.Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_square;
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * TODO Get text of questions
     * @param question - number of question
     * @return text for question
     */
    private String getQuestionText(int question) {
        switch (question) {
            case QuestionHelper.Q_ACTINIA:
                return getString(R.string.question_actinia);
            case QuestionHelper.Q_CABO_DA_ROCA:
                return getString(R.string.question_cabo_da_roca);
            case QuestionHelper.Q_GENERAL_SHERMAN:
                return getString(R.string.question_general_sherman);
            case QuestionHelper.Q_QUINTA_DA_REGALEIRA:
                return getString(R.string.question_quinta_da_regaleira);
        }
        return getString(R.string.error_question);
    }

}
