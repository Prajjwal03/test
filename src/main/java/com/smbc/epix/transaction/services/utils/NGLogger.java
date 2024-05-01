package com.smbc.epix.transaction.services.utils;

public class NGLogger {
    private static final String EPIXAPP_MODULE = "EPIXAPPLogs";
    private final String cabinetName;

    public NGLogger(TransactionServicesUtils transactionServicesUtils) {
        cabinetName = transactionServicesUtils.getPropertyFile().getProperty("cabinetName");
    }

    public void consoleLog(String message) {
        com.newgen.commonlogger.NGLogger.writeConsoleLog(cabinetName, EPIXAPP_MODULE, message);
    }

    public void xmlLog(String message) {
        com.newgen.commonlogger.NGLogger.writeXmlLog(cabinetName, EPIXAPP_MODULE, message);
    }

    public void errorLog(String message) {
        com.newgen.commonlogger.NGLogger.writeErrorLog(cabinetName, EPIXAPP_MODULE, message);
    }

    public void queryLog(String message) {
        com.newgen.commonlogger.NGLogger.writeQueryLog(cabinetName, EPIXAPP_MODULE, message);
    }
}
