package org.festerson.halloween;

import java.util.EnumSet;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.impl.PinImpl;

public class MockPin {

    public static final Pin DIGITAL_BIDIRECTIONAL_PIN = new PinImpl(MockGpioProvider.NAME, 0, "GPIO-0",
            EnumSet.of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT),
            PinPullResistance.all());

    public static final Pin DIGITAL_INPUT_PIN = new PinImpl(MockGpioProvider.NAME, 1, "GPIO-1",
            EnumSet.of(PinMode.DIGITAL_INPUT),
            PinPullResistance.all());

    public static final Pin DIGITAL_OUTPUT_PIN = new PinImpl(MockGpioProvider.NAME, 2, "GPIO-2",
            EnumSet.of(PinMode.DIGITAL_OUTPUT));

    public static final Pin PWM_OUTPUT_PIN = new PinImpl(MockGpioProvider.NAME, 3, "GPIO-3",
            EnumSet.of(PinMode.PWM_OUTPUT));

    public static final Pin ANALOG_BIDIRECTIONAL_PIN  = new PinImpl(MockGpioProvider.NAME, 4, "GPIO-4",
            EnumSet.of(PinMode.ANALOG_INPUT,
                    PinMode.ANALOG_OUTPUT));
    public static final Pin ANALOG_INPUT_PIN = new PinImpl(MockGpioProvider.NAME, 5, "GPIO-5",
            EnumSet.of(PinMode.ANALOG_INPUT));

    public static final Pin ANALOG_OUTPUT_PIN = new PinImpl(MockGpioProvider.NAME, 6, "GPIO-6",
            EnumSet.of(PinMode.ANALOG_OUTPUT));
}
