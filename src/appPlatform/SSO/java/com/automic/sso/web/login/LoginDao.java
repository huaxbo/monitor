package com.automic.sso.web.login;

import java.util.*;
import java.sql.SQLException;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import com.automic.sso.po.*;
import com.automic.util.hibernate.HibernateAnnoBaseDao;
import com.automic.util.hibernate.HibernateCallback;

/**
 * @author Administrator
 *--Repository：设置spring annotation方式注入Dao，供Busi中annotation注入dao
 *注意：类型必须整个context中没有重复类型
 */
@Repository
public class LoginDao extends HibernateAnnoBaseDao<PomfTbSysUser> {
	

	/**
	 * 通过用户名密码得到用户
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PomfTbSysUser> findUser(String userName , String password , String fixedIp) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback(new String[]{userName , password , fixedIp}) {
					public List<PomfTbSysUser> doInHibernate(final Session s) throws HibernateException,SQLException {
						String sql = "from PomfTbSysUser as u where u.name=:name and u.password=:password" ;
						String fixedIp = this.params[2]==null?null:((String)this.params[2]).trim() ;
						if(fixedIp != null && !fixedIp.equals("")){
							sql += " and u.fixedIp='" + (String)this.params[2] + "'" ;
						}
						Query query = s.createQuery(sql);
						query.setString("name", (String)this.params[0]) ;
						query.setString("password", (String)this.params[1]) ;
						List<PomfTbSysUser> list = query.list();
						return list;
					}
		});
	}
	/**
	 * 得到用户的角色
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PomfTbSysRole> findRole(String id) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback(new String[]{id}) {
					public List<PomfTbSysRole> doInHibernate(final Session s) throws HibernateException,SQLException {
						Query query = s.createQuery("from PomfTbSysRole as u where u.id=:id");
						query.setString("id", (String)this.params[0]) ;
						List<PomfTbSysRole> list = query.list();
						return list;
					}
		});
	}
	/**
	 * 得到所有权限
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PomfTbSysPower> findAllPower() {
		return getHibernateTemplate().executeFind(
				new HibernateCallback(new String[]{}) {
					public List<PomfTbSysPower> doInHibernate(final Session s) throws HibernateException,SQLException {
						Query query = s.createQuery("from PomfTbSysPower as u");
						List<PomfTbSysPower> list = query.list();
						return list;
					}
		});
	}
	/**
	 * 得到角色所有权限
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PomfTbSysRolePower> findRolePower(String roleId) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback(new String[]{roleId}) {
					public List<PomfTbSysRolePower> doInHibernate(final Session s) throws HibernateException,SQLException {
						String sql = "from PomfTbSysRolePower u where u.id.roleId='" + (String)this.params[0] + "'" ;
						Query q = s.createQuery(sql);
						
						return (List<PomfTbSysRolePower>)q.list();
					}
		});
	}
	
	
	
}
