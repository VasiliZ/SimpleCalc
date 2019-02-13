package com.github.vasiliz.clck01;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView viewCalcActions, tempValue;
    private CalcViewModel calcViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCalcActions = findViewById(R.id.view_input_textview);
        init();
    }

    private void init() {

        tempValue = findViewById(R.id.temp_textView);
        findViewById(R.id.input_one_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.one));
            }
        });
        findViewById(R.id.input_two_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.two));
            }
        });
        findViewById(R.id.input_three_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.three));
            }
        });
        findViewById(R.id.input_four_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.four));
            }
        });
        findViewById(R.id.input_five_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.five));
            }
        });
        findViewById(R.id.input_six_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.six));
            }
        });
        findViewById(R.id.input_seven_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.seven));
            }
        });
        findViewById(R.id.input_eight_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.eight));
            }
        });
        findViewById(R.id.input_nine_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.nine));
            }
        });
        findViewById(R.id.input_zero_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setClick(getResources().getString(R.string.zero));
            }
        });

        findViewById(R.id.plus_minus_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcViewModel.setMinusForNumber(getResources().getString(R.string.minus));
            }
        });

        //action

        findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.clearBuffer();
            }
        });
        findViewById(R.id.backspace_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.doBackspace();
            }
        });

        findViewById(R.id.divide_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.onAction(getResources().getString(R.string.divide));
            }
        });
        findViewById(R.id.multiply_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.onAction(getResources().getString(R.string.multiply));
            }
        });
        findViewById(R.id.minus_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.onAction(getResources().getString(R.string.minus));
            }
        });
        findViewById(R.id.plus_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.onAction(getResources().getString(R.string.plus));
            }
        });
        findViewById(R.id.equal_sign_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.calculate();
            }
        });
        findViewById(R.id.input_button_dot).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                calcViewModel.setDot(getResources().getString(R.string.dot));
            }
        });

        calcViewModel = ViewModelProviders.of(this).get(CalcViewModel.class);
        calcViewModel.tempValues.observe(this, new Observer<String>() {

            @Override
            public void onChanged(@Nullable final String s) {
                tempValue.setText(s);
            }
        });
        calcViewModel.liveData.observe(this, new Observer<String>() {

            @Override
            public void onChanged(@Nullable final String s) {
                viewCalcActions.setText(s);
            }
        });

    }
}
