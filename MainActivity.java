package com.example.midterm_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultTextView;
    private StringBuilder currentNumber;
    private double firstNumber;
    private double secondNumber;
    private double memory;
    private String currentOperation; //연산을 수행할 연산자를 따로 저장하기 위한 변수

    private String currentOperation_temp = null;
    private NumberFormat decimalFormat;

    private int repeat_flag = 0;

    double result = 0;

    private boolean isResultDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result_text_view);
        currentNumber = new StringBuilder();
        decimalFormat = new DecimalFormat("#.##########");

        for (int i = 0; i < 10; i++) {
            int id = getResources().getIdentifier("button_" + i, "id", getPackageName());
            findViewById(id).setOnClickListener(this);
        }

        findViewById(R.id.button_dot).setOnClickListener(this);
        findViewById(R.id.button_plus).setOnClickListener(this);
        findViewById(R.id.button_minus).setOnClickListener(this);
        findViewById(R.id.button_Multiply).setOnClickListener(this);
        findViewById(R.id.button_divide).setOnClickListener(this);
        findViewById(R.id.button_modulus).setOnClickListener(this);
        findViewById(R.id.button_sign_change).setOnClickListener(this);
        findViewById(R.id.button_equal).setOnClickListener(this);
        //findViewById(R.id.button_percent).setOnClickListener(this);
        findViewById(R.id.button_CE).setOnClickListener(this);
        findViewById(R.id.button_C).setOnClickListener(this);
        findViewById(R.id.button_Erase).setOnClickListener(this);
        findViewById(R.id.button_reciprocal).setOnClickListener(this);
        findViewById(R.id.button_square).setOnClickListener(this);
        findViewById(R.id.button_root).setOnClickListener(this);
        findViewById(R.id.button_MC).setOnClickListener(this);
        findViewById(R.id.button_MR).setOnClickListener(this);
        findViewById(R.id.button_MP).setOnClickListener(this);
        findViewById(R.id.button_MM).setOnClickListener(this);
        findViewById(R.id.button_MS).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if ("0123456789.".contains(buttonText)) { // 숫자나 점을 누르면

            if (isResultDisplayed) {
                currentNumber.setLength(0);
                isResultDisplayed = false;
            }
            currentNumber.append(buttonText);
            resultTextView.setText(currentNumber.toString());
            //currentNumber.append(buttonText);   // 텍스트에 추가
            //resultTextView.setText(currentNumber.toString()); // 추가한 텍스트를 표시한다.
        } else if ("+-x÷%".contains(buttonText)) {   // 연산 버튼을 누른 경우
            if (currentNumber.length() > 0) {   // 입력된 문자가 0모다 큰 경우
                firstNumber = Double.parseDouble(currentNumber.toString()); // 입력된 문자를 double 자료형으로 변환해서 firstNumber에 저장한다
                // 이 숫자가 연산을 수행할 첫번 쨰 숫자이다.
                currentNumber.setLength(0);  //  방금전에 입력했던 currentNumber를 지워서 두번째 숫자를 입력 받을 준비를 한다.
                currentOperation = buttonText;  // 앞으로 수행 할 연산자를 currentOperation 에 저장한다.
            }
        } else {  //  나머지 연산버튼에 대한 처리를 하는 부분이다.
            switch (view.getId()) {
                case R.id.button_sign_change:
                    if (!currentNumber.toString().isEmpty()) { // 현제 입력된 숫자가 있는 경우에
                        double tempNumber = Double.parseDouble(currentNumber.toString());
                        tempNumber = -tempNumber;
                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(tempNumber));
                        resultTextView.setText(currentNumber.toString()); // 부호를 바꿔준 다음 다시 표시 화면에 표시
                    }
                    break;

                case R.id.button_equal:
                    if (!currentNumber.toString().isEmpty() && currentOperation != null) { // 숫자 + 연산자 + 숫자 입력이 완료된 상황에서
                        secondNumber = Double.parseDouble(currentNumber.toString()); // 2번째 입력한 숫자를 secondNumberd에 저장한다.
                        // double result = 0;
                        // 조건에 따라 연산를 수행
                        switch (currentOperation) {
                            case "+":
                                result = firstNumber + secondNumber;
                                break;
                            case "-":
                                result = firstNumber - secondNumber;
                                break;
                            case "x":
                                result = firstNumber * secondNumber;
                                break;
                            case "÷":
                                result  = firstNumber / secondNumber;
                                break;
                            case "%":
                                result = firstNumber % secondNumber;
                                break;
                        }

                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(result));
                        resultTextView.setText(currentNumber.toString());
                        currentOperation_temp =  currentOperation;
                        currentOperation = null;
                        isResultDisplayed = true;
                    }

                    else if(currentOperation_temp != null){ // 연산을 한 후에 다시 = 을 눌렀을 때 결과값에 기존에 했던 연산을 반복한다.

                        switch (currentOperation_temp) {
                            case "+":
                                result = result + secondNumber;
                                break;
                            case "-":
                                result = result - secondNumber;
                                break;
                            case "x":
                                result = result * secondNumber;
                                break;
                            case "÷":
                                result  = result / secondNumber;
                                break;
                            case "%":
                                result = result % secondNumber;
                                break;
                        }

                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(result));
                        resultTextView.setText(currentNumber.toString());
                        isResultDisplayed = true;


                    }
                    break;

                case R.id.button_CE: // 현제 숫자 초기화
                    result = 0;
                    currentNumber.setLength(0);
                    resultTextView.setText("0");
                    break;

                case R.id.button_C:  // 모든 계산기록 초기화
                    currentNumber.setLength(0);
                    firstNumber = 0;
                    secondNumber = 0;
                    currentOperation = null;
                    resultTextView.setText("0");
                    break;

                case R.id.button_Erase:  // 입력된 숫자를 하나씩 지우는 연산
                    if (currentNumber.length() > 0) {
                        currentNumber.setLength(currentNumber.length() - 1);
                        if (currentNumber.length() == 0) {
                            resultTextView.setText("0");
                        } else {
                            resultTextView.setText(currentNumber.toString());
                        }
                    }
                    break;

                case R.id.button_reciprocal: // 역수연산
                    if (!currentNumber.toString().isEmpty()) {
                        double tempNumber = Double.parseDouble(currentNumber.toString());
                        tempNumber = 1 / tempNumber;
                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(tempNumber));
                        resultTextView.setText(currentNumber.toString());
                    }
                    break;

                case R.id.button_square:  // 제곱연산
                    if (!currentNumber.toString().isEmpty()) {
                        double tempNumber = Double.parseDouble(currentNumber.toString());
                        tempNumber = tempNumber * tempNumber;
                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(tempNumber));
                        resultTextView.setText(currentNumber.toString());
                    }
                    break;

                case R.id.button_root:  // 루트연산
                    if (!currentNumber.toString().isEmpty()) {
                        double tempNumber = Double.parseDouble(currentNumber.toString());
                        tempNumber = Math.sqrt(tempNumber);
                        currentNumber.setLength(0);
                        currentNumber.append(decimalFormat.format(tempNumber));
                        resultTextView.setText(currentNumber.toString());
                    }
                    break;

                case R.id.button_MC: // 메모리 값을 0으로 초기화
                    memory = 0;
                    break;

                case R.id.button_MR: // 메모리값 읽기 :  현제 메모리 값을 읽어서 불러온다.
                    currentNumber.setLength(0);
                    currentNumber.append(decimalFormat.format(memory));
                    resultTextView.setText(currentNumber.toString());
                    break;

                case R.id.button_MP: //메모리값 =  현재메모리에 저장된 값 - 현제 입력된 값
                    if (!currentNumber.toString().isEmpty()) {
                        memory += Double.parseDouble(currentNumber.toString());
                    }
                    break;

                case R.id.button_MM://메모리값 = 현재메모리에 저장된 값 - 현제 입력된 값
                    if (!currentNumber.toString().isEmpty()) {
                        memory -= Double.parseDouble(currentNumber.toString());
                    }
                    break;

                case R.id.button_MS: // 현제 입력된 값을 메모리에 저장한다.
                    if (!currentNumber.toString().isEmpty()) {
                        memory = Double.parseDouble(currentNumber.toString());
                    }
                    break;
            }
        }
    }
}