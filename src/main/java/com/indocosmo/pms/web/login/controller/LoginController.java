package com.indocosmo.pms.web.login.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.common.setttings.commonSettings;
import com.indocosmo.pms.web.currency.model.Currency;
import com.indocosmo.pms.web.currency.service.CurrencyService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.User;
import com.indocosmo.pms.web.login.model.UserSession;
import com.indocosmo.pms.web.login.service.LoginService;
import com.indocosmo.pms.web.serviceTax.service.ServiceTaxService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
import com.indocosmo.pms.web.systemSettings.model.Company;
import com.indocosmo.pms.web.systemSettings.model.SystemSettings;
import com.indocosmo.pms.web.systemSettings.service.SystemSettingsService;

@Controller
@Scope("prototype")
public class LoginController {

	@Autowired
	LoginService loginService;

	@Autowired
	SystemSettingsService systemSettingsService;

	@Autowired
	CurrencyService currencyService;

	@Autowired
	ServiceTaxService serviceTaxService;

	@Autowired
	private JavaMailSenderImpl mailSenderimp;

	@Autowired
	private SysPermissionsService sysPermissionsService;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String startPage(Model model) {
		User userForm = new User();
		model.addAttribute("userForm", userForm); System.out.println("Login");
		return "login/login";
	}

	/**
	 * Login function
	 * 
	 * @param userForm
	 * @param model
	 * @param session
	 * @return if login true redirect to currency / if login false redirect to login
	 *         page
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String checkLogin(@ModelAttribute User userForm, Model model, HttpSession session,
			HttpServletRequest request) throws Exception {
		String redirectUrl = "/";
		try {

			DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			List users = null;
			logger.info("Checking login with " + userForm.getName());
			System.out.println("redirect-->user"+userForm.getName());
			users = loginService.checkLogin(userForm);

			if (users.size() > 0) {
				logger.info("Login success with " + ((User) users.get(0)).getName().toString());
				userForm.setId(((User) users.get(0)).getId());
				userForm.setName(((User) users.get(0)).getName().toString());
				userForm.setPassword(((User) users.get(0)).getPassword().toString());
				userForm.setAdmin(((User) users.get(0)).isAdmin());
				userForm.setName(((User) users.get(0)).getLoginId().toString());
				userForm.setCashier(((User) users.get(0)).isCashier());
				// System.out.println("-------"+((User)users.get(0)).isAdmin());

				SystemSettings systemSettings = systemSettingsService.getSystemSettings();
				Company companyDetails = systemSettingsService.getcompany();

				Currency currency = currencyService.getRecord(systemSettings.getBaseCurrencyId());
				String dateFormat = systemSettings.getDateFormat();
				String timeFormat=systemSettings.getTimeFormat();
				Date checkOutTime = systemSettings.getCheckOutTime();
				String hotelDate = systemSettings.getHotelDate().toString();
				String tax1Name = systemSettings.getTax1Name();
				String tax2Name = systemSettings.getTax2Name();
				String tax3Name = systemSettings.getTax3Name();
				String tax4Name = systemSettings.getTax4Name();
				String companyName = companyDetails.getCompanyName();
				String companyN = companyDetails.getLogoFolder();
				Integer longStay = companyDetails.getLongStay();
				commonSettings.setServiceChargeApplicable(systemSettings.isServiceCharge());
				commonSettings.setRoundrule(systemSettingsService.getRoundingRule());
				commonSettings.setServiceTaxIncluded(systemSettings.isServiceTaxIncl());
				commonSettings.setServiceTaxId(serviceTaxService.getActiveServiceTax());
				commonSettings.setDecimalPlaces(currency.getDecimalPlaces());
				commonSettings.setCurrencySymbol(currency.getSymbol());
				commonSettings.setMailNotifyReservation(systemSettings.isMailNotifyReservation());
				commonSettings.setMailNotifyCutoffDate(systemSettings.isMailNotifyCutoffDate());
				commonSettings.setMailNotifyConfirmation(systemSettings.isMailNotifyConfirmation());
				commonSettings.setMailNotifyCancellation(systemSettings.isMailNotifyCancellation());
				commonSettings.setMailNotifyNoshow(systemSettings.isMailNotifyNoShow());
				commonSettings.setMailNotifyCheckIn(systemSettings.isMailNotifyCheckIn());
				commonSettings.setMailNotifyCheckOut(systemSettings.isMailNotifyCheckOut());
				// commonSettings.systmstngs=systemSettings;
				commonSettings.setSmsNotifyReservation(systemSettings.isSmsNotifyReservation());
				commonSettings.setSmsNotifyCutoffDate(systemSettings.isSmsNotifyCutoffDate());
				commonSettings.setSmsNotifyConfirmation(systemSettings.isSmsNotifyConfirmation());
				commonSettings.setSmsNotifyCancellation(systemSettings.isSmsNotifyCancellation());
				commonSettings.setSmsNotifyNoshow(systemSettings.isSmsNotifyNoShow());
				commonSettings.setSmsNotifyCheckIn(systemSettings.isSmsNotifyCheckIn());
				commonSettings.setSmsNotifyCheckOut(systemSettings.isSmsNotifyCheckOut());

				mailSenderimp.setHost(systemSettings.getSmtpServer());

				if (!systemSettings.getSmtpPort().equals("") && systemSettings.getSmtpPort() != null) {
					mailSenderimp.setPort(Integer.parseInt(systemSettings.getSmtpPort()));
				}

				mailSenderimp.setProtocol("smtp");
				mailSenderimp.setUsername(systemSettings.getSmtpSUserId());
				mailSenderimp.setPassword(systemSettings.getSmtpPassword());
				DateFormat simpleDateFormatHotelDate = new SimpleDateFormat(dateFormat);

				List<SysPermissions> sysPermissionsList = sysPermissionsService.getPermissionsList();
				List<UserGroupPermission> userGroupPermissionList = sysPermissionsService
						.getPermissionsGrupList(((User) users.get(0)).getUserGroupId());

				// List<SysPermissions> sysPermissionsList
				HashMap<String, SysPermissions> permissionsList = new HashMap<String, SysPermissions>();

				for (SysPermissions sysPermissionsListObj : sysPermissionsList) {

					for (UserGroupPermission userGroupPermissionListObj : userGroupPermissionList) {
						if (sysPermissionsListObj.getId() == userGroupPermissionListObj.getSysdefPermissionId()) {
							sysPermissionsListObj.setCanView(userGroupPermissionListObj.isCanView());
							sysPermissionsListObj.setCanAdd(userGroupPermissionListObj.isCanAdd());
							sysPermissionsListObj.setCanAdd(userGroupPermissionListObj.isCanAdd());
							sysPermissionsListObj.setCanEdit(userGroupPermissionListObj.isCanEdit());
							sysPermissionsListObj.setCanDelete(userGroupPermissionListObj.isCanDelete());
							sysPermissionsListObj.setCanExecute(userGroupPermissionListObj.isCanExecute());
							sysPermissionsListObj.setCanExport(userGroupPermissionListObj.isCanExport());
							sysPermissionsListObj.setUserGroupPermissionId(userGroupPermissionListObj.getId());
						}

					}

					sysPermissionsListObj.setUserGroupId(((User) users.get(0)).getUserGroupId());

					permissionsList.put(sysPermissionsListObj.getCode(), sysPermissionsListObj);

				}
				UserSession userSessionData = new UserSession();

				userSessionData.setAdmin(userForm.isCashier());
				userSessionData.setAdmin(userForm.isAdmin());
				userSessionData.setName(userForm.getName());
				userSessionData.setPermissionsList(permissionsList);

				Date date = simpleDateFormat.parse(hotelDate);
				commonSettings.setHotelDate(date);
				session.setAttribute("userForm", userForm);
				session.setAttribute("currenctDate", new Date());

				systemSettings.setCurrencySymbol(currency.getSymbol());

				session.setAttribute("currencyObject", currency);

				session.setAttribute("currencySymbol", currency.getSymbol());
				session.setAttribute("tax1Name", tax1Name);
				session.setAttribute("tax2Name", tax2Name);
				session.setAttribute("tax3Name", tax3Name);
				session.setAttribute("tax4Name", tax4Name);
				session.setAttribute("dateFormat", dateFormat);
				session.setAttribute("timeFormat",timeFormat);
				session.setAttribute("hotelDate", hotelDate);
				session.setAttribute("hotelDateEpoch", date);
				session.setAttribute("checkOutTime", checkOutTime);
				session.setAttribute("hotelDateDisplay",
						simpleDateFormatHotelDate.format(systemSettings.getHotelDate()));
				session.setAttribute("userSession", userSessionData);
				session.setAttribute("loginId", userForm.getId());
				session.setAttribute("userGrpId", ((User) users.get(0)).getUserGroupId());
				session.setAttribute("loginName", userForm.getName());
				session.setAttribute("isCashier", userForm.getIsCashier());
				session.setAttribute("isAdmin", userForm.isAdmin());
				session.setAttribute("companyName", companyName);
				session.setAttribute("companyN", companyN);
				session.setAttribute("longStay", longStay);
				session.setAttribute("iscanview", false);
				session.setAttribute("otalogin", false);
				
				session.setAttribute("rootPath", request.getContextPath());

				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				InputStream input = null;
				Properties prop = new Properties();
				input = loader.getResourceAsStream("/configfile.properties");
				prop.load(input);
				session.setAttribute("VERSION",  prop.getProperty("VERSION"));
				redirectUrl = "/dashboard";
			}

		} catch (Exception e) {
			logger.error("Method : checkLogin()" + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		return "redirect:" + redirectUrl;
	}

	/**
	 * Logout function
	 * 
	 * @param model
	 * @param session
	 * @return if logout is true redirect to logout page
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) throws IOException, ServletException {
		User userForm = new User();
		model.addAttribute("userForm", userForm);
		session.invalidate();

		return "redirect:/";
	}

	@RequestMapping(value = "/errorpage")
	public String getErrorPage() {
		return "exception/exception";
	}
}