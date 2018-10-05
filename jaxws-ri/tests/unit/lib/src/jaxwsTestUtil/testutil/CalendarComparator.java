/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * $Id: CalendarComparator.java,v 1.1 2005/10/07 22:46:08 kk122374 Exp $
 */

package testutil;

import java.util.Date;
import java.util.Calendar;
import java.lang.reflect.*;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class CalendarComparator extends junit.framework.Assert {

    public static boolean compareCalendars(Calendar cal1, Calendar cal2) throws Exception {
        if (cal1 == cal2)
            return true;
        if ((cal1 == null && cal2 != null) ||
            (cal1 != null && cal2 == null)) {
            return false;
        }
        if (!compareCalendarStrings(cal1, cal2)) {
            return false;
        }

        Method getDSTSavingsMethod = TimeZone.getDefault().getClass().getMethod("getDSTSavings", null);
	return cal1.getTime().equals(cal2.getTime());

/*        int offset1 = cal1.get(Calendar.ZONE_OFFSET)+(cal1.getTimeZone().inDaylightTime(cal1.getTime()) ?
                ((Integer)getDSTSavingsMethod.invoke(cal1.getTimeZone(), null)).intValue() : 0);
        int offset2 = cal2.get(Calendar.ZONE_OFFSET)+(cal2.getTimeZone().inDaylightTime(cal2.getTime()) ?
                ((Integer)getDSTSavingsMethod.invoke(cal2.getTimeZone(), null)).intValue() : 0);
		return (cal1.getTime().equals(cal2.getTime()) && offset1 == offset2);
*/
    }

	public static boolean compareCalendarByTime(Calendar cal1, Calendar cal2) throws Exception {
		if (cal1 == cal2)
			return true;
		if ((cal1 == null && cal2 != null) ||
			(cal1 != null && cal2 == null)) {
			return false;
		}

		return cal1.getTime().equals(cal2.getTime());
	}
	
    public static void assertCalendarsAreEqual(Calendar cal1, Calendar cal2) throws Exception {
        if (cal1 == cal2)
            return;

        if (cal1 == null) {
            assertTrue("expected null calendar", cal2 == null);
            return;
        } else {
            assertTrue("expected non-null calendar", cal2 != null);
        }

        String cal1String = getCalendarString(cal1);
        String cal2String = getCalendarString(cal2);
        if (!cal1String.equals(cal2String)) {
            assertTrue("Excpected calendar: "+cal1String+" but got: "+cal2String, false);
        }

        Method getDSTSavingsMethod = TimeZone.getDefault().getClass().getMethod("getDSTSavings", null);

/*        int offset1 = cal1.get(Calendar.ZONE_OFFSET)+(cal1.getTimeZone().inDaylightTime(cal1.getTime()) ?
                ((Integer)getDSTSavingsMethod.invoke(cal1.getTimeZone(), null)).intValue() : 0);
        int offset2 = cal2.get(Calendar.ZONE_OFFSET)+(cal2.getTimeZone().inDaylightTime(cal2.getTime()) ?
                ((Integer)getDSTSavingsMethod.invoke(cal2.getTimeZone(), null)).intValue() : 0);
*/
        if (!cal1.getTime().equals(cal2.getTime())) {
            assertTrue("Expected date of: "+cal1.getTime()+" but got: "+cal2.getTime(), false);
        }
/*        if (offset1 != offset2) {
            assertTrue("Expected an offset of: "+offset1+" but got: "+offset2, false);
        }*/
    }

    public static boolean compareCalendarStrings(Calendar cal1, Calendar cal2) throws Exception {
        boolean succeed = getCalendarString(cal1).equals(getCalendarString(cal2));
        if (!succeed) {
            System.out.println("cal1: \"" +getCalendarString(cal1) + "\"");
            System.out.println("cal2: \"" +getCalendarString(cal2) + "\"");
        }
        return succeed;
    }

    public static String getCalendarString(Calendar cal) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS z G");
        TimeZone tmpzone = cal.getTimeZone();
        tmpzone.setID("Custom");
        df.setTimeZone(tmpzone);
//System.out.println("******** String1="+df.format(cal.getTime()));
        return df.format(cal.getTime());
    }

    public static boolean compareCalendarArrays(Object[] arr1, Object[] arr2) throws Exception {
        boolean retVal = true;
        if (arr1.length != arr2.length)
            return false;
        for (int i=0; i< arr1.length && retVal; i++) {
            if (arr1[i] instanceof Date || arr2[i] instanceof Date) {
                Date date1;
                Date date2;
                if (arr1[i] instanceof Date)
                    date1 = (Date)arr1[i];
                else
                    date1 = ((Calendar)arr1[i]).getTime();
                if (arr2[i] instanceof Date)
                    date2 = (Date)arr2[i];
                else
                    date2 = ((Calendar)arr2[i]).getTime();
                retVal = date1.equals(date2);
            } else {
                retVal = compareCalendars((Calendar)arr1[i], (Calendar)arr2[i]);
            }
            if (!retVal) {
                System.out.println("arr1: " + arr1[i]);
                System.out.println();
                System.out.println("arr2: " + arr2[i]);
            }
        }
        return retVal;
    }

    public static boolean compareCalendarArraysByDate(Object[] arr1, Object[] arr2) throws Exception {
        boolean retVal = true;
        if (arr1.length != arr2.length)
            return false;
        for (int i=0; i< arr1.length && retVal; i++) {
            Date date1;
            Date date2;
            if (arr1[i] instanceof Date)
                date1 = (Date)arr1[i];
            else
                date1 = ((Calendar)arr1[i]).getTime();
            if (arr2[i] instanceof Date)
                date2 = (Date)arr2[i];
            else
                date2 = ((Calendar)arr2[i]).getTime();
            retVal = date1.equals(date2);
            if (!retVal) {
                System.out.println("arr1: " + arr1[i]);
                System.out.println();
                System.out.println("arr2: " + arr2[i]);
            }
        }
        return retVal;
    }
}
