package com.nishanth.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.nishanth.rest.Ack;
import com.nishanth.rest.SportsKnown;

@Controller
public class SpringRestController {
	
	@Value("${sample.key1}")
	private String value;

	@RequestMapping( value="/")
	public @ResponseBody String welcomePage()
	{
		return "Welcome to Spring Rest Layer";
	}
	@RequestMapping( value="/greeting")
	public @ResponseBody String greetingPage()
	{
		System.out.println("VALUE = "+value);
		return "HELLO SPRING";
	}
	
	@RequestMapping( value = "/getxmlObject", produces = {"application/xml"})
    public @ResponseBody SampleObject getXMLObject()
    {
		SampleObject sampleObject = new SampleObject();
		sampleObject.setId("1");
		sampleObject.setName("XML");
		return sampleObject;
    }
	
	@RequestMapping( value = "/getjsonObject", produces = {"application/json"})
    public @ResponseBody SampleObject getJSONObject()
    {
		SampleObject sampleObject = new SampleObject();
		sampleObject.setId("1");
		sampleObject.setName("JOSN");
		return sampleObject;
    }
	
	@RequestMapping( value = "/plain", method = RequestMethod.POST)
    public @ResponseBody Ack getPlainObject( @RequestBody String str, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setUniqueId("123789");
		ack.setType("plain");
		return ack;
    }
	
	@RequestMapping( value = "/xmlRequest", consumes = {"application/xml"}, method = RequestMethod.POST)
    public @ResponseBody Ack getXmlObject( @RequestBody SampleObject sample, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setUniqueId(sample.getId());
		ack.setType(sample.getName());
		return ack;
    }
	
	@RequestMapping( value = "/jsonRequest", consumes = "application/json", method = RequestMethod.POST)
    public @ResponseBody Ack getJsonObject( @RequestBody SampleObject sample, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setUniqueId(sample.getId());
		ack.setType(sample.getName());
		return ack;
    }
	
	@RequestMapping( value = "/xmlRequestProduceJson", consumes = "application/xml",produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody Ack submitXmlRequestProduceJson( @RequestBody SampleObject sampleObject, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setUniqueId(sampleObject.getId());
		ack.setType(sampleObject.getName());
		return ack;
    }
	
	@RequestMapping( value = "/jsonRequestProduceJson", consumes = "application/json",produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody Ack submitJsonRequestProduceJson( @RequestBody SampleObject sampleObject, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setUniqueId(sampleObject.getId());
		ack.setType(sampleObject.getName());
		return ack;
    }
	
	
	@RequestMapping( value = "/xmlListRequest", consumes = "application/xml", method = RequestMethod.POST)
    public @ResponseBody Ack submitXmlListRequest( @RequestBody Athlete athlete, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		System.out.println(athlete.getName());
		System.out.println(athlete.getAddress());
		System.out.println(athlete.getAge());
		List<SportsKnown> lists = athlete.getSports();
		for(SportsKnown list : lists)
		{
			System.out.println(list.getSport());
			System.out.println(list.getHandedness());
		}
		return ack;
    }
	
	@RequestMapping( value = "/jsonListRequest", consumes = "application/json", method = RequestMethod.POST)
    public @ResponseBody Ack submitJsonListRequest( @RequestBody Athlete athlete, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		System.out.println(athlete.getName());
		System.out.println(athlete.getAddress());
		System.out.println(athlete.getAge());
		List<SportsKnown> lists = athlete.getSports();
		for(SportsKnown list : lists)
		{
			System.out.println(list.getSport());
			System.out.println(list.getHandedness());
		}
		return ack;
    }
	
	@Autowired
	private void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter mappingManager) {
		if(mappingManager == null) {
			return;
		}
		for( HttpMessageConverter conv : mappingManager.getMessageConverters()) {
			if( conv instanceof MappingJacksonHttpMessageConverter) {
				( (MappingJacksonHttpMessageConverter) conv).setObjectMapper(new JacksonCustomObjectMapper());
			}
		}
	}

}
