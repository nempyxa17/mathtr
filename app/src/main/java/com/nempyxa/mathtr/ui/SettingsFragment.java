package com.nempyxa.mathtr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.nempyxa.mathtr.R;
import com.nempyxa.mathtr.databinding.FragmentSettingsBinding;
import com.nempyxa.mathtr.exception.InvalidSettingsException;
import com.nempyxa.mathtr.model.math.MathQuestionGenerationParameters;
import com.nempyxa.mathtr.model.math.Operation;
import com.nempyxa.mathtr.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment implements View.OnFocusChangeListener, NumericKeypadView.OnClickListener {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding mBinding;

    private EditText mFocusedInputView;
    private String mPreviousText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        mBinding.setListener(this);
        mBinding.numericKeypad.setOnClickListener(this);
        mBinding.edittextLeftMin.setShowSoftInputOnFocus(false);
        mBinding.edittextLeftMax.setShowSoftInputOnFocus(false);
        mBinding.edittextRightMin.setShowSoftInputOnFocus(false);
        mBinding.edittextRightMax.setShowSoftInputOnFocus(false);

        mBinding.edittextLeftMin.setOnFocusChangeListener(this);
        mBinding.edittextLeftMax.setOnFocusChangeListener(this);
        mBinding.edittextRightMin.setOnFocusChangeListener(this);
        mBinding.edittextRightMax.setOnFocusChangeListener(this);

        View root = mBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MathQuestionGenerationParameters parameters = mViewModel.getSetting();
        mBinding.edittextLeftMin.setText(String.valueOf(parameters.getLeftArgMin()));
        mBinding.edittextLeftMax.setText(String.valueOf(parameters.getLeftArgMax()));
        mBinding.edittextRightMin.setText(String.valueOf(parameters.getRightArgMin()));
        mBinding.edittextRightMax.setText(String.valueOf(parameters.getRightArgMax()));
        ((MaterialButton) mBinding.addition).setChecked(parameters.getOperations().contains(Operation.ADDITION));
        ((MaterialButton) mBinding.subtraction).setChecked(parameters.getOperations().contains(Operation.SUBTRACTION));
        ((MaterialButton) mBinding.multiplication).setChecked(parameters.getOperations().contains(Operation.MULTIPLICATION));
        ((MaterialButton) mBinding.division).setChecked(parameters.getOperations().contains(Operation.DIVISION));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    public void discard(@NonNull View view) {
        NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_Settings_to_Mathtr);
    }

    public void apply(@NonNull View view) {
        try {
            int leftMin = Integer.parseInt(String.valueOf(mBinding.edittextLeftMin.getText()));
            int leftMax = Integer.parseInt(String.valueOf(mBinding.edittextLeftMax.getText()));
            int rightMin = Integer.parseInt(String.valueOf(mBinding.edittextRightMin.getText()));
            int rightMax = Integer.parseInt(String.valueOf(mBinding.edittextRightMax.getText()));
            boolean addition = ((MaterialButton) mBinding.addition).isChecked();
            boolean multiplication = ((MaterialButton) mBinding.multiplication).isChecked();
            boolean division = ((MaterialButton) mBinding.division).isChecked();
            boolean subtraction = ((MaterialButton) mBinding.subtraction).isChecked();
            try {
                mViewModel.attemptSettingsChange(leftMin, leftMax, rightMin, rightMax, addition, multiplication, division, subtraction);
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_Settings_to_Mathtr);
            } catch (InvalidSettingsException ise) {
                Toast.makeText(requireActivity(), "Invalid settings: " + ise.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(requireActivity(), "Invalid number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edittext_left_min:
            case R.id.edittext_left_max:
            case R.id.edittext_right_min:
            case R.id.edittext_right_max:
                if (hasFocus) {
                    if (mPreviousText != null && mFocusedInputView != null) {
                        mFocusedInputView.setText(mPreviousText);
                    }
                    mFocusedInputView = (EditText) v;
                    mPreviousText = mFocusedInputView.getText().toString();
                    mFocusedInputView.setText("");
                }
                break;
            default:
                mFocusedInputView = null;
        }
    }

    @Override
    public void onClick(NumericKeypadView.Keys key) {
        if (mFocusedInputView == null) return;
        String text = mFocusedInputView.getText().toString();
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
                text = text + key.getStringValue();
                mPreviousText = null;
                break;
            case BACKSPACE:
                text = text.length() > 0 ? text.substring(0, text.length() - 1) : text;
                mPreviousText = null;
                break;
            default:
                throw new IllegalArgumentException("Invalid key: " + key);
        }
        mFocusedInputView.setText(text);

    }

    @Override
    public void onLongClick(NumericKeypadView.Keys key) {
        if (mFocusedInputView == null) return;
        if (key == NumericKeypadView.Keys.BACKSPACE) {
            mFocusedInputView.setText("");
        }
    }
}