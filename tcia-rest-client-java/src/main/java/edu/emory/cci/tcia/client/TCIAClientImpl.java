package edu.emory.cci.tcia.client;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

public class TCIAClientImpl implements ITCIAClient{

	private final static String API_KEY_FIELD = "api_key";
	private String apiKey;
	
	public TCIAClientImpl(String apiKey)
	{
		this.apiKey = apiKey;
	}
	public InputStream execute(String baseUrl,
			Map<String, String> queryParameters)
			throws TCIAClientException {
		try {
			URIBuilder uriBuilder = new URIBuilder(baseUrl);
			
			// Append query parameters to the base URL
			if(queryParameters!=null)
			for(String queryParam : queryParameters.keySet())
			{
				uriBuilder.addParameter(queryParam, queryParameters.get(queryParam));
			}
			
			URI uri = uriBuilder.build();
			
			// create a new HttpGet request
			HttpGet request = new HttpGet(uri);
			
			// add api_key to the header
			request.setHeader(API_KEY_FIELD, apiKey);
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode()!= 200 ) // TCIA Server error
			{
				if(response.getStatusLine().getStatusCode() == 401) // Unauthorized
				{
					throw new TCIAClientException(baseUrl , "Unauthorized access"  );
				}
				else if (response.getStatusLine().getStatusCode() == 404)
				{
					throw new TCIAClientException(baseUrl , "Resource not found");
				}
				else
				{
					throw new TCIAClientException(baseUrl , "Server Error : " + response.getStatusLine().getReasonPhrase());
				}
					
				
			}
			else
			{
				HttpEntity entity = response.getEntity();
				if(entity!=null && entity.getContent()!=null)
				{
					return entity.getContent();
				}
				else
				{
					throw new TCIAClientException(baseUrl , "No Content");
				}
			}
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
		
	}

	

}
