package com.android.samplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button dot;
    private Button equal;
    private Button mul;
    private Button div;
    private Button plus;
    private Button minus;
    private Button del;
    private TextView data;
    Character op = 'q';
    private String str ="";
    private double num,numtemp;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zero = (Button)findViewById(R.id.button0);
        one = (Button)findViewById(R.id.button1);
        two = (Button)findViewById(R.id.button2);
        three = (Button)findViewById(R.id.button3);
        four = (Button)findViewById(R.id.button4);
        five = (Button)findViewById(R.id.button5);
        six = (Button)findViewById(R.id.button6);
        seven = (Button)findViewById(R.id.button7);
        eight = (Button)findViewById(R.id.button8);
        nine = (Button)findViewById(R.id.button9);
        dot = (Button)findViewById(R.id.buttonDot);
        div = (Button)findViewById(R.id.buttonDiv);
        mul = (Button)findViewById(R.id.buttonMul);
        plus = (Button)findViewById(R.id.buttonPlus);
        equal = (Button)findViewById(R.id.buttonEqual);
        minus = (Button)findViewById(R.id.buttonMinus);
        del = (Button)findViewById(R.id.buttonDel);

        data = (TextView)findViewById(R.id.textdata);


    }

    public void buttonDotClicked(View v) {
        str = str + ".";
        dot.setEnabled(false);
        display();
    }

    public void button0Clicked(View v) {
        insert(0);
    }

    public void button1Clicked(View v) {
        insert(1);
    }

    public void button2Clicked(View v) {
        insert(2);
    }

    public void button3Clicked(View v) {
        insert(3);
    }

    public void button4Clicked(View v) {
        insert(4);

    }

    public void button5Clicked(View v) {
        insert(5);
    }

    public void button6Clicked(View v) {
        insert(6);
    }

    public void button7Clicked(View v) {
        insert(7);
    }

    public void button8Clicked(View v) {
        insert(8);
    }

    public void button9Clicked(View v) {
        insert(9);
    }

    public void buttonPlusClicked(View v) {
        op = '+';
        perform();
    }

    public void buttonMinusClicked(View v) {
        op = '-';
        perform();
    }

    public void buttonDivClicked(View v) {
        op = '/';
        perform();
    }

    public void buttonMulClicked(View v) {
        op = '*';
        perform();
    }

    public void buttonEqualClicked(View v) {
        calculate();
    }

    public void buttonDelClicked(View v) {
        reset();
    }

    private void reset() {
        str ="";
        op ='q';
        num = 0;
        numtemp = 0;
        data.setText("");
    }

   private void insert(double n) {
        try {
            str = str + Integer.toString((int) n);

            num = Double.valueOf(str);
        }
       catch (NumberFormatException e) {}
       display();
    }

    private void perform() {
        String s = op.toString();
        data.setText(s);
        str = "";
        numtemp = num;
    }

    private void calculate() {
        if(op == '+')
            num = numtemp+num;
        else if(op == '-')
            num = numtemp- num;
        else if(op == '/')
            num = numtemp/num;
        else if(op == '*')
            num = numtemp*num;
        data.setText(""+num);
    }

    private void display() {
        data.setText(str);
    }
}
