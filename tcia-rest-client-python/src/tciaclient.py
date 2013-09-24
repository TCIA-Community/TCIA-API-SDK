
import urllib2, urllib,sys
#
# Refer https://wiki.cancerimagingarchive.net/display/Public/REST+API+Usage+Guide for complete list of API
#
class TCIAClient:
    GET_IMAGE = "getImage"
    GET_MANUFACTURER_VALUES = "getManufacturerValues"
    GET_MODALITY_VALUES = "getModalityValues"
    GET_COLLECTION_VALUES = "getCollectionValues"
    GET_BODY_PART_VALUES = "getBodyPartValues"
    GET_PATIENT_STUDY = "getPatientStudy"
    GET_SERIES = "getSeries"
    GET_PATIENT = "getPatient"
    
    def __init__(self, apiKey , baseUrl):
        self.apiKey = apiKey
        self.baseUrl = baseUrl
        
    def execute(self, url, queryParameters={}):
        queryParameters = dict((k, v) for k, v in queryParameters.iteritems() if v)
        headers = {"api_key" : self.apiKey }
        queryString = "?%s" % urllib.urlencode(queryParameters)
        requestUrl = url + queryString
        request = urllib2.Request(url=requestUrl , headers=headers)
        resp = urllib2.urlopen(request)
        return resp
    
    def get_modality_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_MODALITY_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    
    def get_manufacturer_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_MANUFACTURER_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
        
    def get_collection_values(self,outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_COLLECTION_VALUES
        queryParameters = { "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
        
    def get_body_part_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_BODY_PART_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_patient_study(self,collection = None , patientId = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_PATIENT_STUDY
        queryParameters = {"Collection" : collection , "PatientID" : patientId , "StudyInstanceUID" : studyInstanceUid , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_series(self, collection = None , modality = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_SERIES
        queryParameters = {"Collection" : collection , "StudyInstanceUID" : studyInstanceUid , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_patient(self,collection = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_PATIENT
        queryParameters = {"Collection" : collection , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_image(self , seriesInstanceUid):
        serviceUrl = self.baseUrl + "/" + self.GET_IMAGE
        queryParameters = { "SeriesInstanceUID" : seriesInstanceUid }
        resp = self.execute( serviceUrl , queryParameters)
        return resp
        
    
                
        

def printServerResponse(response):
    if response.getcode() == 200:
        print "Server Returned:\n"
        print response.read()
        print "\n"
    
    else:
        print "Error : " + str(response.getcode) # print error code

# Instantiate TCIAClient object
tcia_client = TCIAClient(apiKey = "<api_key>" , baseUrl = "https://services.cancerimagingarchive.net/services/TCIA/TCIA/query")  

# test get_manufacturer_values
try:    
    response = tcia_client.get_manufacturer_values(collection = None, bodyPartExamined = None, modality =None, outputFormat = "json")
    printServerResponse(response);
except urllib2.HTTPError, err:
    print "Error executing program:\nError Code: ", str(err.code) , "\nMessage: " , err.read()

try:
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM", bodyPartExamined = None, modality =None, outputFormat = "csv")
    printServerResponse(response);
except urllib2.HTTPError, err:
    print "Error executing program:\nError Code: ", str(err.code) , "\nMessage: " , err.read()
try:
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM", bodyPartExamined = None, modality ="MR", outputFormat = "xml")
    printServerResponse(response);
except urllib2.HTTPError, err:
    print "Error executing program:\nError Code: ", str(err.code) , "\nMessage: " , err.read()
    

# test get_image
try:
    response = tcia_client.get_image(seriesInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440");
    # Save server response as images.zip in current directory
    if response.getcode() == 200:
        print "\n" + str(response.info())
        bytesRead = response.read()
        fout = open("images.zip", "wb")
        fout.write(bytesRead)
        fout.close()
        print "\nDownloaded file images.zip from the server"
    else:
        print "Error : " + str(response.getcode) # print error code
        print "\n" + str(response.info())
except urllib2.HTTPError, err:
    print "Error executing program:\nError Code: ", str(err.code) , "\nMessage: " , err.read()