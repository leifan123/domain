package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainOperatelog  implements Serializable{

	private static final long serialVersionUID = 1534211779261623264L;
	private Integer id;

	public DomainOperatelog setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomainOperatelog setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String operator;

	public DomainOperatelog setOperator(String operator){
		 this.operator = operator;
		 return this;
	}

	public String getOperator(){
		 return operator;
	}
	private String operatorip;

	public DomainOperatelog setOperatorip(String operatorip){
		 this.operatorip = operatorip;
		 return this;
	}

	public String getOperatorip(){
		 return operatorip;
	}
	private String operatetarget;

	public DomainOperatelog setOperatetarget(String operatetarget){
		 this.operatetarget = operatetarget;
		 return this;
	}

	public String getOperatetarget(){
		 return operatetarget;
	}
	private String operatetype;

	public DomainOperatelog setOperatetype(String operatetype){
		 this.operatetype = operatetype;
		 return this;
	}

	public String getOperatetype(){
		 return operatetype;
	}
	private String operatedes;

	public DomainOperatelog setOperatedes(String operatedes){
		 this.operatedes = operatedes;
		 return this;
	}

	public String getOperatedes(){
		 return operatedes;
	}
	private Integer operatestatus;

	public DomainOperatelog setOperatestatus(Integer operatestatus){
		 this.operatestatus = operatestatus;
		 return this;
	}

	public Integer getOperatestatus(){
		 return operatestatus;
	}
	private String errormessage;

	public DomainOperatelog setErrormessage(String errormessage){
		 this.errormessage = errormessage;
		 return this;
	}

	public String getErrormessage(){
		 return errormessage;
	}
	private Date operatortime;

	public DomainOperatelog setOperatortime(Date operatortime){
		 this.operatortime = operatortime;
		 return this;
	}

	public Date getOperatortime(){
		 return operatortime;
	}
	private String remark;

	public DomainOperatelog setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}