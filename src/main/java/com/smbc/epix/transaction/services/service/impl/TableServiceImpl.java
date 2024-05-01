package com.smbc.epix.transaction.services.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.smbc.dto.iBPS.WFGetWorkitemDataOutput;
import com.smbc.epix.transaction.services.dao.TableDAO;
import com.smbc.epix.transaction.services.dto.table.CallbackActionHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.CallbackAmendmentHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.CallbackAmendmentHistoryResponseDTO;
import com.smbc.epix.transaction.services.dto.table.CallbackStatus;
import com.smbc.epix.transaction.services.dto.table.CustomDashboardDataCurrency;
import com.smbc.epix.transaction.services.dto.table.ListApplicationDTO;
import com.smbc.epix.transaction.services.dto.table.ListQueueParamsDTO;
import com.smbc.epix.transaction.services.dto.table.SwapHistory;
import com.smbc.epix.transaction.services.dto.table.UserDTO;
import com.smbc.epix.transaction.services.dto.table.UserRoleDTO;
import com.smbc.epix.transaction.services.dto.table.UtilizationHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.UtilizationHistoryResponseDTO;
import com.smbc.epix.transaction.services.dto.table.VersionHistoryDTO;
import com.smbc.epix.transaction.services.exception.DAOException;
import com.smbc.epix.transaction.services.exception.ProductApiException;
import com.smbc.epix.transaction.services.exception.ServiceException;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.rest.IbpsRestTemplate;
import com.smbc.epix.transaction.services.service.TableService;
import com.smbc.epix.transaction.services.utils.NGLogger;
import com.smbc.epix.transaction.services.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private IbpsRestTemplate ibpsRestTemplate;

    @Override
    public List<Map> getToDoTableFields(DynamicTable dynamicQuery) {
        List<String> userRoleName = tableDAO.getUserCategory(dynamicQuery);

        if (!CollectionUtils.isEmpty(userRoleName)) {
            dynamicQuery.setDynamicWhereClauseCase1(userRoleName.stream().collect(Collectors.joining("', '", " '", "'")));
        }

        return tableDAO.getToDoTableFields(dynamicQuery);
    }

    @Override
    public List<Map> getToDoColumnTableFields(DynamicTable dynamicQuery) {
        List<String> userCategories = tableDAO.getUserCategory(dynamicQuery);

        if (!CollectionUtils.isEmpty(userCategories)) {
            dynamicQuery.setDynamicWhereClauseCase1(userCategories.stream().collect(Collectors.joining("', '", " '", "'")));
        }

        return tableDAO.getToDoColumnTableFields(dynamicQuery);
    }

    @Override
    public List<Map> getEnquiryTableFields(DynamicTable dynamicQuery) {
        List<String> userCategories = tableDAO.getUserCategory(dynamicQuery);

        if (!CollectionUtils.isEmpty(userCategories)) {
            dynamicQuery.setDynamicWhereClauseCase1(userCategories.stream().collect(Collectors.joining("', '", " '", "'")));
        }

        return tableDAO.getEnquiryTableFields(dynamicQuery);
    }

    @Override
    public List<Map> getEnquiryColumnTableFields(DynamicTable dynamicQuery) {
        List<String> userCategories = tableDAO.getUserCategory(dynamicQuery);

        if (!CollectionUtils.isEmpty(userCategories)) {
            dynamicQuery.setDynamicWhereClauseCase1(userCategories.stream().collect(Collectors.joining("', '", " '", "'")));
        }

        return tableDAO.getEnquiryColumnTableFields(dynamicQuery);
    }

    @Override
    public List<Map> getDropDownFields(DynamicTable dynamicQuery) {
        return tableDAO.getDropDownData(dynamicQuery);
    }

    /**
     * @param productCode - Added to resolve the cross application dual role issue
     */
    @Override
    public List<Map> getListQueueAccessTemp(int userIndex, String tableName, String productCode) {
        String dynamicQuery = tableDAO.getListQueueParams().stream()
                .map(ListQueueParamsDTO::getParam)
                .collect(Collectors.joining(","));

        DynamicTable dynamicTable = new DynamicTable();
        dynamicTable.setListQueueParamQuery(dynamicQuery);
        dynamicTable.setSearchString1(tableName);
        dynamicTable.setPassingValue1(userIndex);
        dynamicTable.setSearchString2(productCode);
        return tableDAO.getListQueueAccess(dynamicTable);
    }

    @Override
    public List<Map> getBranch(int userIndex) {
        return tableDAO.getBranch(userIndex);
    }

    @Override
    public List<UserDTO> getUserName(int userIndex) {
        return tableDAO.getUserName(userIndex);
    }

    @Override
    public List<Map> getProduct(DynamicTable dynamicQuery) {
        return tableDAO.getProduct(dynamicQuery);
    }

    @Override
    public List<Map> getEnquirySearchFilter(DynamicTable dynamicQuery) {
        return tableDAO.getEnquirySearchFilter(dynamicQuery);
    }

    @Override
    public List<Map> getDropDownFieldsForTransactionType(DynamicTable dynamicQuery) {
        return tableDAO.getDropDownFieldsForTransactionType(dynamicQuery);
    }

    @Override
    public List<Map> getCaseStatus(DynamicTable dynamicQuery) {
        return tableDAO.getCaseStatus(dynamicQuery);
    }

    @Override
    public List<Map> getCommentHistory(String transactionID) {
        return tableDAO.getCommentHistory(transactionID);
    }

    @Override
    public List<Map> getCommentHistoryDfx(String transactionID) {
        return tableDAO.getCommentHistoryDfx(transactionID);
    }

    @Override
    public List<Map> getSessionStatus(DynamicTable dynamicQuery) {
        return tableDAO.getSessionStatus(dynamicQuery);
    }

    @Override
    public List<VersionHistoryDTO> getVersionHistory(DynamicTable dynamicQuery) {
        return tableDAO.getVersionHistory(dynamicQuery);
    }

    @Override
    public List<SwapHistory> getSwapHistory(DynamicTable dynamicQuery) {
        return tableDAO.getSwapHistory(dynamicQuery);
    }

    @Override
    public List<Map> getCustomDashboardDataSubProduct(DynamicTable dynamicQuery) {
        return tableDAO.getCustomDashboardDataSubProduct(dynamicQuery);
    }

    @Override
    public List<CustomDashboardDataCurrency> getCustomDashboardDataCurrency(DynamicTable dynamicQuery) {
        return tableDAO.getCustomDashboardDataCurrency(dynamicQuery);
    }

    @Override
    public List<UserRoleDTO> getUserRoleDetails(int userIndex) {
        return tableDAO.getUserRoleDetails(userIndex);
    }

    @Override
    public Boolean getCreateAuth(int userIndex) {
        List<String> roleNames = tableDAO.getUserRoleName(userIndex);

        for (String roleName : roleNames) {
            if ("Branch Maker".equalsIgnoreCase(roleName) || "SSC Maker".equalsIgnoreCase(roleName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<ListApplicationDTO> getApplication() {
        return tableDAO.getApplication();
    }

    @Override
    public List<Map> getIformDetails(String productCode) {
        return tableDAO.getIformDetails(productCode);
    }

    @Override
    public String callbackAmendment(String pid, String username) {
        String errorMessage = "Select one valid transaction to initiate Callback Amendment.";
        if (!StringUtils.hasText(pid)) {
            return errorMessage;
        }

        errorMessage = "Unable to connect to process";
        String sessionId = null;

        try {
            sessionId = ibpsRestTemplate.connectCabinet();
            if (!StringUtils.hasText(sessionId)) {
                return errorMessage;
            }

            ngLogger.consoleLog("Attempting to get workitem details");
            WFGetWorkitemDataOutput workitemDetails = ibpsRestTemplate.getWorkitemDetails(pid, sessionId);
            Long approvedCallbackCount = tableDAO.getApprovedCallbackCount(pid);
            String validationWIError = validateWorkitemForCallbackAmendment(workitemDetails, approvedCallbackCount);
            if (StringUtils.hasText(validationWIError)) {
                throw new ProductApiException(validationWIError);
            }

            CallbackStatus callbackStatus = tableDAO.getCallbackStatus(pid);
            String validationTransactionError = validateTransactionForCallbackAmendment(callbackStatus, pid);
            if (StringUtils.hasText(validationTransactionError)) {
                throw new ProductApiException(validationTransactionError);
            }

            errorMessage = "Unable to lock workitem";
            ngLogger.consoleLog("Attempting to lock the workitem with process instance Id" + pid);
            boolean lockOnWorkItem = ibpsRestTemplate.getLockOnWorkItem(pid, sessionId);
            if (!lockOnWorkItem) {
                throw new ProductApiException(errorMessage);
            }

            errorMessage = "Unable to update workitem";
            ngLogger.consoleLog("Attempting to set attributes for the workitem with process instance Id" + pid);
            String attributesXml = String.format("<Action_Taken>CallbackAmendment</Action_Taken><RequestRaisedWorkStep>%s</RequestRaisedWorkStep>",
                    workitemDetails.getInstrument().getActivityName());
            boolean isWorkitemUpdated = ibpsRestTemplate.setAttributes(pid, sessionId, attributesXml);
            if (!isWorkitemUpdated) {
                throw new ProductApiException(errorMessage);
            }

            errorMessage = "Unable to complete callback amendment";
            ngLogger.consoleLog("Attempting to complete the workitem with process instance Id" + pid);
            boolean isWorkitemCompleted = ibpsRestTemplate.completeWorkitem(pid, sessionId);
            if (!isWorkitemCompleted) {
                throw new ProductApiException(errorMessage);
            }

            addCallbackActionHistory(username, callbackStatus.getItemIndex());
        } catch (DAOException | RestClientException | ProductApiException e) {
            ngLogger.errorLog("Either the transaction is not eligible or error occurred while processing the transaction " + pid + ", error: " + e.getMessage());
            return e.getMessage();
        } finally {
            if (StringUtils.hasText(sessionId)) {
                try {
                    boolean isDisconnected = ibpsRestTemplate.disconnectCabinet(sessionId);
                    if (!isDisconnected) {
                        ngLogger.errorLog("Unable to disconnect cabinet");
                    }
                } catch (Exception e) {
                    ngLogger.errorLog("Unable to disconnect cabinet" + e.getMessage());
                }
            }
        }

        ngLogger.consoleLog("Successfully completed the workitem");
        return "Callback Amendment request submitted successfully";
    }

    @Override
    public String getRoleName(int userIndex) {
        List<String> roleName = tableDAO.getUserRoleName(userIndex);
        return roleName.get(0);
    }

    @Override
    public String utilizationReversal(String pid, String username) {
        String errorMessage = "Select one valid transaction to initiate Utilization Reversal.";
        if (!StringUtils.hasText(pid)) {
            return errorMessage;
        }

        errorMessage = "Unable to connect to process";
        String sessionId = null;

        try {
            sessionId = ibpsRestTemplate.connectCabinet();
            if (!StringUtils.hasText(sessionId)) {
                return errorMessage;
            }

            ngLogger.consoleLog("Attempting to get workitem details");
            WFGetWorkitemDataOutput workitemDetails = ibpsRestTemplate.getWorkitemDetails(pid, sessionId);

            String validationWIError = validateWorkitemForUtilizationReversal(workitemDetails);
            if (StringUtils.hasText(validationWIError)) {
                throw new ProductApiException(validationWIError);
            }

            CallbackStatus callbackStatus = tableDAO.getCallbackStatus(pid);
            List<String> departments = tableDAO.getDepartmentsForUtilizationHistory();
            Long settledUtilizationCount = tableDAO.getSettledUtilizationCount(pid);
            // todo add validation for department
            String validationTransactionError = validateTransactionForUtilizationHistory(callbackStatus, departments, settledUtilizationCount);
            if (StringUtils.hasText(validationTransactionError)) {
                throw new ProductApiException(validationTransactionError);
            }

            errorMessage = "Unable to lock workitem";
            ngLogger.consoleLog("Attempting to lock the workitem");
            boolean lockOnWorkItem = ibpsRestTemplate.getLockOnWorkItem(pid, sessionId);
            if (!lockOnWorkItem) {
                throw new ProductApiException(errorMessage);
            }

            errorMessage = "Unable to update workitem";
            ngLogger.consoleLog("Attempting to set attributes for the workitem");
            String attributesXml = "<Action_Taken>UtilizationReversal</Action_Taken>";
            boolean isWorkitemUpdated = ibpsRestTemplate.setAttributes(pid, sessionId, attributesXml);
            if (!isWorkitemUpdated) {
                throw new ProductApiException(errorMessage);
            }

            errorMessage = "Unable to complete callback amendment";
            ngLogger.consoleLog("Attempting to complete the workitem");
            boolean isWorkitemCompleted = ibpsRestTemplate.completeWorkitem(pid, sessionId);
            if (!isWorkitemCompleted) {
                throw new ProductApiException(errorMessage);
            }
            // todo insert utilization history
        } catch (DAOException | RestClientException | ProductApiException e) {
            return e.getMessage();
        } finally {
            if (StringUtils.hasText(sessionId)) {
                try {
                    boolean isDisconnected = ibpsRestTemplate.disconnectCabinet(sessionId);
                    if (!isDisconnected) {
                        ngLogger.errorLog("Unable to disconnect cabinet");
                    }
                } catch (Exception e) {
                    ngLogger.errorLog("Unable to disconnect cabinet" + e.getMessage());
                }
            }
        }

        ngLogger.consoleLog("Successfully completed the workitem");
        return "Utilization reversal request submitted successfully";
    }

    @Override
    public List<CallbackAmendmentHistoryResponseDTO> callbackAmendmentHistory(String pid) {
        List<CallbackAmendmentHistoryResponseDTO> callbackAmendmentHistoryResponseDTO = new ArrayList<>();
        List<CallbackAmendmentHistoryDTO> callbackAmendmentHistoryDTO = tableDAO.getCallbackAmendmentHistory(pid);
        int currentCallbackID = 0;

        for (CallbackAmendmentHistoryDTO callbackAmendmentHistory : callbackAmendmentHistoryDTO) {
            CallbackAmendmentHistoryResponseDTO callbackHistoryResponse = new CallbackAmendmentHistoryResponseDTO();
            callbackHistoryResponse.setUserName(callbackAmendmentHistory.getActionUser());
            callbackHistoryResponse.setDateTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(callbackAmendmentHistory.getActionDateTime()));
            callbackHistoryResponse.setAction(callbackAmendmentHistory.getAction());

            if (currentCallbackID != callbackAmendmentHistory.getCallbackID()) {
                currentCallbackID = callbackAmendmentHistory.getCallbackID();
                StringBuilder callbackDetails = new StringBuilder("Call-back Waived : ").append(Util.convertBooleanToYesOrNo(callbackAmendmentHistory.getCallBackWaived()))
                        .append("<br>Reason why Call-back is not required: ").append(Util.getString(callbackAmendmentHistory.getReasonNotRequired()))
                        .append("<br>Risk Disclosure Checked : ").append(Util.convertBooleanToYesOrNo(callbackAmendmentHistory.getRiskDisclosureChecked()))
                        .append("<br>Customer Name : ").append(Util.getString(callbackAmendmentHistory.getCustomerName()))
                        .append("<br>Confirmed Date Time : ").append(buildDateTime(callbackAmendmentHistory))
                        .append("<br>Confirmed By(SMBC) : ").append(Util.getString(callbackAmendmentHistory.getConfirmedBy()));
                callbackHistoryResponse.setCallbackDetails(callbackDetails.toString());
            }

            callbackAmendmentHistoryResponseDTO.add(callbackHistoryResponse);
        }

        return callbackAmendmentHistoryResponseDTO;
    }

    @Override
    public List<UtilizationHistoryResponseDTO> getUtilizationHistory(String pid) {
        List<UtilizationHistoryResponseDTO> utilizationHistoryResponseDTO = new ArrayList<>();
        List<UtilizationHistoryDTO> utilizationHistoryDTO = tableDAO.getUtilizationHistory(pid);

        for (UtilizationHistoryDTO utilizationHistory : utilizationHistoryDTO) {
            UtilizationHistoryResponseDTO utilizationHistoryResponse = new UtilizationHistoryResponseDTO();

            StringBuilder utilizationDetails = new StringBuilder("CCY1: ")
                    .append(buildCcyAmount(utilizationHistory.getCcy1(), utilizationHistory.getCcy1UtilizationAmount()))
                    .append("<br>CCY2: ").append(buildCcyAmount(utilizationHistory.getCcy2(), utilizationHistory.getCcy2UtilizationAmount()))
                    .append("<br>Department: ").append(utilizationHistory.getDepartment());

            utilizationHistoryResponse.setUtlizationDetails(utilizationDetails.toString());

            utilizationHistoryResponse.setUserName(utilizationHistory.getActionUser());
            utilizationHistoryResponse.setDateTime(
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(utilizationHistory.getActionDateTime()));
            utilizationHistoryResponse.setAction(utilizationHistory.getAction());

            utilizationHistoryResponseDTO.add(utilizationHistoryResponse);
        }

        return utilizationHistoryResponseDTO;
    }

    @Override
    public List<String> getMasterData(String type) {
        if (!StringUtils.hasText(type)) {
            throw new ServiceException("Provided type is invalid");
        }

        if ("TransactionSubType".equalsIgnoreCase(type)) {
            return tableDAO.getTxnSubType();
        } else if ("TransactionStatus".equalsIgnoreCase(type)) {
            return tableDAO.getTransactionStatus();
        } else if ("FoxStatus".equalsIgnoreCase(type)) {
            return tableDAO.getFoxTransactionStatus();
        } else {
            throw new ServiceException("Provided type is invalid");
        }
    }

    private String validateTransactionForCallbackAmendment(CallbackStatus callbackStatus, String pid) {
        String errorMessage = null;

        if (callbackStatus == null || callbackStatus.getTransactionStatus() == null) {
            errorMessage = "Transaction contains invalid transaction status";
            ngLogger.errorLog("Error in fetching transaction and utilization status for process instance Id" + pid);
        } else if ("Utilization".equalsIgnoreCase(callbackStatus.getTransactionStatus())) {
            if (!(callbackStatus.getUtilizationStatus() == null || callbackStatus.getUtilizationStatus().isEmpty() || "UnUtilized".equalsIgnoreCase(callbackStatus.getUtilizationStatus()))) {
                errorMessage = "Only Utilization transactions that are effectively unutilized are eligible for Callback Amendment";
            }
        } else if (!"Completed".equalsIgnoreCase(callbackStatus.getTransactionStatus())) {
            errorMessage = "Only Completed transactions or Utilization transactions that are effectively unutilized are eligible for Callback Amendment";
        } else {
            ngLogger.consoleLog(pid + " transaction is eligible for callback amendment");
        }

        return errorMessage;
    }

    private String validateTransactionForUtilizationHistory(CallbackStatus callbackStatus, List<String> departments, Long settledUtilizationCount) {
        String errorMessage = null;

        if (callbackStatus == null || callbackStatus.getTransactionStatus() == null) {
            errorMessage = "Transaction contains invalid transaction status";
        }

//        if (CollectionUtils.isEmpty(departments)) {
//            errorMessage = "Only a fully utilized transaction with at least one utilization by your department within 30 days of completion is eligible for Utilization Reversal.";
//        }

        if (settledUtilizationCount == null || settledUtilizationCount == 0) {
            errorMessage = "Only a fully utilized transaction with at least one utilization within 30 days of completion is eligible for Utilization Reversal.";
        } else {
            ngLogger.consoleLog("Transaction is eligible for utilization history");
        }

        return errorMessage;
    }

    private String validateWorkitemForCallbackAmendment(WFGetWorkitemDataOutput workitemDetails, Long approvedCallbackCount) {
        String errorMessage = null;

        if (workitemDetails == null || workitemDetails.getInstrument() == null) {
            errorMessage = "Unable to fetch workitem details";
        } else if ("DFX_Exit".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName())) {
            errorMessage = "This transaction is beyond 30 days of completion and cannot be Amended";
        } else if (!("Operation Maker".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName()) ||
                "HLD_Exit".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName()))) {

            errorMessage = "Only a Completed transaction having callback details or Transaction in Utilization status with no effective utilization is eligible for Amendment";
        } else if (approvedCallbackCount == null || approvedCallbackCount == 0) {
            errorMessage = "Only a transaction having callback details is eligible for Amendment";
        } else if ("Y".equals(workitemDetails.getInstrument().getLockStatus())) {
            errorMessage = "Workitem is locked by another user, please request unlock of workitem before proceeding with Callback Amendment";
        } else {
            ngLogger.consoleLog(workitemDetails.getInstrument().getProcessInstanceId() + " transaction is eligible for callback amendment");
        }

        return errorMessage;
    }

    private String validateWorkitemForUtilizationReversal(WFGetWorkitemDataOutput workitemDetails) {
        String errorMessage = null;

        if (workitemDetails == null || workitemDetails.getInstrument() == null) {
            errorMessage = "Unable to fetch workitem details";
        } else if ("Y".equals(workitemDetails.getInstrument().getLockStatus())) {
            ngLogger.errorLog("Workitem is locked");
            errorMessage = "Workitem is locked";
        } else if ("DFX_Exit".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName())) {
            errorMessage = "This transaction is beyond 30 days of completion and is not eligible for Utilization Reversal";
        } else if ("DFX_Discard".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName())) {
            errorMessage = "A new version of this transaction exists. This is not eligible for Utilization Reversal";
        } else if (!"HLD_Exit".equalsIgnoreCase(workitemDetails.getInstrument().getActivityName())) {
            errorMessage = "Only a utilized transaction with at least one utilization by your department within 30 days of completion is eligible for Utilization Reversal. Check Utilization History for details.";
        } else {
            ngLogger.consoleLog(workitemDetails.getInstrument().getProcessInstanceId() + " transaction is eligible for utilization reversal");
        }

        return errorMessage;
    }

    private String buildDateTime(CallbackAmendmentHistoryDTO callbackAmendmentHistory) {
        String formattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(callbackAmendmentHistory.getConfirmedDate());

        return formattedDate + " " + callbackAmendmentHistory.getConfirmedTime().substring(0, 2) + ":" +
                callbackAmendmentHistory.getConfirmedTime().substring(2, 4) + ":" +
                callbackAmendmentHistory.getConfirmedTime().substring(4, 6);
    }

    private void addCallbackActionHistory(String username, String itemIndex) {
        CallbackActionHistoryDTO callbackActionHistoryDTO = new CallbackActionHistoryDTO();
        callbackActionHistoryDTO.setAction("Callback Amendment Requested");
        callbackActionHistoryDTO.setItemIndex(itemIndex);
        callbackActionHistoryDTO.setActionUser(username);

        try {
            Boolean isActionHistoryInserted = tableDAO.insertCallbackActionHistory(callbackActionHistoryDTO);
            if (Boolean.TRUE.equals(isActionHistoryInserted)) {
                ngLogger.consoleLog("Successfully inserted entry to callback action history table");
            } else {
                ngLogger.errorLog("Unable to insert callback action history table");
            }
        } catch (DAOException e) {
            ngLogger.errorLog("Error occurred while inserting record in callback action history table, error: " + e.getMessage());
        }
    }

    private String buildCcyAmount(String currencyCode, BigDecimal amount) {
        Map<String, Integer> currencyConfigMap = tableDAO.getCurrencyConfig();

        String finalCcyAmount = amount.toString();
        if (!StringUtils.isEmpty(currencyCode) && !StringUtils.isEmpty(amount)) {
            finalCcyAmount = String.format(String.format("%%.%df %%s", currencyConfigMap.get(currencyCode)), amount, currencyCode);
        }

        return finalCcyAmount;
    }
}
