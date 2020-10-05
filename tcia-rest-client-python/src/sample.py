from tciaclient import TCIAClient
import urllib.request, urllib.error, urllib.parse, urllib.request, urllib.parse, urllib.error,sys
####################################  Function to print server response #######
def printServerResponse(response):
    if response.getcode() == 200:
        print("Server Returned:\n")
        print(response.read())
        print("\n")
    else:
        print("Error: " + str(response.getcode()))

####################################  Create Clients for Two Different Resources  ####
tcia_client = TCIAClient(baseUrl="https://services.cancerimagingarchive.net/services/v4",resource = "TCIA")
tcia_client2 = TCIAClient(baseUrl="https://services.cancerimagingarchive.net/services/v4",resource="SharedList")

# Test content_by_name
try:
    response = tcia_client2.contents_by_name(name = "sharedListApiUnitTest")
    printServerResponse(response);
except urllib.error.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_manufacturer_values no query parameters
try:
    response = tcia_client.get_manufacturer_values(collection =
                                                   None,bodyPartExamined =
                                                   None,modality =
                                                   None,outputFormat = "json")
    printServerResponse(response);

except urllib.error.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_series_size
try:
    response = tcia_client.get_series_size(SeriesInstanceUID="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440")
    printServerResponse(response);
except urllib.error.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_manufacturer_values with query Parameter
try:
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM",bodyPartExamined = None,modality = None, outputFormat = "csv")
    printServerResponse(response);

except urllib.error.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_image.
# NOTE: Image response consumed differently
tcia_client.get_image(seriesInstanceUid ="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440" , downloadPath  ="./", zipFileName ="images.zip");
