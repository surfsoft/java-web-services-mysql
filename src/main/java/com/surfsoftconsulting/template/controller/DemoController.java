package com.surfsoftconsulting.template.controller;

import com.surfsoftconsulting.template.domain.Person;
import com.surfsoftconsulting.template.service.DemoService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(path = "/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @Transactional
    @RequestMapping(method = GET, path = "/")
    public List<Person> list() {
        return demoService.getAll();
    }

    @Transactional
    @RequestMapping(method = GET, path = "/{id}")
    public Person get(@PathVariable("id") String id) {
        Optional<Person> person = demoService.getPerson(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Transactional
    @RequestMapping(method = PUT)
    public String create(@RequestParam("first-name") String firstName,
                         @RequestParam("last-name") String lastName) {
        return demoService.create(firstName, lastName);
    }

    @Transactional
    @RequestMapping(method = POST, path = "/{id}")
    public void update(@PathVariable("id") String id,
                       @RequestParam("first-name") String firstName,
                       @RequestParam("last-name") String lastName) {
        if (!demoService.update(id, firstName, lastName)) {
            throw new PersonNotFoundException();
        }
    }

    @Transactional
    @RequestMapping(method = DELETE, path = "/{id}")
    public void delete(@PathVariable("id") String id) {
        demoService.delete(id);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "person not found")
    public static class PersonNotFoundException extends RuntimeException {
    }

}
