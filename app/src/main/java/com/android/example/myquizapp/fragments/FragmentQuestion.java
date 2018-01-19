package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.example.myquizapp.QuizHelper;
import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentQuestion extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = "FragmentQuestion";
    private final String QUESTION_MODE_KEY = "questionMode";
    private final int MODE_QUESTION = 0, MODE_ANSWER = 1;
    private final String ANSWER_TYPE_KEY = "answerType";
    private final int Q_TYPE_ERROR = 0,
            Q_TYPE_RADIO_GROUP = 1, Q_TYPE_CHECK_BOX = 2, Q_TYPE_INPUT = 3;
    private final int A_TYPE_WRONG = 0, A_TYPE_PARTIALLY = 1, A_TYPE_CORRECT = 2;
    private int questionNumber;
    // Work mode for save state
    private int questionMode;
    // Type of question
    private int questionType;
    // Type of answer
    private int answerType;
    // Interface for connect to activity
    private AnswerListener listener;
    // Views
    private ImageView ivQuestion, ivAnswer;
    private TextView tvQuestion, tvAnswer;
    private CheckBox chbAnswer0, chbAnswer1, chbAnswer2, chbAnswer3;
    private RadioGroup rgAnswers;
    private EditText etAnswer;
    private Button btnSubmit, btnWiki;
    private RelativeLayout rlAnswer;

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
     * TODO Define fragment's variables
     */
    private void defineVariables() {
        listener = (AnswerListener) getActivity();
        questionNumber = getArguments().getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionMode = MODE_QUESTION;
        answerType = A_TYPE_WRONG;
    }

    /**
     * Get data from saved state
     *
     * @param state - saved instance state
     */
    private void getSavedState(Bundle state) {
        // TODO
        questionNumber = state.getInt(QuizHelper.ARG_QUESTION, QuizHelper.Q_INTRO);
        questionMode = state.getInt(QUESTION_MODE_KEY, MODE_QUESTION);
        answerType = state.getInt(ANSWER_TYPE_KEY, A_TYPE_WRONG);
    }

    /**
     * TODO Save current state of app
     *
     * @param outState - current state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, "Save question State");
        outState.putInt(QUESTION_MODE_KEY, questionMode);
        outState.putInt(QuizHelper.ARG_QUESTION, questionNumber);
        outState.putInt(ANSWER_TYPE_KEY, answerType);
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
            if (questionMode == MODE_QUESTION) {
                setQuestion(view);
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
        // TODO
        ivQuestion = view.findViewById(R.id.iv_question_image);
        tvQuestion = view.findViewById(R.id.tv_question_text);
        btnSubmit = view.findViewById(R.id.btn_submit);
        rlAnswer = view.findViewById(R.id.rl_answer_layout);
        ivAnswer = view.findViewById(R.id.iv_answer_image);
        tvAnswer = view.findViewById(R.id.tv_answer_text);
        btnWiki = view.findViewById(R.id.btn_wiki);
        // Add listeners
        btnSubmit.setOnClickListener(this);
        btnWiki.setOnClickListener(this);
        // Hide unused elements
        hideUnusedElements();
    }

    /**
     * Hide unused elements
     */
    private void hideUnusedElements() {
        switch (questionMode) {
            case MODE_ANSWER:
                // TODO
                btnSubmit.setEnabled(false);
                ivQuestion.setImageDrawable(null);
                tvQuestion.setText("");
                rlAnswer.setVisibility(View.VISIBLE);
                btnWiki.setEnabled(true);
                break;
            case MODE_QUESTION:
                // TODO
                btnSubmit.setEnabled(true);
                rlAnswer.setVisibility(View.INVISIBLE);
                ivAnswer.setImageDrawable(null);
                tvAnswer.setText("");
                btnWiki.setEnabled(false);
                break;
        }
    }

    /**
     * Set Image, text and other views for question mode
     *
     * @param view - root view of fragment
     */
    private void setQuestion(View view) {
        // Set image
        ivQuestion.setImageResource(getQuestionImage());
        // Set text of question
        tvQuestion.setText(getQuestionText());
        // Set type of question
        questionType = getQuestionType();
        // Add views for different answer's variants
        addAnswerVariants(view);
    }

    /**
     * TODO Get image for question
     *
     * @return image for question
     */
    private int getQuestionImage() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return R.drawable.actinia_square;
            case QuizHelper.Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_square;
            case QuizHelper.Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_square;
            case QuizHelper.Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_square;
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * TODO Get text of questions
     *
     * @return text for question
     */
    private String getQuestionText() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getString(R.string.question_actinia);
            case QuizHelper.Q_CABO_DA_ROCA:
                return getString(R.string.question_cabo_da_roca);
            case QuizHelper.Q_GENERAL_SHERMAN:
                return getString(R.string.question_general_sherman);
            case QuizHelper.Q_QUINTA_DA_REGALEIRA:
                return getString(R.string.question_quinta_da_regaleira);
        }
        return getString(R.string.error_question);
    }

    /**
     * TODO
     *
     * @return type of answer format (single choice, multi choice, input)
     */
    private int getQuestionType() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return Q_TYPE_CHECK_BOX;
            case QuizHelper.Q_CABO_DA_ROCA:
                return Q_TYPE_RADIO_GROUP;
        }
        return Q_TYPE_ERROR;
    }

    /**
     * TODO
     *
     * @param view - Root view of fragment
     */
    private void addAnswerVariants(View view) {
        if (questionType != Q_TYPE_ERROR) {
            // TODO Define elements
            RelativeLayout rlAnswers = view.findViewById(R.id.rl_answers);
            LinearLayout llCheckBoxes = rlAnswers.findViewById(R.id.ll_answer_check_boxes);
            rgAnswers = rlAnswers.findViewById(R.id.rg_answer_radio_buttons);
            // TODO Hide elements
            llCheckBoxes.setVisibility(View.INVISIBLE);
            rgAnswers.setVisibility(View.INVISIBLE);
            switch (questionType) {
                case Q_TYPE_CHECK_BOX:
                    showCheckBoxes(llCheckBoxes);
                    break;
                case Q_TYPE_RADIO_GROUP:
                    showRadioButtons();
                    break;
                case Q_TYPE_INPUT:
                    // TODO
                    break;
            }
        }
    }

    /**
     * Set Image and text for answer fragment's mode
     */
    private void setAnswer() {
        // Get current device orientation
        int orientation = getResources().getConfiguration().orientation;
        // Set image
        ivAnswer.setImageResource(getAnswerImage(orientation));
        // Set text of answer
        String answer = getStringTypeAnswer() + " " + getAnswerText(orientation);
        tvAnswer.setText(answer);
    }

    /**
     * Get image for answer's ImageView
     *
     * @param deviceOrientation - current device orientation
     * @return image for answer
     */
    private int getAnswerImage(int deviceOrientation) {
        switch (deviceOrientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return getPortraitAnswerImage();
            case Configuration.ORIENTATION_LANDSCAPE:
                return getLandscapeAnswerImage();
            default:
                // Return squared image
                return getQuestionImage();
        }
    }

    /**
     * TODO Get image for answer in portrait orientation
     *
     * @return portrait image for answer
     */
    private int getPortraitAnswerImage() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return R.drawable.actinia_portrait;
            case QuizHelper.Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_portrait;
        }
        return 0;
    }

    /**
     * TODO Get image for answer in portrait orientation
     *
     * @return landscape image for answer
     */
    private int getLandscapeAnswerImage() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return R.drawable.actinia_landscape;
            case QuizHelper.Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_landscape;
        }
        return 0;
    }

    /**
     * Get text based on answer
     *
     * @return text about correct, wrong or incorrect answer
     */
    private String getStringTypeAnswer() {
        Log.d(LOG_TAG, "Answer type - " + answerType);
        switch (answerType) {
            case A_TYPE_CORRECT:
                return getString(R.string.correct_answer);
            case A_TYPE_PARTIALLY:
                return getString(R.string.partially_answer);
        }
        return getString(R.string.wrong_answer);
    }

    /**
     * Get Text for answer
     *
     * @param deviceOrientation - current device orientation
     * @return text for answer TextView
     */
    private String getAnswerText(int deviceOrientation) {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getDefaultAnswerText();
            // TODO
            default:
                return getSpecialAnswerText(deviceOrientation);
        }
    }

    /**
     * Get answer text for unknown orientation or for any orientation
     *
     * @return default answer text.
     */
    private String getDefaultAnswerText() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getString(R.string.description_actinia);

            // TODO
        }
        return "";
    }

    /**
     * Get text based on the device orientstion
     *
     * @param orientation of device
     * @return text based on the device orientation
     */
    private String getSpecialAnswerText(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return getPortraitAnswerText();
            case Configuration.ORIENTATION_LANDSCAPE:
                return getLandscapeAnswerText();
            default:
                // Return default text
                return getDefaultAnswerText();
        }
    }

    /**
     * TODO Get answer text for portrait orientation
     *
     * @return answer text for portrait orientation
     */
    private String getPortraitAnswerText() {
        return "";
    }

    /**
     * TODO Get answer text for landscape orientation
     *
     * @return answer text for landscape orientation
     */
    private String getLandscapeAnswerText() {
        return "";
    }

    /**
     * Prepare and show checkBoxes
     *
     * @param layout - root view for checkBoxes
     */
    private void showCheckBoxes(LinearLayout layout) {
        // Define check boxes
        chbAnswer0 = layout.findViewById(R.id.chb_answer_0);
        chbAnswer1 = layout.findViewById(R.id.chb_answer_1);
        chbAnswer2 = layout.findViewById(R.id.chb_answer_2);
        chbAnswer3 = layout.findViewById(R.id.chb_answer_3);
        // ... and set text to them
        String[] answers = getAnswerVariants();
        if (answers != null) {
            chbAnswer0.setText(answers[0]);
            chbAnswer1.setText(answers[1]);
            chbAnswer2.setText(answers[2]);
            chbAnswer3.setText(answers[3]);
        } else {
            String textError = getString(R.string.error);
            chbAnswer0.setText(textError);
            chbAnswer1.setText(textError);
            chbAnswer2.setText(textError);
            chbAnswer3.setText(textError);
        }
        // Show Layout
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * Prepare and show RadioButtons
     */
    private void showRadioButtons() {
        // Define radio group
        RadioButton rbAnswer0 = rgAnswers.findViewById(R.id.rb_answer_0);
        RadioButton rbAnswer1 = rgAnswers.findViewById(R.id.rb_answer_1);
        RadioButton rbAnswer2 = rgAnswers.findViewById(R.id.rb_answer_2);
        RadioButton rbAnswer3 = rgAnswers.findViewById(R.id.rb_answer_3);
        // >>> and set text to it's child elements
        String[] answers = getAnswerVariants();
        if (answers != null) {
            rbAnswer0.setText(answers[0]);
            rbAnswer1.setText(answers[1]);
            rbAnswer2.setText(answers[2]);
            rbAnswer3.setText(answers[3]);
        } else {
            String textError = getString(R.string.error);
            rbAnswer0.setText(textError);
            rbAnswer1.setText(textError);
            rbAnswer2.setText(textError);
            rbAnswer3.setText(textError);
        }
        // Show Layout
        rgAnswers.setVisibility(View.VISIBLE);
    }

    /**
     * TODO
     *
     * @return array of answer variants for question
     */
    private String[] getAnswerVariants() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getResources().getStringArray(R.array.variants_actinia);
            case QuizHelper.Q_CABO_DA_ROCA:
                return getResources().getStringArray(R.array.variants_cabo_da_roca);
        }
        questionType = Q_TYPE_ERROR;
        return null;
    }

    // TODO
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                questionMode = MODE_ANSWER;
                checkCorrectness();
                if (listener != null) {
                    listener.hasAnswer(answerType);
                }
                showAnswerView();
                break;
            case R.id.btn_wiki:
                String link = getLink();
                if (link != null) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    } catch (Exception ex) {
                        badLink();
                    }
                } else {
                    // No link
                    badLink();
                }
                break;
        }
    }

    /**
     * Get link for wiki page
     *
     * @return link
     */
    private String getLink() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getString(R.string.link_actinia);
            case QuizHelper.Q_CABO_DA_ROCA:
                return getString(R.string.link_cabo_da_roca);
            // TODO
        }
        return null;
    }

    /**
     * TODO
     */
    private void badLink() {

    }

    /**
     * invalidate UI and set Image and show answer's image and text
     */
    private void showAnswerView() {
        hideUnusedElements();
        setAnswer();
    }

    /**
     * Check answer and calculate points based on the answer correctness type
     */
    private void checkCorrectness() {
        answerType = A_TYPE_WRONG;
        switch (questionType) {
            case Q_TYPE_CHECK_BOX:
                checkCheckBoxAnswer();
                break;
            case Q_TYPE_RADIO_GROUP:
                checkRadioGroupAnswer();
                break;
            case Q_TYPE_INPUT:
                checkEditTextAnswer();
        }
    }

    /**
     * Define is answer correct and calculate score for checkBox type question
     */
    private void checkCheckBoxAnswer() {
        String[] rightAnswers = getRightAnswers();
        int score = 0;
        if (rightAnswers != null) {
            for (String answer : rightAnswers) {
                if (checkCheckBox(chbAnswer0, answer) || checkCheckBox(chbAnswer1, answer) ||
                        checkCheckBox(chbAnswer2, answer) || checkCheckBox(chbAnswer3, answer)) {
                    score++;
                }
            }
            // Define is answer correct
            if (score == rightAnswers.length) {
                answerType = A_TYPE_CORRECT;
            } else if (score > 0 && score < rightAnswers.length) {
                answerType = A_TYPE_PARTIALLY;
            }
        }
    }

    /**
     * Check for right answer for CheckBox
     *
     * @param checkBox - to check
     * @param answer   - text value to check
     * @return answer is right
     */
    private boolean checkCheckBox(CheckBox checkBox, String answer) {
        return checkBox.isChecked() && checkBox.getText().toString().equals(answer);
    }

    /**
     * Define is answer correct and calculate score for RadioGroup type question
     */
    private void checkRadioGroupAnswer() {
        String rightAnswer = getRightAnswer();
        int radioButtonId = rgAnswers.getCheckedRadioButtonId();
        String userAnswer = ((RadioButton) rgAnswers.findViewById(radioButtonId)).getText().toString();
        if (rightAnswer != null && rightAnswer.equals(userAnswer)) {
            answerType = A_TYPE_CORRECT;
        }
    }

    /**
     * TODO
     */
    private void checkEditTextAnswer() {

    }

    /**
     * TODO
     *
     * @return list of right answers for question
     */
    private String[] getRightAnswers() {
        switch (questionNumber) {
            case QuizHelper.Q_ACTINIA:
                return getResources().getStringArray(R.array.answers_actinia);
        }
        questionType = Q_TYPE_ERROR;
        return null;
    }

    /**
     * TODO
     *
     * @return right answer for single-item choice question
     */
    private String getRightAnswer() {
        switch (questionNumber) {
            case QuizHelper.Q_CABO_DA_ROCA:
                return getString(R.string.answer_cabo_da_roca);
        }
        questionType = Q_TYPE_ERROR;
        return null;
    }

    public interface AnswerListener {
        void hasAnswer(int scoreForQuestion);
    }
}
