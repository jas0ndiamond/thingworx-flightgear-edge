package org.jason.fgedge.config;

import java.util.Properties;

import org.jason.fgedge.callback.AppKeyCallback;
import org.jason.fgedge.callback.ProxyPassCallback;
import org.jason.fgedge.exception.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thingworx.communications.client.IPasswordCallback;
import com.thingworx.communications.client.proxy.ProxyConfig;

public class EdgeConfigVisitor {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(EdgeConfigVisitor.class);

    private final static String WS_PROTOCOL_STR = "wss://";
    private final static String PLATFORM_URI_COMPONENT_STR = "/Thingworx/WS";
	
    private final static int DEFAULT_PLATFORM_PORT = 8443;
    
	private EdgeConfigVisitor() {}
	
	public static void buildEdgeConfig(EdgeConfig edgeConfig, Properties twxConfigProperties) throws ConfigException {
		
		//twx platform connection
        String platformHost;
        int platformPort;
        String appKey;
        String uri;
        String proxyHost;
        int proxyPort;
        String proxyUser;
        IPasswordCallback proxyPassCB;
        
        //platform host
        if(twxConfigProperties.containsKey(TWXConfigDirectives.PLATFORM_HOST_DIRECTIVE)) {
        	platformHost = twxConfigProperties.getProperty(TWXConfigDirectives.PLATFORM_HOST_DIRECTIVE);
        } else {
        	throw new ConfigException("TWX platform host not set in config");
        }
        
        //platform port
        if( twxConfigProperties.containsKey(TWXConfigDirectives.PLATFORM_PORT_DIRECTIVE) ) {
        	platformPort = Integer.parseInt(twxConfigProperties.getProperty(TWXConfigDirectives.PLATFORM_PORT_DIRECTIVE));
        } else {
        	LOGGER.warn("Using default platform port");
        	platformPort = DEFAULT_PLATFORM_PORT;
        }
        
        uri = WS_PROTOCOL_STR + platformHost + ":" + platformPort + PLATFORM_URI_COMPONENT_STR;
        edgeConfig.setUri(uri);
        
        LOGGER.info("Config built with URI: {}", uri);
        
        //app key
        if( twxConfigProperties.containsKey(TWXConfigDirectives.APPKEY_DIRECTIVE)) {
        	appKey = twxConfigProperties.getProperty(TWXConfigDirectives.APPKEY_DIRECTIVE);
        	
            edgeConfig.setSecurityClaims( new AppKeyCallback(appKey) );
        }
        else {
        	throw new ConfigException("TWX platform appkey not set in config");
        }
        
        //thing name
        if(twxConfigProperties.containsKey(TWXConfigDirectives.THINGNAME_DIRECTIVE)) {
        	edgeConfig.setThingName(twxConfigProperties.getProperty(TWXConfigDirectives.THINGNAME_DIRECTIVE));
        } else {
        	throw new ConfigException("TWX Thingname not set in config");
        }
        
        //caltrops config
        //technically workable without caltropsHost and caltropsPort- which will be ordinary proxy connectivity
        if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_HOST_DIRECTIVE)) {
        	edgeConfig.setCaltropsHost(twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_HOST_DIRECTIVE));
        	
        	if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_PORT_DIRECTIVE)) {
        		edgeConfig.setCaltropsPort(
        			Integer.parseInt(twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_PORT_DIRECTIVE))
        		);
        	}
        	else {
        		throw new ConfigException("Caltrops port not set in config");
        	}
        }
        
        //proxy config
        //proxy host
        if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_PROXY_HOST_DIRECTIVE)) {
        	
        	proxyHost = twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_PROXY_HOST_DIRECTIVE);
        	edgeConfig.setCaltropsProxyHost(proxyHost);
        	
        	//proxy port
        	if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_PROXY_PORT_DIRECTIVE)) {
        		proxyPort = Integer.parseInt(twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_PROXY_PORT_DIRECTIVE));
        		edgeConfig.setCaltropsProxyPort(proxyPort);
        		
        		//proxy user
            	if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_PROXY_USER_DIRECTIVE)) {
            		proxyUser = twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_PROXY_USER_DIRECTIVE);
            		edgeConfig.setCaltropsProxyUser(proxyUser);
            		
            		//proxy pass
            		if(twxConfigProperties.containsKey(TWXConfigDirectives.CALTROPS_PROXY_PASS_DIRECTIVE)) {
            			
            			proxyPassCB = new ProxyPassCallback(twxConfigProperties.getProperty(TWXConfigDirectives.CALTROPS_PROXY_PASS_DIRECTIVE));
            			edgeConfig.setCaltropsProxyPassCallback(proxyPassCB);
            			
            			LOGGER.info("Using proxy config {}:{} with authentication", proxyHost, proxyPort);
            			edgeConfig.setProxyConfig(
            				new ProxyConfig(platformHost, platformPort, proxyHost, proxyPort, proxyUser, proxyPassCB)
            			);
            		}
            	} else {
        			LOGGER.warn("Using proxy config {}:{} without authentication", proxyHost, proxyPort);
        			edgeConfig.setProxyConfig(new ProxyConfig(platformHost, platformPort, proxyHost, proxyPort));
        		}
        	} else {
        		throw new ConfigException("Proxy port not set in config");
        	}
        }
        
        //common value hardcoding - invoker can override
        edgeConfig.ignoreSSLErrors(true);
        edgeConfig.setReconnectInterval(10);
        edgeConfig.setIdlePingRate(10);
        
        LOGGER.info("Built edge config: {}", edgeConfig.toString());
	}
}
