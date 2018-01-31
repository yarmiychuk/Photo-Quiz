package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import org.jetbrains.annotations.Contract;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentQuestion extends Fragment implements View.OnClickListener {

    // Toast for error message
    Toast mToast;
    // Type of answer
    private int answerType;
    // Type of question
    private int questionType;
    // Number of current question
    private String[] answers;
    private int question, questionNumber;
    // Work mode for save state
    private int questionMode;
    // UI visibility
    private boolean isUIHidden;
    // Interface for connect to activity
    private AnswerListener listener;
    // Views
    private ImageView mQuestionIV, mAnswerIV;
    private TextView mQuestionTV, mAnswerTV;
    private LinearLayout mCheckBoxesLL, mEditTextLL;
    private CheckBox[] mAnswerCHB = new CheckBox[4];
    private RadioGroup mAnswerRG;
    private EditText mAnswerET;
    private Button mSubmitBTN, mWikiBTN;
    private RelativeLayout mQuestionRL, mAnswerRL;
    // Animation
    private Animation mAnimOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        // Variables
        initializeVariables();
        // Get saved state
        if (savedInstanceState != null) {
            getSavedState(savedInstanceState);
        }
        // Override Back Button
        addBackListener(view);
        // Set question's view
        initializeFragmentView(view);
        return view;
    }

    /**
     * Override Back Button to show hidden UI
     *
     * @param view - root view of Fragment
     */
    private void addBackListener(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && isUIHidden) {
                    // Button back is pressed and UI was hidden. Just show UI.
                    restoreUI();
                    return true;
                }
                // Usual click on Back button
                return false;
            }
        });
    }

    /**
     * Define fragment's variables
     */
    private void initializeVariables() {
        listener = (AnswerListener) getActivity();
        initializeAnimation();
        question = getArguments().getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionNumber = getArguments().getInt(QuizHelper.ARG_QUESTION_NUMBER, QuizHelper.Q_INTRO);
        questionMode = QuizHelper.MODE_QUESTION;
        answerType = QuizHelper.A_TYPE_WRONG;
        isUIHidden = false;
    }

    /**
     * Prepare animation for answer ImageView
     */
    private void initializeAnimation() {
        mAnimOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Not used
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnswerIV.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Not used
            }
        });
    }

    /**
     * Get data from saved state
     *
     * @param state - saved instance state
     */
    private void getSavedState(Bundle state) {
        question = state.getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionMode = state.getInt(QuizHelper.KEY_QUESTION_MODE, QuizHelper.MODE_QUESTION);
        answerType = state.getInt(QuizHelper.KEY_ANSWER_TYPE, QuizHelper.A_TYPE_WRONG);
        answers = state.getStringArray(QuizHelper.KEY_QUESTION_ANSWERS);
        questionNumber = state.getInt(QuizHelper.ARG_QUESTION_NUMBER, QuizHelper.Q_INTRO);
        isUIHidden = state.getBoolean(QuizHelper.KEY_UI_HIDDEN, false);
    }

    /**
     * Save current state of app
     *
     * @param outState - current state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(QuizHelper.KEY_QUESTION_MODE, questionMode);
        outState.putInt(QuizHelper.ARG_QUESTION, question);
        outState.putInt(QuizHelper.KEY_ANSWER_TYPE, answerType);
        outState.putStringArray(QuizHelper.KEY_QUESTION_ANSWERS, answers);
        outState.putInt(QuizHelper.ARG_QUESTION_NUMBER, questionNumber);
        outState.putBoolean(QuizHelper.KEY_UI_HIDDEN, isUIHidden);
        super.onSaveInstanceState(outState);
    }

    /**
     * Prepare question image and text to show
     *
     * @param view - Root view of fragment
     */
    private void initializeFragmentView(View view) {
        // Check for wrong arguments
        if (question == QuizHelper.Q_INTRO) {
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
        mAnswerIV = view.findViewById(R.id.iv_answer);
        mAnswerTV = view.findViewById(R.id.tv_answer_text);
        // Add listeners
        mQuestionIV.setOnClickListener(this);
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
        if (isUIHidden) {
            mQuestionRL.setVisibility(View.INVISIBLE);
        } else {
            mQuestionRL.setVisibility(View.VISIBLE);
        }
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
        if (isUIHidden) {
            mAnswerRL.setVisibility(View.INVISIBLE);
        } else {
            mAnswerRL.setVisibility(View.VISIBLE);
        }
        mWikiBTN.setEnabled(true);
    }

    /**
     * Set Image, text and other views for question mode
     */
    private void setQuestion() {
        // Set image
        mQuestionIV.setImageResource(QuizHelper.getQuestionImage(getOrientation(), question));
        // Set text of question
        String questionText = "";
        if (questionNumber > 0) {
            questionText = questionNumber + "/" + QuizHelper.QUESTIONS_LIMIT + ". ";
        }
        questionText += QuizHelper.getQuestionText(getResources(), question);
        mQuestionTV.setText(questionText);
        // Set type of question
        questionType = QuizHelper.getQuestionType(question);
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
                    showAnswersVariants(mCheckBoxesLL);
                    break;
                case QuizHelper.Q_TYPE_RADIO_GROUP:
                    showAnswersVariants(mAnswerRG);
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
        mQuestionIV.setImageResource(QuizHelper.getQuestionImage(orientation, question));
        // Set text of answer
        String answer = QuizHelper.getStringTypeAnswer(getResources(), answerType) + " " +
                QuizHelper.getAnswerText(getResources(), orientation, question);
        mAnswerTV.setText(answer);
    }

    /**
     * Show answer variants for CheckBox layout and RadioGroup
     * @param view Parent view
     */
    private void showAnswersVariants(LinearLayout view) {
        // Get answer text array
        if (answers == null) {
            // get new Array because it was not get from saved instance state
            answers = QuizHelper.getAnswerVariants(getResources(), question);
        }
        // And set text to view's child
        for (int i = 0; i < 4; i++) {
            ((TextView) view.getChildAt(i)).setText(answers[i]);
            mAnswerCHB[i].setText(answers[i]);
        }
        // Show Layout
        view.setVisibility(View.VISIBLE);
    }

    /**
     * OnClickListener for Fragment's views
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
            case R.id.iv_question_image:
                onClickImage();
                break;
        }
    }

    /**
     * Called when button Submit clicked
     */
    private void onClickSubmit() {
        hideKeyboard();
        if (isAnswerComplete()) {
            questionMode = QuizHelper.MODE_ANSWER;
            checkCorrectness();
            if (listener != null) {
                listener.answerReceived(answerType);
            }
            showAnswerView();
            showAnswerResult();
        }
    }

    /**
     * Check is user submit answer and answers is correct
     *
     * @return is answer correct
     */
    private boolean isAnswerComplete() {
        String message = null;
        switch (questionType) {
            case QuizHelper.Q_TYPE_CHECK_BOX:
                message = getMessageForCheckBox();
                break;
            case QuizHelper.Q_TYPE_RADIO_GROUP:
                if (mAnswerRG.getCheckedRadioButtonId() < 0) {
                    message = getString(R.string.need_answer);
                }
                break;
            case QuizHelper.Q_TYPE_INPUT:
                if (convertTextFromEditText().equals("")) {
                    message = getString(R.string.need_answer);
                }
                break;
        }
        if (message == null) {
            return true;
        }
        // Show error message
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();
        return false;
    }

    /**
     * Return message for Toast if CheckBoxes checked incorrect
     *
     * @return message for Toast
     */
    @Nullable
    private String getMessageForCheckBox() {
        // Check for all CheckBox is checked
        if (mAnswerCHB[0].isChecked() && mAnswerCHB[1].isChecked() &&
                mAnswerCHB[2].isChecked() && mAnswerCHB[3].isChecked()) {
            return getString(R.string.too_many_check_box);
        }

        if (!mAnswerCHB[0].isChecked() && !mAnswerCHB[1].isChecked() &&
                !mAnswerCHB[2].isChecked() && !mAnswerCHB[3].isChecked()) {
            return getString(R.string.need_answer);
        }
        return null;
    }

    /**
     * Called when button Wiki clicked
     */
    private void onClickWiki() {
        String link = QuizHelper.getLink(getResources(), question);
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
        String[] rightAnswers = QuizHelper.getRightAnswers(getResources(), question);
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
                QuizHelper.getRightAnswer(getResources(), question)
                        .equals(button.getText().toString())) {
            answerType = QuizHelper.A_TYPE_CORRECT;
        }
    }

    /**
     * Define is answer correct and calculate score for EditText type question
     */
    private void checkEditTextAnswer() {
        String userAnswer = convertTextFromEditText();
        String rightAnswer = QuizHelper.getRightAnswer(getResources(), question).toUpperCase();
        if (userAnswer.equals(rightAnswer) || (userAnswer + getString(R.string.the_article))
                .equals(rightAnswer)) {
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

    /**
     * Display an image that indicates the correctness of the user's response and Toast.
     */
    private void showAnswerResult() {
        mAnswerIV.setVisibility(View.VISIBLE);
        mAnswerIV.setImageResource(getAnswerImage());
        mAnswerIV.setBackground(getAnswerBackground());
        addAnimation();
        showScoreToast();
    }

    /**
     * Make text about user's score points for current answer for toast and show it
     */
    private void showScoreToast() {
        String toastMessage = getString(R.string.two_points);
        if (answerType == QuizHelper.A_TYPE_WRONG) {
            return;
        } else if (answerType == QuizHelper.A_TYPE_PARTIALLY) {
            toastMessage = getString(R.string.one_point);
        }
        Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Get image resource for answer ImageView
     *
     * @return image resource
     */
    @Contract(pure = true)
    private int getAnswerImage() {
        switch (answerType) {
            case QuizHelper.A_TYPE_WRONG:
                return R.mipmap.ic_close_black_48dp;
            default:
                return R.mipmap.ic_done_black_48dp;
        }
    }

    /**
     * Make background drawable with different color
     *
     * @return background drawable
     */
    private GradientDrawable getAnswerBackground() {
        // Make drawable
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{getAnswerColor(), getDarkAnswerColor()});
        // Define corners size
        float corner = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                getResources().getDimension(R.dimen.corners_size), getResources().getDisplayMetrics());
        float corners[] = new float[]{corner, corner, corner, corner, corner, corner, corner, corner};
        // Set corners
        drawable.setCornerRadii(corners);
        // Return drawable
        return drawable;
    }

    /**
     * Get light color resource for drawable
     *
     * @return color
     */
    @Contract(pure = true)
    private int getAnswerColor() {
        switch (answerType) {
            case QuizHelper.A_TYPE_CORRECT:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorGreenLight);
            case QuizHelper.A_TYPE_PARTIALLY:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorYellowLight);
            default:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorRedLight);
        }
    }

    /**
     * Get main color resource for drawable
     *
     * @return color
     */
    @Contract(pure = true)
    private int getDarkAnswerColor() {
        switch (answerType) {
            case QuizHelper.A_TYPE_CORRECT:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorGreen);
            case QuizHelper.A_TYPE_PARTIALLY:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorYellow);
            default:
                return ContextCompat.getColor(getActivity().getBaseContext(), R.color.colorRed);
        }
    }

    /**
     * Add delayed animation to answer ImageView
     */
    private void addAnimation() {
        mAnswerIV.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mAnswerIV.startAnimation(mAnimOut);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }, 1000);
    }

    /**
     * Hide or show UI when user clicks question image
     */
    private void onClickImage() {
        hideKeyboard();
        if (isUIHidden) {
            restoreUI();
        } else {
            isUIHidden = true;
            mQuestionRL.setVisibility(View.INVISIBLE);
            mAnswerRL.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Show UI after it was hidden
     */
    private void restoreUI() {
        isUIHidden = false;
        switch (questionMode) {
            case QuizHelper.MODE_ANSWER:
                mAnswerRL.setVisibility(View.VISIBLE);
                break;
            case QuizHelper.MODE_QUESTION:
                mQuestionRL.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface AnswerListener {
        void answerReceived(int scoreForAnswer);
    }
}
