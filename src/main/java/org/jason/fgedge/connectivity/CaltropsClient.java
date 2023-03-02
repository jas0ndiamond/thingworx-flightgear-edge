package org.jason.fgedge.connectivity;

import java.net.URI;
import java.util.LinkedHashMap;

import org.jason.fgcontrol.connection.rest.CommonHeaders;
import org.jason.fgcontrol.connection.rest.RESTClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface for interacting with a Caltrops instance for affecting network connectivity 
 * in a controlled manner - https://github.com/jas0ndiamond/caltrops
 * 
 * @author jason
 *
 */
/**
 * @author jason
 *
 */
public class CaltropsClient {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CaltropsClient.class);

	private String caltropsHost;
	private int caltropsPort;
	
	private RESTClient restClient;

	private String resetUri;

	private String uriPrefix;

	//caltrops resources - inbound
	private final static String DROP_INBOUND_RES = "drop_inbound";
	private final static String REJECT_INBOUND_RES = "reject_inbound";
	private final static String ACCEPT_INBOUND_RES = "accept_inbound";
	
	//caltrops resources - outbound
	private final static String DROP_OUTBOUND_RES = "drop_outbound";
	private final static String REJECT_OUTBOUND_RES = "reject_outbound";
	private final static String ACCEPT_OUTBOUND_RES = "accept_outbound";
	
	private final static String HTTP_RESULT_FIELD = "change"; 
	private final static String HTTP_RESULT_SUCCESS = "SUCCESS";
	private final static String HTTP_RESULT_SKIP = "SKIP";
	
	private final static String PORT_PARAM = "port";
	
	private final static String PROTOCOL = "http";
	
	/**
	 * Build a Caltrops client pointed at the specified host and port. Point a browser 
	 * at http://caltropsHost:caltropsPort and the rule state should be displayed
	 * 
	 * @param caltropsHost	Caltrops caltropsHost
	 * @param caltropsPort	Caltrops caltropsPort
	 */
	public CaltropsClient(String caltropsHost, int caltropsPort) {
		
		if(caltropsHost == null || caltropsPort <= 0) {
			throw new IllegalArgumentException("Invalid caltrops config -> " + caltropsHost + ":" + caltropsPort);
		}
		
		this.caltropsHost = caltropsHost;
		this.caltropsPort = caltropsPort;
		
		LinkedHashMap<String, String> clientHeaders = new LinkedHashMap<String, String>();
		clientHeaders.put(CommonHeaders.CONNECTION, CommonHeaders.CONNECTION_CLOSE);
		clientHeaders.put(CommonHeaders.HTTP_ACCEPT, CommonHeaders.CONTENT_TYPE_JSON);
		restClient = new RESTClient(clientHeaders);
		
		uriPrefix = PROTOCOL + "://" + this.caltropsHost + ":" + this.caltropsPort;
		resetUri = URI.create(uriPrefix + "/reset_rules").toString();
	}
	
	/**
	 * Reset all caltrops rules to their default policy (likely ACCEPT)
	 * 
	 * @return	true if change was reported as applied, false otherwise
	 */
	public boolean resetRules() {	
		byte[] responseBody = null;

		responseBody = restClient.makeGETRequestAndGetBody(resetUri);
		
		if(responseBody == null) {
			return false;
		} else {		
			return appliedChangeSuccessfully(new String(responseBody));
		}
	}
	
	public boolean dropInbound(int port) {
		
		//TODO: get judgement for inbound port, and change only if necessary
		
		return makeCaltropsRequest(DROP_INBOUND_RES, port);
	}
	
	public boolean rejectInbound(int port) {
		return makeCaltropsRequest(REJECT_INBOUND_RES, port);
	}
	
	public boolean acceptInbound(int port) {
		return makeCaltropsRequest(ACCEPT_INBOUND_RES, port);
	}
	
	public boolean dropOutbound(int port) {
		return makeCaltropsRequest(DROP_OUTBOUND_RES, port);
	}
	
	public boolean rejectOutbound(int port) {
		return makeCaltropsRequest(REJECT_OUTBOUND_RES, port);
	}
	
	public boolean acceptOutbound(int port) {
		return makeCaltropsRequest(ACCEPT_OUTBOUND_RES, port);
	}
	
	private boolean makeCaltropsRequest(String resource, int port) {
		
		String requestUri = URI.create(uriPrefix + "/" + resource).toString();
		
		LinkedHashMap<String, String> requestParams = new LinkedHashMap<String, String>();
		requestParams.put(PORT_PARAM, String.valueOf(port));
		
		LOGGER.info("Issuing Caltrops request: {} with params {}", requestUri, requestParams);
		
		return appliedChangeSuccessfully(new String(restClient.makeGETRequestAndGetBody(requestUri, requestParams)));
	}

	/**
	 * Was the port policy change successful?
	 * TODO: necessary? is the status code enough? we may want to consider the result field too
	 * 
	 * @param responseBody	the response from the caltrops request
	 * 
	 * @return	true if change was successful, false otherwise
	 */
	//TODO: incorporate into makeCaltropsRequest or remove
	private boolean appliedChangeSuccessfully(String responseBody) {
		boolean retval = false;
		
		if(responseBody != null) {
			
			LOGGER.info("Received response body\n{}\n=====", responseBody);
			
			try {
				JSONObject responseBodyJSON = new JSONObject(responseBody);
				String resultVal = responseBodyJSON.getString(HTTP_RESULT_FIELD);
				if (HTTP_RESULT_SUCCESS.equals(resultVal)) {
					retval = true;

				} else if (HTTP_RESULT_SKIP.equals(resultVal)) {
					retval = true;

					LOGGER.warn("Caltrops did not need to apply change");
				} else {
					LOGGER.error("Caltrops failed to apply change");
				}

			} catch (JSONException jsonException) {
				//can get here if caltrops encounters an internal errorq
				LOGGER.error("JSON Exception processing caltrops response", jsonException);				
			}

		} else {
			LOGGER.error("Caltrops request received a null response body");
		}

		return retval;
	}
	
	
	/**
	 * @param port
	 * @return
	 */
	private String getPortCurrentJudgement(int port) {
		//TODO: implement as option to check port status before changing
		//maybe not- might introduce toctou problems 
		
		return null;
	}
}
