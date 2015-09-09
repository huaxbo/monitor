package com.hxb.common.secure.priv ;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.hxb.common.secure.SessionUserHolder;
import com.hxb.common.secure.config.SecureConfig;
import com.hxb.common.sso.session.SessionUserVO;


/**
 * ΪDWR���
 * �෽������Ȩ�޿��ƵĻ�����ǿ
 * ���з���Ȩ����֤
 * @author Administrator
 *
 */
public class PrivMethodDwr {
	
	public static String NOPRIV = "noPriv" ;
	
	/**
	 * �෽������Ȩ�޿��ƵĻ�����ǿ
	 * ���з���Ȩ����֤
	 * @param p
	 * @return 
	 * @throws Throwable
	 */
	public Object aroundMethod(ProceedingJoinPoint p) throws Exception{

		if(!SecureConfig.debug){
			SessionUserVO vo = (SessionUserVO)SessionUserHolder.getUserInfo() ;
			
			if(vo == null){
				throw new Exception("����Ȩ����֤ʱ��û�еõ��Ự�е��û���Ϣ��������Ϊϵͳʵ��Ӧ����Ȩ�޿��ƣ���SecureFilterSSO���óɲ�Ӧ��Ȩ�޿���!") ;
			}
			PrivHelp help = new PrivHelp() ;
			boolean proceed = false ;
			String methodName = p.getSignature().toLongString() ;
			Method[] methods = p.getTarget().getClass().getDeclaredMethods() ;
			if(methods != null){
				for(Method m : methods){
					if(methodName.equals(m.toGenericString())){
						String powers[] = vo.getPowers() ;
						String privs_value = ((CheckPrivDwr)(m.getAnnotation(CheckPrivDwr.class))).value() ;
						
						proceed = help.checkValue(m, powers , privs_value) ;
						if(!proceed){
							String[] privs_valueAll = ((CheckPrivDwr)(m.getAnnotation(CheckPrivDwr.class))).ifAll() ;
							proceed = help.checkIfAll(m, powers , privs_valueAll) ;
						}
						if(!proceed){
							String[] privs_valueAny = ((CheckPrivDwr)(m.getAnnotation(CheckPrivDwr.class))).ifAny() ;
							proceed = help.checkIfAny(m, powers , privs_valueAny) ;
						}
						
						break ;
					}
				}
			}
			if(proceed){
				try{
					return p.proceed() ;
				}catch(Throwable ab){
					ab.printStackTrace() ;
					throw new Exception(ab.getMessage()) ;
				}
			}else{
				throw new Exception("noPriv") ;
				//return "noPriv" ;
			}
		}else{
			try{
				return p.proceed() ;
			}catch(Throwable ab){
				ab.printStackTrace() ;
				throw new Exception(ab.getMessage()) ;
			}
		}
	}
}
