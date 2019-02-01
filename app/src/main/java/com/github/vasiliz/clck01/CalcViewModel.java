package com.github.vasiliz.clck01;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class CalcViewModel extends AndroidViewModel {

    MutableLiveData<String> liveData = new MutableLiveData<>();
    MutableLiveData<String> tempValues = new MutableLiveData<>();
    private final ArrayList<String> strings = new ArrayList<>();
    private final StringBuilder calculateString = new StringBuilder();
    private final StringBuilder stringBuilder = new StringBuilder();
    private final String[] operators = {"+", "-", "/", "*"};
    private final String EMPTY_STRING = "";
    private final String END_DOUBLE_VALUE = ".0";
    private boolean isCalculate;

    private final Stack<String> stackP = new Stack<>();
    private final Stack<String> operations = new Stack<>();
    private final Stack<String> calculationStack = new Stack<>();

    public CalcViewModel(@NonNull final Application application) {
        super(application);
    }

    //todo action here

    void setClick(final String action) {
        if (isCalculate) {
            isCalculate = false;
            tempValues.setValue(calculateString.toString());
            liveData.setValue(stringBuilder.toString());
        }else {
            stringBuilder.append(action);
            liveData.setValue(stringBuilder.toString());
        }
    }

    void doBackspace() {
        if (stringBuilder.toString().isEmpty()) {
            return;
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        calculateString.delete(calculateString.length() - 1, calculateString.length());
        liveData.setValue(stringBuilder.toString());

    }

    void clearBuffer() {
        clearStringBuilder(stringBuilder);
        clearStringBuilder(calculateString);
        strings.clear();
        liveData.setValue(stringBuilder.toString());
        tempValues.setValue(calculateString.toString());
    }

    void onAction(final String action) {
        if (calculateString.toString().endsWith(action) | (calculateString.toString().isEmpty()&&stringBuilder.toString().isEmpty())) {
            return;
        }
        calculateString.append(stringBuilder);
        strings.add(stringBuilder.toString());
        calculateString.append(action);
        tempValues.setValue(calculateString.toString());
        clearStringBuilder(stringBuilder);
        liveData.setValue(stringBuilder.toString());
        strings.add(action);
    }

    public void setDot(final String dot) {
        if (stringBuilder.toString().isEmpty()){
            return;
        }
        if (!stringBuilder.toString().contains(getApplication()
                .getResources()
                .getString(R.string.dot))) {
            stringBuilder.append(dot);
            liveData.setValue(stringBuilder.toString());
        }
    }

    public void setMinusForNumber(final String minus) {
        if (!stringBuilder.toString().isEmpty()) {
            if (!stringBuilder.toString().isEmpty()
                    && stringBuilder.toString().startsWith(getApplication().getResources().getString(R.string.minus))) {
                stringBuilder.delete(0, 1);

                liveData.setValue(stringBuilder.toString());
                return;
            }
            stringBuilder.insert(0, minus);
            liveData.postValue(stringBuilder.toString());
        }

    }

    void calculate() {
        if (strings.isEmpty()) {
            return;
        }

        strings.add(stringBuilder.toString());
        tempValues.setValue(calculateString.toString());
        if (strings.get(strings.size()-1).equals(EMPTY_STRING)) {
            return;
        }

        if (isOperator(strings.get(strings.size() - 1))) {
            strings.remove(strings.get(strings.size() - 1));
            strings.trimToSize();
        }
        convertToRPN(strings);
        calculationStack.clear();
        Collections.reverse(stackP);
        try {
            while (!stackP.empty()) {
                Log.d("item", stackP.peek());
                final String token = stackP.pop();
                if (isNumber(token)) {
                    calculationStack.push(token);
                } else if (isOperator(token)) {
                    final double op1 = Double.parseDouble(calculationStack.pop());
                    final double op2 = Double.parseDouble(calculationStack.pop());
                    switch (token) {
                        case "+":
                            calculationStack.push(String.valueOf(op2 + op1));
                            break;
                        case "-":
                            calculationStack.push(String.valueOf(op2 - op1));
                            break;
                        case "*":
                            calculationStack.push(String.valueOf(op2 * op1));
                            break;
                        case "/":
                            if (op2 == 0 || op1 == 0) {
                                liveData.setValue(getApplication()
                                        .getResources()
                                        .getString(R.string.error));
                                strings.clear();

                                break;
                            }
                            final String total = String.valueOf(op2 / op1);
                            calculationStack.push(total);
                            break;
                    }
                }
            }
            final String result = calculationStack.pop();
            String cutString = "";
            if (result.endsWith(END_DOUBLE_VALUE)) {
                cutString = result.substring(0, result.lastIndexOf(getApplication()
                        .getResources()
                        .getString(R.string.dot)));
                liveData.setValue(cutString);
            } else {
                liveData.setValue(result);
            }

            clearStringBuilder(stringBuilder);
            clearStringBuilder(calculateString);
            calculateString.append(cutString);
            strings.clear();
            strings.add(cutString);
            isCalculate = true;

        } catch (final EmptyStackException e) {
            liveData.setValue(getApplication().getResources().getString(R.string.error));
        }

    }

    private void convertToRPN(final List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            if (isNumber(strings.get(i))) {
                stackP.push(strings.get(i));
            } else if (isOperator(strings.get(i))) {
                while (!operations.empty()
                        && isOperator(operations.lastElement())
                        && priority(strings.get(i))
                        <= priority(operations.lastElement())) {
                    stackP.push(operations.pop());
                }
                operations.push(strings.get(i));
            }
        }
        while (!operations.empty()) {
            stackP.push(operations.pop());
        }
    }

    private boolean isNumber(final String token) {
        try {
            Double.parseDouble(token);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    private boolean isOperator(final String token) {
        for (final String op : operators) {
            if (op.equals(token)) {
                return true;
            }
        }
        return false;
    }

    private int priority(final String operation) {
        if (operation
                .equals(getApplication()
                        .getResources()
                        .getString(R.string.minus))
                || operation
                .equals(getApplication()
                        .getResources()
                        .getString(R.string.plus))) {
            return 1;
        } else {
            return 2;
        }
    }

    public void clearStringBuilder(StringBuilder stringBuffer) {
        stringBuffer.delete(0, stringBuffer.length());
    }
}




