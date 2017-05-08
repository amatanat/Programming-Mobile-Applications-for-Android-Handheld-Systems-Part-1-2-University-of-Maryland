package geoquiz.com.example.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_INDEX= "index";
    private static final String KEY_SCORE = "score";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;

    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, true),
            new TrueFalse(R.string.question_oceans, true)
    };

    private int mCurrentIndex = 0;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex= (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // find button id
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mPreviousButton =(Button) findViewById(R.id.previous_button);

        // check question answer on 'true' button click
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // send 'true' as parameter because 'true' button is clicked
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // send 'false' as parameter because 'false' button is clicked
                checkAnswer(false);
            }
        });

        // get next question
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // update index of quesion
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // get previous question
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if current question is in zero index
                if (mCurrentIndex == 0){
                    // get last question and update question
                    mCurrentIndex = mQuestionBank.length - 1;
                    updateQuestion();
                } else{
                    // get previous question and update question
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        if (savedInstanceState != null){
            // get index of current question
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);

            // get score
            mScore = savedInstanceState.getInt(KEY_SCORE);
        }
        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, mScore);
    }

    /*
    Update textview that shows question
     */
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    /*
    Check answer of question and show toast message
     */
    private void checkAnswer(boolean userPressedTrue){
        // get answer of current question
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId;

        // check if user's answer is correct
        if(userPressedTrue == answerIsTrue){
            mScore++;
            messageResId = R.string.correct_toast;
        } else {
            if (mScore != 0){
                mScore--;
                messageResId = R.string.incorrect_toast;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }

        // show toast message
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
