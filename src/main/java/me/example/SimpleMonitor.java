package me.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;




import org.apache.log4j.Logger;

public class SimpleMonitor {
	
	private static Logger _logger = Logger.getLogger(SimpleMonitor.class);
	
	private  Config config ;
	
	 //configuration fields 
	 // comma separated urls to monitor
	 private String urlsToMonitor ;
	 
	 // emails recipients in csv format
	 private String emailsToNotify;
	 
	 // sender's email
	 private String fromEmail;
	 
	 // smtp server.
	 private String smtpServer;
	 
	 // email subject
	 private String emailSubject;
	
	
	public SimpleMonitor(){
		
		try {
			config = Config.getInstance();
		} catch (IOException e) {
			_logger.error("Unable to load the config.properties file from the working directory or the classpath", e);
			//die
			System.exit(-1);
		}
		if(config == null){
			throw new IllegalStateException("Unable to load the config.properties file from the working directory or the classpath");
		}
		urlsToMonitor =  config.asString("monitor.urls");
		emailsToNotify = config.asString("monitor.notify");
		fromEmail = config.asString("monitor.fromEmail");
		smtpServer = config.asString("monitor.smtpServer");
		emailSubject = config.asString("monitor.emailSubject","");
		if(urlsToMonitor.isEmpty()){
			throw new IllegalStateException("No urls (monitor.urls) specified in the config.properies.");
		}
	}
	
	public static void main (String[] args){
		
		new SimpleMonitor().start();
	}
	
	public void start(){
		
		
		List<String> urlsToMonitorList = csvToList(this.urlsToMonitor);
	
		// create anonymous threads 
		for (final String url : urlsToMonitorList) {
			
			Thread t = new Thread( new Runnable() {
				
				@Override
				public void run() {
					pingUrl(url);
					
				}
			} );
			t.start();
		
		}
	}
	
	/**
     * Makes a connection to the specified URL. Returns false if the response code is not between 200 and 400. returns true otherwise 
     * Assumption: successful is success:2xx  or redirection: 3xx
     * @return true if the call succeeded false otherwise
     */
    public boolean pingUrl(String urlToMonitor) {
    	if(urlToMonitor == null){
    		return false;
    	}
        boolean callResult = false;
        try {
        	//make sure protocol is used.
        	 if (!urlToMonitor.startsWith("http://") && !urlToMonitor.startsWith("https://")) {
        		 urlToMonitor = "http://" + urlToMonitor;
             }
        	_logger.trace(String.format("Connecting to %s\n",urlToMonitor));
        	
            HttpURLConnection conn = (HttpURLConnection) (new URL(urlToMonitor)).openConnection();
            int code = conn.getResponseCode();
            String message = String.format("Server Returned %d  %s ", code, conn.getResponseMessage());
            if (code >= 200 && code < 400 ) {
                callResult = true;
                _logger.trace(message);
            } else {
                _logger.error(message);
                sendEmail(message); 
             }
            
        } catch (Exception e) {
            callResult = false;
            String message = String.format("An error occured while connecting to %s.  %s", urlToMonitor, e.getMessage());
            sendEmail(message);
        } finally {
            _logger.info(String.format("Calling URL:%s - %s " , urlToMonitor, (callResult ? " [Successful]" : " [Failed]") ));
        }


        return callResult;
    }
	
	
	// helper method type methods
	
	private static List<String> csvToList(String csv){
		if(csv == null){
			return new ArrayList<String>();
		}
		
		return Arrays.asList(csv.split("\\s*,\\s*"));
		
	}
	
	private void sendEmail(String message) {
        try {
        	// do not send email notification of email to notify is empty
        	if(emailsToNotify == null || emailsToNotify.trim().isEmpty()){
        		return;
        	}
        	 Properties props = new Properties();
             
             // use the system property mail.host if available; otherwise use the monitor.smtpServer
             props.put("mail.smtp.host", System.getProperty("mail.host", smtpServer));

             Emailer.getInstance().sendEmail(fromEmail, emailsToNotify, emailSubject, message, props);
        } catch (Exception e) {
            _logger.error("An error occurred while sending an email", e);
        }
    }

	
}
