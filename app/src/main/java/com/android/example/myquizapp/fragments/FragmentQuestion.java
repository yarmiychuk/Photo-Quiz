package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.myquizapp.QuizHelper;
import com.android.example.myquizapp.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentQuestion extends Fragment implements View.OnClickListener {

    // Type of answer
    private int answerType;
    // Type of question
    private int questionType;
    // Number of current question
    private int questionNumber;
    // Work mode for save state
    private int questionMode;
    // Interface for connect to activity
    private AnswerListener listener;
    // Views
    private ImageView mQuestionIV;
    private TextView mQuestionTV, mAnswerTV;
    private LinearLayout mCheckBoxesLL, mEditTextLL;
    private CheckBox[] mAnswerCHB = new CheckBox[4];
    private RadioGroup mAnswerRG;
    private EditText mAnswerET;
    private Button mSubmitBTN, mWikiBTN;
    private RelativeLayout mQuestionRL, mAnswerRL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        // Variables
        defineVariables();
        // Get saved current state
        if (savedInstanceState != null) {
            getSavedState(savedInstanceState);
        }
        // Set question view
        initializeFragmentView(view);
        return view;
    }

    /**
     * Define fragment's variables
     */
    private void defineVariables() {
        listener = (AnswerListener) getActivity();
        questionNumber = getArguments().getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionMode = QuizHelper.MODE_QUESTION;
        answerType = QuizHelper.A_TYPE_WRONG;
    }

    /**
     * Get data from saved state
     *
     * @param state - saved instance state
     */
    private void getSavedState(Bundle state) {
        questionNumber = state.getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionMode = state.getInt(QuizHelper.QUESTION_MODE_KEY, QuizHelper.MODE_QUESTION);
        answerType = state.getInt(QuizHelper.ANSWER_TYPE_KEY, QuizHelper.A_TYPE_WRONG);
    }

    /**
     * Save current state of app
     *
     * @param outState - current state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuizHelper.QUESTION_MODE_KEY, questionMode);
        outState.putInt(QuizHelper.ARG_QUESTION, questionNumber);
        outState.putInt(QuizHelper.ANSWER_TYPE_KEY, answerType);
        super.onSaveInstanceState(outState);
    }

    /**
     * Prepare question image and text to show
     *
     * @param view - Root view of fragment
     */
    private void initializeFragmentView(View view) {
        // Check for wrong arguments
        if (questionNumber == QuizHelper.Q_INTRO) {
            // Error mode
            getActivity().finish();
        } else {
            // Invalidate fragment's views
            invalidateFragmentViews(view);
            // Set fragment question or answer fragment view
            if (questionMode == QuizHelper.MODE_QUESTION) {
                setQuestion();
            } else {
                setAnswer();
            }
        }
    }

    /**
     * Define this fragment's views
     *
     * @param view - root view
     */
    private void invalidateFragmentViews(View view) {
        // View for question image
        mQuestionIV = view.findViewById(R.id.iv_question_image);
        // Question layout
        mQuestionRL = view.findViewById(R.id.rl_question);
        mQuestionTV = view.findViewById(R.id.tv_question_text);
        mSubmitBTN = view.findViewById(R.id.btn_submit);
        // Answer's checkBoxes
        mCheckBoxesLL = view.findViewById(R.id.ll_answer_check_boxes);
        for (int i = 0; i < 4; i++) {
            mAnswerCHB[i] = (CheckBox) mCheckBoxesLL.getChildAt(i);
        }
        // Answer's RadioGroup
        mAnswerRG = view.findViewById(R.id.rg_answer_radio_buttons);
        mWikiBTN = view.findViewById(R.id.btn_wiki);
        // Answer's EditText
        mEditTextLL = view.findViewById(R.id.ll_answer_edit_text);
        mAnswerET = view.findViewById(R.id.et_answer);
        // Answer Layout
        mAnswerRL = view.findViewById(R.id.rl_answer_layout);
        mAnswerTV = view.findViewById(R.id.tv_answer_text);
        // Add listeners
        mSubmitBTN.setOnClickListener(this);
        mWikiBTN.setOnClickListener(this);
        // Hide unused elements
        prepareUI();
    }

    /**
     * Prepare UI question and answer elements to display
     */
    private void prepareUI() {
        switch (questionMode) {
            case QuizHelper.MODE_QUESTION:
                prepareUIForQuestion();
                break;
            case QuizHelper.MODE_ANSWER:
                prepareUIForAnswer();
                break;
        }
        hideKeyboard();
    }

    /**
     * Hide keyboard if it shows
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Prepare UI to show question view
     */
    private void prepareUIForQuestion() {
        mQuestionRL.setVisibility(View.VISIBLE);
        mSubmitBTN.setEnabled(true);
        mAnswerRL.setVisibility(View.INVISIBLE);
        mAnswerTV.setText("");
        mWikiBTN.setEnabled(false);
    }

    /**
     * Prepare UI to show answer view
     */
    private void prepareUIForAnswer() {
        mQuestionRL.setVisibility(View.INVISIBLE);
        mSubmitBTN.setEnabled(false);
        mQuestionTV.setText("");
        mAnswerRL.setVisibility(View.VISIBLE);
        mWikiBTN.setEnabled(true);
    }

    /**
     * Set Image, text and other views for question mode
     */
    private void setQuestion() {
        // Set image
        mQuestionIV.setImageResource(QuizHelper.getQuestionImage(getOrientation(), questionNumber));
        // Set text of question
        mQuestionTV.setText(QuizHelper.getQuestionText(getResources(), questionNumber));
        // Set type of question
        questionType = QuizHelper.getQuestionType(questionNumber);
        // Add views for different answer's variants
        addAnswerVariants();
    }

    /**
     * Get current device orientation
     * @return orientation
     */
    private int getOrientation() {
        return getResources().getConfiguration().orientation;
    }

    /**
     * Preparation of UI depending on the current question type.
     */
    private void addAnswerVariants() {
        if (questionType != QuizHelper.Q_TYPE_ERROR) {
            mCheckBoxesLL.setVisibility(View.GONE);
            mAnswerRG.setVisibility(View.GONE);
            mEditTextLL.setVisibility(View.GONE);
            switch (questionType) {
                case QuizHelper.Q_TYPE_CHECK_BOX:
                    showCheckBoxes();
                    break;
                case QuizHelper.Q_TYPE_RADIO_GROUP:
                    showRadioButtons();
                    break;
                case QuizHelper.Q_TYPE_INPUT:
                    mEditTextLL.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * Set Image and text for answer fragment's mode
     */
    private void setAnswer() {
        // Get current device orientation
        int orientation = getOrientation();
        // Set image
        mQuestionIV.setImageResource(QuizHelper.getQuestionImage(orientation, questionNumber));
        // Set text of answer
        String answer = QuizHelper.getStringTypeAnswer(getResources(), answerType) + " " +
                QuizHelper.getAnswerText(getResources(), orientation, questionNumber);
        mAnswerTV.setText(answer);
    }

    /**
     * Prepare and show checkBoxes
     */
    private void showCheckBoxes() {
        // Get answer text array
        String[] answers = QuizHelper.getAnswerVariants(getResources(), questionNumber);
        // And put it to CheckBoxes
        for (int i = 0; i < 4; i++) {
            mAnswerCHB[i].setText(answers[i]);
        }
        // Show Layout
        mCheckBoxesLL.setVisibility(View.VISIBLE);
    }

    /**
     * Prepare and show RadioButtons
     */
    private void showRadioButtons() {
        String[] answers = QuizHelper.getAnswerVariants(getResources(), questionNumber);
        for (int i = 0; i < 4; i++) {
            ((RadioButton) mAnswerRG.getChildAt(i)).setText(answers[i]);
        }
        // Show Layout
        mAnswerRG.setVisibility(View.VISIBLE);
    }

    /**
     * OnClickListener for Fragment's buttons
     *
     * @param v - the view was clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                onClickSubmit();
                break;
            case R.id.btn_wiki:
                onClickWiki();
                break;
        }
    }

    /**
     * Called when button Submit clicked
     */
    private void onClickSubmit() {
        hideKeyboard();
        questionMode = QuizHelper.MODE_ANSWER;
        checkCorrectness();
        if (listener != null) {
            listener.answerReceived(answerType);
        }
        showAnswerView();
    }

    /**
     * Called when button Wiki clicked
     */
    private void onClickWiki() {
        String link = QuizHelper.getLink(getResources(), questionNumber);
        if (link != null) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            } catch (Exception ex) {
                // Bad link
                badLink();
            }
        } else {
            // No link
            badLink();
        }
    }

    /**
     * Show message about bad link or unable to open it
     */
    private void badLink() {
        mWikiBTN.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), getString(R.string.error_link), Toast.LENGTH_SHORT).show();
    }

    /**
     * invalidate UI and set Image and show answer's image and text
     */
    private void showAnswerView() {
        prepareUI();
        setAnswer();
    }

    /**
     * Check answer and calculate points based on the answer correctness type
     */
    private void checkCorrectness() {
        answerType = QuizHelper.A_TYPE_WRONG;
        switch (questionType) {
            case QuizHelper.Q_TYPE_CHECK_BOX:
                checkCheckBoxAnswer();
                break;
            case QuizHelper.Q_TYPE_RADIO_GROUP:
                checkRadioGroupAnswer();
                break;
            case QuizHelper.Q_TYPE_INPUT:
                checkEditTextAnswer();
        }
    }

    /**
     * Define is answer correct and calculate score for checkBox type question
     */
    private void checkCheckBoxAnswer() {
        String[] rightAnswers = QuizHelper.getRightAnswers(getResources(), questionNumber);
        int rightChoice = getCHBRightChoice(rightAnswers);
        if (rightChoice == rightAnswers.length && getCHBWrongChoice(rightAnswers) == 0) {
            // Answer is correct, all CheckBox's in right position
            answerType = QuizHelper.A_TYPE_CORRECT;
        } else if (rightChoice > 0) {
            // Answer is partially correct
            answerType = QuizHelper.A_TYPE_PARTIALLY;
        }
    }

    /**
     * Calculate number of user's right answers
     *
     * @param answers - array of question's right answers
     * @return number of right answers
     */
    private int getCHBRightChoice(String[] answers) {
        int rightChoice = 0;
        for (int i = 0; i < 4; i++) {
            if (mAnswerCHB[i].isChecked() &&
                    isAnswersContains(answers, mAnswerCHB[i].getText().toString())) {
                rightChoice++;
            }
        }
        return rightChoice;
    }

    /**
     * Calculate number of user's wrong answers
     *
     * @param answers - array of question's right answers
     * @return number of wrong answers
     */
    private int getCHBWrongChoice(String[] answers) {
        int wrongChoice = 0;
        for (int i = 0; i < 4; i++) {
            CheckBox checkBox = mAnswerCHB[i];
            String checkBoxText = checkBox.getText().toString();
            Boolean isAnswerVariant = isAnswersContains(answers, checkBoxText);
            if (!checkBox.isChecked() && isAnswerVariant || checkBox.isChecked() && !isAnswerVariant) {
                wrongChoice++;
            }
        }
        return wrongChoice;
    }

    /**
     * Compare the correct answers with the text
     *
     * @param answers - Array of correct answers
     * @param text    - text to compare
     * @return is answers contain the text
     */
    private boolean isAnswersContains(String[] answers, String text) {
        for (String answer : answers) {
            if (answer.equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Define is answer correct and calculate score for RadioGroup type question
     */
    private void checkRadioGroupAnswer() {
        RadioButton button = mAnswerRG.findViewById(mAnswerRG.getCheckedRadioButtonId());
        if (button != null &&
                QuizHelper.getRightAnswer(getResources(), questionNumber)
                        .equals(button.getText().toString())) {
            answerType = QuizHelper.A_TYPE_CORRECT;
        }
    }

    /**
     * Define is answer correct and calculate score for EditText type question
     */
    private void checkEditTextAnswer() {
        String userAnswer = convertTextFromEditText();
        String rightAnswer = QuizHelper.getRightAnswer(getResources(), questionNumber).toUpperCase();
        if (userAnswer.equals(rightAnswer)) {
            answerType = QuizHelper.A_TYPE_CORRECT;
        }
    }

    /**
     * Remove unused space from text in EditText
     *
     * @return text without space at begin and end of text
     */
    @NonNull
    private String convertTextFromEditText() {
        String text = mAnswerET.getText().toString();
        while (text.length() > 0 && text.substring(0, 1).equals(" ")) {
            if (text.length() == 1) {
                text = "";
            } else {
                text = text.substring(1, text.length());
            }
        }
        while (text.length() > 0 && text.substring(text.length() - 1, text.length()).equals(" ")) {
            text = text.substring(0, text.length() - 1);
        }
        return text.toUpperCase();
    }

    public interface AnswerListener {
        void answerReceived(int scoreForAnswer);
    }
}
