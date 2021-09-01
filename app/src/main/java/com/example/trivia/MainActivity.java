package com.example.trivia;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="Tag" ;
    private TextView questionView;
    private Button true_btn,false_btn;
    private int currentQuesIndex=0;
    private List<Question> questionList;
    private int currscore=0;
    private TextView timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionView=findViewById(R.id.question_text);
        timer=findViewById(R.id.timerView);
        true_btn=findViewById(R.id.true_button);
        false_btn=findViewById(R.id.false_button);


        true_btn.setOnClickListener(this);
        false_btn.setOnClickListener(this);
        long duration= TimeUnit.MINUTES.toMillis(1);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l)
            {
                String sDuration=String.format(Locale.ENGLISH,"%02d: %02d",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                timer.setText(sDuration);
            }

            @Override
            public void onFinish()
            {
                timer.setVisibility(View.GONE);
                Intent intent=new Intent(getApplicationContext(),Score.class);
                intent.putExtra("Final_Score",currscore);
                startActivity(intent);
    finish();
            }
        }.start();

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                currentQuesIndex=(currentQuesIndex + (int)(Math.random() * questionArrayList.size()) - 1) % questionArrayList.size();
                questionView.setText(questionArrayList.get(currentQuesIndex).getques());
                Log.d("Hello", "processFinished: "+questionArrayList);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.false_button:
                checkans(false);
                break;

            case R.id.true_button:
                checkans(true);
                break;

        }
    }

    private void checkans(boolean answerGiven)
    {
        boolean correctAns=questionList.get(currentQuesIndex).getCorrectAns();
        if(answerGiven==correctAns)
        {
            currscore++;
            Toast.makeText(this,"Right Answer",Toast.LENGTH_SHORT).show();
            changeQues();
        }
        else
        {
            Toast.makeText(this,"Wrong Answer",Toast.LENGTH_SHORT).show();
            changeQues();
        }
    }

    private void changeQues()
    {
        currentQuesIndex=(currentQuesIndex + (int)(Math.random() * questionList.size()) - 1) % questionList.size();
        String ques=questionList.get(currentQuesIndex).getques();
        questionView.setText(ques);
    }

}
