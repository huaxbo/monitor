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
 *--Transactional：声明事物监控
 *--Service：设置spring annotation方式注入Busi，供strutsAction中annotation注入busi
 *注意：类型必须整个context中没有重复类型
 */
@Transactional
@Service
public class Demo2Busi {

	/**
	 * 声明dao层对象
	 * --Autowired：spring annotation方式注入对象。
	 * 此属性不必生成setter、getter方法，annotation方式注入已经做处理
	 * 注意：此对象必须添加注释选项@Repository进行注入。且此类型必须整个context中没有重复类型
	 */
	@Autowired
	private Demo2Dao dao;
	
	/****************
	/**
	 * --Transactional：设置事物控制类型。查询操作设置值为：readOnly=true
	 */
	@Transactional(readOnly=true)
	public Long getCount(Hashtable<String,Object> params,String param){
		return dao.getCount(params,param);
	}
	/**
	 * --Transactional：设置事物控制类型。查询操作设置值为：readOnly=true
	 */
	@Transactional(readOnly=true)
	public List<Object> list(Hashtable<String,Object> params,String param){
		return dao.list(params,param);
	}
	
	
	/**
	 * --Transactional：设置事物控制类型。查询操作设置值为：readOnly=true
	 */
	/** 
	* @Title: getById 
	* @Description: TODO(获取指定记录详情) 
	* @return PomfTbDemoStudent    返回类型 ：学生信息
	* @throws 
	*/ 
	@Transactional(readOnly=true)
	public TbDemoStudent getById(String id){
		return dao.get(id);
	}
	/**
	 * --Transactional：设置事物控制类型。查询操作设置值为：readOnly=true
	 */
	
	/** 
	* @Title: validateForm 
	* @Description: TODO(远程校验：学生编码是否重复) 
	* @return Boolean    返回类型 
	* @throws 
	*/ 
	@Transactional(readOnly=true)
	public Boolean validateForm(Hashtable<String,Object> params){
		return dao.validateForm(params);
	}
	
	/**
	 * save test
	 * --Transactional：设置事物控制类型。存储、更新、删除操作设置值为：rollbackFor=Exception.class
	 */
	/** 
	* @Title: save 
	* @Description: TODO(保存学生信息) 
	* @return String    返回类型 ：学生信息id
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	public String save(TbDemoStudent po,File[][] files,
			String[][] fileNames,
			String[] del_fileIds){
		po = (TbDemoStudent)dao.save(po);
		//附件处理
		remvAttas(del_fileIds);//删除附件
		saveAttas(po.getId(),files,fileNames);//新增附件
		
		return po.getId();
	}	
	
	/**
	 * delete test
	 * --Transactional：设置事物控制类型。存储、更新、删除操作设置值为：rollbackFor=Exception.class
	 */
	/** 
	* @Title: save 
	* @Description: TODO(删除学生信息) 
	* @return String    返回类型 ：学生信息id
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	public void delete(String[] ids){		
		//删除关联附件记录及文件
		for(String id:ids){
			remvAttas(dao.getAttas(id));
			//删除记录
			dao.delete(id);
		}
	}
		
	/** 
	* @Title: remvAttas 
	* @Description: TODO(删除附件：：编辑操作时对已有附件(文件)的删除操作) 
	* @return void    返回类型 
	* @throws 
	*/ 
	private void remvAttas(String[] del_fileIds){
		if(del_fileIds != null){
			String[] furls = this.delAttas(del_fileIds);
			if(furls != null){
				for(String furl:furls){//逐项删除文件
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
	* @Description: TODO(保存上传附件) 
	* @return void    返回类型 
	* @throws 
	*/ 
	private void saveAttas(String id,File[][] filess,String[][] fileNamess){
		for(int j=0;j<filess.length;j++){
			File[] files = filess[j];
			String[] fileNames = fileNamess[j];
			if(files != null){
				for(int i = 0;i< files.length;i++){
					File f = files[i];//文件
					String fn = fileNames[i];//文件名称
					if(f == null){//附件为空，继续下一附件处理操作
						continue;
					}
					//保存文件
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
					
					//保存文件信息
					TbDemoStuAttachment atta = new TbDemoStuAttachment();
					atta.setAttaUrl(Config.getConfig("attachmentPosition") + "/" + fa);
					atta.setName(fn);
					atta.setStudentId(id);
					if(j>0){//单附件处理
						atta.setAttaType("1");
					}
					this.saveAttachement(atta);
				}
			}
		}		
	}
	/** 
	* @Title: saveAttachement 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	private String saveAttachement(TbDemoStuAttachment atta){
		return dao.saveAttachement(atta);
	}
	/** 
	* @Title: delAttas 
	* @Description: TODO(删除附件记录) 
	* @return String[]    返回类型 
	* @throws 
	*/ 
	@Transactional(rollbackFor=Exception.class)
	private String[] delAttas(String[] ids){
		return dao.delAttas(ids);
	}
}
