package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainTransfer  implements Serializable{

	private static final long serialVersionUID = 1537951930760400041L;
	private Integer id;

	public DomainTransfer setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomainTransfer setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String domainname;

	public DomainTransfer setDomainname(String domainname){
		 this.domainname = domainname;
		 return this;
	}

	public String getDomainname(){
		 return domainname;
	}
	private Integer userid;

	public DomainTransfer setUserid(Integer userid){
		 this.userid = userid;
		 return this;
	}

	public Integer getUserid(){
		 return userid;
	}
	private String authcode;

	public DomainTransfer setAuthcode(String authcode){
		 this.authcode = authcode;
		 return this;
	}

	public String getAuthcode(){
		 return authcode;
	}
	private Integer mcdns;

	public DomainTransfer setMcdns(Integer mcdns){
		 this.mcdns = mcdns;
		 return this;
	}

	public Integer getMcdns(){
		 return mcdns;
	}
	private String bounddomain;

	public DomainTransfer setBounddomain(String bounddomain){
		 this.bounddomain = bounddomain;
		 return this;
	}

	public String getBounddomain(){
		 return bounddomain;
	}
	private String signature;

	public DomainTransfer setSignature(String signature){
		 this.signature = signature;
		 return this;
	}

	public String getSignature(){
		 return signature;
	}
	private String jm;

	public DomainTransfer setJm(String jm){
		 this.jm = jm;
		 return this;
	}

	public String getJm(){
		 return jm;
	}
	private Date createtime;

	public DomainTransfer setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private Date endtime;

	public DomainTransfer setEndtime(Date endtime){
		 this.endtime = endtime;
		 return this;
	}

	public Date getEndtime(){
		 return endtime;
	}
	private String remark;

	public DomainTransfer setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
	private Integer status;

	public DomainTransfer setStatus(Integer status){
		 this.status = status;
		 return this;
	}

	public Integer getStatus(){
		 return status;
	}
	private String msg;

	public DomainTransfer setMsg(String msg){
		 this.msg = msg;
		 return this;
	}

	public String getMsg(){
		 return msg;
	}
}