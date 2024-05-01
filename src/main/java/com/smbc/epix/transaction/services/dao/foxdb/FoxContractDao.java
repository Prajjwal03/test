package com.smbc.epix.transaction.services.dao.foxdb;

import java.util.List;

import com.smbc.epix.transaction.services.dto.table.FoxContractDetailsDTO;
import org.apache.ibatis.annotations.Param;

public interface FoxContractDao {
    List<FoxContractDetailsDTO> getLatestVersion(@Param("contractNo") String contractNo);
}

