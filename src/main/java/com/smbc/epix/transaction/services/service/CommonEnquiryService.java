package com.smbc.epix.transaction.services.service;

import com.amazonaws.util.json.JSONException;
import com.smbc.epix.transaction.services.dto.table.CommonEnquiryDetailsDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;

public interface CommonEnquiryService {

    CommonEnquiryDetailsDTO getEnquiryData(DynamicTable dynamicQuery) throws JSONException;
}
