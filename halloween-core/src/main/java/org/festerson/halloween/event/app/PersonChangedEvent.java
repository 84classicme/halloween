package org.festerson.halloween.event.app;

import org.springframework.context.ApplicationEvent;

public class PersonChangedEvent extends ApplicationEvent {
    private final Person person;

    public PersonChangedEvent(Person person, Object source) {
        super(source);
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }
}
