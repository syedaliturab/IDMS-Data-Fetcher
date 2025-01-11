package com.drivesoft.idmsdatafetcher.converter;

import com.drivesoft.idmsdatafetcher.utils.Encrypter;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptedStringConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            if (attribute != null) {
                return Encrypter.encrypt(attribute);
            }
        } catch (Exception e) {
            log.error("Error in encrypting attribute:{}",e.getMessage());
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            if (dbData != null) {
                return Encrypter.decrypt(dbData);
            }
        } catch (Exception e) {
           log.error("Error in decrypting db data :{}",e.getMessage());
        }
        return null;
    }
}
