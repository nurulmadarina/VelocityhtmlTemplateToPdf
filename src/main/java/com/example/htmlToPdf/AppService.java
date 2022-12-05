package com.example.htmlToPdf;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONStringer;





@Service
public class AppService {

	public BaseResponse printData(RequestDto request) {
		// TODO Auto-generated method stub
		
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();

		/* next, get the Template */
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		Template t = ve.getTemplate("templates/biodata.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("request", request);
//        context.put("json", JSONStringer.class);
//		context.put("name", request.getNama());
//		context.put("age", request.getUmur());
//		context.put("hobby", request.getKesukaan());
//		context.put("job", request.getPekerjaan());
//		context.put("order", request.getOrder());
		context.put("genDateTime", LocalDateTime.now());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());
//		String resp = writer.toString().trim();
		
		Gson gson = new Gson();  
//		JSONObject obj = new JSONObject(request);
//		JSONObject responseobj = new JSONObject(obj.get(writer.toString()).toString());
		templateResponse response = gson.fromJson(writer.toString(), templateResponse.class);
		return new BaseResponse<>(response);
	}
	
	public String buildTemplate(String requestJson) throws ParseException {
	    VelocityEngine velocityEngine = new VelocityEngine();
	    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	    velocityEngine.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    velocityEngine.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
	    velocityEngine.init();
	    Map<String, Object> map = new HashMap<>();
	    map.put("format", "yyyy-MM-dd'T'HH:mm:SS.ssss");
	    map.put("timezone", TimeZone.getTimeZone("UTC"));
	    Map<String, Object> properties = new HashMap<>();
	    properties.put("engine", velocityEngine);

	    Template template = velocityEngine.getTemplate("templates/tryjson.vm");
	    DateTool dateTool = new DateTool();
	    dateTool.configure(map);
	    VelocityContext velocityContext = new VelocityContext();
	    velocityContext.put("root", requestJson);
	    velocityContext.put("esc", new EscapeTool());
	    velocityContext.put("date", dateTool);
	    velocityContext.put("math", new MathTool());

	    StringWriter stringWriter = new StringWriter();
	    template.merge(velocityContext, stringWriter);
	    System.out.println("Cek result :: " + stringWriter.toString());
	    Gson gson = new Gson();  
	    return gson.fromJson(stringWriter.toString(), JsonObject.class).toString();
	}

}
