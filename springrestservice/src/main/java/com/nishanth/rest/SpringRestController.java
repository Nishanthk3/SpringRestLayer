package com.nishanth.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Controller
public class SpringRestController {

	@RequestMapping( value="/")
	public @ResponseBody String welcomePage()
	{
		return "Welcome to Spring Rest Layer";
	}
	@RequestMapping( value="/greeting")
	public @ResponseBody String greetingPage()
	{
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
		ack.setInterchangeId("123789");
		ack.setType("plain");
		return ack;
    }
	
	@RequestMapping( value = "/xmlRequest", consumes = {"application/xml"}, method = RequestMethod.POST)
    public @ResponseBody Ack getXmlObject( @RequestBody SampleObject sample, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setInterchangeId("123456");
		ack.setType("xml");
		return ack;
    }
	
	@RequestMapping( value = "/jsonRequest", consumes = "application/json", method = RequestMethod.POST)
    public @ResponseBody Ack getJsonObject( @RequestBody SampleObject sample, HttpServletRequest httpReq,
    		HttpServletResponse httpResp)
    {
		Ack ack = new Ack();
		ack.setInterchangeId("456789");
		ack.setType("json");
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
