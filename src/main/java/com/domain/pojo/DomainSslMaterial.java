package com.domain.pojo;

import java.util.Date;

import java.io.Serializable;

public class DomainSslMaterial  implements Serializable{

	private static final long serialVersionUID = 1541399563828106581L;
	private Integer id;

	public DomainSslMaterial setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public DomainSslMaterial setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private Integer sslcertid;

	public DomainSslMaterial setSslcertid(Integer sslcertid){
		 this.sslcertid = sslcertid;
		 return this;
	}

	public Integer getSslcertid(){
		 return sslcertid;
	}
	private String pctype;

	public DomainSslMaterial setPctype(String pctype){
		 this.pctype = pctype;
		 return this;
	}

	public String getPctype(){
		 return pctype;
	}
	private String pccentent;

	public DomainSslMaterial setPccentent(String pccentent){
		 this.pccentent = pccentent;
		 return this;
	}

	public String getPccentent(){
		 return pccentent;
	}
	private Date picexpfromdate;

	public DomainSslMaterial setPicexpfromdate(Date picexpfromdate){
		 this.picexpfromdate = picexpfromdate;
		 return this;
	}

	public Date getPicexpfromdate(){
		 return picexpfromdate;
	}
	private Date picexptodate;

	public DomainSslMaterial setPicexptodate(Date picexptodate){
		 this.picexptodate = picexptodate;
		 return this;
	}

	public Date getPicexptodate(){
		 return picexptodate;
	}
}