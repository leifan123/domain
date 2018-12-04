package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomianCart  implements Serializable{

	private static final long serialVersionUID = 153421177927161604L;
	private Integer id;

	public DomianCart setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomianCart setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String ordernumber;

	public DomianCart setOrdernumber(String ordernumber){
		 this.ordernumber = ordernumber;
		 return this;
	}

	public String getOrdernumber(){
		 return ordernumber;
	}
	private Integer domainId;

	public DomianCart setDomainId(Integer domainId){
		 this.domainId = domainId;
		 return this;
	}

	public Integer getDomainId(){
		 return domainId;
	}
	private Float cpCase;

	public DomianCart setCpCase(Float cpCase){
		 this.cpCase = cpCase;
		 return this;
	}

	public Float getCpCase(){
		 return cpCase;
	}
	private Integer caseType;

	public DomianCart setCaseType(Integer caseType){
		 this.caseType = caseType;
		 return this;
	}

	public Integer getCaseType(){
		 return caseType;
	}
	private Integer caseTime;

	public DomianCart setCaseTime(Integer caseTime){
		 this.caseTime = caseTime;
		 return this;
	}

	public Integer getCaseTime(){
		 return caseTime;
	}
	private Date ordercreatetime;

	public DomianCart setOrdercreatetime(Date ordercreatetime){
		 this.ordercreatetime = ordercreatetime;
		 return this;
	}

	public Date getOrdercreatetime(){
		 return ordercreatetime;
	}
	private Date orderendtime;

	public DomianCart setOrderendtime(Date orderendtime){
		 this.orderendtime = orderendtime;
		 return this;
	}

	public Date getOrderendtime(){
		 return orderendtime;
	}
	private Integer paymentstatus;

	public DomianCart setPaymentstatus(Integer paymentstatus){
		 this.paymentstatus = paymentstatus;
		 return this;
	}

	public Integer getPaymentstatus(){
		 return paymentstatus;
	}
	private String zoneid;

	public DomianCart setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private String zonename;

	public DomianCart setZonename(String zonename){
		 this.zonename = zonename;
		 return this;
	}

	public String getZonename(){
		 return zonename;
	}
	private String remark;

	public DomianCart setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}