package com.example.serviceappjava.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<Integer> stepCounter = new MutableLiveData<>(0);
    public MutableLiveData<Integer> previousSteps = new MutableLiveData<>(0);

    public void setSteps(int steps) {
        stepCounter.setValue(steps);
    }

    public void resetSteps() {
        previousSteps.setValue(stepCounter.getValue());
    }

    public void addStep() {
        stepCounter.setValue(stepCounter.getValue() != null ? stepCounter.getValue() + 1 : 1);
    }
}