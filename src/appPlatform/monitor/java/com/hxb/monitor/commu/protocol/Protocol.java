/**
 * 
 */
package com.hxb.monitor.commu.protocol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Administrator
 *
 */
public class Protocol {

	/**每一类协议拥有的属性**/
	private String protocol;//协议版本
	private ArrayList<Command> commands_read = new ArrayList<Command>(0);//此协议“遥测”命令集
	private ArrayList<Command> commands_set = new ArrayList<Command>(0);//此协议“设置”命令集
	private ArrayList<Command> commands_func = new ArrayList<Command>(0);//此协议“命令”命令集
	
	/**
	 * 获取全部协议命令结合
	 * 按照协议分组，结构如下：
	 * protocols
	 * 	|- protocol_1
	 *		|- commands_read
	 * 		|- commands_set
	 * 		|- commands_func 			
	 * 	|- protocol_2
	 * 		|- commands_read
	 * 		|- commands_set
	 * 		|- commands_func
	 * @param protocols
	 * 	参数结构如下：
	 * 	protocol_1
	 * 		|- protocolName
	 * 		|- command_Class
	 * 		|- command_Object
	 * protocol_2
	 * 		|- protocolName
	 * 		|- command_Class
	 * 		|- command_Object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static ArrayList<Protocol> getAllCommands(Object[][] protocols) throws IllegalArgumentException, IllegalAccessException{
		ArrayList<Protocol> pros = new ArrayList<Protocol>(0);
		if(protocols == null){
			return pros;
		}
		for(int i = 0 ;i < protocols.length;i++){
			String pnm = (String)protocols[i][0];
			Class<?> clss = (Class<?>)protocols[i][1];
			Object obj = protocols[i][2];
			Protocol pro = new Protocol();
			getCommandsForProtocol(pro,pnm,clss,obj,null);
			pros.add(pro);
		}
		return pros;
	}
	/**
	 * 获取当前协议的命令集合
	 * @param command:当前协议命令集合对象
	 * @param protocol:协议版本
	 * @param command_Class：协议命令class
	 * @param command_Obj：协议命令object
	 * @param commandht: 命令哈希表，用于进行命令重载记录使用
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static void getCommandsForProtocol(Protocol protocol,String protocolVer,
			Class<?> command_Class,
			Object command_Obj,
			Hashtable<String,Command> commandht) throws IllegalArgumentException, IllegalAccessException{
		if(protocol == null){
			return ;
		} 
		if(protocolVer == null || protocolVer.equals("")){
			return ;
		}
		if(command_Class == null || command_Obj == null){
			return ;
		}
		if(commandht == null){
			commandht = new Hashtable<String,Command>(0);
		}
		//读取父类对象命令属性
		Class<?> sup = command_Class.getSuperclass();
		if(sup != null && !sup.getSimpleName().equals(Object.class.getSimpleName())){
			getCommandsForProtocol(protocol,protocolVer,sup,command_Obj,commandht);
		}
		//读去本对象命令属性
		protocol.protocol = protocolVer;
		Field[] fds = command_Class.getDeclaredFields();//获取声明属性集合
		if(fds == null){
			return ;
		}
		for(Field fd : fds){
			String fdnm = fd.getName();
			fd.setAccessible(true);
			CommandAnnotation ca = fd.getAnnotation(CommandAnnotation.class);
			if(ca == null){
				continue;
			}
			String funcCode = (String)fd.get(command_Obj);
			boolean usage = ca.usage();
			String commandType = ca.commandType();
			String commandName = ca.commandName();
			String commandTip = ca.commandTip();
			if(commandTip == null || commandTip.equals("")){
				commandTip = commandName;
			}
			String doAction = ca.doAction();
			String doMethod = ca.doMethod();
			boolean winScroll = ca.winScroll();
			boolean resizeable = ca.resizeable();
			int winLeft = ca.winLeft();
			int winTop = ca.winTop();
			int winWidth = ca.winWidth();
			int winHeight = ca.winHeight();
			String paramType = ca.paramType();
			if(paramType == null){
				paramType = "";
			}
			Command cd = new Command();
			cd.setCommandName(commandName);
			cd.setCommandTip(commandTip);
			cd.setFieldName(fdnm);
			cd.setDoAction(doAction);
			cd.setDoMethod(doMethod);
			cd.setWinScroll(winScroll);
			cd.setResizeable(resizeable);
			cd.setWinLeft(winLeft);
			cd.setWinTop(winTop);
			cd.setWinWidth(winWidth);
			cd.setWinHeight(winHeight);
			cd.setParamType(paramType);
			cd.setFuncCode(funcCode);
			//处理重载属性
			if(commandht.containsKey(fdnm)){
				commandht.remove(fdnm);
				commandht.put(fdnm, cd);
				if(!overrideHandle(protocol.commands_read,cd,usage)){
					if(!overrideHandle(protocol.commands_set,cd,usage)){
						overrideHandle(protocol.commands_func,cd,usage);
					}
				}
				continue;
			}
			if(usage){
				commandht.put(fdnm, cd);
				if(commandType != null && commandType.equals(CommandConstant.COMMAND_READ)){
					protocol.commands_read.add(cd);
				}
				if(commandType != null && commandType.equals(CommandConstant.COMMAND_SET)){
					protocol.commands_set.add(cd);
				}
				if(commandType != null && commandType.equals(CommandConstant.COMMAND_FUNC)){
					protocol.commands_func.add(cd);
				}
			}
		}
	} 
	/**
	 * 处理重载command
	 * @param commands
	 * @param command
	 * @param usage
	 */
	private static boolean overrideHandle(ArrayList<Command> commands,Command command,boolean usage){
		if(command == null || commands == null){
			return false;
		}
		for(Command cd :commands){
			if(command.getFieldName().equals(cd.getFieldName())){
				commands.remove(cd);
				if(usage){
					commands.add(command);
				}
				return true;
			}
		}
		return false;
	}
	/*********************/
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public ArrayList<Command> getCommands_read() {
		return commands_read;
	}

	public void setCommands_read(ArrayList<Command> commandsRead) {
		commands_read = commandsRead;
	}

	public ArrayList<Command> getCommands_set() {
		return commands_set;
	}


	public void setCommands_set(ArrayList<Command> commandsSet) {
		commands_set = commandsSet;
	}


	public ArrayList<Command> getCommands_func() {
		return commands_func;
	}


	public void setCommands_func(ArrayList<Command> commandsFunc) {
		commands_func = commandsFunc;
	}
}
/**
 * command object
 * @author Administrator
 *
 */
class Command{
	private String fieldName;//字段名称
	/**每一条具体命令涉及到的属性字段**/
	private String commandName;//命令名称
	private String commandTip;//命令tip提示内容
	private String funcCode;//功能码
	private String doAction;//命令调用的action
	private String doMethod;//命令调用的medhod
	private boolean winScroll;//窗口是否滚动
	private boolean resizeable;//窗口是否运行调整
	private int winLeft;//窗口距显示屏左边的距离
	private int winTop;//窗口距显示器顶端的距离
	private int winWidth;//窗口宽度
	private int winHeight;//窗口高度
	private String paramType;//命令参数种类。在相同协议中，进行不同参数隐藏|显示控制

	public String getCommandTip() {
		return commandTip;
	}
	public void setCommandTip(String commandTip) {
		this.commandTip = commandTip;
	}
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getDoAction() {
		return doAction;
	}
	public void setDoAction(String doAction) {
		this.doAction = doAction;
	}
	public String getDoMethod() {
		return doMethod;
	}
	public void setDoMethod(String doMethod) {
		this.doMethod = doMethod;
	}
	public boolean isWinScroll() {
		return winScroll;
	}
	public void setWinScroll(boolean winScroll) {
		this.winScroll = winScroll;
	}
	public boolean isResizeable() {
		return resizeable;
	}
	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}
	public int getWinLeft() {
		return winLeft;
	}
	public void setWinLeft(int winLeft) {
		this.winLeft = winLeft;
	}
	public int getWinTop() {
		return winTop;
	}
	public void setWinTop(int winTop) {
		this.winTop = winTop;
	}
	public int getWinWidth() {
		return winWidth;
	}
	public void setWinWidth(int winWidth) {
		this.winWidth = winWidth;
	}
	public int getWinHeight() {
		return winHeight;
	}
	public void setWinHeight(int winHeight) {
		this.winHeight = winHeight;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
}
