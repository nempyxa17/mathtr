package com.nempyxa.mathtr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nempyxa.mathtr.databinding.FragmentMathtrBinding;
import com.nempyxa.mathtr.log.Logger;
import com.nempyxa.mathtr.viewmodel.MathtrViewModel;

public class MathtrFragment extends Fragment implements NumericKeypadView.OnClickListener {

    private MathtrViewModel mViewModel;
    private FragmentMathtrBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(MathtrViewModel.class);

        mBinding = FragmentMathtrBinding.inflate(inflater, container, false);
        mBinding.setListener(this);
        mBinding.numericKeypad.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getQuestionText().observe(getViewLifecycleOwner(), text -> {
            mBinding.questionView.setText(text);
            mBinding.setAnswerText("");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
        mViewModel.checkSettingsChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    public void onAnswerTextChanged(CharSequence s, int start, int before, int count) {
        mViewModel.verify(String.valueOf(s));
    }

    @Override
    public void onClick(NumericKeypadView.Keys key) {
        String answerText = mBinding.answerView.getText().toString();
        switch (key) {
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
                answerText = answerText + key.getStringValue();
                break;
            case BACKSPACE:
                answerText = answerText.length() > 0 ? answerText.substring(0, answerText.length() - 1) : answerText;
                break;
            case NEXT:
                mViewModel.playNext();
                break;
            default:
                throw new IllegalArgumentException("Invalid key: " + key);
        }
        mBinding.setAnswerText(answerText);
    }

    @Override
    public void onLongClick(NumericKeypadView.Keys key) {
        if (key == NumericKeypadView.Keys.BACKSPACE) {
            mBinding.setAnswerText("");
        }
    }
}