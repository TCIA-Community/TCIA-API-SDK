package edu.emory.cci.tcia.client.test;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import edu.emory.cci.tcia.client.ITCIAClient;
import edu.emory.cci.tcia.client.ITCIAClient.ImageResult;
import edu.emory.cci.tcia.client.OutputFormat;
import edu.emory.cci.tcia.client.TCIAClientException;
import edu.emory.cci.tcia.client.TCIAClientImpl;




/**
 *  Refer https://wiki.cancerimagingarchive.net/display/Public/REST+API+Usage+Guide for complete list of API
 */
public class TestTCIAClient {

	private static String baseUrl = "https://services.cancerimagingarchive.net/services/v2"; // Base URL of the service
	private static String resourceTCIA = "/TCIA";
	private static String resourceSharedList = "/SharedList";
	
	private static String apiKey = "9d0d3230-45a6-4d9d-adf1-be6910a46bd2";
	
	/**
	 *  Method : GetCollectionValues
	 *  Description : Returns  set of all collection values
	 */
	
	@Test
	public void testGetCollectionValues()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respXML = client.getCollectionValues(OutputFormat.xml);
			String respJSON = client.getCollectionValues(OutputFormat.json);
			String respCSV = client.getCollectionValues(OutputFormat.csv);
			
			// Print server response
			System.out.println(respXML);
			System.out.println(respJSON);
			System.out.println(respCSV);
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
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
		
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String seriesInstanceUID = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440";
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			ImageResult imageResult = client.getImage(seriesInstanceUID);
			double averageDICOMFileSize = 200 * 1024d ; // 200KB
			double compressionRatio = 0.75 ; // approx
			int estimatedFileSize = (int) (averageDICOMFileSize * compressionRatio * imageResult.getImageCount());
			saveTo(imageResult.getRawData(),  seriesInstanceUID + ".zip", ".", estimatedFileSize);
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	

	@Test
	public void testGetSeries()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = "TCGA-GBM"; // optional
		String modality = "MR"; // optional
		String studyInstanceUID = null; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getSeries(collection, modality, studyInstanceUID, OutputFormat.json);
			
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	
	@Test
	public void testGetPatientStudy()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = "TCGA-GBM"; // optional
		String patientID = null; // optional
		String studyInstanceUID = null; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getPatientStudy(collection, patientID, studyInstanceUID, OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	@Test
	public void testGetPatient()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = "TCGA-GBM"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getPatient(collection , OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetBodyPartValues()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = null ; // optional
		String bodyPartExamined = null; // optional
		String modality = "MR"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getBodyPartValues(collection, bodyPartExamined, modality, OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetModalityValues()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = null ; // optional
		String bodyPartExamined = "BRAIN"; // optional
		String modality = "MR"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getModalityValues(collection, bodyPartExamined, modality, OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetSharedList()
	{
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceSharedList);
		String name = "sharedListApiUnitTest";
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getSharedList(name, OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetManufacturerValues()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl(apiKey , baseUrl+resourceTCIA);
		String collection = null ; // optional
		String bodyPartExamined = "BRAIN"; // optional
		String modality = "MR"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getManufacturerValues(collection, bodyPartExamined, modality, OutputFormat.json);
			
			// Print server response
			System.out.println(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	
	
	
	
	public static void saveTo(InputStream in , String name , String directory , int estimatedBytes) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(directory + "/" + name);
		byte[] buffer = new byte[4096];
		int read = -1;
		int sum = 0;
		while((read = in.read(buffer)) > 0)
		{
			fos.write(buffer , 0 , read);
			long mseconds = System.currentTimeMillis();
			sum += read;
			
			if(mseconds % 10 == 0)
			{
				System.out.println(String.format("Bytes Written %s out of estimated %s  : " , sum , estimatedBytes));
			}
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
