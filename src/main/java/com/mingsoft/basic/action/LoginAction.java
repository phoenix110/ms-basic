package com.mingsoft.basic.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.IAppBiz;
import com.mingsoft.basic.biz.IManagerBiz;
import com.mingsoft.basic.biz.IRoleBiz;
import com.mingsoft.basic.biz.ISystemSkinBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.RoleEntity;
import com.mingsoft.basic.entity.SystemSkinEntity;
import com.mingsoft.util.StringUtil;

/**
 * 登录的基础应用层
 * @author 王天培
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015-1-10<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/msadmin")
public class LoginAction extends BaseAction {
	
	/**
	 * 管理员业务层
	 */
	@Autowired
	private IManagerBiz managerBiz;

	/**
	 * 角色业务request层
	 */
	@Autowired
	private IRoleBiz roleBiz;

	/**
	 * 站点业务层
	 */
	@Autowired
	private IAppBiz appBiz;
	
	/**
	 * 系统皮肤业务处理层
	 */
	@Autowired
	private ISystemSkinBiz systemSkinBiz;

	/**
	 * 加载管理员登录界面
	 * @param request 请求对象
	 * @return 管理员登录界面地址
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		// 根据请求地址来显示标题
		AppEntity app = this.getApp(request);
		request.setAttribute("app", app);
		//判断应用实体是否存在
		if(app!=null){
			//检测应用是否有自定义界面b
			SystemSkinEntity sse = systemSkinBiz.getByManagerId(app.getAppManagerId());
			if (sse!=null && !StringUtil.isBlank(sse.getSystemSkinLoginPage())) {
				return "redirect:/"+sse.getSystemSkinLoginPage();
			}
		}
		
		return "/manager/login";
	}

	/**
	 * 验证登录
	 * 
	 * @param manager
	 *            管理员实体
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/checkLogin")
	public void checkLogin(@ModelAttribute ManagerEntity manager, HttpServletRequest request, HttpServletResponse response) {
		AppEntity urlWebsite = null;
		urlWebsite = appBiz.getByUrl(this.getDomain(request)); // 根据url地址获取站点信息，主要是区分管理员对那些网站有权限
		if (urlWebsite == null) {
			this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err"));
			return;
		}
		//根据账号获取当前管理员信息
		ManagerEntity _manager = managerBiz.queryManagerByManagerName(manager.getManagerName());
		if (_manager == null) {
			// 系统不存在此用户
			this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.nameEmpty"));
		} else {
			//判断当前用户输入的密码是否正确
			if (StringUtil.Md5(manager.getManagerPassword()).equals(_manager.getManagerPassword())) {
				SystemSkinEntity systemSkin = systemSkinBiz.getByManagerId(_manager.getManagerId());
				//创建管理员session对象
				ManagerSessionEntity managerSession = new ManagerSessionEntity();
				AppEntity website = new AppEntity();
				//获取管理员所在的角色
				RoleEntity role = (RoleEntity) roleBiz.getEntity(_manager.getManagerRoleID());
				website = (AppEntity) appBiz.getByManagerId(role.getRoleManagerId());
				//判断当前登录管理员是否为该网站的系统管理员，如果是，如果不是则判断是否为超级管理员
				if (website != null && urlWebsite != null && urlWebsite.getAppId() == website.getAppId() && _manager.getManagerRoleID() > Const.DEFAULT_SYSTEM_MANGER_ROLE_ID) {
					
					List<BaseEntity> childManagerList = managerBiz.queryAllChildManager(managerSession.getManagerId());
					managerSession.setBasicId(website.getAppId());
					managerSession.setManagerParentID(role.getRoleManagerId());
					managerSession.setManagerChildIDs(childManagerList);
					managerSession.setStyle(website.getAppStyle());
					//压入管理员seesion
					setSession(request, SessionConstEnum.MANAGER_ESSION, managerSession);
				} else {
					if (!(_manager.getManagerRoleID()==Const.DEFAULT_SYSTEM_MANGER_ROLE_ID)) {
						this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err"));
					} else { 
						setSession(request, SessionConstEnum.MANAGER_ESSION, managerSession);
					}
				}
				BeanUtils.copyProperties(_manager, managerSession);
				if (systemSkin!=null) {
					managerSession.setSystemSkin(systemSkin);
				}
				
				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken upt = new UsernamePasswordToken(managerSession.getManagerName(), managerSession.getManagerPassword());
				subject.login(upt);
				this.outJson(response, ModelCode.ADMIN_LOGIN, true, null);
			} else {
				// 密码错误
				this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.password"));
			}
		}
	}

}
