package com.automic.sso.web.login;

import java.util.*;

import com.automic.sso.util.*;
import com.automic.sso.po.*;
import com.automic.sso.session.UserVO ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 *--Transactional：声明事物监控
 *--Service：设置spring annotation方式注入Busi，供strutsAction中annotation注入busi
 *注意：类型必须整个context中没有重复类型
 */
@Transactional
@Service
public class LoginBusi {
	
	/**
	 * 声明dao层对象
	 * --Autowired：spring annotation方式注入对象。
	 * 此属性不必生成setter、getter方法，annotation方式注入已经做处理
	 * 注意：此对象必须添加注释选项@Repository进行注入。且此类型必须整个context中没有重复类型
	 */
	@Autowired
	private LoginDao dao ;
	
	/**
	 * --Transactional：设置事物控制类型。查询操作设置值为：readOnly=true
	 */
	@Transactional(readOnly=true)
	public UserVO login(String userName , String password , String fixedIp){
		UserVO vo = new UserVO() ;
		List<PomfTbSysUser> users = this.dao.findUser(userName , password , fixedIp) ;
		if(users == null || users.size() == 0){
			return null ;
		}else{
			//登录成功
			PomfTbSysUser po = users.get(0) ;
			vo.setName(po.getName()) ;
			vo.setPassword(po.getPassword()) ;
			vo.setFixedIP(po.getFixedIp()) ;

			vo.setId(po.getId()) ;
			vo.setRoleId(po.getRoleId()) ;
			vo.setNameCN(po.getNameCn()) ;
			vo.setOrgId(po.getPomfTbSysRole().getPomfTbSysOrg().getId());
			
			if(po.getIsAdmin()!=null&&po.getIsAdmin().equals(Constant.YES)){
				//是系统管理员，默认赋值全部权限信息
				vo.setIsAdmin(Constant.YES) ;
				
				//得到所有权限，给系统管理员
				List<PomfTbSysPower> powers = this.dao.findAllPower() ;
				if(powers != null && powers.size() > 0){
					PomfTbSysPower ppo = null ;
					String powerIds[] = new String[powers.size() + 1] ;
					powerIds[0] = Constant.debugPower ;
					int count = 1 ;
					Iterator<PomfTbSysPower> it = powers.iterator() ;
					while(it.hasNext()){
						ppo = it.next() ;
						powerIds[count] = ppo.getPcd() ;
						count++ ;
					}
					vo.setPowers(powerIds) ;
				}else{
					vo.setPowers(new String[]{Constant.debugPower}) ;
				}
				
			}else{
				//一般用户
				vo.setIsAdmin(Constant.NO) ;
				//得到用户的在角色
				List<PomfTbSysRole> roles = this.dao.findRole(po.getRoleId()) ;
				if(roles == null || roles.size() == 0){
					return null ;
				}else{
					PomfTbSysRole role = (PomfTbSysRole)roles.get(0) ;
					vo.setRoleId(role.getId()) ;
					vo.setRoleName(role.getName()) ;
					//得到角色所拥有的权限
					List<PomfTbSysRolePower> powers = this.dao.findRolePower(role.getId()) ;
					if(powers != null && powers.size() > 0){
						PomfTbSysRolePower ppo = null ;
						String powerIds[] = new String[powers.size() + 1] ;
						powerIds[0] = Constant.debugPower ;
						int count = 1 ;
						Iterator<PomfTbSysRolePower> it = powers.iterator() ;
						while(it.hasNext()){
							ppo = it.next() ;
							powerIds[count] = ppo.getId().getPcd() ;
							count++ ;
						}
						vo.setPowers(powerIds) ;
					}else{
						vo.setPowers(new String[]{Constant.debugPower}) ;
					}
				}
			}
		}
		////////////////////////////
		//放入sessionUserVO的隐含属性
		vo = new VoHiddenAtt().setSessionUserVOHiddenAtt(vo) ;
		
		return vo ;
	}
	/**
	 * save test
	 * --Transactional：设置事物控制类型。存储、更新、删除操作设置值为：rollbackFor=Exception.class
	 */
	@Transactional(rollbackFor=Exception.class)
	public void loginLog(UserVO vo){
		PomfTbSysLog log = new PomfTbSysLog() ;
		log.setRoleId(vo.getRoleId()) ;
		log.setOptIp(vo.getCurrIp()) ;
		log.setOptCnt("登录系统") ;
		log.setUserName(vo.getNameCN()) ;
		log.setOptTm(new Date(System.currentTimeMillis())) ;
		dao.save(log);
	}

}
