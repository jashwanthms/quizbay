package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.quizapp.application.ApplicationClass;
import com.example.quizapp.model.Contest;
import com.example.quizapp.model.ContestSave;
import com.example.quizapp.model.GetUserContestState;
import com.example.quizapp.model.QuestionResponseListItem;
import com.example.quizapp.model.QuestionsItem;
import com.example.quizapp.model.UserResponse;
import com.example.quizapp.network.ApiInterFace;
import com.example.quizapp.network.UserApiInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextField,questionView,difficultyLevel, marks, imageQuestion, videoQuestion, audioQuestion;
    private Button nextButton;
    private VideoView videoView;
    List<QuestionResponseListItem> questionResponseListItems = new ArrayList<>();
    UserResponse userResponse = new UserResponse();
    private int i=0;

    private Contest contest;
    CountDownTimer countDownTimer;
    private MediaController mediaController;

    private ApiInterFace apiInterFace;
    private  UserApiInterface userApiInterface;

    private RadioButton option1,option2,option3,option4;
    private RadioGroup radioGroup;
    private CheckBox chOption1,chOption2,chOption3,chOption4;

    private ImageView imageView,playPause;

    private LinearLayout layout, image_linear, video_linear, audio_linear;

    private MediaPlayer mp;
    List<QuestionsItem> listquestions;
    private ProgressBar progressBar;

    long duration;

    private  void addAnswer(){
        List<String> answerList = new ArrayList<>();
        if(radioGroup.getVisibility() == View.VISIBLE){
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if(radioButton != null) {
                answerList.add(radioButton.getText().toString());
            }else{
                Toast.makeText(this, "Select Answer!", Toast.LENGTH_SHORT).show();
            }
            System.out.println(answerList.toString());
        }else{
            if(chOption1.isChecked()) {
                answerList.add(chOption1.getText().toString());
                chOption1.setChecked(false);
                //radioGroup.clearCheck();
            }
            else if(chOption2.isChecked()) {
                answerList.add(chOption2.getText().toString());
                chOption2.setChecked(false);
                //radioGroup.clearCheck();
            }
            else if(chOption3.isChecked()) {
                answerList.add(chOption3.getText().toString());
                chOption3.setChecked(false);
                //radioGroup.clearCheck();
            }
            else if(chOption4.isChecked()) {
                answerList.add(chOption4.getText().toString());
                chOption4.setChecked(false);
                //radioGroup.clearCheck();
            }
        }

        QuestionResponseListItem responseItem = new QuestionResponseListItem();
        responseItem.setQuestionId(listquestions.get(i).getQuestionId());

        if (listquestions.get(i).getAnswer().equals(answerList)) {
            responseItem.setScore(Integer.parseInt(marks.getText().toString()));
        } else {
            responseItem.setScore(0);
        }
        questionResponseListItems.add(responseItem);
        i++;
        radioGroup.clearCheck();
    }

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
        //radioGroup.clearCheck();

        this.marks.setText(marks+"");
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
                            mediaController = new MediaController(MainActivity.this);
                            videoView.setMediaController(mediaController);
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

        progressBar.setVisibility(View.VISIBLE);
        apiInterFace.getById(id).enqueue(
                new Callback<Contest>() {
                    @Override
                    public void onResponse(Call<Contest> call, Response<Contest> response) {
                        if(response.isSuccessful()&& response.body()!=null)
                        {
                            contest = response.body();
                            duration=contest.getDuration()*1000L;
                            listquestions = contest.getQuestions();
                            progressBar.setVisibility(View.GONE);
                            setScreen(listquestions.get(i));
                            //addAnswer(0);
                            Log.i("response", listquestions.toString());
                          // Toast.makeText(MainActivity.this, "got response"+response.body(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "empty response", Toast.LENGTH_SHORT).show();
                            Log.i("response empty", listquestions.toString());
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Contest> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error",t.getMessage());
                        progressBar.setVisibility(View.GONE);
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
        progressBar=findViewById(R.id.progressBar_main);

        apiInterFace=((ApplicationClass)getApplication()).retrofit.create(ApiInterFace.class);
        userApiInterface = ((ApplicationClass) getApplication()).userRetrofit.create(UserApiInterface.class);

        Intent contestIntent=getIntent();
        contest = (Contest) contestIntent.getSerializableExtra("newContest");
        getContest(contest.getContestId());

        userApiInterface.getContestState("1", contest.getContestId()).enqueue(new Callback<GetUserContestState>() {
            @Override
            public void onResponse(Call<GetUserContestState> call, Response<GetUserContestState> response) {
                if(response.body() != null) {
                    GetUserContestState contestState = response.body();
                    long remainingTime = contestState.getRemainingTime();
                    long timeLeft = contestState.getTimeLeft();
                    long presentTime = new Date().getTime();
                    Log.e("state response", response.body().toString());
                    if (presentTime - timeLeft < remainingTime) {
                        i = response.body().getIndex();
                        duration = remainingTime - (presentTime - timeLeft);
                    } else {
                        Toast.makeText(MainActivity.this, "Time Up!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ContestsActivity.class));
                        finish();
                    }
                }else{
                    Log.e("state response null", "null state");
                    duration = contest.getDuration() * 1000L;
                }
            }

            @Override
            public void onFailure(Call<GetUserContestState> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("save error", t.getLocalizedMessage());
                duration = contest.getDuration() * 1000L;

            }
        });

        mp=new MediaPlayer();

        countDownTimer = new CountDownTimer(20 * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText( ""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                addAnswer();
                userResponse.setQuestionResponseList(questionResponseListItems);
                userResponse.setContestId(contest.getContestId());
                userResponse.setUserId("1");
                userResponse.setContestStatus("completed");
                userResponse.setQuestionResponseList(questionResponseListItems);
                userResponse.setTimeStamp(new Date().getTime());

                userApiInterface.getQuestionResponse(userResponse).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
//                        Log.i("user response", response.body().toString());
                        Toast.makeText(MainActivity.this, "score = " + response.body(), Toast.LENGTH_SHORT).show();
                        Intent leader=new Intent(MainActivity.this,LeaderBoard.class);
                        leader.putExtra("contestId",contest.getContestId());
                        startActivity(leader);
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.i("user fail", t.getLocalizedMessage());
                    }
                });
//              obj.cancel();
//                if(i < listquestions.size()) {
//                    setScreen(listquestions.get(i));
//                    i+=1;
//                    // obj.start();
//                }
            }
        }.start();



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioGroup.getVisibility() == View.VISIBLE && radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "Select an answer!", Toast.LENGTH_SHORT).show();
                }else{
                    if(i < listquestions.size()-1) {
                        addAnswer();
                        setScreen(listquestions.get(i));
                    }else{
                        addAnswer();
                        userResponse.setQuestionResponseList(questionResponseListItems);
                        userResponse.setContestId(contest.getContestId());
                        userResponse.setUserId("1");
                        userResponse.setContestStatus("completed");
                        userResponse.setQuestionResponseList(questionResponseListItems);
                        userResponse.setTimeStamp(new Date().getTime());

                        userApiInterface.getQuestionResponse(userResponse).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Log.i("user response", response.body().toString());
                                Toast.makeText(MainActivity.this, "score = " + response.body(), Toast.LENGTH_SHORT).show();
                                Intent leader=new Intent(MainActivity.this,LeaderBoard.class);
                                leader.putExtra("contestId",contest.getContestId());
                                startActivity(leader);
                                finish();
                            }
                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.i("user fail", t.getLocalizedMessage());
                            }
                        });
                    }
                }
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

        userApiInterface.putContext("1", contestSave).enqueue(new Callback<GetUserContestState>() {
            @Override
            public void onResponse(Call<GetUserContestState> call, Response<GetUserContestState> response) {
                Toast.makeText(MainActivity.this, "state saved!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetUserContestState> call, Throwable t) {

            }
        });

        userResponse.setQuestionResponseList(questionResponseListItems);
        userResponse.setContestId(contest.getContestId());
        userResponse.setUserId("1");
        userResponse.setContestStatus("not completed");
        userResponse.setQuestionResponseList(questionResponseListItems);
        userResponse.setTimeStamp(new Date().getTime());

        userApiInterface.getQuestionResponse(userResponse).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //Log.i("user response", response.body().toString());
                Toast.makeText(MainActivity.this, "score = " + response.body(), Toast.LENGTH_SHORT).show();
                Intent leader=new Intent(MainActivity.this,LeaderBoard.class);
                leader.putExtra("contestId",contest.getContestId());
                startActivity(leader);
                finish();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("user fail", t.getLocalizedMessage());
            }
        });
    }
}