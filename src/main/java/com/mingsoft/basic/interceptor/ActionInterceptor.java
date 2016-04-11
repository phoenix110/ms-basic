package com.mingsoft.basic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mingsoft.base.constant.Const;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.util.StringUtil;

/**
 * 所有action的拦截器，主要是设置base与basepath
 * 
 * @author 王天培QQ:78750478
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public class ActionInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static String BASE = "base";

	private static String BASE_PATH = "basePath";

	private static String MODEL_ID = "modelId";

	private static String BASE_URL = "baseUrl";

	private static String BASE_MANAGER_PATH = "baseManager";

	public static boolean IS_WINDOWS = false;

	static {
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") > 0) {
			IS_WINDOWS = true;
		}
	}

	/**
	 * 所有action的拦截,主要拦截base与basepath
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @param handler
	 *            处理器
	 * @throws Exception
	 *             异常处理
	 * @return 处理后返回true
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String modelId = request.getParameter(MODEL_ID); // 获取模块编号
		if (StringUtil.isInteger(modelId)) {
			request.getSession().setAttribute(SessionConstEnum.MODEL_ID_SESSION.toString(), modelId);
			request.getSession().setAttribute(SessionConstEnum.MODEL_TITLE_SESSION.toString(),request.getParameter("modelTitle"));
		}
		String base = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
		request.setAttribute(BASE_MANAGER_PATH, com.mingsoft.basic.constant.Const.MANAVER);
		request.setAttribute(BASE, Const.BASE);
		request.setAttribute(BASE_PATH, base + Const.BASE);
		request.setAttribute(BASE_URL, base + request.getContextPath() + request.getServletPath()
				+ (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
		return true;
	}

}
