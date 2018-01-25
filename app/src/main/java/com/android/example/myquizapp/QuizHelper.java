package com.android.example.myquizapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Dmitriy Yarmiychuk on 15.01.2018.
 * Создал Dmitriy Yarmiychuk 15.01.2018
 */

public final class QuizHelper {

    public static final String ARG_QUESTION = "question";
    public static final String ARG_QUESTION_NUMBER = "questionNumber";
    public static final String ARG_RESULT = "result";

    public static final String QUESTION_MODE_KEY = "questionMode";
    public static final int MODE_QUESTION = 0, MODE_ANSWER = 1;
    public static final String QUESTION_ANSWERS_KEY = "questionAnswers";
    public static final int SCORE_DEFAULT = 0;
    public static final int TOTAL_QUESTIONS = 4;
    public static final int Q_INTRO = 0;
    public static final int Q_TYPE_ERROR = 0,
            Q_TYPE_RADIO_GROUP = 1, Q_TYPE_CHECK_BOX = 2, Q_TYPE_INPUT = 3;
    public static final String ANSWER_TYPE_KEY = "answerType";
    public static final int A_TYPE_WRONG = 0, A_TYPE_PARTIALLY = 1, A_TYPE_CORRECT = 2;
    static final String SCORE_KEY = "score";
    static final String QUESTION_LIST_KEY = "questionsList";
    static final String CURRENT_QUESTION = "currentQuestion";
    static final int Q_FINISH = 100;
    private static final int Q_ACTINIA = 1;
    private static final int Q_CABO_DA_ROCA = 2;
    private static final int Q_VICTORIA = 3;
    private static final int Q_SKY_TREE = 4;
    private static final int Q_GENERAL_SHERMAN = 44;
    private static final int Q_QUINTA_DA_REGALEIRA = 45;

    /**
     * Get image for question
     * @param orientation - device orientation
     * @param questionNumber - number of current question
     * @return image for question
     */
    public static int getQuestionImage(int orientation, int questionNumber) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return getPortraitImage(questionNumber);
            case Configuration.ORIENTATION_LANDSCAPE:
                return getLandscapeImage(questionNumber);
            default:
                return getDefaultImage(questionNumber);
        }
    }

    /**
     * Get default question's image
     * @param questionNumber - number of current question
     * @return image's resource id
     */
    private static int getDefaultImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_square;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_square;
            case Q_VICTORIA:
                return R.drawable.victoria_landscape;
            case Q_SKY_TREE:
                return R.drawable.sky_tree_portrait;
            // TODO
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * Get Question image for portrait orientation
     * @param questionNumber - number of question
     * @return image resource id
     */
    private static int getPortraitImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_square;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_portrait;
            case Q_VICTORIA:
                return R.drawable.victoria_square;
            case Q_SKY_TREE:
                return R.drawable.sky_tree_portrait;
            // TODO
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * Get Question image for landscape orientation
     * @param questionNumber - number of question
     * @return image resource id
     */
    private static int getLandscapeImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_landscape;
            case Q_CABO_DA_ROCA:
                return R.drawable.cabo_da_roca_landscape;
            case Q_VICTORIA:
                return R.drawable.victoria_landscape;
            case Q_SKY_TREE:
                return R.drawable.sky_tree_landscape;
            // TODO
        }
        return R.drawable.yarmiychuk;
    }

    /**
     * Get text for a question
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return Question text
     */
    public static String getQuestionText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.question_actinia);
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.question_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.question_victoria);
            case Q_SKY_TREE:
                return res.getString(R.string.question_sky_tree);
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.question_general_sherman);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.question_quinta_da_regaleira);
            // TODO
        }
        return res.getString(R.string.error_question);
    }

    /**
     * Get type of answers
     *
     * @param questionNumber - Number of question
     * @return type of answer format (single choice, multi choice, input)
     */
    public static int getQuestionType(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return Q_TYPE_CHECK_BOX;
            case Q_CABO_DA_ROCA:
            case Q_SKY_TREE:
                return Q_TYPE_RADIO_GROUP;
            case Q_VICTORIA:
                return Q_TYPE_INPUT;
            // TODO
        }
        return Q_TYPE_ERROR;
    }

    /**
     * Get answer variants for a question
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return array of answer variants for question
     */
    public static String[] getAnswerVariants(Resources res, int questionNumber) {
        String[] variants;
        switch (questionNumber) {
            case Q_ACTINIA:
                variants = res.getStringArray(R.array.variants_actinia);
                break;
            case Q_CABO_DA_ROCA:
                variants = res.getStringArray(R.array.variants_cabo_da_roca);
                break;
            case Q_SKY_TREE:
                variants = res.getStringArray(R.array.variants_sky_tree);
                break;
                // TODO
            default:
                variants = getErrorArray(res);
        }
        // Reorder variants
        Collections.shuffle(Arrays.asList(variants));
        return variants;
    }

    /**
     * Get list of right answers for a question
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return list of right answers for question
     */
    public static String[] getRightAnswers(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getStringArray(R.array.answers_actinia);
                // TODO
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
     * Get right answer for single-item choice question
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return right answer
     */
    public static String getRightAnswer(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.answer_cabo_da_roca);
            case Q_VICTORIA:
                return res.getString(R.string.answer_victoria);
            case Q_SKY_TREE:
                return res.getString(R.string.answer_sky_tree);
                // TODO
        }
        return res.getString(R.string.error);
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
     * @param orientation - current device orientation
     * @return text for answer TextView
     */
    public static String getAnswerText(Resources res, int orientation, int questionNumber) {
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
     * Get answer text for unknown orientation or for any orientation
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
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
            case Q_SKY_TREE:
                return res.getString(R.string.description_sky_tree);
            // TODO
        }
        return "";
    }

    /**
     * Get answer text for portrait orientation
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return answer text for portrait orientation
     */
    private static String getPortraitAnswerText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
            case Q_VICTORIA:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_full);
                // TODO return getDefaultAnswerText(res, questionNumber) + " "
                //        + res.getString(R.string.rotate_to_another);
            default:
                return getDefaultAnswerText(res, questionNumber);
        }
    }

    /**
     * Get answer text for landscape orientation
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return answer text for landscape orientation
     */
    private static String getLandscapeAnswerText(Resources res, int questionNumber) {
        switch (questionNumber) {
            //case Q_CABO_DA_ROCA:
            //    return getDefaultAnswerText(res, questionNumber) + " "
            //            + res.getString(R.string.rotate_to_another);
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
            case Q_SKY_TREE:
                return res.getString(R.string.link_sky_tree);
            // TODO
        }
        return null;
    }
}
