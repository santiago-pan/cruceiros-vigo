package com.nadisam.cruceirosvigo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.os.AsyncTask;

/** Fetches the HTML content of a web page (or HTTP header) as a String. */
public final class WebFetcher {

	private class WebTask extends AsyncTask<URL, Integer, Long> {
	     
		protected Long doInBackground(URL... urls) {
	         int count = urls.length;
	         long totalSize = 0;
	         
	         return totalSize;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	     }
	 }
	
	public WebFetcher(URL aURL) {
		if (!HTTP.equals(aURL.getProtocol())) 
		{
			throw new IllegalArgumentException("URL is not for HTTP Protocol: "
					+ aURL);
		}
		fURL = aURL;
	}

	public WebFetcher(String aUrlName) throws MalformedURLException {
		this(new URL(aUrlName));
	}

	/** Fetch the HTML content of the page as simple text. */
	public String getPageContent() {
		String result = null;
		URLConnection connection = null;
		String web = "";
		try 
		{
			connection = fURL.openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter(END_OF_INPUT);
			result = scanner.next();
			
			while (null != result)
			{
				web = web + result;
				result = scanner.next();
			}
			
		} catch (Exception ex) {
			log("Cannot open connection to: " + ex);
		}
		return web;
	}

	/** Fetch HTML headers as simple text. */
	public String getPageHeader() {
		StringBuilder result = new StringBuilder();

		URLConnection connection = null;
		try {
			connection = fURL.openConnection();
		} catch (IOException ex) {
			log("Cannot open connection to URL: " + fURL);
		}

		// not all headers come in key-value pairs - sometimes the key is
		// null or an empty String
		int headerIdx = 0;
		String headerKey = null;
		String headerValue = null;
		while ((headerValue = connection.getHeaderField(headerIdx)) != null) {
			headerKey = connection.getHeaderFieldKey(headerIdx);
			if (headerKey != null && headerKey.length() > 0) {
				result.append(headerKey);
				result.append(" : ");
			}
			result.append(headerValue);
			result.append(NEWLINE);
			headerIdx++;
		}
		return result.toString();
	}

	// PRIVATE //
	private URL fURL;

	private static final String HTTP = "http";
	private static final String HEADER = "header";
	private static final String CONTENT = "content";
	private static final String END_OF_INPUT = "\\Z";
	private static final String NEWLINE = System.getProperty("line.separator");

	private static void log(Object aObject) {
		System.out.println(aObject);
	}
}
