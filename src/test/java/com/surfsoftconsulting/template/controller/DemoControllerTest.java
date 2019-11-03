package com.surfsoftconsulting.template.controller;

import com.surfsoftconsulting.template.domain.Person;
import com.surfsoftconsulting.template.service.DemoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DemoControllerTest {

    private static final String PERSON_ID = UUID.randomUUID().toString();
    private static final String FIRST_NAME = "Mark";
    private static final String LAST_NAME = "Webber";

    private final DemoService demoService = mock(DemoService.class);

    private final DemoController underTest = new DemoController(demoService);

    private Person person = mock(Person.class);
    private List<Person> people = new ArrayList<>();

    @Test
    void getPerson() {

        when(demoService.getPerson(PERSON_ID)).thenReturn(Optional.of(person));

        assertThat(underTest.get(PERSON_ID)).isEqualTo(person);

    }

    @Test
    void getPersonThrowsNotFound() {

        when(demoService.getPerson(PERSON_ID)).thenReturn(Optional.empty());

        assertThrows(DemoController.PersonNotFoundException.class, () -> underTest.get(PERSON_ID));

    }

    @Test
    void list() {

        when(demoService.getAll()).thenReturn(people);

        assertThat(underTest.list()).isEqualTo(people);

    }

    @Test
    void create() {

        when(demoService.create(FIRST_NAME, LAST_NAME)).thenReturn(PERSON_ID);

        String response = underTest.create(FIRST_NAME, LAST_NAME);

        assertThat(response).isEqualTo(PERSON_ID);

    }

    @Test
    void updateSucceeds() {

        when(demoService.update(PERSON_ID, FIRST_NAME, LAST_NAME)).thenReturn(true);

        underTest.update(PERSON_ID, FIRST_NAME, LAST_NAME);

    }

    @Test
    void updateThrowsNotFound() {

        when(demoService.update(PERSON_ID, FIRST_NAME, LAST_NAME)).thenReturn(false);

        assertThrows(DemoController.PersonNotFoundException.class, () -> underTest.update(PERSON_ID, FIRST_NAME, LAST_NAME));

    }

    @Test
    void delete() {

        underTest.delete(PERSON_ID);

        verify(demoService).delete(PERSON_ID);

    }

}