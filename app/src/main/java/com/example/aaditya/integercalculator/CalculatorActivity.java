package com.example.aaditya.integercalculator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTextChanged;

import static com.example.aaditya.integercalculator.Operation.DIVIDE;
import static com.example.aaditya.integercalculator.Operation.MINUS;
import static com.example.aaditya.integercalculator.Operation.MULTIPLY;
import static com.example.aaditya.integercalculator.Operation.PLUS;

public class CalculatorActivity extends Activity {

    @BindView(R.id.button7) Button button7;
    @BindView(R.id.button8) Button button8;
    @BindView(R.id.button9) Button button9;
    @BindView(R.id.button4) Button button4;
    @BindView(R.id.button5) Button button5;
    @BindView(R.id.button6) Button button6;
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;
    @BindView(R.id.button3) Button button3;
    @BindView(R.id.buttonClear) Button buttonClear;
    @BindView(R.id.button0) Button button0;
    @BindView(R.id.buttonEqual) Button buttonEqual;
    @BindView(R.id.buttonplus) Button buttonplus;
    @BindView(R.id.buttonDiv) Button buttonDiv;
    @BindView(R.id.buttonMul) Button buttonMul;
    @BindView(R.id.buttonMinus) Button buttonMinus;
    @BindView(R.id.display) TextView display;
    @BindView(R.id.root) LinearLayout root;

    private Operation operation = null;
    private String firstOperand = null;
    private boolean operationCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);

        HashMap < String, Object> savedValues
                = (HashMap<String, Object>)this.getLastNonConfigurationInstance();

        if (savedValues == null)
            return;

        operation = (Operation) savedValues.get("operation");
        firstOperand = (String)savedValues.get("firstOperand");
        display.setText((String)savedValues.get("display"));
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        HashMap<String, Object> savedValues = new HashMap<String, Object>();
        savedValues.put("display", display.getText());
        savedValues.put("operation", operation);
        savedValues.put("firstOperand", firstOperand);
        return savedValues;
    }

    private boolean isValidLength() {
        if (display.length() < 7) {
            return true;
        }

        overFlowMessage();
        return false;
    }

    private void overFlowMessage() {
        //Snackbar.make(root, "Overflow Error", Snackbar.LENGTH_SHORT).show();
        reset();
        display.setText("OVERFLOW");
    }

    /*@OnTextChanged(R.id.display)
    public void checkDisplay() {
        if (Integer.parseInt(display.getText().toString()) == 0 && operation == null) {
            buttonClear.setText("AC");
            return;
        }
        int value = Integer.parseInt(display.getText().toString());
        if (value == 0) {
            return;
        }

        buttonClear.setText("C");
    }*/

    @OnClick({R.id.button7, R.id.button8, R.id.button9, R.id.button4, R.id.button5, R.id.button6, R.id.button1, R.id.button2, R.id.button3, R.id.button0})
    public void onNumberClick(View view) {
        /*switch (view.getId()) {
            case R.id.button7:
                showDisplay("7");
                break;
            case R.id.button8:
                showDisplay("8");
                break;
            case R.id.button9:
                showDisplay("9");
                break;
            case R.id.button4:
                showDisplay("4");
                break;
            case R.id.button5:
                showDisplay("5");
                break;
            case R.id.button6:
                showDisplay("6");
                break;
            case R.id.button1:
                showDisplay("1");
                break;
            case R.id.button2:
                showDisplay("2");
                break;
            case R.id.button3:
                showDisplay("3");
                break;
            case R.id.button0:
                showDisplay("0");
        }*/
        String value = ((Button)findViewById(view.getId())).getText().toString();
        showDisplay(value);
    }

    @OnLongClick(R.id.buttonClear)
    public boolean onLongPressClear() {
        display.setText(String.valueOf(0));
        buttonClear.setText("AC");
        operation = null;
        firstOperand = null;

        return true;
    }

    //TODO : optimize this block
    @OnClick({R.id.buttonplus, R.id.buttonDiv, R.id.buttonMul, R.id.buttonMinus})
    public void onOperationClick(View view) {
        if ( operation != null && firstOperand != null) {
            performOperation();
        }
        switch (view.getId()) {
            case R.id.buttonplus:
                operation = PLUS;
                break;
            case R.id.buttonDiv:
                operation = DIVIDE;
                break;
            case R.id.buttonMul:
                operation = MULTIPLY;
                break;
            case R.id.buttonMinus:
                operation = MINUS;
                break;
        }
    }

    public void performOperation() {
        int result = 0;
        int operand1 = Integer.parseInt(firstOperand);
        int operand2 = Integer.parseInt(display.getText().toString());
        switch (operation) {
            case PLUS :
                result = PLUS.apply(operand1, operand2);
                break;
            case MINUS:
                result = MINUS.apply(operand1,operand2);
                break;
            case MULTIPLY:
                result = MULTIPLY.apply(operand1, operand2);
                break;
            case DIVIDE:
                try {
                    if (operand2 == 0) {
                        throw new ArithmeticException("Argument 'divisor' is 0");
                    }
                    result = DIVIDE.apply(operand1, operand2);
                } catch (ArithmeticException e) {
                    overFlowMessage();
                    display.setText("ERROR");
                    return;
                }
        }
        firstOperand = null;
        operation = null;

        if (result > 9999999 || result < -9999999) {
            display.setText(String.valueOf(0));

            overFlowMessage();

            return;
        }
        display.setText(String.valueOf(result));
    }

    @OnClick(R.id.buttonEqual)
    public void onEqualClick() {
        if (firstOperand == null) {
            overFlowMessage();
            display.setText("ERROR");
            return;
        }
        if (operation != null && firstOperand != null) {
            operationCompleted = true;
            performOperation();
            return;
        }

    }

    @OnClick(R.id.buttonClear)
    public void onClear() {
        reset();
    }

    private void reset() {
        display.setText("0");
        operationCompleted = false;
        operation = null;
        firstOperand = null;
    }

    private void showDisplay(String number) {
        if (display.getText().equals("OVERFLOW") || display.getText().equals("ERROR")) {
            display.setText(number);
            operationCompleted = false;

            return;
        }

        if (operation != null && firstOperand == null) {
            operationCompleted = false;
            firstOperand = display.getText().toString();
            display.setText(number);

            return;
        }

        int value = Integer.parseInt(display.getText().toString());
        if (value == 0) {
            operationCompleted = false;
            display.setText(number);
            return;
        }

        if (operationCompleted) {
            display.setText(number);
            operationCompleted = false;
            return;
        }

        if (isValidLength()) {
            display.setText(display.getText() + number);
        }

    }

}
