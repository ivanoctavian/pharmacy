package com.pharmacy.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FieldValidator {
    public void validateMandatoryFields(Object obj){
        Set<String> emptyFields = validateMandatoryFields_SET(obj);
        if(!emptyFields.isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Fields: %s are mandatory.", emptyFields));
        }
    }

    private Set<String> validateMandatoryFields_SET(Object obj) {
        Set<String> missingFields = new HashSet<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Mandatory.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null) {
                        missingFields.add(field.getName());
                    } else if (value instanceof String && ((String) value).isEmpty()) {
                        missingFields.add(field.getName());
                    } else if (value instanceof List && ((List<?>) value).isEmpty()) {
                        missingFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                }
            }
        }
        return missingFields;
    }
}
