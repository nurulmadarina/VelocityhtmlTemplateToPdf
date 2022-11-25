package com.example.htmlToPdf;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





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
		context.put("name", request.getNama());
		context.put("age", request.getUmur());
		context.put("hobby", request.getKesukaan());
		context.put("job", request.getPekerjaan());
		context.put("genDateTime", LocalDateTime.now());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());
//		String resp = writer.toString().trim();
		
		Gson gson = new Gson();  

		templateResponse response = gson.fromJson(writer.toString(), templateResponse.class);
		return new BaseResponse<>(response);
	}

}
