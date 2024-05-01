package com.smbc.epix.transaction.services.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.smbc.epix.transaction.services.dao.CommonEnquiryDAO;
import com.smbc.epix.transaction.services.dao.TableDAO;
import com.smbc.epix.transaction.services.dto.table.CommonEnquiryDetailsDTO;
import com.smbc.epix.transaction.services.model.CommonEnquiry;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.CommonEnquiryService;
import com.smbc.epix.transaction.services.service.TableService;
import com.smbc.epix.transaction.services.utils.Constants;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CommonEnquiryServiceImpl implements CommonEnquiryService {

    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private CommonEnquiryDAO cedao;

    @Autowired
    private TableService tableService;

    @Override
    public CommonEnquiryDetailsDTO getEnquiryData(DynamicTable dynamicQuery) throws JSONException {
        List<String> tables = new ArrayList<>();
        List<String> joins = new ArrayList<>();
        int count = 0;
        String product = "";
        String tableName = "";
        StringBuilder selectedColumn = new StringBuilder();
        String extTableName = "";
        JSONObject searchDetails = dynamicQuery.getSearchDetails();
        CommonEnquiry ce = new CommonEnquiry();
        List<String> searchWhereClauses = new ArrayList<>();
        List<Map> todoField = tableService.getEnquiryTableFields(dynamicQuery);
        List<Map> todoColumnField = tableService.getEnquiryColumnTableFields(dynamicQuery);
        Iterator keys = searchDetails.keys();
        while (keys.hasNext()) {
            String currentDynamicKey = (String) keys.next();
            String currentValue = dynamicQuery.getSearchDetails().getString(currentDynamicKey);

            if (currentDynamicKey.equalsIgnoreCase(Constants.BRANCH_CODE_COLUMN)) {
                ce.setBranch("'" + currentValue + "'");
                selectedColumn.append(currentDynamicKey).append(",");
                searchWhereClauses.add(" BranchCode='" + currentValue + "' ");
            }
            if (currentDynamicKey.equalsIgnoreCase(Constants.PRODUCT_CODE_COLUMN)) {
                product = currentValue;
                ce.setProduct("'" + currentValue + "'");
                selectedColumn.append("BusinessCategoryCode").append(",");
                searchWhereClauses.add(" BusinessCategoryCode='" + currentValue + "' ");
            }

            for (Map map : todoField) {
                String fieldNameTemp = (String) map.get(Constants.FIELD_NAME_ALIAS_COLUMN);
                fieldNameTemp = fieldNameTemp.replaceAll("\\s+", "");
                fieldNameTemp = fieldNameTemp.replace(",'',", ",' ',");
                if (currentDynamicKey.equalsIgnoreCase((String) map.get("FieldName"))) {
                    if (!selectedColumn.toString().contains((String) map.get("FieldName"))) {
                        if (currentDynamicKey.equalsIgnoreCase("CreatedDateTime")) {
                            selectedColumn.append("WFINSTRUMENTTABLE.").append(currentDynamicKey).append(",");
                        } else {
                            selectedColumn.append(currentDynamicKey).append(",");
                        }
                    }
                    String fieldType = (String) map.get("FieldType");
                    if (fieldType.equalsIgnoreCase(Constants.MULTI_FIELD_TYPE)) {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "").replace("[", "").replaceAll("\"", "");
                        String[] str = list.split(",");
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("','", "'", "'"));
                        multiSelectValue = multiSelectValue.substring(0, multiSelectValue.length() - 1);
                        searchWhereClauses.add(" " + fieldNameTemp + " in(" + multiSelectValue + ") ");
                    } else if (fieldType.equalsIgnoreCase(Constants.DROPDOWN_FIELD_TYPE)) {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "").replace("[", "").replaceAll("\"", "");
                        String[] str = list.split(",");
                        String fieldNamePrefix = fieldNameTemp + " = '";
                        // multiple OR conditions
                        String multiSelectValue = Arrays.stream(str).collect(Collectors.joining("' OR " + fieldNamePrefix, fieldNamePrefix, "' "));
                        searchWhereClauses.add(multiSelectValue);
                    } else if (fieldType.equalsIgnoreCase(Constants.DATE_FIELD_TYPE)) {
                        if (dynamicQuery.getSearchDetails().getString(Constants.BRANCH_CODE_COLUMN).equalsIgnoreCase("BKK")
                                && currentDynamicKey.equalsIgnoreCase("CreatedDateTime")) {
                            searchWhereClauses.add(" WFINSTRUMENTTABLE." + currentValue + " ");
                        } else {
                            searchWhereClauses.add(" " + currentValue + " ");
                        }
                        ngLogger.consoleLog("***Enquiry searchWhereClause:" + searchWhereClauses);
                    } else {
                        String list = dynamicQuery.getSearchDetails().getString(currentDynamicKey);
                        list = list.replace("]", "").replace("[", "").replaceAll("\"", "");
                        String[] str = list.split(",");
                        List<String> al = Arrays.asList(str);
                        StringBuilder multiSelectValue = new StringBuilder();
                        if (al.size() == 1) {
                            if (product.equalsIgnoreCase("tf")) {
                                if (fieldNameTemp.equalsIgnoreCase("T24ReferenceNo") || fieldNameTemp.equalsIgnoreCase("RelatedT24RefNo")) {
                                    ngLogger.consoleLog("search key " + fieldNameTemp + al.size());
                                    ngLogger.consoleLog("seasrch q " + multiSelectValue + "T24ReferenceNo LIKE '" + al.get(0) + "%' OR"
                                            + " RelatedT24RefNo LIKE '" + al.get(0) + "% OR ");
                                    multiSelectValue.append("T24ReferenceNo LIKE '").append(al.get(0)).append("%' OR RelatedT24RefNo LIKE '")
                                            .append(al.get(0)).append("%' OR ");
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
                        searchWhereClause = searchWhereClause.substring(0, searchWhereClause.length() - 3);
                        ngLogger.consoleLog("muliselect Value " + searchWhereClause);
                        searchWhereClauses.add(searchWhereClause);
                    }
                }
            }
        }
        ngLogger.consoleLog("searchWhereClause " + searchWhereClauses);

        List<String> extTable = tableDAO.getExtTableDetails(product);
        if (!CollectionUtils.isEmpty(extTable)) {
            extTableName = extTable.get(0);
        }
        ngLogger.consoleLog("Ext_TableName : " + extTableName);

        StringBuilder join;
        //Modified by Sai 04 May 2021 for the dynamicjoin
        for (Map value : todoColumnField) {
            if (!selectedColumn.toString().contains((String) value.get("FieldName"))) {
                if (value.get(Constants.TABLE_NAME_COLUMN) != null) {
                    selectedColumn.append(value.get(Constants.TABLE_NAME_COLUMN)).append(".").append(value.get("FieldName")).append(",");
                } else {
                    selectedColumn.append(value.get("FieldName")).append(",");
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
                                        .append(map.get("ColumnToJoin")).append("= ").append(map.get(Constants.TABLE_NAME_COLUMN)).append(".")
                                        .append(map.get("ColumnToJoin"));
                                count++;
                                joins.add(join.toString());
                                tables.add(tableName);
                            }
                        }
                    }
                }
            }
        }

        ngLogger.consoleLog("search values " + searchWhereClauses);
        //Added by Sai to resolve the Search with Amount
        if (extTableName.equals(Constants.DFX_EXTERNAL_TABLE)) {
            if (!selectedColumn.toString().contains("DFX_T_EXT.Buy_Side_Currency") && !selectedColumn.toString().contains("Buy_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Buy_Side_Currency ");
            }
            if (!selectedColumn.toString().contains("DFX_T_EXT.Sell_Side_Currency") && !selectedColumn.toString().contains("Sell_Side_Currency,")) {
                selectedColumn.append("DFX_T_EXT.Sell_Side_Currency ");
            }
        }
        String selectedColumns = selectedColumn.substring(0, selectedColumn.length() - 1);
        selectedColumns = selectedColumns.replaceAll("\\s+", "").replace(",'',", ",' ',");
        ngLogger.consoleLog("Enquiry selected columns " + selectedColumns);
        ngLogger.consoleLog("Enquiry Dynamic Joins " + joins);
        ce.setDynamicJoin(joins);
        ce.setDynamicSelect(selectedColumns);
        ce.setDynamicSearchWhere(searchWhereClauses);
        ce.setPageNo(dynamicQuery.getPageNo());
        ce.setPageSize(dynamicQuery.getPageSize());
        ce.setSortBy(dynamicQuery.getSortBy());
        ce.setOrderBy(dynamicQuery.getOrderBy());
        ce.setCount(dynamicQuery.getCount());
        ce.setExtTableName(extTableName);
        ngLogger.consoleLog("dynamic where clause " + searchWhereClauses);
        return cedao.getEnquiryData(ce);
    }
}
