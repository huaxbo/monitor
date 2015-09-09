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
 *--Transactional������������
 *--Service������spring annotation��ʽע��Busi����strutsAction��annotationע��busi
 *ע�⣺���ͱ�������context��û���ظ�����
 */
@Transactional
@Service
public class LoginBusi {
	
	/**
	 * ����dao�����
	 * --Autowired��spring annotation��ʽע�����
	 * �����Բ�������setter��getter������annotation��ʽע���Ѿ�������
	 * ע�⣺�˶���������ע��ѡ��@Repository����ע�롣�Ҵ����ͱ�������context��û���ظ�����
	 */
	@Autowired
	private LoginDao dao ;
	
	/**
	 * --Transactional����������������͡���ѯ��������ֵΪ��readOnly=true
	 */
	@Transactional(readOnly=true)
	public UserVO login(String userName , String password , String fixedIp){
		UserVO vo = new UserVO() ;
		List<PomfTbSysUser> users = this.dao.findUser(userName , password , fixedIp) ;
		if(users == null || users.size() == 0){
			return null ;
		}else{
			//��¼�ɹ�
			PomfTbSysUser po = users.get(0) ;
			vo.setName(po.getName()) ;
			vo.setPassword(po.getPassword()) ;
			vo.setFixedIP(po.getFixedIp()) ;

			vo.setId(po.getId()) ;
			vo.setRoleId(po.getRoleId()) ;
			vo.setNameCN(po.getNameCn()) ;
			vo.setOrgId(po.getPomfTbSysRole().getPomfTbSysOrg().getId());
			
			if(po.getIsAdmin()!=null&&po.getIsAdmin().equals(Constant.YES)){
				//��ϵͳ����Ա��Ĭ�ϸ�ֵȫ��Ȩ����Ϣ
				vo.setIsAdmin(Constant.YES) ;
				
				//�õ�����Ȩ�ޣ���ϵͳ����Ա
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
				//һ���û�
				vo.setIsAdmin(Constant.NO) ;
				//�õ��û����ڽ�ɫ
				List<PomfTbSysRole> roles = this.dao.findRole(po.getRoleId()) ;
				if(roles == null || roles.size() == 0){
					return null ;
				}else{
					PomfTbSysRole role = (PomfTbSysRole)roles.get(0) ;
					vo.setRoleId(role.getId()) ;
					vo.setRoleName(role.getName()) ;
					//�õ���ɫ��ӵ�е�Ȩ��
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
		//����sessionUserVO����������
		vo = new VoHiddenAtt().setSessionUserVOHiddenAtt(vo) ;
		
		return vo ;
	}
	/**
	 * save test
	 * --Transactional����������������͡��洢�����¡�ɾ����������ֵΪ��rollbackFor=Exception.class
	 */
	@Transactional(rollbackFor=Exception.class)
	public void loginLog(UserVO vo){
		PomfTbSysLog log = new PomfTbSysLog() ;
		log.setRoleId(vo.getRoleId()) ;
		log.setOptIp(vo.getCurrIp()) ;
		log.setOptCnt("��¼ϵͳ") ;
		log.setUserName(vo.getNameCN()) ;
		log.setOptTm(new Date(System.currentTimeMillis())) ;
		dao.save(log);
	}

}
