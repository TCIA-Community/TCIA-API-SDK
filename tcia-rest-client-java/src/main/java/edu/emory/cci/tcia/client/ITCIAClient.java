package edu.emory.cci.tcia.client;

import java.io.InputStream;


public interface ITCIAClient {
	public String getModalityValues(String collection,String bodyPartExamined,String modality , OutputFormat format) throws TCIAClientException;
	public String getManufacturerValues(String collection,String bodyPartExamined,String modality, OutputFormat format) throws TCIAClientException;
	public String getCollectionValues(OutputFormat format) throws TCIAClientException;
	public String getBodyPartValues(String collection,String bodyPartExamined,String modality, OutputFormat format) throws TCIAClientException;
	public String getPatientStudy(String collection,String patientID , String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	public String getSeries(String collection,String modality,String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	public String getPatient(String collection, OutputFormat format) throws TCIAClientException;
	public ImageResult getImage(String seriesInstanceUID) throws TCIAClientException;
	
	public static class ImageResult {
		private InputStream rawData;
		private Integer imageCount;
		public InputStream getRawData() {
			return rawData;
		}
		public void setRawData(InputStream rawData) {
			this.rawData = rawData;
		}
		public Integer getImageCount() {
			return imageCount;
		}
		public void setImageCount(Integer imageCount) {
			this.imageCount = imageCount;
		}
		
	}
}
