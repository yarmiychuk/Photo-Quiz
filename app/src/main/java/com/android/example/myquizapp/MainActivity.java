package com.android.example.myquizapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.example.myquizapp.fragments.FragmentFinish;
import com.android.example.myquizapp.fragments.FragmentQuestion;
import com.android.example.myquizapp.fragments.FragmentIntro;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements FragmentQuestion.AnswerListener {

    // Views
    private Toolbar toolbar;
    //private ActionBar actionBar;
    private FrameLayout flContent;
    private Button btnNext;

    // Animations
    private Animation animationIn;

    // Constants and variables for fragments
    private int currentQuestion, questionMode, score;
    private ArrayList<Integer> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        initializeVariables();

        invalidateState(savedInstanceState);
    }

    // Define views
    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        flContent = findViewById(R.id.content_frame);
        btnNext = findViewById(R.id.btn_next);
    }

    // Define variables
    private void initializeVariables() {
        animationIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        currentQuestion = QuizHelper.Q_INTRO;
        score = QuizHelper.SCORE_DEFAULT;
        questionsList = new ArrayList<>();
    }

    /**
     * Check for saved state (current fragment and score)
     * @param state - saved state
     */
    private void invalidateState(Bundle state) {
        if (state != null) {
            currentQuestion = state.getInt(QuizHelper.KEY_CURRENT_QUESTION, QuizHelper.Q_INTRO);
            questionMode = state.getInt(QuizHelper.KEY_QUESTION_MODE, QuizHelper.MODE_QUESTION);
            score = state.getInt(QuizHelper.KEY_SCORE, QuizHelper.SCORE_DEFAULT);
            questionsList = state.getIntegerArrayList(QuizHelper.KEY_QUESTION_LIST);
            if (questionsList == null) {
                questionsList = new ArrayList<>();
            }
            invalidateButtonNext();
        } else {
            showButtonNext();
            setFragment();
        }
        invalidateToolbar();
    }

    /**
     * Save current state of app
     * @param outState - current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuizHelper.KEY_CURRENT_QUESTION, currentQuestion);
        outState.putInt(QuizHelper.KEY_SCORE, score);
        outState.putInt(QuizHelper.KEY_QUESTION_MODE, questionMode);
        outState.putIntegerArrayList(QuizHelper.KEY_QUESTION_LIST, questionsList);
        super.onSaveInstanceState(outState);
    }

    /**
     * Set some fragment to FrameLayout
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
        // Show or hide Toolbar
        invalidateToolbar();
    }

    /**
     * Show or hide app toolbar
     */
    private void invalidateToolbar() {
        if (currentQuestion > 0 && currentQuestion <= QuizHelper.QUESTIONS_TOTAL) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Fragment getter
     * @return fragment for current app state
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
     * Just show Next button
     */
    private void showButtonNext() {
       questionMode = QuizHelper.MODE_ANSWER;
       invalidateButtonNext();
    }

    /**
     * Make arguments for fragment
     * @return arguments for question fragment
     */
    private Bundle getQuestionArguments() {
        Bundle arguments = new Bundle();
        if (currentQuestion < QuizHelper.Q_FINISH) {
            arguments.putInt(QuizHelper.ARG_QUESTION, currentQuestion);
            arguments.putInt(QuizHelper.ARG_QUESTION_NUMBER, questionsList.size());
        } else if (currentQuestion > QuizHelper.Q_INTRO) {
            arguments.putInt(QuizHelper.ARG_RESULT, score);
        }
        return arguments;
    }

    /**
     * OnClick listener
     * @param view - The View was clicked
     */
    public void onClickNext(View view) {
        questionMode = QuizHelper.MODE_QUESTION;
        invalidateButtonNext();
        if (questionsList.size() < QuizHelper.QUESTION_LIMIT) {
            getNewQuestion();
        } else if (questionsList.size() == QuizHelper.QUESTION_LIMIT) {
            if (currentQuestion == QuizHelper.Q_FINISH) {
                startAgain();
            } else {
                currentQuestion = QuizHelper.Q_FINISH;
                setFragment();
            }
        }
    }

    /**
     * Start a new quiz
     */
    private void startAgain() {
        initializeVariables();
        getNewQuestion();
    }

    /**
     * Prepare variables for next random question and show it
     */
    private void getNewQuestion() {
        int question = 0;
        while (question == 0 || questionsList.contains(question)) {
            question = new Random().nextInt(QuizHelper.QUESTIONS_TOTAL) + 1;
        }
        currentQuestion = question;
        questionsList.add(currentQuestion);
        setFragment();
    }

    /**
     * Change "Next" button's text and visibility
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
                btnNext.setText(getString(R.string.nav_again));
            } else {
                btnNext.setText(getString(R.string.nav_next));
            }
        }
    }

    /**
     * Receive answer's points for current question
     * @param scoreForAnswer - value of score to add to total score
     */
    @Override
    public void answerReceived(int scoreForAnswer) {
        score += scoreForAnswer;
        showButtonNext();
    }

    @Override
    public void onBackPressed() {
        if (currentQuestion > QuizHelper.Q_INTRO && currentQuestion < QuizHelper.Q_FINISH) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(getString(R.string.dialog_title));
            builder.setMessage(getString(R.string.dialog_message));
            builder.setNegativeButton(getString(R.string.pause), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Hide app (pause)
                    moveTaskToBack(true);
                }
            });
            builder.setPositiveButton(getString(R.string.cancel), null);
            builder.setNeutralButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }
}
