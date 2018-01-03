package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    ProgressBar mProgressBar;
    int mIndex;
    int mScore;
    int mQuestion;



    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };


    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int)Math.ceil(100.0 / mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieving saved state on rotation
        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        mTrueButton = (Button)findViewById(R.id.true_button);//findViewById casted to Button type
        mFalseButton = (Button)findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //TrueFalse firstQuestion = mQuestionBank[mIndex];
        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        //on rotation updating the score, after mScoreTextView has been declared
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);



        // First way to call Listener on the click of the button
      /*  View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quizzler", "Button True pressed!");
                //Toast is for like ToolTips messages
                Toast.makeText(getApplicationContext(),"True Pressed!", Toast.LENGTH_SHORT).show();
            }
        };
        mTrueButton.setOnClickListener(myListener);*/


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.d("Quizzler", "Button True pressed!");
                Toast myToast = Toast.makeText(getApplicationContext(),"True pressed!",Toast.LENGTH_SHORT);
                myToast.show();*/
                checkAnswer(true);
                updateQuestion();

            }
        });
        // Second way to call Listener on the click of the button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Log.d("Quizzler", "Button False pressed!");
                Toast.makeText(getApplicationContext(),"False pressed!",Toast.LENGTH_SHORT).show();*/
               checkAnswer(false);
               updateQuestion();

            }
        });

    }//end of onCreate

    private void updateQuestion(){
        mIndex = (mIndex + 1) % mQuestionBank.length;

        //closing dialog at the end of the quiz
        if(mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false); //user can't cancel the dialog
            alert.setMessage("You scored " + mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { finish();/*on click, close app*/ }
            });
            alert.show();//show alert dialog
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if (userSelection == correctAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }

    //to fix the app on rotation, save the current state and restore the saved values in new orientation
    @Override
    public void onSaveInstanceState(Bundle outState) // Bundle is like a Map with Key-Value enties
    {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);

    }
}
