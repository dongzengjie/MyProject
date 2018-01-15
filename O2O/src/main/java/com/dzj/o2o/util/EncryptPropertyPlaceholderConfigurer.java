package com.dzj.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;



public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };
	
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}
}
