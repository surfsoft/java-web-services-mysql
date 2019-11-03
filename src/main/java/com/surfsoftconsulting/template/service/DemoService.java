package com.surfsoftconsulting.template.service;

import com.surfsoftconsulting.template.domain.Person;
import com.surfsoftconsulting.template.domain.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Optional.empty;

@Component
public class DemoService {

    private final UuidValidator uuidValidator;
    private final PersonRepository personRepository;

    public DemoService(UuidValidator uuidValidator, PersonRepository personRepository) {
        this.uuidValidator = uuidValidator;
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(String id) {
        return uuidValidator.isValid(id) ? personRepository.findById(id) : empty();
    }

    public List<Person> getAll() {

        return StreamSupport.stream(personRepository.findAll().spliterator(), false).collect(Collectors.toList());

    }

    public String create(String firstName, String lastName) {

        return personRepository.save(new Person(firstName, lastName)).getId();

    }

    public boolean update(String id, String firstName, String lastName) {

        if (uuidValidator.isValid(id)) {
            Optional<Person> person = personRepository.findById(id);
            if (person.isPresent()) {
                Person updatedPerson = person.get();
                updatedPerson.setFirstName(firstName);
                updatedPerson.setLastName(lastName);
                personRepository.save(updatedPerson);
            }
            return person.isPresent();
        }
        else {
            return false;
        }

    }

    public void delete(String id) {

        if (uuidValidator.isValid(id)) {
            personRepository.deleteById(id);
        }

    }

}
