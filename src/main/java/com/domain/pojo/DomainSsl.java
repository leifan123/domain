package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainSsl  implements Serializable{

	private static final long serialVersionUID = 1541399563828590299L;
	private Integer id;

	public DomainSsl setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private Integer certtypeid;

	public DomainSsl setCerttypeid(Integer certtypeid){
		 this.certtypeid = certtypeid;
		 return this;
	}

	public Integer getCerttypeid(){
		 return certtypeid;
	}
	private String companyid;

	public DomainSsl setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String certencrypttype;

	public DomainSsl setCertencrypttype(String certencrypttype){
		 this.certencrypttype = certencrypttype;
		 return this;
	}

	public String getCertencrypttype(){
		 return certencrypttype;
	}
	
	private String cerepayType;
	
	public String getCerepayType() {
		return cerepayType;
	}

	public void setCerepayType(String cerepayType) {
		this.cerepayType = cerepayType;
	}
	private Integer certexptime;

	public DomainSsl setCertexptime(Integer certexptime){
		 this.certexptime = certexptime;
		 return this;
	}

	public Integer getCertexptime(){
		 return certexptime;
	}
	private Integer freessl30;

	public DomainSsl setFreessl30(Integer freessl30){
		 this.freessl30 = freessl30;
		 return this;
	}

	public Integer getFreessl30(){
		 return freessl30;
	}
	private Integer certservernumber;

	public DomainSsl setCertservernumber(Integer certservernumber){
		 this.certservernumber = certservernumber;
		 return this;
	}

	public Integer getCertservernumber(){
		 return certservernumber;
	}
	private String certalldomain;

	public DomainSsl setCertalldomain(String certalldomain){
		 this.certalldomain = certalldomain;
		 return this;
	}

	public String getCertalldomain(){
		 return certalldomain;
	}
	private String certcsr;

	public DomainSsl setCertcsr(String certcsr){
		 this.certcsr = certcsr;
		 return this;
	}

	public String getCertcsr(){
		 return certcsr;
	}
	private Integer certvalidatetype;

	public DomainSsl setCertvalidatetype(Integer certvalidatetype){
		 this.certvalidatetype = certvalidatetype;
		 return this;
	}

	public Integer getCertvalidatetype(){
		 return certvalidatetype;
	}
	private String certbankname;

	public DomainSsl setCertbankname(String certbankname){
		 this.certbankname = certbankname;
		 return this;
	}

	public String getCertbankname(){
		 return certbankname;
	}
	private String certbankno;

	public DomainSsl setCertbankno(String certbankno){
		 this.certbankno = certbankno;
		 return this;
	}

	public String getCertbankno(){
		 return certbankno;
	}
	private String orgName;

	public DomainSsl setOrgName(String orgName){
		 this.orgName = orgName;
		 return this;
	}

	public String getOrgName(){
		 return orgName;
	}
	private String orgEmail;

	public DomainSsl setOrgEmail(String orgEmail){
		 this.orgEmail = orgEmail;
		 return this;
	}

	public String getOrgEmail(){
		 return orgEmail;
	}
	private String orgType;

	public DomainSsl setOrgType(String orgType){
		 this.orgType = orgType;
		 return this;
	}

	public String getOrgType(){
		 return orgType;
	}
	private String orgPhone;

	public DomainSsl setOrgPhone(String orgPhone){
		 this.orgPhone = orgPhone;
		 return this;
	}

	public String getOrgPhone(){
		 return orgPhone;
	}
	private String ownuserName;

	public DomainSsl setOwnuserName(String ownuserName){
		 this.ownuserName = ownuserName;
		 return this;
	}

	public String getOwnuserName(){
		 return ownuserName;
	}
	private String ownuserEmail;

	public DomainSsl setOwnuserEmail(String ownuserEmail){
		 this.ownuserEmail = ownuserEmail;
		 return this;
	}

	public String getOwnuserEmail(){
		 return ownuserEmail;
	}
	private String ownuserPhone;

	public DomainSsl setOwnuserPhone(String ownuserPhone){
		 this.ownuserPhone = ownuserPhone;
		 return this;
	}

	public String getOwnuserPhone(){
		 return ownuserPhone;
	}
	private String ownuserIdcardnumber;

	public DomainSsl setOwnuserIdcardnumber(String ownuserIdcardnumber){
		 this.ownuserIdcardnumber = ownuserIdcardnumber;
		 return this;
	}

	public String getOwnuserIdcardnumber(){
		 return ownuserIdcardnumber;
	}
	
	private String orderCode;
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	private Integer type;

	public DomainSsl setType(Integer type){
		 this.type = type;
		 return this;
	}

	public Integer getType(){
		 return type;
	}
	private Integer status;

	public DomainSsl setStatus(Integer status){
		 this.status = status;
		 return this;
	}

	public Integer getStatus(){
		 return status;
	}
	private Date createtime;

	public DomainSsl setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private Date endtime;

	public DomainSsl setEndtime(Date endtime){
		 this.endtime = endtime;
		 return this;
	}

	public Date getEndtime(){
		 return endtime;
	}
	private String remark;

	public DomainSsl setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}