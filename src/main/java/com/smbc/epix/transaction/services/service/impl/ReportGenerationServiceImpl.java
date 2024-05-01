package com.smbc.epix.transaction.services.service.impl;

import java.util.List;

import com.smbc.epix.transaction.services.dao.ReportGenerationDAO;
import com.smbc.epix.transaction.services.dto.ReportGenerationDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.ReportGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportGenerationServiceImpl implements ReportGenerationService {

    @Autowired
    private ReportGenerationDAO reportGenerationDAO;

    @Override
    public List<ReportGenerationDTO> getReportDetails(DynamicTable dynamicQuery) {
        return reportGenerationDAO.getReportDetails(dynamicQuery);
    }
}
