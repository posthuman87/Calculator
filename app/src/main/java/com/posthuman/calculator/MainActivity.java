package com.posthuman.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvLCD;
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button btnPlus, btnMulti, btnMinus, btnEqual, btnClear, btnDiv, btnPoint, btnSquare, btnRadical, btnBack;
    double operand1, operand2;
    int flagAction;
    int[] bt_ids;
    Button[] bt_array;
    int len;
    double result;
    Boolean flagPoint;
    int degree_before, degree_after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bt_ids = new int[]{R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive,
                R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine, R.id.btnZero,
                R.id.btnPlus, R.id.btnMinus,R.id.btnMulti, R.id.btnDiv, R.id.btnEqual,
                R.id.btnClear, R.id.btnPoint, R.id.btnSquare, R.id.btnRadical, R.id.btnBack};
        bt_array = new Button[]{btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven,
                btnEight, btnNine, btnZero, btnPlus, btnMinus, btnMulti, btnDiv, btnEqual,
                btnClear, btnPoint, btnSquare, btnRadical, btnBack};
        len = bt_array.length;
        for(int i = 0; i < len; i++){
            bt_array[i] = (Button) findViewById(bt_ids[i]);
            bt_array[i].setOnClickListener(this);
        }

        clearVariables();
        tvLCD = (TextView) findViewById(R.id.tvLCD);
        showNumber(operand1);

    }

    private void ClickNumber(int num) {
        if(flagAction == 0){
            if(flagPoint){
                degree_after++;
                if(degree_after <= 2){
                    operand1 += num / Math.pow(10, degree_after);
                }else{
                    degree_after = 2;
                    Toast.makeText(this, R.string.limit_after, Toast.LENGTH_LONG).show();
                }
            }else{
                degree_before++;
                if(degree_before <= 8){
                    operand1 = operand1*10 + num;
                }else{
                    degree_before = 8;
                    Toast.makeText(this, R.string.limit_before, Toast.LENGTH_LONG).show();
                }
            }
            showNumber(operand1);
        }else{
            if(flagPoint){
                degree_after++;
                if(degree_after <= 2){
                    operand2 += num / Math.pow(10, degree_after);
                }else{
                    degree_after = 2;
                    Toast.makeText(this, R.string.limit_after, Toast.LENGTH_SHORT).show();
                }
            }else{
                degree_before++;
                if(degree_before <= 8){
                    operand2 = operand2*10 + num;
                }else{
                    degree_before = 8;
                    Toast.makeText(this, R.string.limit_before, Toast.LENGTH_SHORT).show();
                }
            }
            showNumber(operand2);
        }
    }

    private void showNumber(double num){
        String corr = "";
        if(num > Integer.MAX_VALUE){
            tvLCD.setText("ERROR");
            Toast.makeText(this, R.string.degree_overflow, Toast.LENGTH_LONG).show();
        }else{
            if(num%1 == 0 & degree_after == 0){
                tvLCD.setText(Integer.toString((int) num));
            }else{
                int part_int = (int) num;
                int part_frac = (int) Math.round(num%1 * Math.pow(10, degree_after));
                if(degree_after == 2 & part_frac < 10)corr = "0";
                tvLCD.setText(part_int + "." + corr + part_frac);
            }
        }
    }


    private void clearVariables(){
        operand1 = 0;
        operand2 = 0;
        result = 0;
        flagAction = 0;
        clearDegree();
    }

    private void clearDegree(){
        degree_before = 0;
        degree_after = 0;
        flagPoint = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                ClickNumber(1);
                break;
            case R.id.btnTwo:
                ClickNumber(2);
                break;
            case R.id.btnThree:
                ClickNumber(3);
                break;
            case R.id.btnFour:
                ClickNumber(4);
                break;
            case R.id.btnFive:
                ClickNumber(5);
                break;
            case R.id.btnSix:
                ClickNumber(6);
                break;
            case R.id.btnSeven:
                ClickNumber(7);
                break;
            case R.id.btnEight:
                ClickNumber(8);
                break;
            case R.id.btnNine:
                ClickNumber(9);
                break;
            case R.id.btnZero:
                ClickNumber(0);
                break;
            case R.id.btnPlus:
                if (flagAction == 0) {
                    flagAction = 1;
                    clearDegree();
                }
                break;
            case R.id.btnMinus:
                if (flagAction == 0) {
                    flagAction = 2;
                    clearDegree();
                }
                break;
            case R.id.btnMulti:
                if (flagAction == 0) {
                    flagAction = 3;
                    clearDegree();
                }
                break;
            case R.id.btnDiv:
                if (flagAction == 0) {
                    flagAction = 4;
                    clearDegree();
                }
                break;
            case R.id.btnPoint:
                flagPoint = true;
                break;
            case R.id.btnSquare:
                if(flagAction == 0){
                    result = Math.pow(operand1, 2);
                    if(result%1 != 0)degree_after = 2;
                    showNumber(result);
                    clearVariables();
                }else{
                    Toast.makeText(this, R.string.other_operation, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRadical:
                if(flagAction == 0){
                    result = Math.sqrt(operand1);
                    if(result%1 != 0)degree_after = 2;
                    showNumber(result);
                    clearVariables();
                }else{
                    Toast.makeText(this, R.string.other_operation, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnBack:
                String Temp;
                if(flagAction == 0){
                    if(operand1%1 == 0 & degree_after == 0){
                        Temp = Integer.toString((int)operand1);
                    }else{Temp = Double.toString(operand1);}
                    Temp = Temp.substring(0, Temp.length()-1);
                    if(Temp.length() > 0){
                        operand1 = Double.parseDouble(Temp);
                    }else{operand1 = 0;}
                    showNumber(operand1);
                }else{
                    if(operand2%1 == 0 & degree_after == 0){
                        Temp = Integer.toString((int)operand2);
                    }else{Temp = Double.toString(operand2);}
                    Temp = Temp.substring(0, Temp.length()-1);
                    if(Temp.length() > 0){
                        operand2 = Double.parseDouble(Temp);
                    }else{operand2 = 0;}
                    showNumber(operand2);
                }
                if(degree_after > 0){
                    degree_after--;
                }else{
                    flagPoint = false;}
                break;
            case R.id.btnEqual:
                switch (flagAction) {
                    case 1:
                        result = operand1 + operand2;
                        break;
                    case 2:
                        result = operand1 - operand2;
                        break;
                    case 3:
                        result = operand1 * operand2;
                        break;
                    case 4:
                        result = (double) operand1 / (double) operand2;
                        break;
                    default:
                        Toast.makeText(this, R.string.no_operation, Toast.LENGTH_LONG).show();
                }
                if(result%1 != 0)degree_after = 2;
                if(flagAction != 0){
                    showNumber(result);
                    clearVariables();
                }
                break;
            case R.id.btnClear:
                clearVariables();
                showNumber(operand1);
                break;
        }
    }
}
