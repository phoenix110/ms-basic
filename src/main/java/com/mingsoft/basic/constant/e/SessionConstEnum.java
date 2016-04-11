package com.mingsoft.basic.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.constant.e.BaseSessionEnum;

/**
 * session枚举类
 * 
 * @author 王天培QQ:78750478
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public enum SessionConstEnum implements BaseSessionEnum {


	/**
	 * 模块idsession
	 */
	MODEL_ID_SESSION("model_id_session"),

	/**
	 * 模块名称
	 */
	MODEL_TITLE_SESSION("model_title_session"),

	/**
	 * 普通管理员的sesison
	 */
	MANAGER_ESSION("manager_session"),

	/**
	 * 验证码session
	 */
	CODE_SESSION("rand_code"),

	/**
	 * 普通管理员角色菜单的sesison
	 */
	MANAGER_ROLE_MODEL_ESSION("manager_role_model_session"),

	/**
	 * 模块编号
	 */
	MANAGER_MODEL_CODE("manager_model_code");

	/**
	 * 设置session常量
	 * 
	 * @param attr
	 *            常量
	 */
	SessionConstEnum(String attr) {
		this.attr = attr;
	}

	private String attr;

	/**
	 * 返回SessionConst常量的字符串表示
	 * 
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return attr;
	}

}
