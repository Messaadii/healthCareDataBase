package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="oldData")
public class OldData {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="oldDataId")
	private Long oldDataId;
	
	@Column(name="referencedId")
	private Long referencedId;
	
	@Column(name="oldDataType")
	private String oldDataType;
	
	@Column(name="oldDataValue")
	private String oldDataValue;
	
	@Column(name="updateDate")
	private String updateDate;

	public Long getOldDataId() {
		return oldDataId;
	}

	public void setOldDataId(Long oldDataId) {
		this.oldDataId = oldDataId;
	}

	public Long getReferencedId() {
		return referencedId;
	}

	public void setReferencedId(Long referencedId) {
		this.referencedId = referencedId;
	}

	public String getOldDataType() {
		return oldDataType;
	}

	public void setOldDataType(String oldDataType) {
		this.oldDataType = oldDataType;
	}

	public String getOldDataValue() {
		return oldDataValue;
	}

	public void setOldDataValue(String oldDataValue) {
		this.oldDataValue = oldDataValue;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public OldData(Long referencedId, String oldDataType, String oldDataValue) {
		super();
		this.referencedId = referencedId;
		this.oldDataType = oldDataType;
		this.oldDataValue = oldDataValue;
	}

}
