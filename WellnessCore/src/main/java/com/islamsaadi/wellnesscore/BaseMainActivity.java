package com.islamsaadi.wellnesscore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.islamsaadi.wellnesscore.databinding.SharedFormInputsBinding;
import com.islamsaadi.wellnesscore.databinding.SharedHeaderBinding;

public abstract class BaseMainActivity extends AppCompatActivity {

    protected SharedHeaderBinding sharedHeader;
    protected SharedFormInputsBinding sharedFormInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void setSharedHeader();
    protected abstract void setSharedFormInputs();


}
