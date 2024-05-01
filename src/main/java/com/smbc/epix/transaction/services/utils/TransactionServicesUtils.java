package com.smbc.epix.transaction.services.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.smbc.epix.transaction.services.exception.PropertyFileNotFoundException;

public class TransactionServicesUtils {

    private final String location;

    public TransactionServicesUtils(String location) {
        this.location = location;
    }

    public Properties getPropertyFile() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(location)) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new PropertyFileNotFoundException("Exception while reading application-transactionServices.properties in TreasuryConfig", e);
        }
    }
}
