package com.surfsoftconsulting.template.service;

import com.surfsoftconsulting.template.domain.Person;
import com.surfsoftconsulting.template.domain.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DemoServiceTest {

    private static final String PERSON_ID = UUID.randomUUID().toString();
    private static final String FIRST_NAME = "Mark";
    private static final String LAST_NAME = "Webber";

    private final UuidValidator uuidValidator = mock(UuidValidator.class);
    private final PersonRepository personRepository = mock(PersonRepository.class);

    private final DemoService underTest = new DemoService(uuidValidator, personRepository);

    private Person person = mock(Person.class);
    private List<Person> people = new ArrayList<>();

    @Test
    void getPerson() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(true);
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));

        assertThat(underTest.getPerson(PERSON_ID)).isEqualTo(Optional.of(person));

    }

    @Test
    void getPersonInvalidId() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(false);

        assertThat(underTest.getPerson(PERSON_ID)).isEqualTo(Optional.empty());
        verify(personRepository, never()).findById(any());

    }

    @Test
    void getPersonNoMatchFound() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(true);
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());

        assertThat(underTest.getPerson(PERSON_ID)).isEqualTo(Optional.empty());

    }

    @Test
    void getAll() {

        when(personRepository.findAll()).thenReturn(people);

        assertThat(underTest.getAll()).isEqualTo(people);

    }

    @Test
    void create() {

        Person person = mock(Person.class);
        when(person.getId()).thenReturn(PERSON_ID);
        when(personRepository.save(any(Person.class))).thenReturn(person);

        String response = underTest.create(FIRST_NAME, LAST_NAME);

        assertThat(response).isEqualTo(PERSON_ID);

    }

    @Test
    void update() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(true);
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(person));

        boolean response = underTest.update(PERSON_ID, FIRST_NAME, LAST_NAME);

        assertThat(response).isTrue();
        verify(personRepository).save(any(Person.class));

    }

    @Test
    void updateInvalidId() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(false);

        boolean response = underTest.update(PERSON_ID, FIRST_NAME, LAST_NAME);

        assertThat(response).isFalse();
        verify(personRepository, never()).findById(any());
        verify(personRepository, never()).save(new Person(FIRST_NAME, LAST_NAME));

    }

    @Test
    void updateNoMatchFound() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(true);
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.empty());

        boolean response = underTest.update(PERSON_ID, FIRST_NAME, LAST_NAME);

        assertThat(response).isFalse();
        verify(personRepository, never()).save(new Person(FIRST_NAME, LAST_NAME));

    }

    @Test
    void delete() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(true);
        underTest.delete(PERSON_ID);

        verify(personRepository).deleteById(PERSON_ID);

    }

    @Test
    void deleteInvalidId() {

        when(uuidValidator.isValid(PERSON_ID)).thenReturn(false);
        underTest.delete(PERSON_ID);

        verify(personRepository, never()).deleteById(any());

    }

}