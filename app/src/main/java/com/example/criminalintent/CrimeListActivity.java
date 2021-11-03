package com.example.criminalintent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity{
    private String TAG = "CrimeListActivity_TAG";
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() called");
    }
}
