package com.smbc.epix.transaction.services.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.smbc.epix.transaction.services.dao.foxdb.FoxContractDao;
import com.smbc.epix.transaction.services.dto.table.FoxContractDetailsDTO;
import com.smbc.epix.transaction.services.service.FoxContractService;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.springframework.stereotype.Service;

@Service
public class FoxContractServiceImpl implements FoxContractService {

	@Autowired
	private NGLogger ngLogger;
	
	@Autowired
	private FoxContractDao foxContractDao;
	
	@Override
	public List<FoxContractDetailsDTO> getLatestVersion(String contractNo) {
		List<FoxContractDetailsDTO> foxDetailsDtos = foxContractDao.getLatestVersion(contractNo);
		ngLogger.consoleLog("Successfully fetched contract details from fox, Contract Details: "+foxDetailsDtos);
		return foxDetailsDtos;
	}
}
