package com.smbc.epix.transaction.services.dao;

import com.smbc.epix.transaction.services.dto.table.CommonEnquiryDetailsDTO;
import com.smbc.epix.transaction.services.model.CommonEnquiry;

public interface CommonEnquiryDAO {

    CommonEnquiryDetailsDTO getEnquiryData(CommonEnquiry commonEnquiry);
}
