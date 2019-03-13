package trd.ams.util;

import android.text.Editable;
import android.widget.TextView;

import android.text.TextWatcher;

/**
 * Created by hanu on 02-01-2018.
 */


public abstract class MeasureValidator implements TextWatcher {
    private final TextView textView;
    private  String lowerLimit = "";
    private  String upperLimit = "";


    public MeasureValidator(TextView textView, String lowerLimit, String upperLimit ) {
        this.textView = textView;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public abstract void validate(TextView textView, String text,   String lowerLimit, String upperLimit);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text, lowerLimit, upperLimit );
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}