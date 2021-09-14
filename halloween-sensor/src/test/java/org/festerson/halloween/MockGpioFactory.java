package org.festerson.halloween;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.impl.GpioControllerImpl;

public class MockGpioFactory {

    // we only allow a single default provider to exists
    private static MockGpioProvider provider = null;

    // private constructor
    private MockGpioFactory() {
        // forbid object construction
    }

    /**
     * Create New GPIO Controller instance
     *
     * @return Return a new GpioController impl instance.
     */
    public static GpioController getInstance() {
        // return a new instance of the GPIO controller
        return new GpioControllerImpl(getMockProvider());
    }


    public static MockGpioProvider getMockProvider() {
        // if a provider has not been created, then create a new instance
        if (provider == null) {
            provider = new MockGpioProvider();
        }
        // return the provider instance
        return provider;
    }
}
