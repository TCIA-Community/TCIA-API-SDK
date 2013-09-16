package edu.emory.cci.tcia.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

public class TCIAClientImpl implements ITCIAClient{

	private final static String API_KEY_FIELD = "api_key";
	private String apiKey;
	private DefaultHttpClient httpClient ;
	private String baseUrl ;
	private static String getImage = "getImage";
	private static String getManufacturerValues = "getManufacturerValues";
	private static  String getModalityValues = "getModalityValues";
	private static  String getCollectionValues = "getCollectionValues";
	private static  String getBodyPartValues = "getBodyPartValues";
	private static  String getPatientStudy = "getPatientStudy";
	private static  String getImageMetaData = "getImageMetaData";
	private static  String getSeries = "getSeries";
	private static  String getPatient = "getPatient";
	
	
	public TCIAClientImpl(String apiKey , String baseUrl)
	{
		this.apiKey = apiKey;
		this.baseUrl = baseUrl;
		httpClient = new DefaultHttpClient();
	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	public String getModalityValues(String collection, String bodyPartExamined,
			String modality, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getModalityValues);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			if(bodyPartExamined!=null)
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);
			
			if(modality!=null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}

	}
	
	private InputStream getRawData(URI uri) throws TCIAClientException, ClientProtocolException, IOException
	{
		// create a new HttpGet request
		HttpGet request = new HttpGet(uri);

		// add api_key to the header
		request.setHeader(API_KEY_FIELD, apiKey);
		HttpResponse response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() != 200) // TCIA Server
																// error
		{
			if (response.getStatusLine().getStatusCode() == 401) // Unauthorized
			{
				throw new TCIAClientException(uri.toString(),
						"Unauthorized access");
			} else if (response.getStatusLine().getStatusCode() == 404) {
				throw new TCIAClientException(uri.toString(),
						"Resource not found");
			} else {
				throw new TCIAClientException(uri.toString(), "Server Error : "
						+ response.getStatusLine().getReasonPhrase());
			}

		} else {
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.getContent() != null) {
				return entity.getContent();
			} else {
				throw new TCIAClientException(baseUrl, "No Content");
			}
		}
	}
	
	public String getManufacturerValues(String collection,
			String bodyPartExamined, String modality, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getManufacturerValues);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			if(bodyPartExamined!=null)
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);
			
			if(modality!=null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public String getCollectionValues(OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getCollectionValues);
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public String getBodyPartValues(String collection, String bodyPartExamined,
			String modality, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getBodyPartValues);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			if(bodyPartExamined!=null)
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);
			
			if(modality!=null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
		
	}
	public String getPatientStudy(String collection, String patientID,
			String studyInstanceUID, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getPatientStudy);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			if(patientID!=null)
				uriBuilder.addParameter(DICOMAttributes.PATIENT_ID, patientID);
			
			if(studyInstanceUID!=null)
				uriBuilder.addParameter(DICOMAttributes.STUDY_INSTANCE_UID, studyInstanceUID);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public String getImageMetaData(String seriesInstanceUID, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getImageMetaData);
			
			if(seriesInstanceUID!=null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public String getSeries(String collection, String modality,
			String studyInstanceUID, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getSeries);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			if(modality!=null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);
			
			if(studyInstanceUID!=null)
				uriBuilder.addParameter(DICOMAttributes.STUDY_INSTANCE_UID, studyInstanceUID);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public String getPatient(String collection, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getPatient);
			
			if(collection!=null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);
			
			uriBuilder.addParameter("format", format.name());
			
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);
			
		} 
		catch (TCIAClientException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TCIAClientException( e , baseUrl); 
		}
	}
	public ImageResult getImage(String seriesInstanceUID)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(baseUrl);
			URIBuilder uriBuilder = new URIBuilder( baseUri.toString() + "/" + getImage);
			
			if(seriesInstanceUID!=null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);
			
			
			
			URI uri = uriBuilder.build();
			// create a new HttpGet request
			HttpGet request = new HttpGet(uri);

			// add api_key to the header
			request.setHeader(API_KEY_FIELD, apiKey);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(request);
			long diff = System.currentTimeMillis() - startTime;
			
			System.out.println("Server Response Received in " + diff + " ms");
			if (response.getStatusLine().getStatusCode() != 200) // TCIA Server
																	// error
			{
				if (response.getStatusLine().getStatusCode() == 401) // Unauthorized
				{
					throw new TCIAClientException(uri.toString(),
							"Unauthorized access");
				} else if (response.getStatusLine().getStatusCode() == 404) {
					throw new TCIAClientException(uri.toString(),
							"Resource not found");
				} else {
					throw new TCIAClientException(uri.toString(), "Server Error : "
							+ response.getStatusLine().getReasonPhrase());
				}

			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null && entity.getContent() != null) {
					ImageResult imageResult = new ImageResult();
					imageResult.setRawData(entity.getContent());
					imageResult.setImageCount(Integer.parseInt(response.getFirstHeader("imageCount").getValue()));
					return imageResult;
				} else {
					throw new TCIAClientException(baseUrl, "No Content");
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
