package com.surfsoftconsulting.template.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UuidValidatorTest {

    private static final String VALID_UUID = UUID.randomUUID().toString();
    private final UuidValidator underTest = new UuidValidator();

    @Test
    void validUuid() {
        assertThat(underTest.isValid(VALID_UUID)).isTrue();
    }

    @Test
    void nullUuid() {
        assertThat(underTest.isValid(null)).isFalse();
    }

    @Test
    void emptyStringUuid() {
        assertThat(underTest.isValid("")).isFalse();
    }

    @Test
    void invalidUuid() {
        assertThat(underTest.isValid(VALID_UUID.substring(0, VALID_UUID.length() - 2) + "??")).isFalse();
    }



}