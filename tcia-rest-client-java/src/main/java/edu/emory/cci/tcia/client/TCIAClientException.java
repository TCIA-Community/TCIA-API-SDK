package edu.emory.cci.tcia.client;

public class TCIAClientException extends Exception {

	private static final long serialVersionUID = -2548423882689490254L;
	private String requestUrl;
	
	
	public TCIAClientException(String requestUrl) {
		super();
		this.requestUrl = requestUrl;
	}

	public TCIAClientException(String message, Throwable cause , String requestUrl) {
		super(message, cause);
		this.requestUrl = requestUrl;
	}

	public TCIAClientException(String message, String requestUrl) {
		super(message);
		this.requestUrl = requestUrl;
	}

	public TCIAClientException(Throwable cause , String requestUrl) {
		super(cause);
		this.requestUrl = requestUrl;
	}
	
	public String toString()
	{
		return String.format("Request URL =[%s]\t%s", this.requestUrl , super.toString());
	}

	@Override
	public String getMessage() {
		
		return String.format("Request URL =[%s]\t%s", this.requestUrl , super.getMessage());
	}
	
	
	
}
