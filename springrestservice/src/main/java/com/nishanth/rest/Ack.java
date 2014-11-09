package com.nishanth.rest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonRootName;

@XmlRootElement(name= "ack")
@JsonRootName(value = "ack")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ack", propOrder = {
		"interchangeId",
		"type"
})
public class Ack {

	protected String interchangeId;
	protected String type;

	public String getInterchangeId() {
		return interchangeId;
	}

	public void setInterchangeId(String interchangeId) {
		this.interchangeId = interchangeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((interchangeId == null) ? 0 : interchangeId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Ack other = (Ack) obj;
		if (interchangeId == null) {
			if (other.interchangeId != null)
				return false;
		} else if (!interchangeId.equals(other.interchangeId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ack [interchangeId=" + interchangeId + ", type=" + type + "]";
	}
}
