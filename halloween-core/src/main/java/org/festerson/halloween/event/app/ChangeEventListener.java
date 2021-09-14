package org.festerson.halloween.event.app;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventListener implements ApplicationListener<PersonChangedEvent> {
    @Override
    public void onApplicationEvent(PersonChangedEvent event) {
        Person person = event.getPerson();
        System.out.println("PersonChangedEvent: { firstname: "
            + person.getFirstname() + ", lastname: " + person.getLastname() + "}");
    }
}
