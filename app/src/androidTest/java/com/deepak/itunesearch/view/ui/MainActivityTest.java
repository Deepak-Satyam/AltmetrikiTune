package com.deepak.itunesearch.view.ui;

import android.view.View;

import com.deepak.itunesearch.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity=null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mainActivity.findViewById(R.id.item_count);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}