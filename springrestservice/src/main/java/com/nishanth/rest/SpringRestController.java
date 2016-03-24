package com.nishanth.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.nishanth.api.Channel;
import com.nishanth.api.Item;
import com.nishanth.api.RSS;
import com.nishanth.rest.Ack;
import com.nishanth.rest.SportsKnown;
import com.nishanth.schedule.ScheduledTask;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.annotation.PostConstruct;


@Controller
@RequestMapping("/")
@SuppressWarnings("restriction")
public class SpringRestController {
	
	@Autowired
	public ScheduledTask task;
	
	@Value("${sample.key1}")
	private String value;
	
	@Value("${version}")
	private String version;
	
	static JAXBContext jaxbContext;
	static Unmarshaller unmarshaller = null;
		static
		{
		try {
			jaxbContext = JAXBContext.newInstance(RSS.class);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@PostConstruct
	public void testPostConstruct()
	{
		System.out.println("This is just for test purposes");
	}
	
	@PostConstruct
	public void schedule()
	{
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleAtFixedRate(task, 10, 10, TimeUnit.SECONDS);
	}
	
	@RequestMapping( value="/")
	public @ResponseBody String welcomePage()
	{
		return "Welcome to Spring Rest Layer, Version : "+version;
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
	
	@RequestMapping( value="cricket/rss",method = RequestMethod.GET)
	public @ResponseBody String cricketRss(ModelMap model, HttpServletRequest httpReq, HttpServletResponse httpResp)
	{
		httpResp.setHeader("Access-Control-Allow-Origin","*");
		Client client = Client.create();
		WebResource webResource = client.resource("http://static.cricinfo.com/rss/livescores.xml");

		ClientResponse response = webResource.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			System.out.println("Response = "+response.getStatus());
			System.out.println("Content  = "+response.getEntity(String.class));
			throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}
		String str = response.getEntity(String.class);

		StringReader reader = new StringReader(str);
		RSS rss = null;
		try {
			rss = (RSS) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Channel channel = rss.getChannel();
		List<Item> item  = channel.getItem();
		List<String> list = new ArrayList<String>();
		for(Item i : item)
		{
			list.add(i.getTitle());
		}
		return "CRICKET \n"+list.toString().split("\\[")[1].split("\\]")[0].replace(",", "\n");
	}

	@RequestMapping( value="eplfootball/rss",method = RequestMethod.GET)
	public @ResponseBody String eplFootballRss(ModelMap model, HttpServletRequest httpReq, HttpServletResponse httpResp)
	{
		httpResp.setHeader("Access-Control-Allow-Origin","*");
		Client client = Client.create();
		//WebResource webResource = client.resource("http://talksport.com/rss/football/premier-league/feed");
		WebResource webResource = client.resource("http://feeds.feedburner.com/PremierLeagueFootballNews?format=xml");
		ClientResponse response = webResource.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			System.out.println("Response = "+response.getStatus());
			System.out.println("Content  = "+response.getEntity(String.class));
			throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}
		String str = response.getEntity(String.class);

		StringReader reader = new StringReader(str);
		RSS rss = null;
		try {
			rss = (RSS) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Channel channel = rss.getChannel();
		List<Item> item  = channel.getItem();
		List<String> list = new ArrayList<String>();
		for(Item i : item)
		{
			list.add(i.getTitle()+"\n");
		}
		return "ENGLISH PREMIER LEAGUE - FOOTBALL \n"+list.toString().split("\\[")[1].split("\\]")[0];
	}
	
	@RequestMapping( value="champsfootball/rss",method = RequestMethod.GET)
	public @ResponseBody String champsFootballRss(ModelMap model, HttpServletRequest httpReq, HttpServletResponse httpResp)
	{
		httpResp.setHeader("Access-Control-Allow-Origin","*");
		Client client = Client.create();
		WebResource webResource = client.resource("http://www.uefa.com/rssfeed/uefachampionsleague/rss.xml");
		ClientResponse response = webResource.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			System.out.println("Response = "+response.getStatus());
			System.out.println("Content  = "+response.getEntity(String.class));
			throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}
		String str = response.getEntity(String.class);

		StringReader reader = new StringReader(str);
		RSS rss = null;
		try {
			rss = (RSS) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Channel channel = rss.getChannel();
		List<Item> item  = channel.getItem();
		List<String> list = new ArrayList<String>();
		for(Item i : item)
		{
			list.add(i.getTitle()+"\n");
		}
		return "UEFA CHAMPIONS LEAGUE - FOOTBALL \n"+list.toString().split("\\[")[1].split("\\]")[0];
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
