package com.android.example.myquizapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

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
    public static final String KEY_ANSWER_TYPE = "answerType";
    public static final String KEY_QUESTION_ANSWERS = "questionAnswers";
    public static final String KEY_QUESTION_MODE = "questionMode";
    public static final String KEY_UI_HIDDEN = "isUIHidden";
    public static final int MODE_QUESTION = 0, MODE_ANSWER = 1;
    public static final int Q_TYPE_ERROR = 0, Q_TYPE_RADIO_GROUP = 1,
            Q_TYPE_CHECK_BOX = 2, Q_TYPE_INPUT = 3;
    public static final int A_TYPE_WRONG = 0, A_TYPE_PARTIALLY = 1, A_TYPE_CORRECT = 2;
    public static final int SCORE_DEFAULT = 0;
    public static final int Q_INTRO = 0;
    public static final int QUESTIONS_LIMIT = 18; // TODO
    static final int QUESTIONS_TOTAL = 18; // TODO
    static final String KEY_CURRENT_QUESTION = "currentQuestion";
    static final String KEY_QUESTION_LIST = "questionsList";
    static final String KEY_SCORE = "score";
    // Questions
    static final int Q_FINISH = 100;
    private static final int Q_ACTINIA = 1; // TODO
    private static final int Q_CABO_DA_ROCA = 2;
    private static final int Q_CABO_DA_ROCA_NAMES = 3;
    private static final int Q_VICTORIA = 4;
    private static final int Q_HONG_KONG = 5;
    private static final int Q_SKY_TREE = 6;
    private static final int Q_GENERAL_SHERMAN = 7;
    private static final int Q_SAGRADA_FAMILIA = 8;
    private static final int Q_PENA_PALACE = 9;
    private static final int Q_PENA_PALACE_BEFORE = 10;
    private static final int Q_TEMPLE_OF_HEAVEN = 11;
    private static final int Q_HEAVEN_RELIGION = 12;
    private static final int Q_TOKYO_KYOTO = 13;
    private static final int Q_EDO_CHIYODA = 14;
    private static final int Q_EMPIRE_STATE = 15;
    private static final int Q_TIMES_SQUARE = 16;
    private static final int Q_QUINTA_DA_REGALEIRA = 17;
    private static final int Q_SAINT_PETERSBURG = 18;
    private static final int Q_BRONZE_HORSEMAN = 19;
    // TODO

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
    @Contract(pure = true)
    private static int getDefaultImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_square;
            case Q_CABO_DA_ROCA:
            case Q_CABO_DA_ROCA_NAMES:
                return R.drawable.cabo_da_roca_portrait;
            case Q_VICTORIA:
            case Q_HONG_KONG:
                return R.drawable.victoria_landscape;
            case Q_SKY_TREE:
                return R.drawable.sky_tree_portrait;
            case Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_square;
            case Q_SAGRADA_FAMILIA:
                return R.drawable.sagrada_familia_portrait;
            case Q_PENA_PALACE:
            case Q_PENA_PALACE_BEFORE:
                return R.drawable.pena_palace_landscape;
            case Q_TEMPLE_OF_HEAVEN:
            case Q_HEAVEN_RELIGION:
                return R.drawable.temple_of_heaven_square;
            case Q_TOKYO_KYOTO:
            case Q_EDO_CHIYODA:
                return R.drawable.tokyo_castle_square;
            case Q_EMPIRE_STATE:
            case Q_TIMES_SQUARE:
                return R.drawable.empire_state_square;
            case Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_landscape;
            case Q_SAINT_PETERSBURG:
                return R.drawable.petersburg_landscape;
            // TODO
        }
        // On error will return this image
        return R.drawable.yarmiychuk;
    }

    /**
     * Get Question image for portrait orientation
     * @param questionNumber - number of question
     * @return image resource id
     */
    @Contract(pure = true)
    private static int getPortraitImage(int questionNumber) {
        switch (questionNumber) {
            case Q_VICTORIA:
            case Q_HONG_KONG:
                return R.drawable.victoria_square;
            case Q_GENERAL_SHERMAN:
                return R.drawable.general_sherman_portrait;
            case Q_QUINTA_DA_REGALEIRA:
                return R.drawable.quinta_da_regaleira_portrait;
            case Q_SAINT_PETERSBURG:
                return R.drawable.petersburg_portrait;
            // TODO
        }
        return getDefaultImage(questionNumber);
    }

    /**
     * Get Question image for landscape orientation
     * @param questionNumber - number of question
     * @return image resource id
     */
    @Contract(pure = true)
    private static int getLandscapeImage(int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return R.drawable.actinia_landscape;
            case Q_CABO_DA_ROCA:
            case Q_CABO_DA_ROCA_NAMES:
                return R.drawable.cabo_da_roca_landscape;
            case Q_SKY_TREE:
                return R.drawable.sky_tree_landscape;
            case Q_SAGRADA_FAMILIA:
                return R.drawable.sagrada_familia_landscape;
            case Q_TEMPLE_OF_HEAVEN:
            case Q_HEAVEN_RELIGION:
                return R.drawable.temple_of_heaven_landscape;
            case Q_TOKYO_KYOTO:
            case Q_EDO_CHIYODA:
                return R.drawable.tokyo_castle_landscape;
            case Q_EMPIRE_STATE:
            case Q_TIMES_SQUARE:
                return R.drawable.empire_state_landscape;
            // TODO
        }
        return getDefaultImage(questionNumber);
    }

    /**
     * Get type of answers
     *
     * @param questionNumber - Number of question
     * @return type of answer format (single choice, multi choice, input)
     */
    @Contract(pure = true)
    public static int getQuestionType(int questionNumber) {
        switch (questionNumber) {
            // CheckBox question type
            case Q_ACTINIA:
            case Q_CABO_DA_ROCA_NAMES:
            case Q_PENA_PALACE:
            case Q_HONG_KONG:
            case Q_EDO_CHIYODA:
            case Q_EMPIRE_STATE:
            case Q_SAINT_PETERSBURG:
                return Q_TYPE_CHECK_BOX;
            // RadioGroup question type
            case Q_CABO_DA_ROCA:
            case Q_SKY_TREE:
            case Q_GENERAL_SHERMAN:
            case Q_PENA_PALACE_BEFORE:
            case Q_TEMPLE_OF_HEAVEN:
            case Q_HEAVEN_RELIGION:
            case Q_TIMES_SQUARE:
            case Q_QUINTA_DA_REGALEIRA:
                return Q_TYPE_RADIO_GROUP;
            // EditText question type
            case Q_VICTORIA:
            case Q_SAGRADA_FAMILIA:
            case Q_TOKYO_KYOTO:
                return Q_TYPE_INPUT;
            // TODO
        }
        return Q_TYPE_ERROR;
    }

    /**
     * Get text for a question
     *
     * @param res - Resources of app
     * @param questionNumber - Number of question
     * @return Question text
     */
    @NonNull
    public static String getQuestionText(Resources res, int questionNumber) {
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.question_actinia);
            case Q_CABO_DA_ROCA:
                return res.getString(R.string.question_cabo_da_roca);
            case Q_CABO_DA_ROCA_NAMES:
                return res.getString(R.string.question_cabo_names);
            case Q_VICTORIA:
                return res.getString(R.string.question_victoria);
            case Q_HONG_KONG:
                return res.getString(R.string.question_hong_kong);
            case Q_SKY_TREE:
                return res.getString(R.string.question_sky_tree);
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.question_general_sherman);
            case Q_SAGRADA_FAMILIA:
                return res.getString(R.string.question_sagrada_familia);
            case Q_PENA_PALACE:
                return res.getString(R.string.question_pena_palace);
            case Q_PENA_PALACE_BEFORE:
                return res.getString(R.string.question_pena_before);
            case Q_TEMPLE_OF_HEAVEN:
                return res.getString(R.string.question_temple_of_heaven);
            case Q_HEAVEN_RELIGION:
                return res.getString(R.string.question_temple_religion);
            case Q_TOKYO_KYOTO:
                return res.getString(R.string.question_tokyo_kyoto);
            case Q_EDO_CHIYODA:
                return res.getString(R.string.question_edo_chiyoda);
            case Q_EMPIRE_STATE:
                return res.getString(R.string.question_empire_state);
            case Q_TIMES_SQUARE:
                return res.getString(R.string.question_times_square);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.question_quinta_da_regaleira);
            case Q_SAINT_PETERSBURG:
                return res.getString(R.string.question_saint_petersburg);
            // TODO
        }
        return res.getString(R.string.error_question);
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
            case Q_CABO_DA_ROCA_NAMES:
                variants = res.getStringArray(R.array.variants_cabo_da_roca);
                break;
            case Q_HONG_KONG:
                variants = res.getStringArray(R.array.variants_hong_kong);
                break;
            case Q_SKY_TREE:
                variants = res.getStringArray(R.array.variants_sky_tree);
                break;
            case Q_GENERAL_SHERMAN:
                variants = res.getStringArray(R.array.variants_general_sherman);
                break;
            case Q_PENA_PALACE:
                variants = res.getStringArray(R.array.variants_pena_palace);
                break;
            case Q_PENA_PALACE_BEFORE:
                variants = res.getStringArray(R.array.variants_pena_before);
                break;
            case Q_TEMPLE_OF_HEAVEN:
                variants = res.getStringArray(R.array.variants_temple_of_heaven);
                break;
            case Q_HEAVEN_RELIGION:
                variants = res.getStringArray(R.array.variants_temple_religion);
                break;
            case Q_EDO_CHIYODA:
                variants = res.getStringArray(R.array.variants_edo_chiyoda);
                break;
            case Q_EMPIRE_STATE:
                variants = res.getStringArray(R.array.variants_empire_state);
                break;
            case Q_TIMES_SQUARE:
                variants = res.getStringArray(R.array.variants_times_square);
                break;
            case Q_QUINTA_DA_REGALEIRA:
                variants = res.getStringArray(R.array.variants_quinta_da_regaleira);
                break;
            case Q_SAINT_PETERSBURG:
                variants = res.getStringArray(R.array.variants_saint_petersburg);
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
            case Q_CABO_DA_ROCA_NAMES:
                return res.getStringArray(R.array.answers_cabo_names);
            case Q_HONG_KONG:
                return res.getStringArray(R.array.answers_hong_kong);
            case Q_PENA_PALACE:
                return res.getStringArray(R.array.answers_pena_palace);
            case Q_EDO_CHIYODA:
                return res.getStringArray(R.array.answers_edo_chiyoda);
            case Q_EMPIRE_STATE:
                return res.getStringArray(R.array.answers_empire_state);
            case Q_SAINT_PETERSBURG:
                return res.getStringArray(R.array.aswers_saint_petersburg);
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
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.answer_general_sherman);
            case Q_SAGRADA_FAMILIA:
                return res.getString(R.string.answer_sagrada_familia);
            case Q_PENA_PALACE_BEFORE:
                return res.getString(R.string.answer_pena_before);
            case Q_TEMPLE_OF_HEAVEN:
                return res.getString(R.string.answer_temple_of_heaven);
            case Q_TOKYO_KYOTO:
                return res.getString(R.string.answer_tokyo_kyoto);
            case Q_HEAVEN_RELIGION:
                return res.getString(R.string.answer_temple_religion);
            case Q_TIMES_SQUARE:
                return res.getString(R.string.answer_times_square);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.answer_quinta_da_regaleira);
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
            case Q_CABO_DA_ROCA_NAMES:
                return res.getString(R.string.description_cabo_names);
            case Q_VICTORIA:
                return res.getString(R.string.description_victoria);
            case Q_HONG_KONG:
                return res.getString(R.string.description_hong_kong);
            case Q_SKY_TREE:
                return res.getString(R.string.description_sky_tree);
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.description_general_sherman);
            case Q_SAGRADA_FAMILIA:
                return res.getString(R.string.description_sagrada_familia);
            case Q_PENA_PALACE:
                return res.getString(R.string.description_pena_palace);
            case Q_PENA_PALACE_BEFORE:
                return res.getString(R.string.description_pena_before);
            case Q_TEMPLE_OF_HEAVEN:
                return res.getString(R.string.description_temple_of_heaven);
            case Q_HEAVEN_RELIGION:
                return res.getString(R.string.description_temple_religion);
            case Q_TOKYO_KYOTO:
                return res.getString(R.string.description_tokyo_kyoto);
            case Q_EDO_CHIYODA:
                return res.getString(R.string.description_edo_chiyoda);
            case Q_EMPIRE_STATE:
                return res.getString(R.string.description_empire_state);
            case Q_TIMES_SQUARE:
                return res.getString(R.string.description_times_square);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.description_quinta_da_regaleira);
            case Q_SAINT_PETERSBURG:
                return res.getString(R.string.description_saint_petersburg);
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
            // TODO
            case Q_CABO_DA_ROCA:
            case Q_CABO_DA_ROCA_NAMES:
            case Q_SKY_TREE:
            case Q_GENERAL_SHERMAN:
                return getDefaultAnswerText(res, questionNumber);
            case Q_SAGRADA_FAMILIA:
            case Q_QUINTA_DA_REGALEIRA:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_another);
            default:
                return getDefaultAnswerText(res, questionNumber) + " "
                        + res.getString(R.string.rotate_to_full);
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
        // TODO
        switch (questionNumber) {
            case Q_GENERAL_SHERMAN:
            case Q_SKY_TREE:
                return getDefaultAnswerText(res, questionNumber) + " " +
                        res.getString(R.string.rotate_to_full);
            case Q_SAGRADA_FAMILIA:
            case Q_QUINTA_DA_REGALEIRA:
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
        // TODO
        switch (questionNumber) {
            case Q_ACTINIA:
                return res.getString(R.string.link_actinia);
            case Q_CABO_DA_ROCA:
            case Q_CABO_DA_ROCA_NAMES:
                return res.getString(R.string.link_cabo_da_roca);
            case Q_VICTORIA:
            case Q_HONG_KONG:
                return res.getString(R.string.link_victoria);
            case Q_SKY_TREE:
                return res.getString(R.string.link_sky_tree);
            case Q_GENERAL_SHERMAN:
                return res.getString(R.string.link_general_sherman);
            case Q_SAGRADA_FAMILIA:
                return res.getString(R.string.link_sagrada_familia);
            case Q_PENA_PALACE:
            case Q_PENA_PALACE_BEFORE:
                return res.getString(R.string.link_pena_palace);
            case Q_TEMPLE_OF_HEAVEN:
                return res.getString(R.string.link_temple_of_heaven);
            case Q_HEAVEN_RELIGION:
                return res.getString(R.string.link_temple_religion);
            case Q_TOKYO_KYOTO:
                return res.getString(R.string.link_tokyo_kyoto);
            case Q_EDO_CHIYODA:
                return res.getString(R.string.link_edo_chiyoda);
            case Q_EMPIRE_STATE:
                return res.getString(R.string.link_empire_state);
            case Q_TIMES_SQUARE:
                return res.getString(R.string.link_times_square);
            case Q_QUINTA_DA_REGALEIRA:
                return res.getString(R.string.link_quinta_da_regaleira);
            case Q_SAINT_PETERSBURG:
                return res.getString(R.string.link_saint_petersburg);
            // TODO
        }
        return null;
    }
}
