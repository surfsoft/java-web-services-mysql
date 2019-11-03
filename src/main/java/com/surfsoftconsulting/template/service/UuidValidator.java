package com.surfsoftconsulting.template.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidValidator {

    boolean isValid(String candidate) {

        if (candidate == null) {
            return false;
        }
        else {
            try {
                UUID.fromString(candidate);
                return true;
            }
            catch(Exception e) {
                return false;
            }
        }

    }

}
