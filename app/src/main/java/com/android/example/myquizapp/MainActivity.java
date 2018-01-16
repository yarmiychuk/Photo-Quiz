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

public class MainActivity extends AppCompatActivity {

    // Views
    private ActionBar actionBar;
    private FrameLayout flContent;
    private Button btnNext;

    // Animations
    private Animation animationIn;

    // Constants and variables for fragments
    private final String LOG_TAG = "ActivityMain";
    private int currentQuestion;

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

        // Define fragment to show
        currentQuestion = QuestionHelper.Q_INTRO;
        if (savedInstanceState != null) {
            currentQuestion = savedInstanceState.getInt(
                    QuestionHelper.CURRENT_QUESTION, QuestionHelper.Q_INTRO);
        }
        setFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Save current state of app
     * @param outState - current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "Save App State");
        Log.d(LOG_TAG, "currentQuestion - " + currentQuestion);
        outState.putInt(QuestionHelper.CURRENT_QUESTION, currentQuestion);
    }

    /** TODO
     *
     */
    private void setFragment() {
        // Hide frame
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
            if (currentQuestion > 0 && currentQuestion <= QuestionHelper.TOTAL_QUESTIONS) {
                title += ": " + currentQuestion + "/" + QuestionHelper.TOTAL_QUESTIONS;
            }
            actionBar.setTitle(title);
        }
        // TODO Remove later
        setTextButtonNext();
        btnNext.setVisibility(View.VISIBLE);
    }

    /**
     * TODO
     * @return
     */
    private Fragment getFragment() {
        Fragment fragment;
        switch (currentQuestion) {
            case QuestionHelper.Q_INTRO:
                fragment = new FragmentIntro();
                break;
            case QuestionHelper.Q_FINISH:
                fragment = new FragmentFinish();
                break;
            default:
                fragment = new FragmentQuestion();
        }
        return fragment;
    }

    /**
     * TODO
     * @return
     */
    private Bundle getQuestionArguments() {
        Bundle arguments = new Bundle();
        arguments.putInt(QuestionHelper.ARG_QUESTION, currentQuestion);

        return arguments;
    }

    // TODO
    public void onClickNext(View view) {
        btnNext.setVisibility(View.INVISIBLE);
        setTextButtonNext();
        if (currentQuestion <= QuestionHelper.TOTAL_QUESTIONS) {
            currentQuestion++;
            //flContent.setVisibility(View.INVISIBLE);
            setFragment();
        } else {
            finish();
        }
    }

    /**
     * TODO Change "Next" button's text
     */
    private void setTextButtonNext() {
        // Invalidate text
        if (currentQuestion == QuestionHelper.Q_INTRO) {
            btnNext.setText(getString(R.string.nav_start));
        } else if (currentQuestion == QuestionHelper.Q_FINISH) {
            btnNext.setText(getString(R.string.nav_finish));
        } else {
            btnNext.setText(getString(R.string.nav_next));
        }
    }
}
