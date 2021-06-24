package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="rate")
public class Rate {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="rateId")
	private Long rateId;
	
	@Column(name="ratedBy")
	private Long ratedBy;
	
	@Column(name="rateTo")
	private Long rateTo;
	
	@Column(name="rateTime")
	private String rateTime;

	@Column(name="rate")
	private int rate;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public Long getRatedBy() {
		return ratedBy;
	}

	public void setRatedBy(Long ratedBy) {
		this.ratedBy = ratedBy;
	}

	public Long getRateTo() {
		return rateTo;
	}

	public void setRateTo(Long rateTo) {
		this.rateTo = rateTo;
	}

	public String getRateTime() {
		return rateTime;
	}

	public void setRateTime(String rateTime) {
		this.rateTime = rateTime;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
