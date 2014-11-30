package com.github.tripville.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driverhistory")
public class DriverHistory {

	@Id
	private String userid;
	@Column(name = "tripid")
	private String tripid;
	@Column(name = "copassId")
	private String copassId;
	@Column(name = "rating")
	private int rating;
	@Column(name = "comments")
	private String comments;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTripid() {
		return tripid;
	}
	public void setTripid(String tripid) {
		this.tripid = tripid;
	}
	public String getCopassId() {
		return copassId;
	}
	public void setCopassId(String copassId) {
		this.copassId = copassId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	@Override
	public String toString() {
		return "DriverHistory [tripid=" + tripid + ", userid=" + userid
				+ ", copassId=" + copassId + ", rating=" + rating
				+ ", comments=" + comments + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
