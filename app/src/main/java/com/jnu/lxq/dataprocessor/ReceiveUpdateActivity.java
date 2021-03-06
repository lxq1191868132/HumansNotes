package com.jnu.lxq.dataprocessor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.lxq.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.sql.Types.NULL;

public class ReceiveUpdateActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextMoney;
    private EditText editTextDate;
    private Spinner spinnertext;

    private int reason_position;
    private int position;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_update_reaceive);

        initData();

        initView();

        initSpinner();

        initButton();
    }

    private void initData() {
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

    }

    private void initView(){

        spinnertext = ReceiveUpdateActivity.this.findViewById(R.id.spinner_receive_reason);
        editTextName= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_name);
        editTextMoney= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_money);
        editTextDate= ReceiveUpdateActivity.this.findViewById(R.id.edit_text_date);

        position=getIntent().getIntExtra("position",0);
        String name = getIntent().getStringExtra("name");
        String money = getIntent().getStringExtra("money");
        String date = getIntent().getStringExtra("date");
        reason_position = getIntent().getIntExtra("reason",0);

        if(null!= name)
            editTextName.setText(name);
        if(null!= money)
            editTextMoney.setText(money);
        if(null!= date)
            editTextDate.setText(date);
        if(NULL != reason_position)
            spinnertext.setSelection(reason_position);
    }

    private void initSpinner() {
        //????????????????????????????????????
        ReasonDataBank reasonDataBank = new ReasonDataBank(this);
        reasonDataBank.Load();
        ArrayList<String> list = reasonDataBank.getReasonList();
        //????????????????????????????????????????????????
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //??????????????????????????????????????????????????????
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //????????????????????????????????????????????????
        spinnertext.setAdapter(adapter);
        //??????????????????????????????????????????????????????????????????
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* ??? spinnertext ??????^*/
                parent.setVisibility(View.VISIBLE);
                reason_position = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initButton() {
        //?????????
        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("name",editTextName.getText().toString());
            intent.putExtra("money",editTextMoney.getText().toString());
            intent.putExtra("date",editTextDate.getText().toString());
            intent.putExtra("reason",reason_position);
            intent.putExtra("position",position);
            setResult(RESULT_OK, intent);
            finish();
        });


        //???????????????
        Button button_setdate = (Button)findViewById(R.id.button_setdate);
        button_setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ?????????????????????
                new DatePickerDialog(v.getContext(),onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
    }

    private final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("???").append("0").
                            append(mMonth + 1).append("???").append("0").append(mDay).append("???").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("???").append("0").
                            append(mMonth + 1).append("???").append(mDay).append("???").toString();
                }
            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("???").
                            append(mMonth + 1).append("???").append("0").append(mDay).append("???").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("???").
                            append(mMonth + 1).append("???").append(mDay).append("???").toString();
                }
            }
            editTextDate.setText(days);
        }
    };


}