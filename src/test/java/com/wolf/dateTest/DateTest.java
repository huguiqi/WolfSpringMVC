package com.wolf.dateTest;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateTest {

    @Test
    @Ignore
    public void testLastMonth(){
            Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                Assert.assertEquals("lastYeaMonth","2013-7",calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1));
    }
}
