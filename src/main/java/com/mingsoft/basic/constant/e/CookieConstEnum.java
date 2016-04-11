package com.mingsoft.basic.constant.e;

import com.mingsoft.base.constant.e.BaseCookieEnum;

/**
 * cookie枚举类
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public enum CookieConstEnum implements BaseCookieEnum{
	
	/**
	 * 分页cookie
	 */
	PAGENO_COOKIE("pageno_cookie"),
	
	/**
	 *上次访问地址
	 */
	BACK_COOKIE("back_cookie");	
	
	/**
	 * 设置CookieConst的常量
	 * @param attr 常量
	 */
	CookieConstEnum(String attr) {
		this.attr = attr;
	}
	
	private String attr;

	/**
	 * 返回该CookieConst常量的字符串表示
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return attr;
	}
}
