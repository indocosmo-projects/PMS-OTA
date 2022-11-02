package com.indocosmo.pms.web.syetemDefPermission.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Throwables;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;
import com.indocosmo.pms.web.syetemDefPermission.model.UserGroupPermission;

@Repository
public class SysPermissionsDAOImp implements SysPermissionsDAO {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SysPermissionsDAOImp.class);

	@SuppressWarnings("unchecked")
	public List<SysPermissions> getPermissionsList() {
		// TODO Auto-generated method stub
			 
		List<SysPermissions> sysPermissionsList = null;

		try {
			sysPermissionsList = sessionFactory.getCurrentSession().createQuery("from SysPermissions sys ORDER BY sys.id ASC").list();		 
			
		} catch(Exception e) {
			logger.error("Method : getPermissionsList, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return sysPermissionsList;
		
		 
	}

	
	
	public List<UserGroupPermission> getPermissionsGrupList(int gid) {
		// TODO Auto-generated method stub
		List<UserGroupPermission> userGroupPermissionList = null;
		try { 
			String hql = "from UserGroupPermission syg WHERE syg.userGroupId = :user_group_id ORDER BY syg.id ASC";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameter("user_group_id",gid);
			userGroupPermissionList = query.list();
						
		} catch(Exception e) {
			logger.error("Method : getPermissionsGrupList, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}

		return userGroupPermissionList;
	}



	 
	public int groupPermissionListUpdate(
			List<UserGroupPermission> userGroupPermissionList) {
		// TODO Auto-generated method stub	
		int status = 0;
		
		 
		try {
		      for(UserGroupPermission obj1 : userGroupPermissionList){	
		       sessionFactory.getCurrentSession().saveOrUpdate(obj1);
	          }
		      status = 1;
		} catch(Exception e) {
			logger.error("Method : groupPermissionListUpdate, " + Throwables.getStackTraceAsString(e));
			e.printStackTrace();
			throw new CustomException();
		}
		return status;
	}

}
