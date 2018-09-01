package cn.xldhome.wechat.utils;

import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		String reqUrl = "";
		Map params = new HashMap<>();
		HttpUtils.buildUrl(reqUrl, params);
	}

}
