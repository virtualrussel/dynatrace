//https://github.com/Dynatrace/OneAgent-SDK-for-Java/blob/master/samples/webrequest/src/main/java/com/dynatrace/oneagent/sdk/samples/webrequest/FakedWebserver.java

package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import com.dynatrace.oneagent.sdk.api.*;
import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.IncomingWebRequestTracer;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;
import com.dynatrace.oneagent.sdk.api.infos.WebApplicationInfo;
@Path("/greeting")
public class GreetingResource {
    private final OneAgentSDK oneAgentSDK=null;
    private  WebApplicationInfo webAppInfo;
    @GET
    @Produces(MediaType.TEXT_PLAIN)



    public String hello() {
         String url="http://marc-openshift-quickstart-dynatrace.apps.ocp4.local";
	 OneAgentSDK oneAgentSDK = OneAgentSDKFactory.createInstance();
	 //next instruction will be ignored as we are NOT tracing yet
	 oneAgentSDK.addCustomRequestAttribute("marctracing","marctracing");
	 webAppInfo = oneAgentSDK.createWebApplicationInfo("servername", "BillingService", "/");
	 IncomingWebRequestTracer incomingWebrequestTracer = oneAgentSDK.traceIncomingWebRequest(webAppInfo, url, "GET");
	 incomingWebrequestTracer.start();
		try {
			//response.setContent("Hello world!".getBytes());
			//response.setStatusCode(200);
			oneAgentSDK.addCustomRequestAttribute("marctracing","marctracing");
			incomingWebrequestTracer.setStatusCode(200);
		} catch (Exception e) {
			oneAgentSDK.addCustomRequestAttribute("marctracing","marctracing");
			// we assume, container is sending http 500 in case of exception is thrown while serving an request:
			incomingWebrequestTracer.error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			oneAgentSDK.addCustomRequestAttribute("marctracing","marctracing");
			incomingWebrequestTracer.end();
		}
	 oneAgentSDK.addCustomRequestAttribute("marctracing","marctracing");   
	 return "hello";
    }
}
