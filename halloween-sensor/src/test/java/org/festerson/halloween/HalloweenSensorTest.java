package org.festerson.halloween;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HalloweenSensorTest {

    private HalloweenSensor sensor;
    private static MockGpioProvider provider;
    private static GpioController gpio;
    private static GpioPinDigitalInput pin;
    private static PinState pinMonitoredState;


    public void setup() {
        sensor = new HalloweenSensor();

        // create a mock gpio provider and controller
        provider = MockGpioFactory.getMockProvider();
        gpio = MockGpioFactory.getInstance();

        // provision pin for testing
        pin = gpio.provisionDigitalInputPin(MockPin.DIGITAL_INPUT_PIN,  "digitalInputPin", PinPullResistance.PULL_DOWN);

        // register pin listener
        pin.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                sensor.handleEvent(event);
                // set pin state
//                if (event.getPin() == pin) {
//                    pinMonitoredState = event.getState();
//                }
            }
        });
    }


    public void testPinHiEvent() throws InterruptedException {
        // explicit mock set on the mock provider
        provider.setMockState(MockPin.DIGITAL_INPUT_PIN, PinState.LOW);

        // wait 1/100 second before continuing test
        Thread.sleep(10);

        // reset pin monitoring variable
        pinMonitoredState = null;

        int timer = 300;
        for(int i =0 ; i < timer;i++){
            // explicit mock set on the mock provider
            provider.setMockState(MockPin.DIGITAL_INPUT_PIN, PinState.HIGH);
            Thread.sleep(10);
            provider.setMockState(MockPin.DIGITAL_INPUT_PIN, PinState.LOW);
        }
        // wait 1/100 second before continuing test
        //Thread.sleep(15000);

        // verify pin hi state
        //assertEquals(PinState.HIGH, pinMonitoredState);
        //assertEquals(1, 1);
    }
}
