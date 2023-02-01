package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.quizapp.application.ApplicationClass;
import com.example.quizapp.model.Contest;
import com.example.quizapp.model.ContestSave;
import com.example.quizapp.model.QuestionsItem;
import com.example.quizapp.network.ApiInterFace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<String> list=new ArrayList<>();
    private TextView mTextField,questionView,difficultyLevel, marks, imageQuestion, videoQuestion, audioQuestion;
    private Button nextButton;
    private VideoView videoView;
    private int i=0;

    private Contest contest;
    CountDownTimer countDownTimer;
    private MediaController mediaController;

    private ApiInterFace apiInterFace;

    private RadioButton option1,option2,option3,option4;
    private RadioGroup radioGroup;
    private CheckBox chOption1,chOption2,chOption3,chOption4;

    private ImageView imageView,playPause;

    private LinearLayout layout, image_linear, video_linear, audio_linear;

    private MediaPlayer mp;
    List<QuestionsItem> listquestions;

    long duration;

    public void setScreen(QuestionsItem question){
        mp.stop();
        QuestionsItem questionsItem = question;
        String questionStatement=questionsItem.getQuestion();
        String optionOne=questionsItem.getOptionOne();
        String optionTwo=questionsItem.getOptionTwo();
        String optionThree=questionsItem.getOptionThree();
        String optionFour=questionsItem.getOptionFour();
        String url=questionsItem.getQuestionUrl();
        String questionType=questionsItem.getTypeOfQuestion();
        String answerType=questionsItem.getTypeOfAnswer();
        int marks = questionsItem.getMarks();
        String difficulty=questionsItem.getDifficultyLevel();

        layout.setVisibility(View.GONE);
        questionView.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        image_linear.setVisibility(View.GONE);
        video_linear.setVisibility(View.GONE);
        audio_linear.setVisibility(View.GONE);

        this.marks.setText(marks+"points");
        difficultyLevel.setText(difficulty);
        if(answerType.equals("SINGLE")){

            radioGroup.setVisibility(View.VISIBLE);
            option1.setText(optionOne);
            option2.setText(optionTwo);
            option3.setText(optionThree);
            option4.setText(optionFour);

        } else if (answerType.equals("MULTI")) {

            layout.setVisibility(View.VISIBLE);
            chOption1.setText(optionOne);
            chOption2.setText(optionTwo);
            chOption3.setText(optionThree);
            chOption4.setText(optionFour);
        }else{
            // layout.setVisibility(View.VISIBLE);
        }

        if(questionType.equals("IMAGE")){
            imageView.setVisibility(View.VISIBLE);
            image_linear.setVisibility(View.VISIBLE);
            Toast.makeText(this, questionStatement, Toast.LENGTH_SHORT).show();
            imageQuestion.setText(questionStatement);
            Glide.with(imageView.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_downloading_24)
                    .into(imageView);
        }else if(questionType.equals("VIDEO")){

            videoView.setVisibility(View.VISIBLE);
            video_linear.setVisibility(View.VISIBLE);
            videoQuestion.setText(questionStatement);
            Toast.makeText(this, questionStatement, Toast.LENGTH_SHORT).show();
            mediaController = new MediaController(this);
            mediaController.setAnchorView(mTextField);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(Uri.parse(url));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            /*
                             * add media controller
                             */
                            mediaController = new MediaController(MainActivity.this);
                            videoView.setMediaController(mediaController);
                            /*
                             * and set its position on screen
                             */
                            mediaController.setAnchorView(videoView);
                        }
                    });
                }
            });
            videoView.start();


        }else if(questionType.equals("AUDIO")){
            Toast.makeText(this, questionsItem.getQuestion(), Toast.LENGTH_SHORT).show();
            playPause.setVisibility(View.VISIBLE);
            audioQuestion.setText(questionStatement);
            audio_linear.setVisibility(View.VISIBLE);
            //stop.setVisibility(View.VISIBLE);
            try{
                mp.setDataSource(url);//Write your location here
                mp.prepare();
                mp.start();
                //mp.setLooping(true);

                playPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playPause.getDrawable().getConstantState().equals(getDrawable(R.drawable.baseline_pause_24).getConstantState())) {
                            mp.pause();
                            playPause.setImageDrawable(getDrawable(R.drawable.baseline_play_arrow_24));
                        } else {
                            mp.start();
                            playPause.setImageDrawable(getDrawable(R.drawable.baseline_pause_24));
                        }

                    }
                });

//                stop.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mp.stop();
//                        try {
//                            mp.setDataSource(url);//Write your location here
//                            mp.prepare();
//                            mp.start();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }
//                });

            }catch(Exception e){e.printStackTrace();}



        }else{
            Toast.makeText(this, questionStatement, Toast.LENGTH_SHORT).show();
            questionView.setVisibility(View.VISIBLE);
            questionView.setText(question.getQuestion());
        }
    }

    private void getContest(String id)
    {

        Log.e("function","inside");
        apiInterFace.getById(id).enqueue(
                new Callback<Contest>() {
                    @Override
                    public void onResponse(Call<Contest> call, Response<Contest> response) {
                        if(response.isSuccessful()&& response.body()!=null)
                        {
                            contest = response.body();
                            duration=contest.getDuration();
                            listquestions = contest.getQuestions();
                            Log.i("response", listquestions.toString());
                           Toast.makeText(MainActivity.this, "got response"+response.body(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "empty response", Toast.LENGTH_SHORT).show();
                            Log.i("response empty", listquestions.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Contest> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error",t.getMessage());
                    }
                }
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextField=findViewById(R.id.timer_check);
        questionView=findViewById(R.id.tv_question);
        nextButton=findViewById(R.id.btn_next);
        videoView=findViewById(R.id.video_question);
        imageView=findViewById(R.id.img_question);
        option1=findViewById(R.id.rd_option1);
        option2=findViewById(R.id.rd_option2);
        option3=findViewById(R.id.rd_option3);
        option4=findViewById(R.id.rd_option4);
        chOption1=findViewById(R.id.check_option1);
        chOption2=findViewById(R.id.check_option2);
        chOption3=findViewById(R.id.check_option3);
        chOption4=findViewById(R.id.check_option4);
        difficultyLevel=findViewById(R.id.tv_diff);
        marks =findViewById(R.id.tv_mark);
        radioGroup=findViewById(R.id.radiogroup);
        layout=findViewById(R.id.check_linear);
        playPause=findViewById(R.id.img_play);
        image_linear=findViewById(R.id.image_linear);
        video_linear=findViewById(R.id.video_linear);
        audio_linear = findViewById(R.id.audio_linear);
        imageQuestion = findViewById(R.id.image_text);
        videoQuestion = findViewById(R.id.video_text);
        audioQuestion = findViewById(R.id.audio_text);

        //stop=findViewById(R.id.img_stop);
        mp=new MediaPlayer();

        apiInterFace=((ApplicationClass)getApplication()).retrofit.create(ApiInterFace.class);
        getContest("82dee9ba-e564-4f14-ab2c-bd036e499c60");

        countDownTimer =new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText( ""+millisUntilFinished / 1000);

            }

            public void onFinish() {
//              obj.cancel();
                if(i < listquestions.size()) {
                    setScreen(listquestions.get(i++));
                    // obj.start();
                }
            }
        }.start();



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // obj.cancel();
                if(i<listquestions.size()) {
                    setScreen(listquestions.get(i++));
                }

                //obj.start();

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ContestSave contestSave = new ContestSave();
        contestSave.setContestId(contest.getContestId());
        contestSave.setRemainingTime(Long.parseLong(mTextField.getText().toString()));
        contestSave.setIndex(i);
        contestSave.setTimeLeft(new Date().getTime());

    }
}