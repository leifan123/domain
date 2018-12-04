package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainTemplate  implements Serializable{

	private static final long serialVersionUID = 1534211779261617742L;
	private Integer id;
	private int role;

	public DomainTemplate setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomainTemplate setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String userid;

	public DomainTemplate setUserid(String userid){
		 this.userid = userid;
		 return this;
	}

	public String getUserid(){
		 return userid;
	}
	private String companyCn;

	public DomainTemplate setCompanyCn(String companyCn){
		 this.companyCn = companyCn;
		 return this;
	}

	public String getCompanyCn(){
		 return companyCn;
	}
	
	private String companyEn;
	
	public String getCompanyEn() {
		return companyEn;
	}

	public DomainTemplate setCompanyEn(String companyEn) {
		this.companyEn = companyEn;
		return this;
	}
	private String lastnameCn;

	public DomainTemplate setLastnameCn(String lastnameCn){
		 this.lastnameCn = lastnameCn;
		 return this;
	}

	public String getLastnameCn(){
		 return lastnameCn;
	}
	private String firstnameCn;

	public DomainTemplate setFirstnameCn(String firstnameCn){
		 this.firstnameCn = firstnameCn;
		 return this;
	}

	public String getFirstnameCn(){
		 return firstnameCn;
	}
	private String countryCn;

	public DomainTemplate setCountryCn(String countryCn){
		 this.countryCn = countryCn;
		 return this;
	}

	public String getCountryCn(){
		 return countryCn;
	}
	private String stateCn;

	public DomainTemplate setStateCn(String stateCn){
		 this.stateCn = stateCn;
		 return this;
	}

	public String getStateCn(){
		 return stateCn;
	}
	private String cityCn;

	public DomainTemplate setCityCn(String cityCn){
		 this.cityCn = cityCn;
		 return this;
	}

	public String getCityCn(){
		 return cityCn;
	}
	private String addressCn;

	public DomainTemplate setAddressCn(String addressCn){
		 this.addressCn = addressCn;
		 return this;
	}

	public String getAddressCn(){
		 return addressCn;
	}
	private String lastnameEn;

	public DomainTemplate setLastnameEn(String lastnameEn){
		 this.lastnameEn = lastnameEn;
		 return this;
	}

	public String getLastnameEn(){
		 return lastnameEn;
	}
	private String firstnameEn;

	public DomainTemplate setFirstnameEn(String firstnameEn){
		 this.firstnameEn = firstnameEn;
		 return this;
	}

	public String getFirstnameEn(){
		 return firstnameEn;
	}
	private String countyEn;

	public DomainTemplate setCountyEn(String countyEn){
		 this.countyEn = countyEn;
		 return this;
	}

	public String getCountyEn(){
		 return countyEn;
	}
	private String stateEn;

	public DomainTemplate setStateEn(String stateEn){
		 this.stateEn = stateEn;
		 return this;
	}

	public String getStateEn(){
		 return stateEn;
	}
	private String cityEn;

	public DomainTemplate setCityEn(String cityEn){
		 this.cityEn = cityEn;
		 return this;
	}

	public String getCityEn(){
		 return cityEn;
	}
	private String addressEn;

	public DomainTemplate setAddressEn(String addressEn){
		 this.addressEn = addressEn;
		 return this;
	}

	public String getAddressEn(){
		 return addressEn;
	}
	private String zipcode;

	public DomainTemplate setZipcode(String zipcode){
		 this.zipcode = zipcode;
		 return this;
	}

	public String getZipcode(){
		 return zipcode;
	}
	private String phone;

	public DomainTemplate setPhone(String phone){
		 this.phone = phone;
		 return this;
	}

	public String getPhone(){
		 return phone;
	}
	private String fax;

	public DomainTemplate setFax(String fax){
		 this.fax = fax;
		 return this;
	}

	public String getFax(){
		 return fax;
	}
	private String email;

	public DomainTemplate setEmail(String email){
		 this.email = email;
		 return this;
	}

	public String getEmail(){
		 return email;
	}
	private Integer usertype;

	public DomainTemplate setUsertype(Integer usertype){
		 this.usertype = usertype;
		 return this;
	}

	public Integer getUsertype(){
		 return usertype;
	}
	private String idtype;

	public DomainTemplate setIdtype(String idtype){
		 this.idtype = idtype;
		 return this;
	}

	public String getIdtype(){
		 return idtype;
	}
	
	private String extensionNumber;
	
	public String getExtensionNumber() {
		return extensionNumber;
	}

	public DomainTemplate setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
		return this;
	}
	
	private String extensionFax;
	
	public String getExtensionFax() {
		return extensionFax;
	}

	public DomainTemplate setExtensionFax(String extensionFax) {
		this.extensionFax = extensionFax;
		return this;
	}
	private String idnumber;

	public DomainTemplate setIdnumber(String idnumber){
		 this.idnumber = idnumber;
		 return this;
	}

	public String getIdnumber(){
		 return idnumber;
	}
	private Integer isdefault;

	public DomainTemplate setIsdefault(Integer isdefault){
		 this.isdefault = isdefault;
		 return this;
	}

	public Integer getIsdefault(){
		 return isdefault;
	}
	private Integer ischecked;

	public DomainTemplate setIschecked(Integer ischecked){
		 this.ischecked = ischecked;
		 return this;
	}

	public Integer getIschecked(){
		 return ischecked;
	}
	private Integer isforbidden;

	public DomainTemplate setIsforbidden(Integer isforbidden){
		 this.isforbidden = isforbidden;
		 return this;
	}

	public Integer getIsforbidden(){
		 return isforbidden;
	}
	
	private Integer templateType;
	
	public Integer getTemplateType() {
		return templateType;
	}

	public DomainTemplate setTemplateType(Integer templateType) {
		this.templateType = templateType;
		return this;
	}
	
	private Date regtime;

	public DomainTemplate setRegtime(Date regtime){
		 this.regtime = regtime;
		 return this;
	}

	public Date getRegtime(){
		 return regtime;
	}
	private Date creattime;

	public DomainTemplate setCreattime(Date creattime){
		 this.creattime = creattime;
		 return this;
	}

	public Date getCreattime(){
		 return creattime;
	}
	private Date updatetime;

	public DomainTemplate setUpdatetime(Date updatetime){
		 this.updatetime = updatetime;
		 return this;
	}

	public Date getUpdatetime(){
		 return updatetime;
	}

	public int getRole() {
		return role;
	}

	public DomainTemplate setRole(int role) {
		this.role = role;
		return this;
	}
	
	private Integer emailStatus;
	private Integer userStatus;

	public Integer getEmailStatus() {
		return emailStatus;
	}

	public DomainTemplate setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
		return this;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public DomainTemplate setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
		return this;
	}
	
}