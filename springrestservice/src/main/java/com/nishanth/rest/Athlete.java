package com.nishanth.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonRootName;

import com.nishanth.rest.SportsKnown;

@XmlRootElement(name= "athlete")
@JsonRootName(value = "athlete")
public class Athlete {
	
	private String name;
	private String address;
	private String age;
	private List<SportsKnown> sports;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public List<SportsKnown> getSports() {
		return sports;
	}
	public void setSports(List<SportsKnown> sports) {
		this.sports = sports;
	}

}
