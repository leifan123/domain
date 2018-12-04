package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainRole  implements Serializable{

	private static final long serialVersionUID = 153447498017888952L;
	private Integer id;

	public DomainRole setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomainRole setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String owner;

	public DomainRole setOwner(String owner){
		 this.owner = owner;
		 return this;
	}

	public String getOwner(){
		 return owner;
	}
	private Date creattime;

	public DomainRole setCreattime(Date creattime){
		 this.creattime = creattime;
		 return this;
	}

	public Date getCreattime(){
		 return creattime;
	}
	private Date updatetime;

	public DomainRole setUpdatetime(Date updatetime){
		 this.updatetime = updatetime;
		 return this;
	}

	public Date getUpdatetime(){
		 return updatetime;
	}
	private String managers;

	public DomainRole setManagers(String managers){
		 this.managers = managers;
		 return this;
	}

	public String getManagers(){
		 return managers;
	}
	private String payee;

	public DomainRole setPayee(String payee){
		 this.payee = payee;
		 return this;
	}

	public String getPayee(){
		 return payee;
	}
	private String operator;

	public DomainRole setOperator(String operator){
		 this.operator = operator;
		 return this;
	}

	public String getOperator(){
		 return operator;
	}
}