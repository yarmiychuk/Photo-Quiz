package com.android.example.myquizapp;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.android.example.myquizapp.fragments.FragmentFinish;
import com.android.example.myquizapp.fragments.FragmentQuestion;
import com.android.example.myquizapp.fragments.FragmentIntro;

public class MainActivity extends AppCompatActivity {

    // Views
    private FrameLayout flContent;

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
        flContent = findViewById(R.id.content_frame);
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
        fragment.setArguments(getQuestionArgunents());
        // Replace Fragment
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        // Show fragment
        flContent.setVisibility(View.VISIBLE);
        flContent.startAnimation(animationIn);
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
    private Bundle getQuestionArgunents() {
        Bundle arguments = new Bundle();
        arguments.putInt(QuestionHelper.ARG_QUESTION, currentQuestion);

        return arguments;
    }

    public void onClickStart(View view) {
        currentQuestion++;
        setFragment();
    }
}
