package edu.emory.cci.tcia.client.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.emory.cci.tcia.client.TCIAClientException;
import edu.emory.cci.tcia.client.TCIAClientImpl;
import edu.emory.cci.tcia.client.ITCIAClient;




/**
 *  Refer https://wiki.cancerimagingarchive.net/display/Public/REST+API+Usage+Guide for complete list of API
 */
public class TestTCIAClient {

	/**
	 *  Method : GetCollectionValues
	 *  Description : Returns set of all collection values
	 */
	
	@Test
	public void testGetCollectionValues()
	{
		String baseUrl = "http://10.28.163.174:9099/services/TCIA/TCIA/query/getCollectionValues" ; // Base URL of the service 
		
		// Set query parameters
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("modality", "MR"); // set modality
		
		// Set API-Key
		String apiKey = "31ed920f-ba6a-415f-966f-52c8e9eea649";
		
		// create TCIA Client by passing API-Key in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey);
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			InputStream is = client.execute(baseUrl, queryParams);
			
			// Assert response is not null
			assertNotNull(is);
			
			// Print server response
			System.out.println(toString(is));
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (IOException e) {
			fail(e.getMessage()); // request failed
		}
	}
	
	/**
	 *  Method : GetImage
	 *  Description : Returns images in a zip file
	 */
	
	@Test
	public void testGetImage()
	{
		String baseUrl = "http://10.28.163.174:9099/services/TCIA/TCIA/query/getImage" ; // Base URL of the service 
		
		// Set query parameters
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("series_instance_uid", "1.3.6.1.4.1.9328.50.45.305379731379418833489008183081988213374"); // set series instance uid
		
		// Set API-Key
		String apiKey = "31ed920f-ba6a-415f-966f-52c8e9eea649"; 
		
		// create TCIA Client by passing API-Key in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey);
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			InputStream is = client.execute(baseUrl, queryParams);
			
			// Assert response is not null
			assertNotNull(is);
			
			// Save the zip file 
			saveTo(is, "images.zip", "."); // save as images.zip in current directory
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (IOException e) {
			fail(e.getMessage()); // request failed
		}
	}
	
	
	
	public static void saveTo(InputStream in , String name , String directory) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(directory + "/" + name);
		byte[] buffer = new byte[4096];
		int read = -1;
		while((read = in.read(buffer)) > 0)
		{
			fos.write(buffer , 0 , read);
		}
		fos.close();
		in.close();
	}
	public static String toString(InputStream in) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int bytesRead = -1;
		
		while((bytesRead = in.read(buffer)) > 0)
		{
			baos.write(buffer, 0, bytesRead);
		}
		
		baos.close();
		return (new String(baos.toByteArray()));
	}

}
