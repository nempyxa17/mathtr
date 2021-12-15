package com.nempyxa.mathtr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.nempyxa.mathtr.R;

public class NumericKeypadView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    private boolean mShowNext;
    private float mContentSize;
    private float mHorizontalSpacing;
    private float mVerticalSpacing;

    public enum Keys {
        ZERO("0"),
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        BACKSPACE("backspace"),
        NEXT("next");

        private final String mValue;

        Keys(String value) {
            mValue = value;
        }

        public String getStringValue() {
            return mValue;
        }
    }

    private NumericKeypadView.OnClickListener mListener;

    public NumericKeypadView(Context context) {
        super(context);
        init();
    }

    public NumericKeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumPadView, 0, 0);
        try {
            mShowNext = a.getBoolean(R.styleable.NumPadView_showNext, true);
            mContentSize = a.getDimensionPixelSize(R.styleable.NumPadView_contentSize, 30);
            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.NumPadView_spacingHorizontal, 0);
            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.NumPadView_spacingVertical, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    public NumericKeypadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.numeric_keypad, this);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.next).setVisibility(mShowNext ? VISIBLE : INVISIBLE);

        MaterialButton nine = (MaterialButton) findViewById(R.id.nine);
        MaterialButton eight = (MaterialButton) findViewById(R.id.eight);
        MaterialButton seven = (MaterialButton) findViewById(R.id.seven);
        MaterialButton six = (MaterialButton) findViewById(R.id.six);
        MaterialButton five = (MaterialButton) findViewById(R.id.five);
        MaterialButton four = (MaterialButton) findViewById(R.id.four);
        MaterialButton three = (MaterialButton) findViewById(R.id.three);
        MaterialButton two = (MaterialButton) findViewById(R.id.two);
        MaterialButton one = (MaterialButton) findViewById(R.id.one);
        MaterialButton zero = (MaterialButton) findViewById(R.id.zero);
        MaterialButton backspace = (MaterialButton) findViewById(R.id.backspace);
        MaterialButton next = (MaterialButton) findViewById(R.id.next);

        next.setVisibility(mShowNext ? VISIBLE : INVISIBLE);

        nine.setOnClickListener(this);
        eight.setOnClickListener(this);
        seven.setOnClickListener(this);
        six.setOnClickListener(this);
        five.setOnClickListener(this);
        four.setOnClickListener(this);
        three.setOnClickListener(this);
        two.setOnClickListener(this);
        one.setOnClickListener(this);
        zero.setOnClickListener(this);
        next.setOnClickListener(this);
        backspace.setOnClickListener(this);
        backspace.setOnLongClickListener(this);

        nine.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        eight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        seven.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        six.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        five.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        four.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        three.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        two.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        one.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        zero.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentSize);
        backspace.setIconSize((int) mContentSize);
        next.setIconSize((int) mContentSize);

        int horizontalMargin = (int) mHorizontalSpacing / 2;
        int verticalMargin = (int) mVerticalSpacing / 4;
        int verticalMarginSmall = verticalMargin;
        int verticalMarginMedium = verticalMargin * 2;
        int verticalMarginBig = verticalMargin * 3;
        adjustMargins(seven, 0, 0, 0, 0);
        adjustMargins(seven, 0, 0, horizontalMargin, verticalMarginBig);
        adjustMargins(eight, horizontalMargin, 0, horizontalMargin, verticalMarginBig);
        adjustMargins(nine, horizontalMargin, 0, 0, verticalMarginBig);
        adjustMargins(four, 0, verticalMarginSmall, horizontalMargin, verticalMarginMedium);
        adjustMargins(five, horizontalMargin, verticalMarginSmall, horizontalMargin, verticalMarginMedium);
        adjustMargins(six, horizontalMargin, verticalMarginSmall, 0, verticalMarginMedium);
        adjustMargins(one, 0, verticalMarginMedium, horizontalMargin, verticalMarginSmall);
        adjustMargins(two, horizontalMargin, verticalMarginMedium, horizontalMargin, verticalMarginSmall);
        adjustMargins(three, horizontalMargin, verticalMarginMedium, 0, verticalMarginSmall);
        adjustMargins(next, 0, verticalMarginBig, horizontalMargin, 0);
        adjustMargins(zero, horizontalMargin, verticalMarginBig, horizontalMargin, 0);
        adjustMargins(backspace, horizontalMargin, verticalMarginBig, 0, 0);
    }

    private void adjustMargins(View view, int left, int top, int right, int bottom) {
        MarginLayoutParams marginParams = (MarginLayoutParams) view.getLayoutParams();
        marginParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(marginParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero:
                notifyListenerClick(Keys.ZERO);
                break;
            case R.id.one:
                notifyListenerClick(Keys.ONE);
                break;
            case R.id.two:
                notifyListenerClick(Keys.TWO);
                break;
            case R.id.three:
                notifyListenerClick(Keys.THREE);
                break;
            case R.id.four:
                notifyListenerClick(Keys.FOUR);
                break;
            case R.id.five:
                notifyListenerClick(Keys.FIVE);
                break;
            case R.id.six:
                notifyListenerClick(Keys.SIX);
                break;
            case R.id.seven:
                notifyListenerClick(Keys.SEVEN);
                break;
            case R.id.eight:
                notifyListenerClick(Keys.EIGHT);
                break;
            case R.id.nine:
                notifyListenerClick(Keys.NINE);
                break;
            case R.id.backspace:
                notifyListenerClick(Keys.BACKSPACE);
                break;
            case R.id.next:
                notifyListenerClick(Keys.NEXT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.backspace) {
            notifyListenerLongClick(Keys.BACKSPACE);
            return true;
        }
        return false;
    }


    public void setOnClickListener(NumericKeypadView.OnClickListener listener) {
        mListener = listener;
    }

    private void notifyListenerClick(Keys key) {
        if (mListener != null) {
            mListener.onClick(key);
        }
    }

    private void notifyListenerLongClick(Keys key) {
        if (mListener != null) {
            mListener.onLongClick(key);
        }
    }

    public interface OnClickListener {
        void onClick(Keys key);

        void onLongClick(Keys key);
    }
}
