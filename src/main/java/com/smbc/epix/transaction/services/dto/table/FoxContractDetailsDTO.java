package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

import lombok.ToString;
@ToString(callSuper = true)
@Data
public class FoxContractDetailsDTO implements Serializable {
	
	private static final long serialVersionUID = -1772192279416962214L;
	private String contVersionInfo;
	private String tradWorkflowStatus;
}
