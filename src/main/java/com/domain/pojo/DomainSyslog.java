package com.domain.pojo;

import java.io.Serializable;

public class DomainSyslog  implements Serializable{

	private static final long serialVersionUID = 1534211779261618903L;
	private Integer id;

	public DomainSyslog setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String loglevel;

	public DomainSyslog setLoglevel(String loglevel){
		 this.loglevel = loglevel;
		 return this;
	}

	public String getLoglevel(){
		 return loglevel;
	}
	private String classname;

	public DomainSyslog setClassname(String classname){
		 this.classname = classname;
		 return this;
	}

	public String getClassname(){
		 return classname;
	}
	private String method;

	public DomainSyslog setMethod(String method){
		 this.method = method;
		 return this;
	}

	public String getMethod(){
		 return method;
	}
	private String msg;

	public DomainSyslog setMsg(String msg){
		 this.msg = msg;
		 return this;
	}

	public String getMsg(){
		 return msg;
	}
	private String createtime;

	public DomainSyslog setCreatetime(String createtime){
		 this.createtime = createtime;
		 return this;
	}

	public String getCreatetime(){
		 return createtime;
	}
}