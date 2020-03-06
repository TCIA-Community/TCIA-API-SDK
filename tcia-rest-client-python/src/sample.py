from tciaclient import TCIAClient
import urllib3, urllib,sys
####################################  Function to print server response #######
def print_server_response(response):
    if response.status == 200:
        print("Server Returned:\n")
        print(response.data.decode('utf-8'))
        print("\n")
    else:
        print("Error: " + str(response.status))

####################################  Create Clients for Two Different Resources  ####
tcia_client = TCIAClient(apiKey=None, baseUrl="https://services.cancerimagingarchive.net/services/v3",resource = "TCIA")
tcia_client2 = TCIAClient(apiKey=None, baseUrl="https://services.cancerimagingarchive.net/services/v3",resource="SharedList")

# Test content_by_name
try:
    response = tcia_client2.contents_by_name(name = "sharedListApiUnitTest")
    print_server_response(response)
except urllib3.exceptions.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_manufacturer_values no query parameters
try:
    response = tcia_client.get_manufacturer_values(collection =
                                                   None,bodyPartExamined =
                                                   None,modality =
                                                   None,outputFormat = "json")
    print_server_response(response)

except urllib3.exceptions.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_series_size
try:
    response = tcia_client.get_series_size(SeriesInstanceUID="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440")
    print_server_response(response)
except urllib3.exceptions.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_manufacturer_values with query Parameter
try:
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM",bodyPartExamined = None,modality = None, outputFormat = "csv")
    print_server_response(response)

except urllib3.exceptions.HTTPError as err:
    print("Errror executing program:\nError Code: ", str(err.code), "\nMessage:", err.read())

# Test get_image.
# NOTE: Image response consumed differently
tcia_client.get_image(seriesInstanceUid ="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440" , downloadPath  ="./", zipFileName ="images.zip");
