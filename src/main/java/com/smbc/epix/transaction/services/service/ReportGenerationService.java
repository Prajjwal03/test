package com.smbc.epix.transaction.services.service;

import java.util.List;

import com.smbc.epix.transaction.services.dto.ReportGenerationDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;

public interface ReportGenerationService {

    List<ReportGenerationDTO> getReportDetails(DynamicTable dynamicQuery);
}
