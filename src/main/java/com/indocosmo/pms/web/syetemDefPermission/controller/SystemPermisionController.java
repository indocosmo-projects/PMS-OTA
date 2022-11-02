package com.indocosmo.pms.web.syetemDefPermission.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.login.model.UserSession;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissionsByGroup;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;
import com.indocosmo.pms.web.syetemDefPermission.service.SysPermissionsService;
 

@Controller
@RequestMapping(value = "/permission")
public class SystemPermisionController {

	@Autowired
	private SysPermissionsService sysPermissionsService;
	
	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;
	 
	
	private SysPermissions permissionObj;
 
	public static final String SYDEF_PERMISION_PAGE_URL = "syspermission/per_list";
	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "syspermission/access_denied";
	public static final Logger logger = LoggerFactory.getLogger(SystemPermisionController.class);
	
	//private SysPermissions p_list;
	/**
	 * Dashboard Home page
	 * 
	 * @return page redirect to dashboard/index
	 */
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) throws Exception { 
		
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_USR_GRP");	
		model.addAttribute("curPagePerObj",permissionObj);
		
		UserSession userSessionData = (UserSession) session.getAttribute("userSession");
              if(userSessionData.isAdmin()){
	                permissionObj.setCanAdd(true);
	                permissionObj.setCanEdit(true);
	                permissionObj.setCanView(true);
	                permissionObj.setIs_view_applicable(true);
	                permissionObj.setIs_edit_applicable(true);	
              }
		
		
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable())?SYDEF_PERMISION_PAGE_URL:"access_denied/access_denied");		
	
      }
	
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessDenied(HttpServletRequest request) throws Exception {
     //   System.out.println("accessdenied");	 
		return "syspermission/access_denied";
	}
	
	
	
/*	public SysPermissions getPermission(HttpSession session,String code) throws Exception {   
		
        UserSession userSessionData = (UserSession)  session.getAttribute("userSession");
        HashMap<String, SysPermissions> permissionsList = userSessionData.getPermissionsList();
        SysPermissions sysPermissionsObj=null ;
		if(permissionsList.containsKey(code)){			
			System.out.println("-----------------------code contain");		
			sysPermissionsObj = permissionsList.get(code);		 
		}
        
        
       
        
		return sysPermissionsObj;
	}*/
	
	@RequestMapping(value = "/getpermissionlist", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody String getPermissionList(HttpSession session) throws Exception {
		
		List<SysPermissionsByGroup>    sysPermissionsByGroupList = new ArrayList<SysPermissionsByGroup>();
		List<SysPermissions>           sysPermissionsList        =  sysPermissionsService.getPermissionsList();	
		HashSet<String>                uniqueValues              = new HashSet<String>();
			
		for(SysPermissions sysPermissionsList1: sysPermissionsList  ){		
			uniqueValues.add(sysPermissionsList1.getSystemGroup());			
		}
		
		for(String systemGroupName: uniqueValues  ){		
			SysPermissionsByGroup sysPermissionsByGroupObj = new SysPermissionsByGroup();  
			List<SysPermissions> sysPermissionsListg = new ArrayList<SysPermissions>();	
			for(SysPermissions sysPermissionsList1: sysPermissionsList  ){
				if(systemGroupName.equals(sysPermissionsList1.getSystemGroup())){		 	
					sysPermissionsListg.add(sysPermissionsList1);			
				}			 
			}		
			sysPermissionsByGroupObj.setSysgrpLabel(systemGroupName);
			sysPermissionsByGroupObj.setFunctionName(sysPermissionsListg);		
			sysPermissionsByGroupList.add(sysPermissionsByGroupObj);		
		}
		
		return  new Gson().toJson(sysPermissionsByGroupList);
	}
	
	
	
	
	
	@RequestMapping(value = "/grouppermission", method = RequestMethod.GET)
	public String groupPermission(Model model,HttpSession session,HttpServletRequest request,@RequestParam(value="groupid" , required=true) int gid) throws Exception {
		permissionObj = pageAccessPermissionService.getPermission(session,"PMS_USR_GRP");	
		model.addAttribute("curPagePerObj",permissionObj);
		
		UserSession userSessionData = (UserSession) session.getAttribute("userSession");
              if(userSessionData.isAdmin()){
	                permissionObj.setCanAdd(true);
	                permissionObj.setCanEdit(true);
	                permissionObj.setCanView(true);
	                permissionObj.setIs_view_applicable(true);
	                permissionObj.setIs_edit_applicable(true);	
              }
              
		model.addAttribute("groupid",gid);
		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable())?"syspermission/group_permission":"access_denied/access_denied");		
 
	}
	
	
	
	
	@RequestMapping(value = "/getpermissionlistbygroupid", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody String getPermissionListByGroupId(HttpSession session,
			@RequestParam(value="gid" , required=true) int gid ) throws Exception {
		
		List<SysPermissionsByGroup>    sysPermissionsByGroupList = new ArrayList<SysPermissionsByGroup>();
		HashSet<String>                uniqueValues              = new HashSet<String>();
		
		List<SysPermissions>           sysPermissionsList        =  sysPermissionsService.getPermissionsList();		
		List<UserGroupPermission>      userGroupPermissionList   =  sysPermissionsService.getPermissionsGrupList(gid);	
			
		for(SysPermissions sysPermissionsListObj: sysPermissionsList  ){	
			
			uniqueValues.add(sysPermissionsListObj.getSystemGroup());			
			for(UserGroupPermission userGroupPermissionListObj: userGroupPermissionList  ){											
				if(sysPermissionsListObj.getId()==userGroupPermissionListObj.getSysdefPermissionId()){					
					sysPermissionsListObj.setCanView(userGroupPermissionListObj.isCanView());					
					sysPermissionsListObj.setCanAdd(userGroupPermissionListObj.isCanAdd());
					sysPermissionsListObj.setCanAdd(userGroupPermissionListObj.isCanAdd());
					sysPermissionsListObj.setCanEdit(userGroupPermissionListObj.isCanEdit());
					sysPermissionsListObj.setCanDelete(userGroupPermissionListObj.isCanDelete());
					sysPermissionsListObj.setCanExecute(userGroupPermissionListObj.isCanExecute());
					sysPermissionsListObj.setCanExport(userGroupPermissionListObj.isCanExport());
					//sysPermissionsListObj.setUserGroupId(gid);
					sysPermissionsListObj.setUserGroupPermissionId(userGroupPermissionListObj.getId());						
				}
				
				
			}	
			
			sysPermissionsListObj.setUserGroupId(gid);	
			
		}
		
		for(String systemGroupName: uniqueValues  ){		
			SysPermissionsByGroup sysPermissionsByGroupObj = new SysPermissionsByGroup();  
			List<SysPermissions> sysPermissionsListg = new ArrayList<SysPermissions>();	
			for(SysPermissions sysPermissionsList1: sysPermissionsList  ){
				if(systemGroupName.equals(sysPermissionsList1.getSystemGroup())){		 	
					sysPermissionsListg.add(sysPermissionsList1);			
				}			 
			}		
			sysPermissionsByGroupObj.setSysgrpLabel(systemGroupName);
			sysPermissionsByGroupObj.setFunctionName(sysPermissionsListg);		
			sysPermissionsByGroupList.add(sysPermissionsByGroupObj);		
		}
		
		return  new Gson().toJson(sysPermissionsByGroupList);
	}
 	
	@RequestMapping(value="/postPermission", method=RequestMethod.POST) 
	public @ResponseBody int postpermission(@RequestBody String permissionsList,HttpSession session)
	{
		int status  =0; 
		
		try {
			List<SysPermissionsByGroup> sysPermissionsByGroupList =  new ObjectMapper()
					.readValue(
							permissionsList,
							TypeFactory.defaultInstance()
							.constructCollectionType(List.class,
									SysPermissionsByGroup.class));
		
			
			List<UserGroupPermission> userGroupPermissionList = new ArrayList<UserGroupPermission>();
			
			for(SysPermissionsByGroup sysPermissionsByGroupObj : sysPermissionsByGroupList){
				 
				List<SysPermissions> sysPermissionsList = sysPermissionsByGroupObj.getFunctionName();
				
				for(SysPermissions sysPermissionsListObj : sysPermissionsList){
							
					UserGroupPermission userGroupPermissionObj = new UserGroupPermission();
					if(sysPermissionsListObj.getUserGroupPermissionId()!=0){
						userGroupPermissionObj.setId(sysPermissionsListObj.getUserGroupPermissionId());
					}
					    
					    userGroupPermissionObj.setUserGroupId(sysPermissionsListObj.getUserGroupId());
					    userGroupPermissionObj.setCode(sysPermissionsListObj.getCode());
					    userGroupPermissionObj.setSysdefPermissionId(sysPermissionsListObj.getId());
					    userGroupPermissionObj.setCanView(sysPermissionsListObj.isCanView());			    
					    userGroupPermissionObj.setCanAdd(sysPermissionsListObj.isCanAdd());
					    userGroupPermissionObj.setCanEdit(sysPermissionsListObj.isCanEdit());
					    userGroupPermissionObj.setCanDelete(sysPermissionsListObj.isCanDelete());
					    userGroupPermissionObj.setCanExecute(sysPermissionsListObj.isCanExecute());
					    userGroupPermissionObj.setCanExport(sysPermissionsListObj.isCanExport());
					    userGroupPermissionObj.setLastUpdTs(new Date());
					  //  userGroupPermissionObj.setSystem(sysPermissionsListObj.);
					
					userGroupPermissionList.add(userGroupPermissionObj);
				}			
			}		
		 
			status =	sysPermissionsService.groupPermissionListUpdate(userGroupPermissionList);	 			
		}catch (Exception e) {
			
    		e.printStackTrace();
			logger.error("Method : setWizard1Data "
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		return  status;
	}
	
	 
	
	
	
}