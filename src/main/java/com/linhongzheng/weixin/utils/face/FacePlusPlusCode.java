package com.linhongzheng.weixin.utils.face;

import java.util.HashMap;
import java.util.Map;

public class FacePlusPlusCode {
	private static Map<Integer, String> status = new HashMap<Integer, String>();

	static {
		status.put(1001, "INTERNAL_ERROR");
		status.put(10032, "AUTHORIZATION_ERROR");
		status.put(1003, "INSUFFICIENT_PRIVILEGE_OR_QUOTA_LIMIT_EXCEEDED");
		status.put(1004, "MISSING_ARGUMENTS");
		status.put(1005, "INVALID_ARGUMENTS");
		status.put(1006, "ILLEGAL_USE_OF_DEMO_KEY");
		status.put(1202, "SERVER_TOO_BUSY");
		status.put(1301, "IMAGE_ERROR_UNSUPPORTED_FORMAT");
		status.put(1302, "IMAGE_ERROR_FAILED_TO_DOWNLOAD");
		status.put(1303, "IMAGE_ERROR_FILE_TOO_LARGE");
		status.put(1304, "IMAGE_ERROR");
		status.put(1502, "BAD_TAG");
	}

}
