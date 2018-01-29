package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.myquizapp.QuizHelper;
import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 15.01.2018.
 * Создал Dmitriy Yarmiychuk 15.01.2018
 */

public class FragmentFinish extends Fragment {

    // Final score for quiz
    private int scoreResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_finish, container, false);

        scoreResult = getArguments().getInt(QuizHelper.ARG_RESULT, QuizHelper.SCORE_DEFAULT);

        invalidateUI(view);

        return view;
    }

    /**
     * Set congratulation text
     *
     * @param view - root view of fragment
     */
    private void invalidateUI(View view) {
        int maxPoints = QuizHelper.QUESTIONS_TOTAL * 2;
        view.findViewById(R.id.ll_score).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.tv_quiz_score)).setText(String.valueOf(scoreResult));
        String resultCaption = getString(R.string.congratulations);
        String resultMessage;
        if (scoreResult == maxPoints) {
            // User answered for all questions
            resultMessage = getString(R.string.all_questions);
        } else if (scoreResult > maxPoints / 2) {
            // User answered for most questions
            resultMessage = getString(R.string.more_half);
        } else if (scoreResult > 0) {
            // User answered for less questions
            resultCaption = getString(R.string.no_bad);
            resultMessage = getString(R.string.less_half);
        } else {
            // User is looser
            resultCaption = getString(R.string.incredible);
            resultMessage = getString(R.string.no_one);
        }
        ((TextView) view.findViewById(R.id.tv_quiz_title)).setText(resultCaption);
        ((TextView) view.findViewById(R.id.tv_quiz_message)).setText(resultMessage);
    }

}
