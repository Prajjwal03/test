package com.smbc.epix.transaction.services.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.smbc.epix.transaction.services.dao.DashBoardDAO;
import com.smbc.epix.transaction.services.dao.TableDAO;
import com.smbc.epix.transaction.services.dto.table.ToDOTableDetailsDTO;
import com.smbc.epix.transaction.services.dto.table.UserAccessMapDTO;
import com.smbc.epix.transaction.services.model.CommonToDO;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.DashBoardService;
import com.smbc.epix.transaction.services.service.TableService;
import com.smbc.epix.transaction.services.utils.Constants;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private DashBoardDAO dashboardDAO;

    @Autowired
    private TableService tableService;

    @Override
    public ToDOTableDetailsDTO getMyWatchList(DynamicTable dynamicQuery) throws JSONException {
        List<String> tables = new ArrayList<>();
        List<String> joins = new ArrayList<>();
        String product = "";
        int count = 0;
        StringBuilder selectedColumn = new StringBuilder();
        String tableName;
        List<String> searchWhereClause = new ArrayList<>();
        JSONObject searchDetails = dynamicQuery.getSearchDetails();
        CommonToDO commonToDO = new CommonToDO();
        List<Map> todoField = tableService.getToDoTableFields(dynamicQuery);
        List<Map> todoColumnField = tableService.getToDoColumnTableFields(dynamicQuery);

        Iterator keys = searchDetails.keys();
        while (keys.hasNext()) {
            String currentDynamicKey = (String) keys.next();
            String currentValue = dynamicQuery.getSearchDetails().getString(currentDynamicKey);

            if (currentDynamicKey.equalsIgnoreCase(Constants.BRANCH_CODE_COLUMN)) {
                commonToDO.setBranch("'" + currentValue + "'");
                selectedColumn.append(currentDynamicKey).append(",");
                searchWhereClause.add(" BranchCode='" + currentValue + "' ");
            }
            if (currentDynamicKey.equalsIgnoreCase(Constants.PRODUCT_CODE_COLUMN)) {
                product = currentValue;
                commonToDO.setProduct("'" + currentValue + "'");
                selectedColumn.append("BusinessCategoryCode,");
                searchWhereClause.add(" BusinessCategoryCode='" + currentValue + "' ");
            }
            for (Map map : todoField) {
                String fieldNameTemp = (String) map.get(Constants.FIELD_NAME_ALIAS_COLUMN);
                fieldNameTemp = fieldNameTemp.replaceAll("\\s+", "");
                fieldNameTemp = fieldNameTemp.replace(",'',", ",' ',");
                if (currentDynamicKey.equalsIgnoreCase((String) map.get(Constants.FIELD_NAME_COLUMN))) {
                    if (!selectedColumn.toString().contains((String) map.get(Constants.FIELD_NAME_COLUMN))) {
                        selectedColumn.append(currentDynamicKey).append(",");
                    }
                    String fieldType = (String) map.get(Constants.FIELD_TYPE_COLUMN);
                    if (fieldType.equalsIgnoreCase(Constants.MULTI_FIELD_TYPE)) {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("','", "'", "'"));
                        searchWhereClause.add(" " + fieldNameTemp + " in (" + multiSelectValue + ") ");
                    } else if (fieldType.equalsIgnoreCase(Constants.DROPDOWN_FIELD_TYPE)) {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        String fieldNamePrefix = fieldNameTemp + " = '";
                        // multiple OR conditions
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("' OR " + fieldNamePrefix, fieldNamePrefix, "' "));
                        searchWhereClause.add(multiSelectValue);
                    } else if (fieldType.equalsIgnoreCase(Constants.DATE_FIELD_TYPE)) {
                        searchWhereClause.add(" " + currentValue + " ");
                    } else {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        List<String> al = Arrays.asList(str);

                        StringBuilder multiSelectValue = new StringBuilder();
                        if (al.size() == 1) {
                            if (product.equalsIgnoreCase("tf")) {
                                if (fieldNameTemp.equalsIgnoreCase("T24ReferenceNo") || fieldNameTemp.equalsIgnoreCase("RelatedT24RefNo")) {
                                    ngLogger.consoleLog("search key " + fieldNameTemp + al.size());
                                    ngLogger.consoleLog("seasrch q " + multiSelectValue + "T24ReferenceNo LIKE '" + al.get(0) + "%' OR RelatedT24RefNo LIKE '" + al.get(0) + "% OR ");
                                    multiSelectValue.append("T24ReferenceNo LIKE '").append(al.get(0)).append("%' OR RelatedT24RefNo LIKE '").append(al.get(0)).append("%' OR ");
                                } else {
                                    multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(al.get(0)).append("%' OR ");
                                }
                            } else {
                                multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(al.get(0)).append("%' OR ");
                            }
                        } else if (al.size() > 1) {
                            for (String s : al) {
                                multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(s).append("%' OR ");
                            }
                        } else {
                            ngLogger.consoleLog("Array size <=0");
                        }
                        String multiSelect = multiSelectValue.toString();
                        multiSelect = "(" + multiSelect.substring(0, multiSelect.length() - 3) + ")";
                        ngLogger.consoleLog("muliselect Value " + multiSelect);
                        searchWhereClause.add(multiSelect);
                    }
                }
            }
        }

        List<String> extTable = tableDAO.getExtTableDetails(product);
        String extTableName = "";
        if (!CollectionUtils.isEmpty(extTable)) {
            extTableName = extTable.get(0);
        }
        ngLogger.consoleLog("Ext_TableName " + extTableName);

        StringBuilder join;
        //Modified by Sai 04 May 2021 for the dynamicjoin
        for (Map value : todoColumnField) {
            if (!selectedColumn.toString().contains((String) value.get(Constants.FIELD_NAME_COLUMN))) {

                if (value.get(Constants.TABLE_NAME_COLUMN) != null) {
                    selectedColumn.append(value.get(Constants.TABLE_NAME_COLUMN)).append(".").append(value.get(Constants.FIELD_NAME_COLUMN)).append(",");
                } else {
                    selectedColumn.append(value.get(Constants.FIELD_NAME_COLUMN)).append(",");
                }
            }

            if (value.get("TableToJoin") != null) {
                tableName = value.get(Constants.TABLE_NAME_COLUMN).toString();

                join = new StringBuilder();
                if (value.get("TableToJoin").equals(extTableName)) {

                    if (count == 0) {
                        join.append(" ").append(value.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".")
                                .append(value.get("ColumnToJoin")).append("= ").append(value.get(Constants.TABLE_NAME_COLUMN)).append(".")
                                .append(value.get("ColumnToJoin"));
                        count++;
                        joins.add(join.toString());
                        tables.add(tableName);
                    } else {
                        for (int k = 0; k < tables.size(); k++) {
                            if (!tables.get(k).equalsIgnoreCase(value.get(Constants.TABLE_NAME_COLUMN).toString())) {
                                join.append(" ").append(value.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".")
                                        .append(value.get("ColumnToJoin")).append("= ").append(value.get(Constants.TABLE_NAME_COLUMN)).append(".")
                                        .append(value.get("ColumnToJoin"));
                                count++;
                                joins.add(join.toString());
                                tables.add(tableName);
                            }
                        }
                    }
                }
            }
        }

        for (Map map : todoField) {
            if (map.get("TableToJoin") != null) {
                tableName = map.get(Constants.TABLE_NAME_COLUMN).toString();
                join = new StringBuilder();

                if (map.get("TableToJoin").equals(extTableName)) {
                    if (count == 0) {
                        join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".")
                                .append(map.get("ColumnToJoin")).append("= ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(".").append(map.get("ColumnToJoin"));
                        count++;
                        joins.add(join.toString());
                        tables.add(tableName);
                    } else {
                        for (int k = 0; k < tables.size(); k++) {
                            if (!tables.get(k).equalsIgnoreCase(map.get(Constants.TABLE_NAME_COLUMN).toString())) {
                                join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".")
                                        .append(map.get("ColumnToJoin")).append("= ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(".").append(map.get("ColumnToJoin"));
                                count++;
                                joins.add(join.toString());
                                tables.add(tableName);
                            }
                        }
                    }
                }
            }
        }

        //Added by Sai to resolve the Search with Amount
        if (extTableName.equals(Constants.DFX_EXTERNAL_TABLE)) {
            if (!selectedColumn.toString().contains("DFX_T_EXT.Buy_Side_Currency") && !selectedColumn.toString().contains("Buy_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Buy_Side_Currency ");
            }
            if (!selectedColumn.toString().contains("DFX_T_EXT.Sell_Side_Currency") && !selectedColumn.toString().contains("Sell_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Sell_Side_Currency ");
            }
        }
        String dynamicSelect = selectedColumn.toString();
        dynamicSelect = dynamicSelect.substring(0, dynamicSelect.length() - 1);
        dynamicSelect = dynamicSelect.replaceAll("\\s+", "").replace(",'',", ",' ',");
        ngLogger.consoleLog("WatchList selected columns " + dynamicSelect);
        commonToDO.setDynamicSelect(dynamicSelect);
        commonToDO.setDynamicJoin(joins);
        commonToDO.setPageNo(dynamicQuery.getPageNo());
        commonToDO.setPageSize(dynamicQuery.getPageSize());
        commonToDO.setCount(dynamicQuery.getCount());
        commonToDO.setDynamicSearchWhere(searchWhereClause);
        commonToDO.setSortBy(dynamicQuery.getSortBy());
        commonToDO.setOrderBy(dynamicQuery.getOrderBy());
        commonToDO.setSearchParam(dynamicQuery.getPassingValue1());
        commonToDO.setExtTableName(extTableName);
        return dashboardDAO.getMyWatchListDetails(commonToDO);
    }

    @Override
    public ToDOTableDetailsDTO getDashBoardDetailsCommon(DynamicTable dynamicQuery) throws JSONException {
        String branchCode = "";
        String productCode = "";
        String branch = "";
        String product = "";
        StringBuilder selectedColumn = new StringBuilder();
        List<String> searchWhereClauses = new ArrayList<>();

        List<Map> todoField = tableService.getToDoTableFields(dynamicQuery);
        Iterator keys = dynamicQuery.getSearchDetails().keys();

        while (keys.hasNext()) {
            String currentDynamicKey = (String) keys.next();
            String currentValue = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
            if (currentDynamicKey.equalsIgnoreCase(Constants.BRANCH_CODE_COLUMN)) {
                branch = currentValue;
                selectedColumn.append(currentDynamicKey).append(",");
                searchWhereClauses.add(" BranchCode='" + currentValue + "' ");
                branchCode = currentValue;
            }
            if (currentDynamicKey.equalsIgnoreCase(Constants.PRODUCT_CODE_COLUMN)) {
                selectedColumn.append("BusinessCategoryCode,");
                searchWhereClauses.add(" BusinessCategoryCode='" + currentValue + "' ");
                product = currentValue;
                productCode = currentValue;
            }
            for (Map map : todoField) {
                ngLogger.consoleLog("inside alias" + map.get(Constants.FIELD_NAME_ALIAS_COLUMN));
                String fieldNameTemp = (String) map.get(Constants.FIELD_NAME_ALIAS_COLUMN);
                fieldNameTemp = fieldNameTemp.replaceAll("\\s+", "");
                fieldNameTemp = fieldNameTemp.replace(",'',", ",' ',");
                if (currentDynamicKey.equalsIgnoreCase((String) map.get(Constants.FIELD_NAME_COLUMN))) {

                    if (!selectedColumn.toString().contains((String) map.get(Constants.FIELD_NAME_COLUMN))) {
                        if (currentDynamicKey.equalsIgnoreCase("CreatedDateTime")) {
                            selectedColumn.append("WFINSTRUMENTTABLE.").append(currentDynamicKey).append(",");
                        } else {
                            selectedColumn.append(currentDynamicKey).append(",");
                        }
                    }

                    String fieldType = (String) map.get("FieldType");
                    if (fieldType.equalsIgnoreCase(Constants.MULTI_FIELD_TYPE)) {

                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("','", "'", "'"));
                        searchWhereClauses.add(" " + fieldNameTemp + " in (" + multiSelectValue + ") ");
                    } else if (fieldType.equalsIgnoreCase(Constants.DROPDOWN_FIELD_TYPE)) {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        String fieldNamePrefix = fieldNameTemp + " = '";
                        // multiple OR conditions
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("' OR " + fieldNamePrefix, fieldNamePrefix, "' "));
                        searchWhereClauses.add(multiSelectValue);
                    } else if (fieldType.equalsIgnoreCase(Constants.DATE_FIELD_TYPE)) {
                        if (dynamicQuery.getSearchDetails().getString(Constants.BRANCH_CODE_COLUMN).equalsIgnoreCase("BKK") &&
                                currentDynamicKey.equalsIgnoreCase("CreatedDateTime")) {

                            searchWhereClauses.add(" WFINSTRUMENTTABLE." + currentValue + " ");
                        } else {
                            searchWhereClauses.add(" " + currentValue + " ");
                        }
                        ngLogger.consoleLog("***Dashboard searchWhereClause:" + searchWhereClauses);
                    } else {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "");
                        list = list.replace("[", "");
                        list = list.replaceAll("\"", "");
                        String[] str = list.split(",");
                        List<String> al = Arrays.asList(str);

                        StringBuilder multiSelectValue = new StringBuilder();
                        if (al.size() == 1) {
                            if (productCode.equalsIgnoreCase("tf")) {
                                if (fieldNameTemp.equalsIgnoreCase("T24ReferenceNo") || fieldNameTemp.equalsIgnoreCase("RelatedT24RefNo")) {
                                    ngLogger.consoleLog("search key " + fieldNameTemp + al.size());
                                    ngLogger.consoleLog("seasrch q " + multiSelectValue + "T24ReferenceNo LIKE '" + al.get(0) + "%' OR RelatedT24RefNo LIKE '" + al.get(0) + "% OR ");
                                    multiSelectValue.append("T24ReferenceNo LIKE '").append(al.get(0)).append("%' OR RelatedT24RefNo LIKE '").append(al.get(0)).append("%' OR ");
                                } else {
                                    multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(al.get(0)).append("%' OR ");
                                }
                            } else {
                                multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(al.get(0)).append("%' OR ");
                            }
                        } else if (al.size() > 1) {
                            for (String s : al) {
                                multiSelectValue.append(fieldNameTemp).append(" LIKE '").append(s).append("%' OR ");
                            }
                        } else {
                            ngLogger.consoleLog("Array size <=0");
                        }
                        String searchWhereClause = multiSelectValue.toString();
                        searchWhereClause = "(" + searchWhereClause.substring(0, searchWhereClause.length() - 3) + ")";
                        ngLogger.consoleLog("insise single element " + searchWhereClause);
                        searchWhereClauses.add(searchWhereClause);
                    }
                }
            }
        }

        List<String> extTable = tableDAO.getExtTableDetails(product);
        String extTableName = "";
        if (!CollectionUtils.isEmpty(extTable)) {
            extTableName = extTable.get(0);
        }
        ngLogger.consoleLog("Ext_TableName " + extTableName);

        String tableName;
        int count = 0;
        List<String> tables = new ArrayList<>();
        List<String> joins = new ArrayList<>();
        List<Map> todoColumnField = tableService.getToDoColumnTableFields(dynamicQuery);
        for (Map map : todoColumnField) {
            if (!selectedColumn.toString().contains((String) map.get(Constants.FIELD_NAME_COLUMN))) {
                if (map.get(Constants.TABLE_NAME_COLUMN) != null) {
                    selectedColumn.append(map.get(Constants.TABLE_NAME_COLUMN)).append(".").append(map.get(Constants.FIELD_NAME_COLUMN)).append(",");
                } else {
                    selectedColumn.append(map.get(Constants.FIELD_NAME_COLUMN)).append(",");
                }
            }

            if (map.get("TableToJoin") != null) {
                tableName = map.get(Constants.TABLE_NAME_COLUMN).toString();

                StringBuilder join = new StringBuilder();
                if (map.get("TableToJoin").equals(extTableName)) {
                    if (count == 0) {
                        join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".").append(map.get("ColumnToJoin")).append("= ").append(map.get("TableName")).append(".").append(map.get("ColumnToJoin"));
                        count++;
                        joins.add(join.toString());
                        tables.add(tableName);
                    } else {
                        for (int k = 0; k < tables.size(); k++) {
                            if (!tables.get(k).equalsIgnoreCase(map.get(Constants.TABLE_NAME_COLUMN).toString())) {

                                join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".").append(map.get("ColumnToJoin")).append("= ").append(map.get("TableName")).append(".").append(map.get("ColumnToJoin"));
                                count++;
                                joins.add(join.toString());
                                tables.add(tableName);
                            }
                        }
                    }
                }
            }
        }

        for (Map map : todoField) {
            if (map.get("TableToJoin") != null) {
                tableName = map.get(Constants.TABLE_NAME_COLUMN).toString();

                StringBuilder join = new StringBuilder();
                if (map.get("TableToJoin").equals(extTableName)) {
                    if (count == 0) {
                        join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".").append(map.get("ColumnToJoin")).append("= ").append(map.get("TableName")).append(".").append(map.get("ColumnToJoin"));
                        count++;
                        joins.add(join.toString());
                        tables.add(tableName);
                    } else {
                        for (int k = 0; k < tables.size(); k++) {
                            if (!tables.get(k).equalsIgnoreCase(map.get(Constants.TABLE_NAME_COLUMN).toString())) {
                                join.append(" ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(" (nolock) on ").append(extTableName).append(".").append(map.get("ColumnToJoin")).append("= ").append(map.get("TableName")).append(".").append(map.get("ColumnToJoin"));
                                count++;
                                joins.add(join.toString());
                                tables.add(tableName);
                            }
                        }
                    }
                }
            }
        }

        //Added by Sai to resolve the Search with Amount
        if (extTableName.equals(Constants.DFX_EXTERNAL_TABLE)) {
            if (!selectedColumn.toString().contains("DFX_T_EXT.Buy_Side_Currency") && !selectedColumn.toString().contains("Buy_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Buy_Side_Currency ");
            }
            if (!selectedColumn.toString().contains("DFX_T_EXT.Sell_Side_Currency") && !selectedColumn.toString().contains("Sell_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Sell_Side_Currency ");
            }
        }

        String dynamicDashboadQuery = selectedColumn.toString();
        dynamicDashboadQuery = dynamicDashboadQuery.substring(0, dynamicDashboadQuery.length() - 1);
        dynamicDashboadQuery = dynamicDashboadQuery.replaceAll("\\s+", "").replace(",'',", ",' ',");
        ngLogger.consoleLog("Selected Column " + dynamicDashboadQuery);
        dynamicQuery.setDynamicDashBoadQuery(dynamicDashboadQuery);

        //New Param "ProductCode" added to resolve Cross Application Dual Role Issue
        List<Map> columnToSearch = tableService.getListQueueAccessTemp(dynamicQuery.getPassingValue1(), dynamicQuery.getSearchString3(), productCode);

        StringBuilder whereClauseColumnCase;
        List<String> whereClause = new ArrayList<>();
        if (extTableName.equals(Constants.TREASURY_EXTERNAL_TABLE)) {
            for (Map toSearch : columnToSearch) {

                if (toSearch.get(Constants.CREATED_BY_COLUMN).equals("Y") && (!toSearch.get(Constants.ACTIVITY_ID_COLUMN).equals(99))) {
                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" createdBy=").append(dynamicQuery.getPassingValue1()).append(" And Q_UserId=")
                            .append(dynamicQuery.getPassingValue1()).append(" And ActivityID=").append(toSearch.get(Constants.ACTIVITY_ID_COLUMN))
                            .append(" And LockStatus='Y' And BranchCode='").append(branch).append("' And BusinessCategoryCode='")
                            .append(product).append("'");
                    whereClause.add(whereClauseColumnCase.toString());
                }

                if (toSearch.get(Constants.Q_USER_ID_COLUMN).equals("Y") && toSearch.get(Constants.LOCK_STATUS_COLUMN).equals("Y") && (toSearch.get(Constants.ACTIVITY_ID_COLUMN).equals(99))) {
                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" Q_UserId=").append(dynamicQuery.getPassingValue1()).append(" And LockStatus='Y' And BranchCode='")
                            .append(branch).append("' And BusinessCategoryCode='").append(product).append("'");
                    whereClause.add(whereClauseColumnCase.toString());
                }

                if (toSearch.get(Constants.ROLE_COLUMN).equals("Y") && (!toSearch.get(Constants.ACTIVITY_ID_COLUMN).equals(99))) {
                    List<UserAccessMapDTO> uamdtos = tableDAO.getUserAccessMap(dynamicQuery);
                    String inValues = uamdtos.stream().map(UserAccessMapDTO::getTxnTypeCode).collect(Collectors.joining("', '", " '", "'"));

                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" TR_T_EXT.BranchCode='").append(branchCode).append("' AND TR_T_EXT.BusinessCategoryCode='")
                            .append(productCode).append("' AND TR_T_EXT.TransactionTypeCode in (").append(inValues).append(") And TR_T_EXT.Role='")
                            .append(toSearch.get("RoleID")).append("'");
                    whereClause.add(whereClauseColumnCase.toString());
                }

                if (toSearch.get(Constants.SYS_GENERATED_COLUMN).equals("Y") && !toSearch.get(Constants.CREATED_BY_ID_COLUMN).equals("0")) {
                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" WFINSTRUMENTTABLE.Createdby=").append(toSearch.get(Constants.CREATED_BY_ID_COLUMN)).append(" And TR_T_EXT.Role='")
                            .append(toSearch.get("RoleID")).append("'");
                    whereClause.add(whereClauseColumnCase.toString());
                }
            }
        } else if (extTableName.equals(Constants.DFX_EXTERNAL_TABLE)) {
            for (Map toSearch : columnToSearch) {
                if (toSearch.get(Constants.Q_USER_ID_COLUMN).equals("Y") && (!toSearch.get(Constants.ACTIVITY_ID_COLUMN).equals(99))) {
                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" Q_UserId=").append(dynamicQuery.getPassingValue1()).append(" And ActivityID=")
                            .append(toSearch.get(Constants.ACTIVITY_ID_COLUMN)).append(" And LockStatus='Y' And BranchCode='").append(branch)
                            .append("' And BusinessCategoryCode='").append(product).append("'");

                    whereClause.add(whereClauseColumnCase.toString());
                }

                if (toSearch.get(Constants.ROLE_COLUMN).equals("Y") && (!toSearch.get(Constants.ACTIVITY_ID_COLUMN).equals(99))) {
                    List<UserAccessMapDTO> uamdtos = tableDAO.getUserAccessMap(dynamicQuery);
                    String userAccessString = uamdtos.stream().map(UserAccessMapDTO::getTxnTypeCode).collect(Collectors.joining("', '", " '", "'"));

                    whereClauseColumnCase = new StringBuilder();
                    whereClauseColumnCase.append(" DFX_T_EXT.BranchCode='").append(branchCode).append("' AND DFX_T_EXT.BusinessCategoryCode='")
                            .append(productCode).append("' AND DFX_T_EXT.TransactionTypeCode in (")
                            .append(userAccessString).append(") AND WFINSTRUMENTTABLE.ActivityID=").append(toSearch.get(Constants.ACTIVITY_ID_COLUMN));
                    whereClause.add(whereClauseColumnCase.toString());
                }
            }
        } else {
            ngLogger.errorLog("Invalid External Table name :" + extTableName);
        }

        if (extTableName.equals(Constants.TREASURY_EXTERNAL_TABLE) && (dynamicQuery.getSearchString3().equalsIgnoreCase("'My Team ToDo'"))) {
            searchWhereClauses.add("ActivityName NOT IN ('Complete','Discard','Exit')");
        }

        String deptCode = "";
        if (extTableName.equals(Constants.DFX_EXTERNAL_TABLE)) {
            boolean isOperationChecker = false;
            List<String> roleNameList = tableDAO.getUserRoleNameDfx(dynamicQuery.getPassingValue1());
            for (String roleName : roleNameList) {
                if (Constants.OPERATION_CHECKER_ACTIVITY.equalsIgnoreCase(roleName)) {
                    isOperationChecker = true;
                    break;
                }
            }
            if (isOperationChecker) {
                List<String> deptCodeList = tableDAO.getDeptCode(dynamicQuery.getPassingValue1());
                if (!CollectionUtils.isEmpty(deptCodeList)) {
                    deptCode = deptCodeList.get(0);
                    searchWhereClauses.add(" DeptCode LIKE '%," + deptCode + ",%' ");
                }
                ngLogger.consoleLog("Department: " + deptCode);
            }
        }

        ngLogger.consoleLog("Total Where Clause " + whereClause);
        ngLogger.consoleLog("joins " + joins);
        ngLogger.consoleLog("Search Where Clause  " + searchWhereClauses);
        CommonToDO commonToDO = new CommonToDO();
        commonToDO.setDynamicSelect(dynamicQuery.getDynamicDashBoadQuery());
        commonToDO.setDynamicJoin(joins);
        commonToDO.setDynamicWhere(whereClause);
        commonToDO.setPageNo(dynamicQuery.getPageNo());
        commonToDO.setPageSize(dynamicQuery.getPageSize());
        commonToDO.setCount(dynamicQuery.getCount());
        commonToDO.setDynamicSearchWhere(searchWhereClauses);
        commonToDO.setSortBy(dynamicQuery.getSortBy());
        commonToDO.setOrderBy(dynamicQuery.getOrderBy());
        commonToDO.setExtTableName(extTableName);
        commonToDO.setDeptCode(deptCode);
        return dashboardDAO.getCommonToDoDetails(commonToDO);
    }
}
