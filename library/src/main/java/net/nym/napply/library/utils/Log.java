/*
 * Copyright (c) 2016  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.napply.library.utils;







import net.nym.napply.library.BuildConfig;

import java.util.MissingFormatArgumentException;

public class Log {
	public static final String TAG = "MUtils";
	public static final String DEBUG_TAG = "debug.net.nym.library";
    private static boolean isDebug = BuildConfig.DEBUG;

    public static  void setDebug(boolean debug)
    {
        isDebug = debug;
    }

	public static void i(String msg, Object... args) {
		try {
			if (isDebug)
				android.util.Log.i(DEBUG_TAG, String.format(msg, args));
		} catch (MissingFormatArgumentException e) {
			android.util.Log.e(TAG, "my.Log", e);
			android.util.Log.i(TAG, msg);
		}
	}

	public static void d(String msg, Object... args) {
		try {
			if (isDebug)
				android.util.Log.d(DEBUG_TAG, String.format(msg, args));
		} catch (MissingFormatArgumentException e) {
			android.util.Log.e(TAG, "my.Log", e);
			android.util.Log.d(TAG, msg);
		}
	}
	
	public static void w(String msg, Object... args) {
		try {
			if (isDebug)
				android.util.Log.w(DEBUG_TAG, String.format(msg, args));
		} catch (MissingFormatArgumentException e) {
			android.util.Log.e(TAG, "my.Log", e);
			android.util.Log.w(TAG, msg);
		}
	}

	public static void v(String msg, Object... args) {
		try {
			if (isDebug)
				android.util.Log.v(DEBUG_TAG, String.format(msg, args));
		} catch (MissingFormatArgumentException e) {
			android.util.Log.e(TAG, "my.Log", e);
			android.util.Log.w(TAG, msg);
		}
	}

	public static void e(String msg, Object... args) {
		try {
				android.util.Log.e(DEBUG_TAG, String.format(msg, args));
		} catch (MissingFormatArgumentException e) {
			android.util.Log.e(TAG, "my.Log", e);
			android.util.Log.e(TAG, msg);
		}
	}

	public static void e(String msg, Throwable t) {
		android.util.Log.e(TAG, msg, t);
	}
	
	public static void println(String msg,Object... args)
	{
		if(isDebug)
			System.out.println(String.format(msg, args));
	}

}