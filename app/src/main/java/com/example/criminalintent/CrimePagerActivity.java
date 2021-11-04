package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "EXTRA_CRIME_ID";
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Button mJumpToFirstButton;
    private Button mJumpToLastButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        mJumpToFirstButton = (Button) findViewById(R.id.jump_first_button);
        mJumpToLastButton = (Button) findViewById(R.id.jump_last_button);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId(), position);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mJumpToFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        mJumpToLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                if (i == 0) {
                    mJumpToFirstButton.setEnabled(false);
                    mJumpToLastButton.setEnabled(true);
                }

                if (i == mCrimes.size() - 1) {
                    mJumpToFirstButton.setEnabled(true);
                    mJumpToLastButton.setEnabled(false);
                }
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mJumpToFirstButton.setEnabled(false);
                    mJumpToLastButton.setEnabled(true);
                } else if (position == mCrimes.size() - 1) {
                    mJumpToFirstButton.setEnabled(true);
                    mJumpToLastButton.setEnabled(false);
                } else {
                    mJumpToLastButton.setEnabled(true);
                    mJumpToFirstButton.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
        return intent;
    }
}
