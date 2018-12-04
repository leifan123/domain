package com.domain.pojo;

import java.io.Serializable;

public class DomainConfigure  implements Serializable{

	private static final long serialVersionUID = 1534211779261805617L;
	private Integer id;

	public DomainConfigure setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String mcUsername;

	public DomainConfigure setMcUsername(String mcUsername){
		 this.mcUsername = mcUsername;
		 return this;
	}

	public String getMcUsername(){
		 return mcUsername;
	}
	private String mcPassword;

	public DomainConfigure setMcPassword(String mcPassword){
		 this.mcPassword = mcPassword;
		 return this;
	}

	public String getMcPassword(){
		 return mcPassword;
	}
	private String mcEmail;

	public DomainConfigure setMcEmail(String mcEmail){
		 this.mcEmail = mcEmail;
		 return this;
	}

	public String getMcEmail(){
		 return mcEmail;
	}
	private String mark1;

	public DomainConfigure setMark1(String mark1){
		 this.mark1 = mark1;
		 return this;
	}

	public String getMark1(){
		 return mark1;
	}
	private String mark2;

	public DomainConfigure setMark2(String mark2){
		 this.mark2 = mark2;
		 return this;
	}

	public String getMark2(){
		 return mark2;
	}
	private String mark3;

	public DomainConfigure setMark3(String mark3){
		 this.mark3 = mark3;
		 return this;
	}

	public String getMark3(){
		 return mark3;
	}
}