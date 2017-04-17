package com.jkdroid.smstransfer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.jkdroid.smstransfer.dao.MySmsDaoImpl;
import com.jkdroid.smstransfer.dao.Sms;
import com.jkdroid.smstransfer.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.jkdroid.smstransfer", appContext.getPackageName());

        MySmsDaoImpl smsDao = new MySmsDaoImpl();
        Sms sms = new Sms("1379030392", "这是第一条测试短信", System.currentTimeMillis());
        sms.setResult(1);
        smsDao.insertSms(sms);
        List<Sms> smses = smsDao.querySmsLimit(0, 10);
        Log.i("aaa", smses.size()+"");
    }
}
