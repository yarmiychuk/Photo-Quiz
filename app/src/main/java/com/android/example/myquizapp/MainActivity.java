package com.android.example.myquizapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.example.myquizapp.fragments.FinishFragment;
import com.android.example.myquizapp.fragments.QuestionFragment;
import com.android.example.myquizapp.fragments.IntroFragment;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements QuestionFragment.AnswerListener {

    // Views
    private Toolbar mToolbar;
    private FrameLayout mContentFrame;
    private Button mNextButton;

    private Toast mToast;

    // Animation
    private Animation mAnimationIn;

    // Constants and variables for fragments
    private int currentQuestion, questionMode, rightAnswersCount, partiallyAnswersCount;
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
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mContentFrame = findViewById(R.id.content_frame);
        mNextButton = findViewById(R.id.btn_next);
    }

    // Define variables
    private void initializeVariables() {
        mAnimationIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        currentQuestion = QuizHelper.Q_INTRO;
        rightAnswersCount = QuizHelper.DEFAULT_POINTS_VALUE;
        partiallyAnswersCount = QuizHelper.DEFAULT_POINTS_VALUE;
        questionsList = new ArrayList<>();
    }

    /**
     * Check for saved state (current fragment and score)
     *
     * @param state - saved state
     */
    private void invalidateState(Bundle state) {
        if (state != null) {
            currentQuestion = state.getInt(QuizHelper.KEY_CURRENT_QUESTION, QuizHelper.Q_INTRO);
            questionMode = state.getInt(QuizHelper.KEY_QUESTION_MODE, QuizHelper.MODE_QUESTION);
            rightAnswersCount = state.getInt(QuizHelper.KEY_COUNT_RIGHT_ANSWERS,
                    QuizHelper.DEFAULT_POINTS_VALUE);
            partiallyAnswersCount = state.getInt(QuizHelper.KEY_COUNT_PARTIALLY_ANSWERS,
                    QuizHelper.DEFAULT_POINTS_VALUE);
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
     *
     * @param outState - current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuizHelper.KEY_CURRENT_QUESTION, currentQuestion);
        outState.putInt(QuizHelper.KEY_COUNT_RIGHT_ANSWERS, rightAnswersCount);
        outState.putInt(QuizHelper.KEY_COUNT_PARTIALLY_ANSWERS, partiallyAnswersCount);
        outState.putInt(QuizHelper.KEY_QUESTION_MODE, questionMode);
        outState.putIntegerArrayList(QuizHelper.KEY_QUESTION_LIST, questionsList);
        super.onSaveInstanceState(outState);
    }

    /**
     * Set some fragment to FrameLayout
     */
    private void setFragment() {
        // Hide frame and button
        mContentFrame.setVisibility(View.INVISIBLE);
        // Define Fragment
        Fragment fragment = getFragment();
        // Put arguments for question
        fragment.setArguments(getQuestionArguments());
        // Replace Fragment
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        // Show fragment
        mContentFrame.setVisibility(View.VISIBLE);
        mContentFrame.startAnimation(mAnimationIn);
        // Show or hide Toolbar
        invalidateToolbar();
    }

    /**
     * Show or hide app toolbar
     */
    private void invalidateToolbar() {
        if (currentQuestion > 0 && currentQuestion <= QuizHelper.QUESTIONS_TOTAL) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Fragment getter
     *
     * @return fragment for current app state
     */
    private Fragment getFragment() {
        Fragment fragment;
        switch (currentQuestion) {
            case QuizHelper.Q_INTRO:
                fragment = new IntroFragment();
                showButtonNext();
                break;
            case QuizHelper.Q_FINISH:
                fragment = new FinishFragment();
                showButtonNext();
                break;
            default:
                fragment = new QuestionFragment();
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
     *
     * @return arguments for question fragment
     */
    private Bundle getQuestionArguments() {
        Bundle arguments = new Bundle();
        if (currentQuestion < QuizHelper.Q_FINISH) {
            arguments.putInt(QuizHelper.KEY_QUESTION, currentQuestion);
            arguments.putInt(QuizHelper.KEY_QUESTION_NUMBER, questionsList.size());
        } else if (currentQuestion > QuizHelper.Q_INTRO) {
            arguments.putInt(QuizHelper.KEY_COUNT_RIGHT_ANSWERS, rightAnswersCount);
            arguments.putInt(QuizHelper.KEY_COUNT_PARTIALLY_ANSWERS, partiallyAnswersCount);
        }
        return arguments;
    }

    /**
     * OnClick listener
     *
     * @param view - The View was clicked
     */
    public void onClickNext(View view) {
        questionMode = QuizHelper.MODE_QUESTION;
        invalidateButtonNext();
        if (questionsList.size() < QuizHelper.QUESTIONS_LIMIT) {
            getNewQuestion();
        } else if (questionsList.size() == QuizHelper.QUESTIONS_LIMIT) {
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
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mNextButton.setVisibility(View.VISIBLE);
            // Invalidate text
            if (currentQuestion == QuizHelper.Q_INTRO) {
                mNextButton.setText(getString(R.string.nav_start));
            } else if (currentQuestion == QuizHelper.Q_FINISH) {
                mNextButton.setText(getString(R.string.nav_again));
            } else {
                mNextButton.setText(getString(R.string.nav_next));
            }
        }
    }

    /**
     * Share quiz result
     *
     * @param view - Share button
     */
    public void shareResult(View view) {
        // Make share intent
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getShareMessage());

        // Verify that the intent will resolve to an activity
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            // Start chooser
            startActivity(Intent.createChooser(shareIntent, getString(R.string.chooser_title)));
        } else {
            // Show toast
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(MainActivity.this,
                    getString(R.string.share_impossible),
                    Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * Make text for share message
     *
     * @return text for message
     */
    @NonNull
    private String getShareMessage() {
        int totalScore = rightAnswersCount * QuizHelper.POINTS_FOR_RIGHT
                + partiallyAnswersCount * QuizHelper.POINTS_FOR_PARTIALLY;
        // Total score result
        String message = getString(R.string.share_title) + "\n"
                + getString(R.string.share_result) + " " + totalScore + " ";
        switch (totalScore) {
            case 1:
                message += getString(R.string.share_points_one);
                break;
            case 2:
            case 3:
            case 4:
                message += getString(R.string.share_points_two_three_four);
                break;
            default:
                message += getString(R.string.share_points);
        }
        // Right answers
        message += "\n" + getString(R.string.correct_answers_result) + " " + rightAnswersCount;
        // Partially answers
        message += "\n" + getString(R.string.partially_correct_answers_result)
                + " " + partiallyAnswersCount;
        return message;
    }

    /**
     * Receive answer's points for current question
     *
     * @param typeOfAnswer - type of user answer (correct, partially, wrong)
     */
    @Override
    public void answerReceived(int typeOfAnswer) {
        switch (typeOfAnswer) {
            case QuizHelper.A_TYPE_CORRECT:
                rightAnswersCount++;
                break;
            case QuizHelper.A_TYPE_PARTIALLY:
                partiallyAnswersCount++;
                break;
        }
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
