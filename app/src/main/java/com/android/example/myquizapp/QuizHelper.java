package com.android.example.myquizapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

/**
 * Created by Dmitriy Yarmiychuk on 15.01.2018.
 * Создал Dmitriy Yarmiychuk 15.01.2018
 */

public final class QuizHelper {

    public static final String ARG_QUESTION = "question";

    public static final String QUESTION_MODE_KEY = "questionMode";
    public static final int MODE_QUESTION = 0, MODE_ANSWER = 1;

    static final String SCORE_KEY = "score";
    static final int SCORE_DEFAULT = 0;

    static final String CURRENT_QUESTION = "currentQuestion";
    static final int TOTAL_QUESTIONS = 3;
    public static final int Q_INTRO = 0;
    private static final int Q_ACTINIA = 1;
    private static final int Q_CABO_DA_ROCA = 2;
    private static final int Q_VICTORIA = 3;
    private static final int Q_GENERAL_SHERMAN = 4;
    private static final int Q_QUINTA_DA_REGALEIRA = 5;
    static final int Q_FINISH = 4;

    public static final int Q_TYPE_ERROR = 0,
            Q_TYPE_RADIO_GROUP = 1, Q_TYPE_CHECK_BOX = 2, Q_TYPE_INPUT = 3;

    public static final String ANSWER_TYPE_KEY = "answerType";
    public static final int A_TYPE_WRONG = 0, A_TYPE_PARTIALLY = 1, A_TYPE_CORRECT = 2;

    /**
     * TODO Get image for question
     *
     * @return image for question
     */
    public static int getQuestionImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_square;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_square;
            case Q_VICTORIA:
                return R.drawable.victoria_square;
            case Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_square;
            case Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_square;
        }
        return R.drawable.yarmiychuk;
    }


    /**
     * TODO Get text of questions
     *
     * @return text for question
     */
    public static String getQuestionText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.question_actinia);
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.question_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.question_victoria);
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.question_general_sherman);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.question_quinta_da_regaleira);
        }
        return res.getString(R.string.error_question);
    }

    /**
     * TODO
     *
     * @return type of answer format (single choice, multi choice, input)
     */
    public static int getQuestionType(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return Q_TYPE_CHECK_BOX;
            case Q_CABO_DA_ROCA:
                return Q_TYPE_RADIO_GROUP;
            case Q_VICTORIA:
                return Q_TYPE_INPUT;
        }
        return Q_TYPE_ERROR;
    }


    /**
     * TODO
     *
     * @return array of answer variants for question
     */
    public static String[] getAnswerVariants(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getStringArray(R.array.variants_actinia);
            case Q_CABO_DA_ROCA:
                return res.getStringArray(R.array.variants_cabo_da_roca);
            default:
                return getErrorArray(res);
        }
    }

    /**
     * TODO
     *
     * @return list of right answers for question
     */
    public static String[] getRightAnswers(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getStringArray(R.array.answers_actinia);
            default:
                return getErrorArray(res);
        }
    }

    /**
     * Make and return array with "Error" words
     * @return "Error" array
     */
    @NonNull
    private static String[] getErrorArray(Resources res) {
        String error = res.getString(R.string.error);
        return new String[] {error, error, error, error};
    }

    /**
     * TODO
     *
     * @return right answer for single-item choice question
     */
    public static String getRightAnswer(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.answer_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.answer_victoria);
        }
        return res.getString(R.string.error);
    }

    /**
     * Get image for answer's ImageView
     *
     * @param deviceOrientation - current device orientation
     * @return image for answer
     */
    public static int getAnswerImage(int deviceOrientation, int questionNumber) {
        switch (deviceOrientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return getPortraitAnswerImage(questionNumber);
            case Configuration.ORIENTATION_LANDSCAPE:
                return getLandscapeAnswerImage(questionNumber);
            default:
                // Return squared image
                return getQuestionImage(questionNumber);
        }
    }

    /**
     * TODO Get image for answer in portrait orientation
     *
     * @return portrait image for answer
     */
    private static int getPortraitAnswerImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_landscape;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_portrait;
            case Q_VICTORIA:
                return getLandscapeAnswerImage(questionNumber);
        }
        // Return squared image
        return getQuestionImage(questionNumber);
    }

    /**
     * TODO Get image for answer in portrait orientation
     *
     * @return landscape image for answer
     */
    private static int getLandscapeAnswerImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_landscape;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_landscape;
            case Q_VICTORIA:
                return R.drawable.victoria_landscape;
        }
        // Return squared image
        return getQuestionImage(questionNumber);
    }

    /**
     * Get text based on answer
     *
     * @return text about correct, wrong or incorrect answer
     */
    @NonNull
    public static String getStringTypeAnswer(Resources res, int answerType) {
        switch (answerType) {
            case A_TYPE_CORRECT:
                return res.getString(R.string.correct_answer);
            case A_TYPE_PARTIALLY:
                return res.getString(R.string.partially_answer);
        }
        return res.getString(R.string.wrong_answer);
    }

    /**
     * Get Text for answer
     *
     * @param deviceOrientation - current device orientation
     * @return text for answer TextView
     */
    public static String getAnswerText(Resources res, int deviceOrientation, int questionNumber) {
        // TODO
        switch (questionNumber) {
            default:
                return getSpecialAnswerText(res, deviceOrientation, questionNumber);
        }
    }

    /**
     * Get answer text for unknown orientation or for any orientation
     *
     * @return default answer text.
     */
    private static String getDefaultAnswerText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.description_actinia);
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.description_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.description_victoria);
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
    private static String getSpecialAnswerText(Resources res, int orientation, int questionNumber) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return getPortraitAnswerText(res, questionNumber);
            case Configuration.ORIENTATION_LANDSCAPE:
                return getLandscapeAnswerText(res, questionNumber);
            default:
                // Return default text
                return getDefaultAnswerText(res, questionNumber);
        }
    }

    /**
     * TODO Get answer text for portrait orientation
     *
     * @return answer text for portrait orientation
     */
    private static String getPortraitAnswerText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
            case Q_VICTORIA:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_full);
            case Q_CABO_DA_ROCA:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_another);
            default:
                return getDefaultAnswerText(res, questionNumber);
        }
    }

    /**
     * TODO Get answer text for landscape orientation
     *
     * @return answer text for landscape orientation
     */
    private static String getLandscapeAnswerText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_CABO_DA_ROCA:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_another);
            default:
                return getDefaultAnswerText(res, questionNumber);
        }
    }

    /**
     * Get link for wiki page
     * @param res - Resources of app
     * @param questionNumber - number of question
     * @return link
     */
    public static String getLink(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.link_actinia);
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.link_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.link_victoria);
            // TODO
        }
        return null;
    }
}
