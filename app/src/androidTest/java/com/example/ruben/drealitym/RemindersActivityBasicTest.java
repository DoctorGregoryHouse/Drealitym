package com.example.ruben.drealitym;

import android.widget.RelativeLayout;

import com.example.ruben.drealitym.uiclasses.RemindersActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class RemindersActivityBasicTest  {

@Rule
    public ActivityTestRule<RemindersActivity> mActivityTestRule  =
        new ActivityTestRule<>(RemindersActivity.class);


@Before
public void init(){

    mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
}


@Test
    public void clickfab_openFragment() {

    //TODO: implement this test


}


}
