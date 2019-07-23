package com.availity.csvprocessor.model;

import java.util.Comparator;
import java.util.List;

public class UserData implements Comparable<UserData>{
	
	private String userid;
	private String fullname;
	private Long version;
	private String company;
	
	public UserData() {

	}
	public UserData(String userid, String fullname, Long version, String company) {
		super();
		this.userid = userid;
		this.fullname = fullname;
		this.version = version;
		this.company = company;
	}
	
	public void createUserData(List<String> lst) throws Exception{
		this.userid = lst.get(0);
		this.fullname = lst.get(1);
		this.version = Long.parseLong(lst.get(2));
		this.company = lst.get(3);
	}
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	public String toFormattedString() {
		return "UserData [userid=" + userid + ", fullname=" + fullname + ", version=" + version + ", company=" + company
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.userid + this.fullname + this.version + this.company;
	}

	@Override
	public int compareTo(UserData o) {
		if(this.getFullname().compareTo(((UserData)o).getFullname()) > 0 ) return 1;
		else if(this.getFullname().compareTo(((UserData)o).getFullname()) < 0 ) return -1;
		else return 0;
	}
	
}
