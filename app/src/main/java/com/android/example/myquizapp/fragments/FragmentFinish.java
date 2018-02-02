package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.myquizapp.QuizHelper;
import com.android.example.myquizapp.R;

import org.jetbrains.annotations.Contract;

/**
 * Created by Dmitriy Yarmiychuk on 15.01.2018.
 * Создал Dmitriy Yarmiychuk 15.01.2018
 */

public class FragmentFinish extends Fragment {

    private final int POINTS_FOR_RIGHT = 2, POINTS_FOR_PARTIALLY = 1;
    // Final results for quiz
    private int rightAnswersCount, partiallyAnswersCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_finish, container, false);

        // Define variables
        getFragmentArguments();

        // Prepare UI
        invalidateUI(view);

        return view;
    }

    /**
     * Get arguments from MainActivity
     */
    private void getFragmentArguments() {
        rightAnswersCount = getArguments().getInt(QuizHelper.KEY_COUNT_RIGHT_ANSWERS,
                QuizHelper.DEFAULT_POINTS_VALUE);
        partiallyAnswersCount = getArguments().getInt(QuizHelper.KEY_COUNT_PARTIALLY_ANSWERS,
                QuizHelper.DEFAULT_POINTS_VALUE);
    }

    /**
     * Set congratulation text
     *
     * @param view - root view of fragment
     */
    private void invalidateUI(View view) {
        // Define and calculate variables
        int maxPoints = QuizHelper.QUESTIONS_LIMIT * POINTS_FOR_RIGHT;
        int scorePoints = getScorePoints();
        String resultCaption = getString(R.string.congratulations);
        String resultMessage;
        if (scorePoints == maxPoints) {
            // User answered for all questions
            resultMessage = getString(R.string.all_questions);
        } else if (scorePoints > maxPoints / 2) {
            // User answered for most questions
            resultMessage = getString(R.string.more_half);
        } else if (scorePoints > 0) {
            // User answered for less questions
            resultCaption = getString(R.string.no_bad);
            resultMessage = getString(R.string.less_half);
        } else {
            // User is looser
            resultCaption = getString(R.string.incredible);
            resultMessage = getString(R.string.no_one);
        }

        // Invalidate UI
        view.findViewById(R.id.ll_result).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.tv_quiz_score)).setText(String.valueOf(scorePoints));
        // Set detailed result messages
        String rightAnswerResult = rightAnswersCount + "/" + QuizHelper.QUESTIONS_LIMIT;
        ((TextView) view.findViewById(R.id.tv_right_count)).setText(rightAnswerResult);
        String partiallyAnswerResult = partiallyAnswersCount + "/" + QuizHelper.QUESTIONS_LIMIT;
        ((TextView) view.findViewById(R.id.tv_partially_count)).setText(partiallyAnswerResult);
        // Set fragment caption
        ((TextView) view.findViewById(R.id.tv_quiz_title)).setText(resultCaption);
        ((TextView) view.findViewById(R.id.tv_quiz_message)).setText(resultMessage);
    }

    /**
     * Calculate total points for quiz
     *
     * @return score points
     */
    @Contract(pure = true)
    private int getScorePoints() {
        return rightAnswersCount * POINTS_FOR_RIGHT + partiallyAnswersCount * POINTS_FOR_PARTIALLY;
    }

}
