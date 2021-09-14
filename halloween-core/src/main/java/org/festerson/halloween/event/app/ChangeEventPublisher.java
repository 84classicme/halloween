package org.festerson.halloween.event.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishPersonChange(Person person) {
        PersonChangedEvent pce = new PersonChangedEvent(person, this);
        this.publisher.publishEvent(pce);
    }
}

