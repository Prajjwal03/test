package com.smbc.epix.transaction.services.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.TableDAO;
import com.smbc.epix.transaction.services.dto.table.CallbackActionHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.CallbackAmendmentHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.CallbackStatus;
import com.smbc.epix.transaction.services.dto.table.CustomDashboardDataCurrency;
import com.smbc.epix.transaction.services.dto.table.ListApplicationDTO;
import com.smbc.epix.transaction.services.dto.table.ListQueueParamsDTO;
import com.smbc.epix.transaction.services.dto.table.SwapHistory;
import com.smbc.epix.transaction.services.dto.table.UserAccessMapDTO;
import com.smbc.epix.transaction.services.dto.table.UserDTO;
import com.smbc.epix.transaction.services.dto.table.UserRoleDTO;
import com.smbc.epix.transaction.services.dto.table.UtilizationHistoryDTO;
import com.smbc.epix.transaction.services.dto.table.VersionHistoryDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;
import org.springframework.stereotype.Repository;

@Repository
public class TableDAOImpl extends BaseDAO implements TableDAO {

    @Override
    public List<ListQueueParamsDTO> getListQueueParams() {
        return selectList("TableDetails.getListQueueParamsTableDetails");
    }

    @Override
    public List<Map> getListQueueAccess(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getListQueueAccessTableDetails", dynamicQuery);
    }

    @Override
    public List<Map> getToDoTableFields(DynamicTable dynamicQuery) {

        return selectList("TableDetails.getToDoSearchField", dynamicQuery);
    }

    @Override
    public List<Map> getToDoColumnTableFields(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getToDoColumnField", dynamicQuery);
    }

    @Override
    public List<Map> getEnquiryTableFields(DynamicTable dynamicQuery) {

        return selectList("TableDetails.getEnquirySearchField", dynamicQuery);
    }

    @Override
    public List<Map> getEnquiryColumnTableFields(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getEnquiryColumnField", dynamicQuery);
    }

    @Override
    public List<Map> getDropDownData(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getDropDownData", dynamicQuery);
    }

    @Override
    public List<UserAccessMapDTO> getUserAccessMap(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getUserAccessMap", dynamicQuery);
    }

    @Override
    public List<Map> getBranch(int userIndex) {
        return selectList("TableDetails.getBranch", userIndex);
    }

    @Override
    public List<Map> getProduct(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getProduct", dynamicQuery);
    }

    @Override
    public List<Map> getEnquirySearchFilter(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getEnquiryColumnFields", dynamicQuery);
    }

    @Override
    public List<Map> getDropDownFieldsForTransactionType(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getDropDownDataForTransactionType", dynamicQuery);
    }

    @Override
    public List<Map> getCaseStatus(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getCaseStatus", dynamicQuery);
    }

    @Override
    public List<String> getUserRoleName(int userIndex) {
        return selectStringList("TableDetails.getUserRoleName", userIndex);
    }

    @Override
    public List<UserDTO> getUserName(int userIndex) {
        return selectList("TableDetails.getUserName", userIndex);
    }

    @Override
    public List<String> getUserCategory(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getUserCategory", dynamicQuery);
    }

    @Override
    public List<UserRoleDTO> getUserRoleDetails(int userIndex) {
        return selectList("TableDetails.getUserRoleDetails", userIndex);
    }

    @Override
    public List<Map> getSessionStatus(DynamicTable dynamicQuery) {
        return selectList("TableDetails.getSessionStatus", dynamicQuery);
    }

    @Override
    public List<Map> getCommentHistory(String transactionID) {
        return selectList("TableDetails.getCommentHistory", transactionID);
    }

    @Override
    public List<ListApplicationDTO> getApplication() {
        return selectList("TableDetails.getListApplicationDetails");
    }

    @Override
    public List<String> getExtTableDetails(String product) {
        return selectStringList("TableDetails.getExtTableDetails", product);
    }

    @Override
    public List<Map> getIformDetails(String productCode) {
        return selectList("TableDetails.getIformDetails", productCode);
    }

    @Override
    public List<VersionHistoryDTO> getVersionHistory(DynamicTable versionQuery) {
        return selectList("TableDetails.getVersionHistory", versionQuery);
    }

    //Added by Richa 17-Jun-2021 for Custom Dashboard
    @Override
    public List<Map> getCustomDashboardDataSubProduct(DynamicTable dashboardQuery) {
        return selectList("TableDetails.getCustomDashboardData", dashboardQuery);
    }

    @Override
    public List<CustomDashboardDataCurrency> getCustomDashboardDataCurrency(DynamicTable dashboardQuery1) {
        return selectList("TableDetails.getCustomDashboardDataCurrency", dashboardQuery1);
    }

    @Override
    public List<Map> getCommentHistoryDfx(String transactionId) {
        return selectList("TableDetails.getCommentHistoryDfx", transactionId);
    }

    @Override
    public List<SwapHistory> getSwapHistory(DynamicTable versionQuery) {
        return selectList("TableDetails.getSwapHistory", versionQuery);
    }

    @Override
    public List<String> getDeptCode(int userIndex) {
        return selectStringList("TableDetails.getDeptCode", userIndex);
    }

    @Override
    public List<String> getUserRoleNameDfx(int userIndex) {
        return selectStringList("TableDetails.getUserRoleNameDfx", userIndex);
    }

    @Override
    public CallbackStatus getCallbackStatus(String pid) {
        return (CallbackStatus) selectOne("TableDetails.getCallbackStatus", pid);
    }

    @Override
    public Boolean insertCallbackActionHistory(CallbackActionHistoryDTO callbackActionHistoryDTO) {
        return insert("TableDetails.insertCallbackActionHistory", callbackActionHistoryDTO);
    }

    @Override
    public List<CallbackAmendmentHistoryDTO> getCallbackAmendmentHistory(String pid) {
        return selectList("TableDetails.getCallbackAmendmentHistory", pid);
    }

    @Override
    public List<UtilizationHistoryDTO> getUtilizationHistory(String pid) {
        return selectList("TableDetails.getUtilizationHistory", pid);
    }

    @Override
    public List<String> getTxnSubType() {
        return selectList("TableDetails.getTxnSubType");
    }

    @Override
    public List<String> getTransactionStatus() {
        return selectList("TableDetails.getTransactionStatus");
    }

    @Override
    public List<String> getFoxTransactionStatus() {
        return selectList("TableDetails.getFoxTransactionStatus");
    }

    @Override
    public Map<String, Integer> getCurrencyConfig() {
        Map<String, Integer> currencyConfigMap = new HashMap<>();

        List<Map<String, String>> currencyConfig = selectList("TableDetails.getCurrencyConfig");
        for (Map<String, String> currencyMap : currencyConfig) {
            currencyConfigMap.put(currencyMap.get("Currency_Code"), Integer.parseInt(currencyMap.get("Decimal_Places")));
        }

        return currencyConfigMap;
    }

    @Override
    public List<String> getDepartmentsForUtilizationHistory() {
        return selectList("TableDetails.getDepartmentsForUtilizationHistory");
    }

    @Override
    public Long getSettledUtilizationCount(String pid) {
        return count("TableDetails.getSettledUtilizationCount", pid);
    }

    @Override
    public Long getApprovedCallbackCount(String pid) {
        return count("TableDetails.getApprovedCallbackCount", pid);
    }
}
