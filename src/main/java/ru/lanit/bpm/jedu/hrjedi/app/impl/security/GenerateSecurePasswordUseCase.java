package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;

import java.lang.reflect.InvocationTargetException;

import static java.util.Arrays.asList;

@Component
public class GenerateSecurePasswordUseCase implements GenerateSecurePasswordInbound {
    /**
     * Legacy code used to load classes by reflection
     *
     * @return secure password
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public String execute() {
        try {
            Class digitsRuleClass = getClass().getClassLoader().loadClass("org.passay.DigitCharacterRule");
            Class loweCharsRuleClass = getClass().getClassLoader().loadClass("org.passay.LowercaseCharacterRule");
            Class upperCharsRuleClass = getClass().getClassLoader().loadClass("org.passay.UppercaseCharacterRule");
            Class passwordGeneratorClass = getClass().getClassLoader().loadClass("org.passay.PasswordGenerator");
            Object digits = digitsRuleClass.getConstructor(int.class).newInstance(2);
            Object lowerChars = loweCharsRuleClass.getConstructor(int.class).newInstance(4);
            Object upperChars = upperCharsRuleClass.getConstructor(int.class).newInstance(2);
            Object passwordGenerator = passwordGeneratorClass.newInstance();
            return (String) MethodUtils.invokeMethod(passwordGenerator, "generatePassword", 8, asList(digits, lowerChars, upperChars));
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Unable to load library", e);
        }
    }
}
