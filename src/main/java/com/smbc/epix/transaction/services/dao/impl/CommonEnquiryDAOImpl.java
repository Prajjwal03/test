package com.smbc.epix.transaction.services.dao.impl;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.CommonEnquiryDAO;
import com.smbc.epix.transaction.services.dto.table.CommonEnquiryDetailsDTO;
import com.smbc.epix.transaction.services.model.CommonEnquiry;
import com.smbc.epix.transaction.services.utils.Constants;
import org.springframework.stereotype.Repository;

@Repository
public class CommonEnquiryDAOImpl extends BaseDAO implements CommonEnquiryDAO {

    @Override
    public CommonEnquiryDetailsDTO getEnquiryData(CommonEnquiry commonEnquiry) {
        CommonEnquiryDetailsDTO enquiryDetailsDTO = new CommonEnquiryDetailsDTO();
        int count = commonEnquiry.getCount();
        String countMethod = "";
        String detailMethod = "";
        if (commonEnquiry.getExtTableName().equals(Constants.DFX_EXTERNAL_TABLE)) {
            countMethod = "CommonEnquiryDetails.getCommonEnquiryDetailsCountDfx";
            detailMethod = "CommonEnquiryDetails.getCommonEnquiryDetailsDfx";
        } else if (commonEnquiry.getExtTableName().equals(Constants.TREASURY_EXTERNAL_TABLE)) {
            countMethod = "CommonEnquiryDetails.getCommonEnquiryDetailsCount";
            detailMethod = "CommonEnquiryDetails.getCommonEnquiryDetails";
        }
        if (count == 0) {
            CommonEnquiryDetailsDTO commonEnquiryDetailsDTO = (CommonEnquiryDetailsDTO) selectOne(countMethod, commonEnquiry);
            enquiryDetailsDTO.setCount(commonEnquiryDetailsDTO.getCount());
        } else {
            enquiryDetailsDTO.setCount(count);
        }

        enquiryDetailsDTO.setCommonEnquiryDetails(selectList(detailMethod, commonEnquiry));
        return enquiryDetailsDTO;
    }
}
