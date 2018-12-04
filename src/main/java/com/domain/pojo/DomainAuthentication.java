package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainAuthentication  implements Serializable{

	private static final long serialVersionUID = 1534211779261146237L;
	private Integer id;

	public DomainAuthentication setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	
	private String companyId;
	
	public String getCompanyId() {
		return companyId;
	}

	public DomainAuthentication setCompanyId(String companyId) {
		this.companyId = companyId;
		return this;
	}
	
	private Integer userid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	private String username;

	public DomainAuthentication setUsername(String username){
		 this.username = username;
		 return this;
	}

	public String getUsername(){
		 return username;
	}
	private String authenicationtype;

	public DomainAuthentication setAuthenicationtype(String authenicationtype){
		 this.authenicationtype = authenicationtype;
		 return this;
	}

	public String getAuthenicationtype(){
		 return authenicationtype;
	}
	
	private String organizationType;
	
	public String getOrganizationType() {
		return organizationType;
	}

	public DomainAuthentication setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
		return this;
	}

	private Integer cardtype;

	public DomainAuthentication setCardtype(Integer cardtype){
		 this.cardtype = cardtype;
		 return this;
	}

	public Integer getCardtype(){
		 return cardtype;
	}
	private String cardnum;

	public DomainAuthentication setCardnum(String cardnum){
		 this.cardnum = cardnum;
		 return this;
	}

	public String getCardnum(){
		 return cardnum;
	}
	private String cardurl;

	public DomainAuthentication setCardurl(String cardurl){
		 this.cardurl = cardurl;
		 return this;
	}

	public String getCardurl(){
		 return cardurl;
	}
	private Integer status;

	public DomainAuthentication setStatus(Integer status){
		 this.status = status;
		 return this;
	}

	public Integer getStatus(){
		 return status;
	}
	private Date createtime;

	public DomainAuthentication setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private Date completetime;

	public DomainAuthentication setCompletetime(Date completetime){
		 this.completetime = completetime;
		 return this;
	}

	public Date getCompletetime(){
		 return completetime;
	}
	private String remark;

	public DomainAuthentication setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}