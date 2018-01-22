package com.android.example.myquizapp;

import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.example.myquizapp.fragments.FragmentFinish;
import com.android.example.myquizapp.fragments.FragmentQuestion;
import com.android.example.myquizapp.fragments.FragmentIntro;

public class MainActivity extends AppCompatActivity
        implements FragmentQuestion.AnswerListener {

    // Views
    private ActionBar actionBar;
    private FrameLayout flContent;
    private Button btnNext;

    // Animations
    private Animation animationIn;

    // Constants and variables for fragments
    private final String LOG_TAG = "ActivityMain";
    private int currentQuestion, questionMode, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define views
        actionBar = getSupportActionBar();
        flContent = findViewById(R.id.content_frame);
        btnNext = findViewById(R.id.btn_next);

        // Define variables
        animationIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        currentQuestion = QuizHelper.Q_INTRO;
        score = QuizHelper.SCORE_DEFAULT;

        invalidateState(savedInstanceState);
    }

    private void invalidateState(Bundle state) {
        // Check for saved state (current fragment and score)
        if (state != null) {
            currentQuestion = state.getInt(QuizHelper.CURRENT_QUESTION, QuizHelper.Q_INTRO);
            questionMode = state.getInt(QuizHelper.QUESTION_MODE_KEY, QuizHelper.MODE_QUESTION);
            score = state.getInt(QuizHelper.SCORE_KEY, QuizHelper.SCORE_DEFAULT);
            invalidateButtonNext();
        } else {
            showButtonNext();
            setFragment();
        }

    }

    /**
     * TODO Save current state of app
     * @param outState - current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuizHelper.CURRENT_QUESTION, currentQuestion);
        outState.putInt(QuizHelper.SCORE_KEY, score);
        outState.putInt(QuizHelper.QUESTION_MODE_KEY, questionMode);
        super.onSaveInstanceState(outState);
    }

    /** TODO
     *
     */
    private void setFragment() {
        // Hide frame and button
        flContent.setVisibility(View.INVISIBLE);
        // Define Fragment
        Fragment fragment = getFragment();
        // Put arguments for question
        fragment.setArguments(getQuestionArguments());
        // Replace Fragment
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        // Show fragment
        flContent.setVisibility(View.VISIBLE);
        flContent.startAnimation(animationIn);
        // Change activity's title
        if (actionBar != null) {
            String title = getString(R.string.app_name);
            if (currentQuestion > 0 && currentQuestion <= QuizHelper.TOTAL_QUESTIONS) {
                title += ": " + currentQuestion + "/" + QuizHelper.TOTAL_QUESTIONS;
            }
            actionBar.setTitle(title);
        }
    }

    /**
     * TODO
     * @return
     */
    private Fragment getFragment() {
        Fragment fragment;
        switch (currentQuestion) {
            case QuizHelper.Q_INTRO:
                fragment = new FragmentIntro();
                showButtonNext();
                break;
            case QuizHelper.Q_FINISH:
                fragment = new FragmentFinish();
                showButtonNext();
                break;
            default:
                fragment = new FragmentQuestion();
        }
        return fragment;
    }

    /**
     *
     */
    private void showButtonNext() {
       questionMode = QuizHelper.MODE_ANSWER;
       invalidateButtonNext();
    }

    /**
     * TODO
     * @return arguments for question fragment
     */
    private Bundle getQuestionArguments() {
        Bundle arguments = new Bundle();
        arguments.putInt(QuizHelper.ARG_QUESTION, currentQuestion);
        return arguments;
    }

    /**
     * TODO
     * @param view
     */
    public void onClickNext(View view) {
        if (currentQuestion < QuizHelper.TOTAL_QUESTIONS) {
            questionMode = QuizHelper.MODE_QUESTION;
            invalidateButtonNext();
        }
        if (currentQuestion <= QuizHelper.TOTAL_QUESTIONS) {
            currentQuestion++;
            setFragment();
        } else {
            finish();
        }
    }

    /**
     * TODO Change "Next" button's text and visibility
     */
    private void invalidateButtonNext() {
        if (questionMode == QuizHelper.MODE_QUESTION) {
            btnNext.setVisibility(View.INVISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            // Invalidate text
            if (currentQuestion == QuizHelper.Q_INTRO) {
                btnNext.setText(getString(R.string.nav_start));
            } else if (currentQuestion == QuizHelper.Q_FINISH) {
                btnNext.setText(getString(R.string.nav_finish));
            } else {
                btnNext.setText(getString(R.string.nav_next));
            }
        }
    }

    /**
     * TODO
     * @param scoreForAnswer - value of score to add to total score
     */
    @Override
    public void answerReceived(int scoreForAnswer) {
        score += scoreForAnswer;
        showButtonNext();
    }
}
