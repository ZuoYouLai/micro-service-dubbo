/*
 * Copyright © 2014-2015 Thunder Software Technology Co., Ltd.
 * All rights reserved.
 */
package com.whforever.micro.common.common.util;

import java.util.Date;

public class LdapUsersInfo {
//	private Users users;
//	private LdapUsers ldapUsers;
//	public Users getUsers() {
//		return users;
//	}
//	public void setUsers(Users users) {
//		this.users = users;
//	}
//	public LdapUsers getLdapUsers() {
//		return ldapUsers;
//	}
//	public void setLdapUsers(LdapUsers ldapUsers) {
//		this.ldapUsers = ldapUsers;
//	}
	private int userId;
	private int ldapId;
	private Date createTime;
	private Date lastloginTime;
	private String name;
	private String email;
	private String workingPlace;
	private String uid;
	private String source;
	
	public LdapUsersInfo(){
		
	}
	public LdapUsersInfo(int userId, int ldapId, Date createTime, Date lastloginTime, String name, String email, String place, String uid, String source){
		this.userId = userId;
		this.ldapId = ldapId;
		this.createTime = createTime;
		this.lastloginTime = lastloginTime;
		this.name = name;
		this.email = email;
		this.workingPlace = place;
		this.uid = uid;
		this.source = source;

	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastloginTime() {
		return lastloginTime;
	}
	public void setLastloginTime(Date lastloginTime) {
		this.lastloginTime = lastloginTime;
	}
	public int getLdapId() {
		return ldapId;
	}
	public void setLdapId(int ldapId) {
		this.ldapId = ldapId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkingPlace() {
		return workingPlace;
	}
	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
