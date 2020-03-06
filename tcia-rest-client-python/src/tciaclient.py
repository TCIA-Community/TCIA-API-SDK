# py2 https://raw.githubusercontent.com/TCIA-Community/TCIA-API-SDK/master/tcia-rest-client-python/src/tciaclient.py
# py3 https://github.com/pangyuteng/TCIA-API-SDK/blob/py37/tcia-rest-client-python/src/tciaclient.py

import os
import urllib3
import urllib
import sys
import math
import traceback
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
    GET_SERIES_SIZE = "getSeriesSize"
    CONTENTS_BY_NAME = "ContentsByName"

    def __init__(self, apiKey, baseUrl, resource):
        self.apiKey = apiKey
        self.baseUrl = baseUrl + "/" + resource

    def execute(self, url, queryParameters={},preload_content=True):
        queryParameters = dict((k, v) for k, v in queryParameters.items() if v)
        
        headers = {}
        if self.apiKey is not None:
            headers = {"api_key" : self.apiKey }

        queryString = "?%s" % urllib.parse.urlencode(queryParameters)
        requestUrl = url + queryString
        http = urllib3.PoolManager()
        request = http.request(method='GET', url=requestUrl , headers=headers, preload_content=preload_content)
        return request

    def get_modality_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_MODALITY_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_series_size(self, SeriesInstanceUID = None, outputFormat = "json"):
        serviceUrl = self.baseUrl + "/query/" + self.GET_SERIES_SIZE
        queryParameters = {"SeriesInstanceUID" : SeriesInstanceUID, "format" :
                           outputFormat}
        resp = self.execute(serviceUrl, queryParameters)
        return resp

    def contents_by_name(self, name = None):
        serviceUrl = self.baseUrl + "/query/" + self.CONTENTS_BY_NAME
        queryParameters = {"name" : name}
        resp = self.execute(serviceUrl,queryParameters)
        return resp

    def get_manufacturer_values(self,collection = None , bodyPartExamined = None, modality = None , outputFormat = "json"):
        serviceUrl = self.baseUrl + "/query/" + self.GET_MANUFACTURER_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_collection_values(self,outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_COLLECTION_VALUES
        queryParameters = { "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_body_part_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_BODY_PART_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_patient_study(self,collection = None , patientId = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_PATIENT_STUDY
        queryParameters = {"Collection" : collection , "PatientID" : patientId , "StudyInstanceUID" : studyInstanceUid , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_series(self, collection = None , modality = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_SERIES
        queryParameters = {"Collection" : collection , "StudyInstanceUID" : studyInstanceUid , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_patient(self,collection = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/query/" + self.GET_PATIENT
        queryParameters = {"Collection" : collection , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_image(self , seriesInstanceUid , downloadPath, zipFileName):
        serviceUrl = self.baseUrl + "/query/" + self.GET_IMAGE
        queryParameters = { "SeriesInstanceUID" : seriesInstanceUid }
        #os.umask(0002)
        try:
            file = os.path.join(downloadPath, zipFileName)
            resp = self.execute( serviceUrl , queryParameters, preload_content=False)
            downloaded = 0
            CHUNK = 256 * 10240
            with open(file, 'wb') as fp:
                for chunk in resp.stream(CHUNK):
                    fp.write(chunk)
        except urllib3.exceptions.HTTPError as e:
            print("HTTP Error:",e.code , serviceUrl)
            return False
        except:
            traceback.print_exc()
            return False

        return True