package org.festerson.halloween.event.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private ChangeEventPublisher publisher;

    public Person createPerson(String firstname, String lastname) {
        Person p = new Person();
        p.setFirstname(firstname);
        p.setLastname(lastname);

        //Emit Event
        this.publisher.publishPersonChange(p);

        return p;
    }
}
