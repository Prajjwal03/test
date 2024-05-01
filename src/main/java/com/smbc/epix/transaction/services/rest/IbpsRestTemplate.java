package com.smbc.epix.transaction.services.rest;

import java.io.StringReader;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.newgen.encryption.DataEncryption;
import com.smbc.dto.iBPS.WFGetWorkitemDataOutput;
import com.smbc.dto.iBPS.WFSetAttributesOutput;
import com.smbc.dto.iBPS.WMCompleteWorkItemOutput;
import com.smbc.dto.iBPS.WMGetWorkItemOutput;
import com.smbc.epix.transaction.services.exception.ProductApiException;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class IbpsRestTemplate {
    private static final String SESSION_ID_HEADER_PARAM = "sessionId";

    @Autowired
    private NGLogger ngLogger;

    @Value("${inpConfig.cabinetName}")
    private String cabinetName;

    @Value("${inpConfig.RestAPIBaseURL}")
    private String restApiBaseUrl;

    @Value("${inpConfig.userName}")
    private String cabinetUsername;

    @Value("${inpConfig.password}")
    private String cabinetPwd;

    private final RestTemplate restTemplate;
    private String connectUrl;
    private String disconnectUrl;
    private String getWorkItemDataUrl;
    private String lockWorkItemUrl;
    private String setAttributeUrl;
    private String completeWorkitemUrl;

    public IbpsRestTemplate() {
        restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void postConstruct() {
        connectUrl = restApiBaseUrl + "/" + cabinetName + "/WMConnect/?userName=" + cabinetUsername + "&UserExist=N";
        disconnectUrl = restApiBaseUrl + "/" + cabinetName + "/WMDisconnect/?name=";
        getWorkItemDataUrl = restApiBaseUrl + "/" + cabinetName + "/WMGetWorkitemData?workitemId=1&processInstanceId=";
        lockWorkItemUrl = restApiBaseUrl + "/" + cabinetName + "/WMGetWorkItem?workitemId=1&processInstanceId=";
        setAttributeUrl = restApiBaseUrl + "/" + cabinetName + "/WFSetAttributes?workitemId=1&processInstanceId=";
        completeWorkitemUrl = restApiBaseUrl + "/" + cabinetName + "/WMCompleteWorkitem?workitemId=1&processInstanceId=";
    }

    public String connectCabinet() {
        String sessionId = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Password", DataEncryption.decrypt(cabinetPwd));
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(connectUrl, HttpMethod.POST, entity, String.class);
            String[] responseString = getSessionDetails(responseEntity);
            String mainCode = responseString[0];
            if ("0".equals(mainCode)) {
                sessionId = responseString[1];
            }
        } catch (RestClientException e) {
            ngLogger.consoleLog("Error occurred creating connection to process error: %s" + e.getMessage());
            throw new ProductApiException(e);
        }

        return sessionId;
    }

    public boolean disconnectCabinet(String sessionId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER_PARAM, sessionId);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(disconnectUrl + cabinetUsername, HttpMethod.POST, entity, String.class);
            return "0".equals(getDisconnectOutput(responseEntity));
        } catch (RestClientException e) {
            ngLogger.consoleLog("Error occurred while disconnecting from the process with error: " + e.getMessage());
            throw new ProductApiException(e);
        }
    }

    public WFGetWorkitemDataOutput getWorkitemDetails(String processInstanceId, String sessionId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER_PARAM, sessionId);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<WFGetWorkitemDataOutput> response = restTemplate.exchange(getWorkItemDataUrl + processInstanceId, HttpMethod.POST, entity, WFGetWorkitemDataOutput.class);
            if (response != null && response.getBody() != null && response.getBody().getException() != null && response.getBody().getInstrument() != null) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            ngLogger.consoleLog("Error occurred while fetching workitem details with error: " + e.getMessage());
            throw new ProductApiException(e);
        }
        return null;
    }

    private String getDisconnectOutput(ResponseEntity<String> response) {

        String mainCode = "";
        try {
            String responseBody = response.getBody();
            if (responseBody != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
                if (jsonObject != null) {
                    JSONObject wmConnectObj = (JSONObject) jsonObject.get("WMDisConnect_Output");
                    if (wmConnectObj != null) {
                        JSONObject exceptionObj = (JSONObject) wmConnectObj.get("Exception");
                        if (exceptionObj != null) {
                            mainCode = String.valueOf(exceptionObj.get("MainCode"));
                        } else {
                            ngLogger.errorLog("getSessionDetails exceptionObj NULL " + responseBody);
                        }
                    } else {
                        ngLogger.errorLog("getSessionDetails wmConnectObj NULL " + responseBody);
                    }
                } else {
                    ngLogger.errorLog("getSessionDetails jsonObject NULL " + responseBody);
                }
            } else {
                ngLogger.errorLog("getSessionDetails responseBody NULL");
            }
        } catch (ParseException e) {
            ngLogger.errorLog("Exception in getSessionDetails : " + e.getMessage());
        }
        return mainCode;
    }

    private String[] getSessionDetails(ResponseEntity<String> response) {

        String[] stringArray = new String[2];
        try {
            String responseBody = response.getBody();
            if (responseBody != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
                if (jsonObject != null) {
                    JSONObject wmConnectObj = (JSONObject) jsonObject.get("WMConnect_Output");
                    if (wmConnectObj != null) {
                        JSONObject exceptionObj = (JSONObject) wmConnectObj.get("Exception");
                        if (exceptionObj != null) {
                            stringArray[0] = String.valueOf(exceptionObj.get("MainCode"));
                        } else {
                            ngLogger.errorLog("getSessionDetails exceptionObj NULL " + responseBody);
                        }

                        JSONObject participantObj = (JSONObject) wmConnectObj.get("Participant");
                        if (participantObj != null) {
                            stringArray[1] = String.valueOf(participantObj.get("SessionId"));
                        } else {
                            ngLogger.errorLog("getSessionDetails participantObj NULL" + responseBody);
                        }
                    } else {
                        ngLogger.errorLog("getSessionDetails wmConnectObj NULL " + responseBody);
                    }
                } else {
                    ngLogger.errorLog("getSessionDetails jsonObject NULL " + responseBody);
                }
            } else {
                ngLogger.errorLog("getSessionDetails responseBody NULL");
            }
        } catch (ParseException e) {
            ngLogger.errorLog("Exception in getSessionDetails : " + e.getMessage());
        }
        return stringArray;
    }

    public boolean getLockOnWorkItem(String processInstanceId, String sessionId) {
        boolean isLocked = false;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER_PARAM, sessionId);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<WMGetWorkItemOutput> response = restTemplate.exchange(lockWorkItemUrl + processInstanceId, HttpMethod.POST, entity, WMGetWorkItemOutput.class);
            if (response != null && response.getBody() != null && response.getBody().getException() != null) {
                isLocked = response.getBody().getException().getMainCode() == 0;
            }
        } catch (HttpServerErrorException e) {
            // error message from IBPS API
            WMGetWorkItemOutput response = parseIBPSErrorResponse(e, WMGetWorkItemOutput.class);
            if (response != null && response.getException() != null) {
                ngLogger.errorLog("Received error code: " + response.getException().getMainCode() + " from product");
            } else {
                ngLogger.errorLog("Unable to parse error code from product");
            }
        } catch (RestClientException e) {
            ngLogger.errorLog("Error occurred while doing lock operation to " + processInstanceId + ", error: %s" +e.getMessage());
        }

        return isLocked;
    }

    private <T> T parseIBPSErrorResponse(HttpServerErrorException e, Class<T> type) {
        try {
            return (T) JAXBContext.newInstance(type).createUnmarshaller().unmarshal(new StringReader(e.getResponseBodyAsString()));
        } catch (JAXBException ex) {
            ngLogger.errorLog("Error occurred while parsing error code from product, error: " +ex.getMessage());
            return null;
        }
    }

    public boolean setAttributes(String processInstanceId, String sessionId, String attributesXml) {
        boolean isSuccess = false;

        StringBuilder inputXml = new StringBuilder();
        inputXml.append("<?xml version=1.0?>");
        inputXml.append("<WFSetAttributes_Input>");
        inputXml.append("<Option>WFSetAttributes</Option>");
        inputXml.append("<SessionId>").append(sessionId).append("</SessionId>");
        inputXml.append("<UserDefVarFlag>Y</UserDefVarFlag>");
        inputXml.append("<EngineName>").append(cabinetName).append("</EngineName>");
        inputXml.append("<ProcessInstanceID>").append(processInstanceId).append("</ProcessInstanceID>");
        inputXml.append("<WorkItemID>").append("1").append("</WorkItemID>");
        inputXml.append("<Attributes>").append(attributesXml).append("</Attributes>");
        inputXml.append("</WFSetAttributes_Input>");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER_PARAM, sessionId);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(inputXml.toString(), headers);
            ResponseEntity<WFSetAttributesOutput> response = restTemplate.exchange(setAttributeUrl + processInstanceId, HttpMethod.POST, entity, WFSetAttributesOutput.class);
            if (response != null && response.getBody() != null && response.getBody().getException() != null) {
                isSuccess = response.getBody().getException().getMainCode() == 0;
            }
        } catch (RestClientException e) {
            ngLogger.errorLog("Error occurred while doing saving attributes to " + processInstanceId + ", error: %s" +e.getMessage());
        }

        return isSuccess;
    }

    public boolean completeWorkitem(String processInstanceId, String sessionId) {
        boolean isComplete = false;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SESSION_ID_HEADER_PARAM, sessionId);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<WMCompleteWorkItemOutput> response = restTemplate.exchange(completeWorkitemUrl + processInstanceId, HttpMethod.POST, entity, WMCompleteWorkItemOutput.class);
            if (response != null && response.getBody() != null && response.getBody().getException() != null) {
                isComplete = response.getBody().getException().getMainCode() == 0;
            }
        } catch (RestClientException e) {
            ngLogger.errorLog("Error occurred while completing workitem to " + processInstanceId + ", error: %s" + e.getMessage());
        }

        return isComplete;
    }
}
