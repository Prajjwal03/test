package com.smbc.epix.transaction.services.dao;

import java.util.List;
import java.util.Map;

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
import org.springframework.cache.annotation.Cacheable;

public interface TableDAO {

    List<ListQueueParamsDTO> getListQueueParams();

    List<Map> getListQueueAccess(DynamicTable dynamicQuery);

    List<Map> getDropDownData(DynamicTable dynamicQuery);

    List<Map> getDropDownFieldsForTransactionType(DynamicTable dynamicQuery);

    List<Map> getCaseStatus(DynamicTable dynamicQuery);

    List<Map> getToDoTableFields(DynamicTable dynamicQuery);

    List<Map> getToDoColumnTableFields(DynamicTable dynamicQuery);

    List<Map> getEnquiryTableFields(DynamicTable dynamicQuery);

    List<Map> getEnquiryColumnTableFields(DynamicTable dynamicQuery);

    List<UserAccessMapDTO> getUserAccessMap(DynamicTable dynamicQuery);

    List<Map> getBranch(int userIndex);

    List<Map> getProduct(DynamicTable dynamicQuery);

    List<Map> getEnquirySearchFilter(DynamicTable dynamicQuery);

    List<String> getUserRoleName(int userIndex);

    List<String> getUserCategory(DynamicTable dynamicQuery);

    List<UserRoleDTO> getUserRoleDetails(int userIndex);

    List<UserDTO> getUserName(int userIndex);

    List<Map> getSessionStatus(DynamicTable dynamicQuery);

    List<Map> getCommentHistory(String transactionID);

    List<ListApplicationDTO> getApplication();

    List<String> getExtTableDetails(String product);

    List<Map> getIformDetails(String product);

    List<VersionHistoryDTO> getVersionHistory(DynamicTable versionQuery);

    List<Map> getCustomDashboardDataSubProduct(DynamicTable dashboardQuery);

    List<CustomDashboardDataCurrency> getCustomDashboardDataCurrency(DynamicTable dashboardQuery1);

    List<Map> getCommentHistoryDfx(String transactionId);

    List<SwapHistory> getSwapHistory(DynamicTable versionQuery);

    List<String> getDeptCode(int userIndex);

    List<String> getUserRoleNameDfx(int userIndex);

    CallbackStatus getCallbackStatus(String pid);

    Boolean insertCallbackActionHistory(CallbackActionHistoryDTO callbackActionHistoryDTO);

    List<CallbackAmendmentHistoryDTO> getCallbackAmendmentHistory(String pid);

    List<UtilizationHistoryDTO> getUtilizationHistory(String pid);

    List<String> getTxnSubType();

    List<String> getTransactionStatus();

    List<String> getFoxTransactionStatus();

    @Cacheable(value = "currencyConfig", unless = "#result==null or #result.isEmpty()")
    Map<String, Integer> getCurrencyConfig();

    List<String> getDepartmentsForUtilizationHistory();

    Long getSettledUtilizationCount(String pid);

    Long getApprovedCallbackCount(String pid);
}
