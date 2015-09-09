package com.hxb.demo.web.demo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbDemoStuAttachment;
import com.hxb.global.po.TbDemoStudent;
import com.hxb.global.util.HttpUtil;
import com.hxb.global.util.CommonUtil;
import com.hxb.util.Config;

/**
 * @author Administrator
 *--Transactional������������
 *--Service������spring annotation��ʽע��Busi����strutsAction��annotationע��busi
 *ע�⣺���ͱ�������context��û���ظ�����
 */
@Transactional
@Service
public class Demo2Busi {

	/**
	 * ����dao�����
	 * --Autowired��spring annotation��ʽע�����
	 * �����Բ�������setter��getter������annotation��ʽע���Ѿ�������
	 * ע�⣺�˶���������ע��ѡ��@Repository����ע�롣�Ҵ����ͱ�������context��û���ظ�����
	 */
	@Autowired
	private Demo2Dao dao;
	
	/****************
	/**
	 * --Transactional����������������͡���ѯ��������ֵΪ��readOnly=true
	 */
	@Transactional(readOnly=true)
	public Long getCount(Hashtable<String,Object> params,String param){
		return dao.getCount(params,param);
	}
	/**
	 * --Transactional����������������͡���ѯ��������ֵΪ��readOnly=true
	 */
	@Transactional(readOnly=true)
	public List<Object> list(Hashtable<String,Object> params,String param){
		return dao.list(params,param);
	}
	
	
	/**
	 * --Transactional����������������͡���ѯ��������ֵΪ��readOnly=true
	 */
	/** 
	* @Title: getById 
	* @Description: TODO(��ȡָ����¼����) 
	* @return PomfTbDemoStudent    �������� ��ѧ����Ϣ
	* @throws 
	*/ 
	@Transactional(readOnly=true)
	public TbDemoStudent getById(String id){
		return dao.get(id);
	}
	/**
	 * --Transactional����������������͡���ѯ��������ֵΪ��readOnly=true
	 */
	
	/** 
	* @Title: validateForm 
	* @Description: TODO(Զ��У�飺ѧ�������Ƿ��ظ�) 
	* @return Boolean    �������� 
	* @throws 
	*/ 
	@Transactional(readOnly=true)
	public Boolean validateForm(Hashtable<String,Object> params){
		return dao.validateForm(params);
	}
	
	/**
	 * save test
	 * --Transactional����������������͡��洢�����¡�ɾ����������ֵΪ��rollbackFor=Exception.class
	 */
	/** 
	* @Title: save 
	* @Description: TODO(����ѧ����Ϣ) 
	* @return String    �������� ��ѧ����Ϣid
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	public String save(TbDemoStudent po,File[][] files,
			String[][] fileNames,
			String[] del_fileIds){
		po = (TbDemoStudent)dao.save(po);
		//��������
		remvAttas(del_fileIds);//ɾ������
		saveAttas(po.getId(),files,fileNames);//��������
		
		return po.getId();
	}	
	
	/**
	 * delete test
	 * --Transactional����������������͡��洢�����¡�ɾ����������ֵΪ��rollbackFor=Exception.class
	 */
	/** 
	* @Title: save 
	* @Description: TODO(ɾ��ѧ����Ϣ) 
	* @return String    �������� ��ѧ����Ϣid
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	public void delete(String[] ids){		
		//ɾ������������¼���ļ�
		for(String id:ids){
			remvAttas(dao.getAttas(id));
			//ɾ����¼
			dao.delete(id);
		}
	}
		
	/** 
	* @Title: remvAttas 
	* @Description: TODO(ɾ�����������༭����ʱ�����и���(�ļ�)��ɾ������) 
	* @return void    �������� 
	* @throws 
	*/ 
	private void remvAttas(String[] del_fileIds){
		if(del_fileIds != null){
			String[] furls = this.delAttas(del_fileIds);
			if(furls != null){
				for(String furl:furls){//����ɾ���ļ�
					File f = new File(HttpUtil.getRoot() + "\\" + furl);
					if(f != null && f.exists()){
						f.delete();
					}
				}
			}
		}
	}
	/** 
	* @Title: saveAttas 
	* @Description: TODO(�����ϴ�����) 
	* @return void    �������� 
	* @throws 
	*/ 
	private void saveAttas(String id,File[][] filess,String[][] fileNamess){
		for(int j=0;j<filess.length;j++){
			File[] files = filess[j];
			String[] fileNames = fileNamess[j];
			if(files != null){
				for(int i = 0;i< files.length;i++){
					File f = files[i];//�ļ�
					String fn = fileNames[i];//�ļ�����
					if(f == null){//����Ϊ�գ�������һ�����������
						continue;
					}
					//�����ļ�
					String fa = CommonUtil.createAttaName();
					fa += "." + fn.split("\\.")[fn.split("\\.").length - 1];
					try{
						FileInputStream fis = new FileInputStream(f);
						FileOutputStream fos = new FileOutputStream(
								HttpUtil.getRoot()+ "\\" + Config.getConfig("attachmentPosition") + "\\" + fa);
						byte[] buff = new byte[1024];
						int len = 0;
						while((len=fis.read(buff))>-1){
							fos.write(buff, 0, len);
						}
						fis.close();
						fos.close();
					}catch(Exception e){
						e.printStackTrace();
					}finally{}	
					
					//�����ļ���Ϣ
					TbDemoStuAttachment atta = new TbDemoStuAttachment();
					atta.setAttaUrl(Config.getConfig("attachmentPosition") + "/" + fa);
					atta.setName(fn);
					atta.setStudentId(id);
					if(j>0){//����������
						atta.setAttaType("1");
					}
					this.saveAttachement(atta);
				}
			}
		}		
	}
	/** 
	* @Title: saveAttachement 
	* @Description: TODO(������һ�仰�����������������) 
	* @return String    �������� 
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	private String saveAttachement(TbDemoStuAttachment atta){
		return dao.saveAttachement(atta);
	}
	/** 
	* @Title: delAttas 
	* @Description: TODO(ɾ��������¼) 
	* @return String[]    �������� 
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	private String[] delAttas(String[] ids){
		return dao.delAttas(ids);
	}
}
