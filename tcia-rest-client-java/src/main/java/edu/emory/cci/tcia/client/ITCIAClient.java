package edu.emory.cci.tcia.client;

import java.io.InputStream;
import java.util.Map;

public interface ITCIAClient {
	public InputStream execute(String baseUrl , Map<String,String> queryParameters ) throws TCIAClientException;
	
}
