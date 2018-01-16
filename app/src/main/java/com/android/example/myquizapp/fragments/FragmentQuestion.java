package com.android.example.myquizapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.example.myquizapp.QuestionHelper;
import com.android.example.myquizapp.R;

/**
 * Created by Dmitriy Yarmiychuk on 14.01.2018.
 * Создал Dmitriy Yarmiychuk 14.01.2018
 */

public class FragmentQuestion extends Fragment implements View.OnClickListener {

    private int questionNumber, questionType;
    private final int Q_TYPE_ERROR = 0,
            Q_TYPE_RADIO_GROUP = 1, Q_TYPE_CHECK_BOX = 2, Q_TYPE_INPUT = 3;

    private AnswerListener listener;

    public interface AnswerListener {
        void hasAnswer(Boolean isRightAnswer);
    }

    private CheckBox chbAnswer0, chbAnswer1, chbAnswer2, chbAnswer3;
    private RadioGroup rgAnswers;
    private EditText etAnswer;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Variables
        listener = (AnswerListener) getActivity();
        questionNumber = getArguments().getInt(QuestionHelper.ARG_QUESTION, 0);

        // Set question view
        invalidateQuestion(view);

        return view;
    }

    /**
     * Prepare question image and text to show
     * @param view - Root view of fragment
     */
    private void invalidateQuestion(View view) {
        // Set image
        ((ImageView) view.findViewById(R.id.iv_question_image)).setImageResource(getQuestionImage());

        // Set text of question
        ((TextView) view.findViewById(R.id.tv_question_text)).setText(getQuestionText());

        // Add listener for button
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        // Set type of question
        questionType = getQuestionType();

        // Add views for answer's variants
        addAnswerView(view);
    }

    /**
     * TODO Get image for question
     * @return image for question
     */
    private int getQuestionImage() {
        switch (questionNumber) {
            case QuestionHelper.Q_ACTINIA:
                return R.drawable.actinia_square;
            case QuestionHelper.Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_square;
            case QuestionHelper.Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_square;
            case QuestionHelper.Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_square;
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * TODO Get text of questions
     * @return text for question
     */
    private String getQuestionText() {
        switch (questionNumber) {
            case QuestionHelper.Q_ACTINIA:
                return getString(R.string.question_actinia);
            case QuestionHelper.Q_CABO_DA_ROCA:
                return getString(R.string.question_cabo_da_roca);
            case QuestionHelper.Q_GENERAL_SHERMAN:
                return getString(R.string.question_general_sherman);
            case QuestionHelper.Q_QUINTA_DA_REGALEIRA:
                return getString(R.string.question_quinta_da_regaleira);
        }
        return getString(R.string.error_question);
    }

    /**
     * TODO
     * @return
     */
    private int getQuestionType() {
        switch (questionNumber) {
            case QuestionHelper.Q_ACTINIA:
                return Q_TYPE_CHECK_BOX;
        }
        return Q_TYPE_ERROR;
    }

    /**
     * TODO
     * @param view
     */
    private void addAnswerView(View view) {
        if (questionType != Q_TYPE_ERROR) {
            RelativeLayout rlAnswers = view.findViewById(R.id.rl_answers);
            LinearLayout llAnswerCheckBoxes = rlAnswers.findViewById(R.id.llAnswerCheckBoxes);
            LayoutInflater inflater = LayoutInflater.from(rlAnswers.getContext());
            switch (questionType) {
                case Q_TYPE_CHECK_BOX:
                    rlAnswers.findViewById(R.id.tv_error_message).setVisibility(View.INVISIBLE);
                    LinearLayout llCheckBoxes = (LinearLayout)
                            inflater.inflate(R.layout.answers_check_boxes, rlAnswers, false);
                    rlAnswers.addView(llCheckBoxes);
                    // Define check boxes
                    chbAnswer0 = llCheckBoxes.findViewById(R.id.chbAnswer0);
                    chbAnswer1 = llCheckBoxes.findViewById(R.id.chbAnswer1);
                    chbAnswer2 = llCheckBoxes.findViewById(R.id.chbAnswer2);
                    chbAnswer3 = llCheckBoxes.findViewById(R.id.chbAnswer3);
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
                    break;
                case Q_TYPE_RADIO_GROUP:
                    // TODO
                    break;
                case Q_TYPE_INPUT:
                    // TODO
                    break;
                default:
                case Q_TYPE_ERROR:
                    // TODO
            }
        }
    }

    /**
     * TODO
     * @return
     */
    private String[] getAnswerVariants() {
        switch (questionNumber) {
            case QuestionHelper.Q_ACTINIA:
                return getResources().getStringArray(R.array.answers_actinia_text);
        }
        questionType = Q_TYPE_ERROR;
        return null;
    }

    // TODO
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            btnSubmit.setVisibility(View.INVISIBLE);
            if (listener != null) {
                listener.hasAnswer(checkAnswer());
            } else {
                // TODO Error
            }
        }
    }

    /**
     * TODO
     * @return
     */
    private Boolean checkAnswer() {

        return false;
    }

    /**
     * TODO
     * @return
     */
    private String[] getRightAnswers() {
        switch (questionNumber) {
            case QuestionHelper.Q_ACTINIA:
                return getResources().getStringArray(R.array.answers_actinia);
        }
        questionType = Q_TYPE_ERROR;
        return null;
    }

    /**
     * TODO
     * @return
     */
    private String getRightAnswer() {

        questionType = Q_TYPE_ERROR;
        return null;
    }

}
