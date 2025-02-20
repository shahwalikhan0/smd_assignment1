package com.example.smd_assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private LinearLayout opt1Wrapper, opt2Wrapper, opt3Wrapper, opt4Wrapper;
    private TextView questionText;
    private TextView option1Text, option2Text, option3Text, option4Text;
    private CheckBox option1, option2, option3, option4;
    private Button nextButton, prevButton;

    private int currentQuestionIndex = 0;

    private final String[] questions = {
            "What is the capital of Pakistan?",
            "How many GBs is Android Studio?",
            "How won 2017 champions trophy?"
    };

    private final String[][] options = {
            {"Islamabad", "London", "Delhi", "Madrid"},
            {"1.2", "2.2", "2.7", "3.2"},
            {"Pakistan", "India", "Australia", "England"}
    };

    private final String[][] answers = {
            {"Islamabad"},
            {"2.7"},
            {"Pakistan"}
    };

    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        init();

        loadNextQuestion();

        nextButton.setOnClickListener(v -> {
            if (!option1.isChecked() && !option2.isChecked() && !option3.isChecked() && !option4.isChecked()) {
                Toast.makeText(this, "Please select an option!", Toast.LENGTH_SHORT).show();
                return;
            }
            storeResult();
            resetBackgrounds();
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadNextQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_SHORT).show();
                intent.putExtra("score", score);
                intent.putExtra("total", questions.length);
                startActivity(intent);
                finish();
            }
        });

        prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                if(isCorrect())
                    score--;
                currentQuestionIndex--;
                loadPreviousQuestion();
            }
            else {
                Toast.makeText(this, "First Question can;t go back!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void init () {
        opt1Wrapper = findViewById(R.id.option1Wrapper);
        opt2Wrapper = findViewById(R.id.option2Wrapper);
        opt3Wrapper = findViewById(R.id.option3Wrapper);
        opt4Wrapper = findViewById(R.id.option4Wrapper);

        questionText = findViewById(R.id.questionText);
        option1Text = findViewById(R.id.option1Text);
        option2Text = findViewById(R.id.option2Text);
        option3Text = findViewById(R.id.option3Text);
        option4Text = findViewById(R.id.option4Text);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        View.OnClickListener checkBoxListener = view -> {
            option1.setChecked(view == option1);
            option2.setChecked(view == option2);
            option3.setChecked(view == option3);
            option4.setChecked(view == option4);

            changeBackgroundOfSelectedOption();
        };

        option1.setOnClickListener(checkBoxListener);
        option2.setOnClickListener(checkBoxListener);
        option3.setOnClickListener(checkBoxListener);
        option4.setOnClickListener(checkBoxListener);
    }
    @SuppressLint("SetTextI18n")
    private void loadPreviousQuestion() {
        loadNextQuestion();
        nextButton.setText("Next");
    }
    private void loadNextQuestion() {
        resetOptions();
        fillInOptions();
        questionText.setText(questions[currentQuestionIndex]);
        nextButton.setText(currentQuestionIndex == questions.length - 1 ? "Finish" : "Next");
        prevButton.setEnabled(currentQuestionIndex > 0);
        prevButton.setVisibility(currentQuestionIndex == 0 ? View.INVISIBLE : View.VISIBLE);
    }
    private void fillInOptions() {
        option1Text.setText(options[currentQuestionIndex][0]);
        option2Text.setText(options[currentQuestionIndex][1]);
        option3Text.setText(options[currentQuestionIndex][2]);
        option4Text.setText(options[currentQuestionIndex][3]);
    }
    private void resetOptions() {
        option1.setChecked(false);
        option2.setChecked(false);
        option3.setChecked(false);
        option4.setChecked(false);
    }
    private void storeResult() {
        if (isCorrect()) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isCorrect() {
        if (option1.isChecked()) {
            return answers[currentQuestionIndex][0].equals(option1Text.getText().toString());
        } else if (option2.isChecked()) {
            return answers[currentQuestionIndex][0].equals(option2Text.getText().toString());
        } else if (option3.isChecked()) {
            return answers[currentQuestionIndex][0].equals(option3Text.getText().toString());
        } else if (option4.isChecked()) {
            return answers[currentQuestionIndex][0].equals(option4Text.getText().toString());
        }
        return false;
    }

    private void resetBackgrounds() {
        opt1Wrapper.setBackgroundResource(R.drawable.round_corner);
        opt2Wrapper.setBackgroundResource(R.drawable.round_corner);
        opt3Wrapper.setBackgroundResource(R.drawable.round_corner);
        opt4Wrapper.setBackgroundResource(R.drawable.round_corner);
    }
    private void changeBackgroundOfSelectedOption() {
        resetBackgrounds();
        if (option1.isChecked()) {
            opt1Wrapper.setBackgroundResource(R.drawable.selected_option);
        } else if (option2.isChecked()) {
            opt2Wrapper.setBackgroundResource(R.drawable.selected_option);
        } else if (option3.isChecked()) {
            opt3Wrapper.setBackgroundResource(R.drawable.selected_option);
        } else if (option4.isChecked()) {
            opt4Wrapper.setBackgroundResource(R.drawable.selected_option);
        }
    }
}