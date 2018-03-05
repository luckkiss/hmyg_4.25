package com.white.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class StringUtil {

	/**
	 * 判断某个字符串是否是以某个后缀名数组结尾的，可用于判断文件名的类型
	 * 
	 * @param name
	 * @param strs
	 * @return
	 */
	public static boolean isEndWithStringArray(String name, String[] strs) {
		for (String suffix : strs) {
			if (name.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/***
	 * Regular expression to match all IANA top-level domains for WEB_URL. List
	 * accurate as of 2011/07/18. List taken from:
	 * http://data.iana.org/TLD/tlds-alpha-by-domain.txt This pattern is
	 * auto-generated by frameworks/ex/common/tools/make-iana-tld-pattern.py
	 */
	private static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:"
			+ "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
			+ "|(?:biz|b[abdefghijmnorstvwyz])"
			+ "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])" + "|d[ejkmoz]"
			+ "|(?:edu|e[cegrstu])" + "|f[ijkmor]"
			+ "|(?:gov|g[abdefghilmnpqrstuwy])" + "|h[kmnrtu]"
			+ "|(?:info|int|i[delmnoqrst])" + "|(?:jobs|j[emop])"
			+ "|k[eghimnprwyz]" + "|l[abcikrstuvy]"
			+ "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
			+ "|(?:name|net|n[acefgilopruz])" + "|(?:org|om)"
			+ "|(?:pro|p[aefghklmnrstwy])" + "|qa" + "|r[eosuw]"
			+ "|s[abcdeghijklmnortuvyz]"
			+ "|(?:tel|travel|t[cdfghjklmnoprtvwz])" + "|u[agksyz]"
			+ "|v[aceginu]" + "|w[fs]"
			// +
			// "|(?:\u03b4\u03bf\u03ba\u03b9\u03bc\u03ae|\u0438\u0441\u043f\u044b\u0442\u0430\u043d\u0438\u0435|\u0440\u0444|\u0441\u0440\u0431|\u05d8\u05e2\u05e1\u05d8|\u0622\u0632\u0645\u0627\u06cc\u0634\u06cc|\u0625\u062e\u062a\u0628\u0627\u0631|\u0627\u0644\u0627\u0631\u062f\u0646|\u0627\u0644\u062c\u0632\u0627\u0626\u0631|\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629|\u0627\u0644\u0645\u063a\u0631\u0628|\u0627\u0645\u0627\u0631\u0627\u062a|\u0628\u06be\u0627\u0631\u062a|\u062a\u0648\u0646\u0633|\u0633\u0648\u0631\u064a\u0629|\u0641\u0644\u0633\u0637\u064a\u0646|\u0642\u0637\u0631|\u0645\u0635\u0631|\u092a\u0930\u0940\u0915\u094d\u0937\u093e|\u092d\u093e\u0930\u0924|\u09ad\u09be\u09b0\u09a4|\u0a2d\u0a3e\u0a30\u0a24|\u0aad\u0abe\u0ab0\u0aa4|\u0b87\u0ba8\u0bcd\u0ba4\u0bbf\u0baf\u0bbe|\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8|\u0b9a\u0bbf\u0b99\u0bcd\u0b95\u0baa\u0bcd\u0baa\u0bc2\u0bb0\u0bcd|\u0baa\u0bb0\u0bbf\u0b9f\u0bcd\u0b9a\u0bc8|\u0c2d\u0c3e\u0c30\u0c24\u0c4d|\u0dbd\u0d82\u0d9a\u0dcf|\u0e44\u0e17\u0e22|\u30c6\u30b9\u30c8|\u4e2d\u56fd|\u4e2d\u570b|\u53f0\u6e7e|\u53f0\u7063|\u65b0\u52a0\u5761|\u6d4b\u8bd5|\u6e2c\u8a66|\u9999\u6e2f|\ud14c\uc2a4\ud2b8|\ud55c\uad6d|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)"
			+ "|y[et]" + "|z[amw]))";

	/***
	 * Good characters for Internationalized Resource Identifiers (IRI). This
	 * comprises most common used Unicode characters allowed in IRI as detailed
	 * in RFC 3987. Specifically, those two byte Unicode characters are not
	 * included.
	 */
	// public static final String GOOD_IRI_CHAR =
	// "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
	private static final String GOOD_IRI_CHAR = "a-zA-Z0-9";

	/***
	 * Regular expression pattern to match most part of RFC 3987
	 * Internationalized URLs, aka IRIs. Commonly used Unicode characters are
	 * added.
	 */

	private static final String STR_PATTERN_LINK = "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
			+ "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
			+ "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
			+ "((?:(?:["
			+ GOOD_IRI_CHAR
			+ "]["
			+ GOOD_IRI_CHAR
			+ "\\-]{0,64}\\.)+" // named host
			+ TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
			+ "|(?:(?:25[0-5]|2[0-4]" // or ip address
			+ "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
			+ "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
			+ "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
			+ "|[1-9][0-9]|[0-9])))"
			+ "(?:\\:\\d{1,5})?)" // plus option port number
			+ "(\\/(?:(?:["
			+ GOOD_IRI_CHAR
			+ "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus option query params
			+ "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
			+ "(?:\\b|$)";// and finally, a word boundary or end of
	// input. This is to stop foo.sure from
	// matching as foo.su
	private static final String STR_PATTERN_EMAIL = "[\\_a-zA-Z0-9]+(\\.[\\_a-zA-Z0-9]+)*@[\\_a-zA-Z0-9]+(\\.[\\_a-zA-Z0-9]+)+";
	private static final String STR_PATTERN_PHONE = "[0-9]{5,56}";

	@SuppressWarnings("unused")
	private static final Pattern mPatternLink = Pattern
			.compile(STR_PATTERN_LINK);
	@SuppressWarnings("unused")
	private static final Pattern mPatternEmail = Pattern
			.compile(STR_PATTERN_EMAIL);
	@SuppressWarnings("unused")
	private static final Pattern mPatternPhone = Pattern
			.compile(STR_PATTERN_PHONE);

	/**
	 * 分割字符串为List<Long>
	 * 
	 * @param string
	 * @return
	 */
	public static List<Long> splitStringToLong(String string) {
		List<Long> res = new ArrayList<Long>();
		String strs[] = string.split(",");
		for (String str : strs) {
			try {
				Long l = Long.parseLong(str);
				res.add(l);
			} catch (Exception e) {
				continue;
			}
		}
		return res;
	}

	/**
	 * 查找消息需要根本背景色的位置
	 */
	public static ArrayList<int[]> getPositions(String item, String key) {
		ArrayList<int[]> positions = new ArrayList<int[]>();
		int start = 0;
		int end = 0;
		for (int i = 0; i < item.length(); i = end) {
			int[] temp = new int[2];
			start = item.toLowerCase().indexOf(key.toLowerCase(), i);
			if (start >= 0) {
				end = start + key.length();
				temp[0] = start;
				temp[1] = end;
				positions.add(temp);
				continue;
			}
			break;
		}
		return positions;
	}

	/**
	 * 
	 * @param src
	 * @param objects
	 * @return
	 */
	public static String format(String src, Object... objects) {
		int k = 0;
		for (Object obj : objects) {
			src = src.replace("{" + k + "}", obj.toString());
			k++;
		}
		return src;
	}

	/**
	 * 将某个用户从字符串列表中移除
	 * 
	 * @param idStr
	 * @param delId
	 * @return
	 */
	public static String deletePersonStr(String idStr, long delId) {
		String[] ids = idStr.split(",");
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			long pId = Long.parseLong(id);
			if (pId != delId) {
				sb.append(id);
				sb.append(",");
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
	}

	/**
	 * 将某个用户从字符串列表中移除
	 * 
	 * @param idStr
	 * @param delId
	 * @return
	 */
	public static boolean isStrContainsPerson(String idStr, long delId) {
		if (idStr.length() < 1) {
			return false;
		}
		String[] ids = idStr.split(",");
		for (String id : ids) {
			long pId = Long.parseLong(id);
			if (pId == delId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是http url图片，否则为本地图片
	 * 
	 * @param idStr
	 * @param id
	 * @return
	 */
	public static boolean isHttpUrlPicPath(String urlPath) {
		return urlPath.startsWith("http:");
	}

	/**
	 * 获取字符串的字节长度
	 */
	public static int getWordCount(String s) {
		return s.getBytes().length;
	}

	/**
	 * 获得文件路径的时间
	 * 
	 * @param name
	 * @return
	 */
	public static int getTimeByFileName(String name) {
		int start = name.lastIndexOf("/");
		String subStr = name.substring(start + 1);
		int end = subStr.lastIndexOf(".");
		if (end < 0)
			end = subStr.length();
		return Integer.parseInt(subStr.substring(0, end));
	}

	/**
	 * 获取浮点数的字符串（避免科学计数） 必须正数
	 * 
	 * @param num
	 *            浮点数
	 * @param size
	 *            小数点后最多保留的位数
	 * @return
	 */
	public static String getFloatString(float num, int size) {
		if (num == 0) {
			return "0";
		}
		BigDecimal bigNum = new BigDecimal(num);
		String result = bigNum.setScale(size, RoundingMode.HALF_UP).toString();
		while ((result.contains(".") && result.endsWith("0"))
				|| result.endsWith(".") || result.endsWith("-")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 获取双浮点数的字符串（避免科学计数） 必须正数
	 * 
	 * @param num
	 *            浮点数
	 * @param size
	 *            小数点后最多保留的位数
	 * @return
	 */
	public static String getDoubleString(double num, int size) {
		if (num == 0) {
			return "0";
		}
		BigDecimal bigNum = new BigDecimal(num);
		String result = bigNum.setScale(size, BigDecimal.ROUND_HALF_UP)
				.toString();
		while ((result.contains(".") && result.endsWith("0"))
				|| result.endsWith(".") || result.endsWith("-")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 获取双浮点数的字符串（避免科学计数） 必须正数
	 * 
	 * @param num
	 *            浮点数
	 * @param size
	 *            小数点后最多保留的位数
	 * @return
	 */
	public static String getDoubleString(BigDecimal num, int size) {
		String result = num.setScale(size, BigDecimal.ROUND_HALF_UP).toString();
		while ((result.contains(".") && result.endsWith("0"))
				|| result.endsWith(".") || result.endsWith("-")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 双浮点数相乘
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static double getDoubleMultiply(double num1, double num2) {
		return num1 * num2;
	}

	/**
	 * 插入回车换行替换为空格
	 */
	public static String changeEnterToSpace(String key) {
		String result = "";
		if (key != null && !key.equals("")) {
			result = key.replaceAll("\r", " ");
			result = key.replaceAll("\n", " ");
			result = key.replaceAll("\f", " ");
			result = key.replaceAll("\t", " ");
		}
		return result;
	}

	private static int[] Wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
			4, 2, 1 };// 加权因子
	private static int[] ValideCode = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };// 身份证验证位值.10代表X

	/**
	 * 验证身份证号码
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean checkPersonIdCard(String idCard) {
		try {
			if (idCard == null)
				return false;
			if (idCard.length() > 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/*
	 * public static boolean checkPersonIdCard(String idCard) { try { if (idCard
	 * == null) return false; if (idCard.length() == 15) { return
	 * isValidityBrithBy15IdCard(idCard); } else if (idCard.length() == 18) {
	 * String[] a_idCard = idCard.split("");// 得到身份证数组 List<String> char_idCard
	 * = new ArrayList<String>(); for (int i = 0; i < a_idCard.length; i++) { if
	 * (!a_idCard[i].equals("")) { char_idCard.add(a_idCard[i]); } } if
	 * (isValidityBrithBy18IdCard(idCard) &&
	 * isTrueValidateCodeBy18IdCard(char_idCard)) { return true; } else { return
	 * false; } } else { return false; } } catch (Exception e) { return false; }
	 * 
	 * }
	 */

	private static boolean isValidityBrithBy15IdCard(String idCard15) {
		String year = idCard15.substring(6, 8);
		String month = idCard15.substring(8, 10);
		String day = idCard15.substring(10, 12);
		Date temp_date = new Date(Integer.parseInt(year),
				Integer.parseInt(month) - 1, Integer.parseInt(day));
		// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
        return !(temp_date.getYear() != Integer.parseInt(year)
                || temp_date.getMonth() != Integer.parseInt(month) - 1
                || temp_date.getDate() != Integer.parseInt(day));
	}

	private static boolean isValidityBrithBy18IdCard(String idCard18) {
		String year = idCard18.substring(6, 10);
		String month = idCard18.substring(10, 12);
		String day = idCard18.substring(12, 14);
		Date temp_date = new Date(Integer.parseInt(year),
				Integer.parseInt(month) - 1, Integer.parseInt(day));
		// 这里用getFullYear()获取年份，避免千年虫问题
        return !((temp_date.getYear()) != Integer.parseInt(year)
                || temp_date.getMonth() != Integer.parseInt(month) - 1
                || temp_date.getDate() != Integer.parseInt(day));
	}

	private static boolean isTrueValidateCodeBy18IdCard(List<String> char_idCard) {
		int sum = 0; // 声明加权求和变量
		if (char_idCard.get(17).toLowerCase().equals("x")) {
			char_idCard.set(17, "10"); // 将最后位为x的验证码替换为10方便后续操作
		}
		for (int i = 0; i < 17; i++) {
			int j = Integer.parseInt(char_idCard.get(i));
			sum += Wi[i] * j;// 加权求和
		}
		int valCodePosition = sum % 11;// 得到验证码所位置
        return char_idCard.get(17).equals(
                Integer.toString(ValideCode[valCodePosition]));
	}
}
