package com.nishanth.rest.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:restServiceApplicationTest-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestClass {

	private String testValue;

	@Autowired
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	@Test
	public void test() {
		System.out.println(testValue);
		assertEquals(testValue, "This is for testing");
		assertNotEquals(testValue, "This is not the right value");
	}

}
