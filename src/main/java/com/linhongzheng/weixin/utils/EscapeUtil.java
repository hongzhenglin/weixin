package com.linhongzheng.weixin.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 转义字符工具类
 * 
 * @author linhz
 *
 */
public class EscapeUtil {

	public static final Map<String, String> HTML40_ARRAY = new LinkedHashMap<String, String>();
	static {
		HTML40_ARRAY.put("quot", "34");
		HTML40_ARRAY.put("amp", "38");
		HTML40_ARRAY.put("lt", "60");
		HTML40_ARRAY.put("gt", "62");
		HTML40_ARRAY.put("apos", "39");
		HTML40_ARRAY.put("fnof", "402");
		HTML40_ARRAY.put("Alpha", "913");
		HTML40_ARRAY.put("Beta", "914");
		HTML40_ARRAY.put("Gamma", "915");
		HTML40_ARRAY.put("Delta", "916");
		HTML40_ARRAY.put("Epsilon", "917");
		HTML40_ARRAY.put("Zeta", "918");
		HTML40_ARRAY.put("Eta", "919");
		HTML40_ARRAY.put("Theta", "920");
		HTML40_ARRAY.put("Iota", "921");
		HTML40_ARRAY.put("Kappa", "922");
		HTML40_ARRAY.put("Lambda", "923");
		HTML40_ARRAY.put("Mu", "924");
		HTML40_ARRAY.put("Nu", "925");
		HTML40_ARRAY.put("Xi", "926");
		HTML40_ARRAY.put("Omicron", "927");
		HTML40_ARRAY.put("Pi", "928");
		HTML40_ARRAY.put("Rho", "929");
		HTML40_ARRAY.put("Sigma", "931");
		HTML40_ARRAY.put("Tau", "932");
		HTML40_ARRAY.put("Upsilon", "933");
		HTML40_ARRAY.put("Phi", "934");
		HTML40_ARRAY.put("Chi", "935");
		HTML40_ARRAY.put("Psi", "936");
		HTML40_ARRAY.put("Omega", "937");
		HTML40_ARRAY.put("alpha", "945");
		HTML40_ARRAY.put("beta", "946");
		HTML40_ARRAY.put("gamma", "947");
		HTML40_ARRAY.put("delta", "948");
		HTML40_ARRAY.put("epsilon", "949");
		HTML40_ARRAY.put("zeta", "950");
		HTML40_ARRAY.put("eta", "951");
		HTML40_ARRAY.put("theta", "952");
		HTML40_ARRAY.put("iota", "953");
		HTML40_ARRAY.put("kappa", "954");
		HTML40_ARRAY.put("lambda", "955");
		HTML40_ARRAY.put("mu", "956");
		HTML40_ARRAY.put("nu", "957");
		HTML40_ARRAY.put("xi", "958");
		HTML40_ARRAY.put("omicron", "959");
		HTML40_ARRAY.put("pi", "960");
		HTML40_ARRAY.put("rho", "961");
		HTML40_ARRAY.put("sigmaf", "962");
		HTML40_ARRAY.put("sigma", "963");
		HTML40_ARRAY.put("tau", "964");
		HTML40_ARRAY.put("upsilon", "965");
		HTML40_ARRAY.put("phi", "966");
		HTML40_ARRAY.put("chi", "967");
		HTML40_ARRAY.put("psi", "968");
		HTML40_ARRAY.put("omega", "969");
		HTML40_ARRAY.put("thetasym", "977");
		HTML40_ARRAY.put("upsih", "978");
		HTML40_ARRAY.put("piv", "982");
		HTML40_ARRAY.put("bull", "8226");
		HTML40_ARRAY.put("hellip", "8230");
		HTML40_ARRAY.put("prime", "8242");
		HTML40_ARRAY.put("Prime", "8243");
		HTML40_ARRAY.put("oline", "8254");
		HTML40_ARRAY.put("frasl", "8260");
		HTML40_ARRAY.put("weierp", "8472");
		HTML40_ARRAY.put("image", "8465");
		HTML40_ARRAY.put("real", "8476");
		HTML40_ARRAY.put("trade", "8482");
		HTML40_ARRAY.put("alefsym", "8501");
		HTML40_ARRAY.put("larr", "8592");
		HTML40_ARRAY.put("uarr", "8593");
		HTML40_ARRAY.put("rarr", "8594");
		HTML40_ARRAY.put("darr", "8595");
		HTML40_ARRAY.put("harr", "8596");
		HTML40_ARRAY.put("crarr", "8629");
		HTML40_ARRAY.put("lArr", "8656");
		HTML40_ARRAY.put("uArr", "8657");
		HTML40_ARRAY.put("rArr", "8658");
		HTML40_ARRAY.put("dArr", "8659");
		HTML40_ARRAY.put("hArr", "8660");
		HTML40_ARRAY.put("forall", "8704");
		HTML40_ARRAY.put("part", "8706");
		HTML40_ARRAY.put("exist", "8707");
		HTML40_ARRAY.put("empty", "8709");
		HTML40_ARRAY.put("nabla", "8711");
		HTML40_ARRAY.put("isin", "8712");
		HTML40_ARRAY.put("notin", "8713");
		HTML40_ARRAY.put("ni", "8715");
		HTML40_ARRAY.put("prod", "8719");
		HTML40_ARRAY.put("sum", "8721");
		HTML40_ARRAY.put("minus", "8722");
		HTML40_ARRAY.put("lowast", "8727");
		HTML40_ARRAY.put("radic", "8730");
		HTML40_ARRAY.put("prop", "8733");
		HTML40_ARRAY.put("infin", "8734");
		HTML40_ARRAY.put("ang", "8736");
		HTML40_ARRAY.put("and", "8743");
		HTML40_ARRAY.put("or", "8744");
		HTML40_ARRAY.put("cap", "8745");
		HTML40_ARRAY.put("cup", "8746");
		HTML40_ARRAY.put("int", "8747");
		HTML40_ARRAY.put("there4", "8756");
		HTML40_ARRAY.put("sim", "8764");
		HTML40_ARRAY.put("cong", "8773");
		HTML40_ARRAY.put("asymp", "8776");
		HTML40_ARRAY.put("ne", "8800");
		HTML40_ARRAY.put("equiv", "8801");
		HTML40_ARRAY.put("le", "8804");
		HTML40_ARRAY.put("ge", "8805");
		HTML40_ARRAY.put("sub", "8834");
		HTML40_ARRAY.put("sup", "8835");
		HTML40_ARRAY.put("sube", "8838");
		HTML40_ARRAY.put("supe", "8839");
		HTML40_ARRAY.put("oplus", "8853");
		HTML40_ARRAY.put("otimes", "8855");
		HTML40_ARRAY.put("perp", "8869");
		HTML40_ARRAY.put("sdot", "8901");
		HTML40_ARRAY.put("lceil", "8968");
		HTML40_ARRAY.put("rceil", "8969");
		HTML40_ARRAY.put("lfloor", "8970");
		HTML40_ARRAY.put("rfloor", "8971");
		HTML40_ARRAY.put("lang", "9001");
		HTML40_ARRAY.put("rang", "9002");
		HTML40_ARRAY.put("loz", "9674");
		HTML40_ARRAY.put("spades", "9824");
		HTML40_ARRAY.put("clubs", "9827");
		HTML40_ARRAY.put("hearts", "9829");
		HTML40_ARRAY.put("diams", "9830");
		HTML40_ARRAY.put("OElig", "338");
		HTML40_ARRAY.put("oelig", "339");
		HTML40_ARRAY.put("Scaron", "352");
		HTML40_ARRAY.put("scaron", "353");
		HTML40_ARRAY.put("Yuml", "376");
		HTML40_ARRAY.put("circ", "710");
		HTML40_ARRAY.put("tilde", "732");
		HTML40_ARRAY.put("ensp", "8194");
		HTML40_ARRAY.put("emsp", "8195");
		HTML40_ARRAY.put("thinsp", "8201");
		HTML40_ARRAY.put("zwnj", "8204");
		HTML40_ARRAY.put("zwj", "8205");
		HTML40_ARRAY.put("lrm", "8206");
		HTML40_ARRAY.put("rlm", "8207");
		HTML40_ARRAY.put("ndash", "8211");
		HTML40_ARRAY.put("mdash", "8212");
		HTML40_ARRAY.put("lsquo", "8216");
		HTML40_ARRAY.put("rsquo", "8217");
		HTML40_ARRAY.put("sbquo", "8218");
		HTML40_ARRAY.put("ldquo", "8220");
		HTML40_ARRAY.put("rdquo", "8221");
		HTML40_ARRAY.put("bdquo", "8222");
		HTML40_ARRAY.put("dagger", "8224");
		HTML40_ARRAY.put("Dagger", "8225");
		HTML40_ARRAY.put("permil", "8240");
		HTML40_ARRAY.put("lsaquo", "8249");
		HTML40_ARRAY.put("rsaquo", "8250");
		HTML40_ARRAY.put("euro", "8364");

		// ///////////////////////////////////////////////

		HTML40_ARRAY.put("nbsp", "160");
		HTML40_ARRAY.put("iexcl", "161");
		HTML40_ARRAY.put("cent", "162");
		HTML40_ARRAY.put("pound", "163");
		HTML40_ARRAY.put("curren", "164");
		HTML40_ARRAY.put("yen", "165");
		HTML40_ARRAY.put("brvbar", "166");
		HTML40_ARRAY.put("sect", "167");
		HTML40_ARRAY.put("uml", "168");
		HTML40_ARRAY.put("copy", "169");
		HTML40_ARRAY.put("ordf", "170");
		HTML40_ARRAY.put("laquo", "171");
		HTML40_ARRAY.put("not", "172");
		HTML40_ARRAY.put("shy", "173");
		HTML40_ARRAY.put("reg", "174");
		HTML40_ARRAY.put("macr", "175");
		HTML40_ARRAY.put("deg", "176");
		HTML40_ARRAY.put("plusmn", "177");
		HTML40_ARRAY.put("sup2", "178");
		HTML40_ARRAY.put("sup3", "179");
		HTML40_ARRAY.put("acute", "180");
		HTML40_ARRAY.put("micro", "181");
		HTML40_ARRAY.put("para", "182");
		HTML40_ARRAY.put("middot", "183");
		HTML40_ARRAY.put("cedil", "184");
		HTML40_ARRAY.put("sup1", "185");
		HTML40_ARRAY.put("ordm", "186");
		HTML40_ARRAY.put("raquo", "187");
		HTML40_ARRAY.put("frac14", "188");
		HTML40_ARRAY.put("frac12", "189");
		HTML40_ARRAY.put("frac34", "190");
		HTML40_ARRAY.put("iquest", "191");
		HTML40_ARRAY.put("Agrave", "192");
		HTML40_ARRAY.put("Aacute", "193");
		HTML40_ARRAY.put("Acirc", "194");
		HTML40_ARRAY.put("Atilde", "195");
		HTML40_ARRAY.put("Auml", "196");
		HTML40_ARRAY.put("Aring", "197");
		HTML40_ARRAY.put("AElig", "198");
		HTML40_ARRAY.put("Ccedil", "199");
		HTML40_ARRAY.put("Egrave", "200");
		HTML40_ARRAY.put("Eacute", "201");
		HTML40_ARRAY.put("Ecirc", "202");
		HTML40_ARRAY.put("Euml", "203");
		HTML40_ARRAY.put("Igrave", "204");
		HTML40_ARRAY.put("Iacute", "205");
		HTML40_ARRAY.put("Icirc", "206");
		HTML40_ARRAY.put("Iuml", "207");
		HTML40_ARRAY.put("ETH", "208");
		HTML40_ARRAY.put("Ntilde", "209");
		HTML40_ARRAY.put("Ograve", "210");
		HTML40_ARRAY.put("Oacute", "211");
		HTML40_ARRAY.put("Ocirc", "212");
		HTML40_ARRAY.put("Otilde", "213");
		HTML40_ARRAY.put("Ouml", "214");
		HTML40_ARRAY.put("times", "215");
		HTML40_ARRAY.put("Oslash", "216");
		HTML40_ARRAY.put("Ugrave", "217");
		HTML40_ARRAY.put("Uacute", "218");
		HTML40_ARRAY.put("Ucirc", "219");
		HTML40_ARRAY.put("Uuml", "220");
		HTML40_ARRAY.put("Yacute", "221");
		HTML40_ARRAY.put("THORN", "222");
		HTML40_ARRAY.put("szlig", "223");
		HTML40_ARRAY.put("agrave", "224");
		HTML40_ARRAY.put("aacute", "225");
		HTML40_ARRAY.put("acirc", "226");
		HTML40_ARRAY.put("atilde", "227");
		HTML40_ARRAY.put("auml", "228");
		HTML40_ARRAY.put("aring", "229");
		HTML40_ARRAY.put("aelig", "230");
		HTML40_ARRAY.put("ccedil", "231");
		HTML40_ARRAY.put("egrave", "232");
		HTML40_ARRAY.put("eacute", "233");
		HTML40_ARRAY.put("ecirc", "234");
		HTML40_ARRAY.put("euml", "235");
		HTML40_ARRAY.put("igrave", "236");
		HTML40_ARRAY.put("iacute", "237");
		HTML40_ARRAY.put("icirc", "238");
		HTML40_ARRAY.put("iuml", "239");
		HTML40_ARRAY.put("eth", "240");
		HTML40_ARRAY.put("ntilde", "241");
		HTML40_ARRAY.put("ograve", "242");
		HTML40_ARRAY.put("oacute", "243");
		HTML40_ARRAY.put("ocirc", "244");
		HTML40_ARRAY.put("otilde", "245");
		HTML40_ARRAY.put("ouml", "246");
		HTML40_ARRAY.put("divide", "247");
		HTML40_ARRAY.put("oslash", "248");
		HTML40_ARRAY.put("ugrave", "249");
		HTML40_ARRAY.put("uacute", "250");
		HTML40_ARRAY.put("ucirc", "251");
		HTML40_ARRAY.put("uuml", "252");
		HTML40_ARRAY.put("yacute", "253");
		HTML40_ARRAY.put("thorn", "254");
		HTML40_ARRAY.put("yuml", "255");

	};

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String unescapeXml(String s) {
		return StringEscapeUtils.unescapeXml(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String escapeXml(String s) {
		return StringEscapeUtils.escapeXml11(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String escapeHtml(String s) {

		return StringEscapeUtils.escapeHtml3(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String unescapeHtml(String s) {
		return StringEscapeUtils.unescapeHtml4(s);
	}

	/**
	 * 
	 * @param parms
	 * @param charset
	 * @return
	 */
	public static String escapeUrl(String parms, String charset) {

		try {

			return URLEncoder.encode(parms, charset);
		} catch (UnsupportedEncodingException e) {
		 
			return "";
		}
	}

	/**
	 * 
	 * @param parms
	 * @param charset
	 * @return
	 */
	public static String unescapeUrl(String parms, String charset) {

		try {
			return URLDecoder.decode(parms, charset);
		} catch (UnsupportedEncodingException e) {
			 
			return "";
		}
	}

}
