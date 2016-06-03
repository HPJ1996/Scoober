package com.example.hpj_1996.scoober;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ProblemReport extends AppCompatActivity {

    EditText inputStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);

        inputStatement = (EditText)findViewById(R.id.input_statement);
    }
}
