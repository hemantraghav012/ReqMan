package com.reqman.pojo;

// Generated 13 Aug, 2017 10:25:05 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserusertypeId generated by hbm2java
 */
@Embeddable
public class UserusertypeId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957998653823413880L;
	private Integer usertypeid;
	private Integer userid;

	public UserusertypeId() {
	}

	public UserusertypeId(Integer usertypeid, Integer userid) {
		this.usertypeid = usertypeid;
		this.userid = userid;
	}

	@Column(name = "usertypeid")
	public Integer getUsertypeid() {
		return this.usertypeid;
	}

	public void setUsertypeid(Integer usertypeid) {
		this.usertypeid = usertypeid;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserusertypeId))
			return false;
		UserusertypeId castOther = (UserusertypeId) other;

		return ((this.getUsertypeid() == castOther.getUsertypeid()) || (this
				.getUsertypeid() != null && castOther.getUsertypeid() != null && this
				.getUsertypeid().equals(castOther.getUsertypeid())))
				&& ((this.getUserid() == castOther.getUserid()) || (this
						.getUserid() != null && castOther.getUserid() != null && this
						.getUserid().equals(castOther.getUserid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getUsertypeid() == null ? 0 : this.getUsertypeid()
						.hashCode());
		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		return result;
	}

}
