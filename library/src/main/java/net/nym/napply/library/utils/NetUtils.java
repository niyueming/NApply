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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class NetUtils {

	/**
	 * 编码
	 *
	 * @param text
	 * @return
	 */
	public static String URLEncode(String text) {

		return URLEncode(text,"UTF-8");

	}

	public static String URLEncode(String text,String charsetName) {
		if (StringUtils.isNullOrEmpty(text)){
			return text;
		}
		String result = null;
		try {
			result = URLEncoder.encode(text, charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 解码
	 *
	 * @param text
	 * @return
	 */
	public static String URLDecode(String text) {
		return URLDecode(text,"UTF-8");

	}
	public static String URLDecode(String text,String charsetName) {
		if (StringUtils.isNullOrEmpty(text)){
			return text;
		}
		String result = null;
		try {
			result = URLDecoder.decode(text, charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		Log.i("URLDecode=%s", result);
		return result;

	}

	/**
	 * @param str
	 * @return
	 */
	public static String ChangeToHtml(String str) {
		// str含有HTML标签的文本
		str = str.replace("&amp;", "&");
		str = str.replace((char) 0, (char) 13);
		str = str.replace("&amp;", "&");
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&lt;br&gt;", "\n");
		return str;
	}
}
