package ru.lanit.bpm.jedu.hrjedi.app.impl.security;

import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.GenerateSecurePasswordInbound;

import java.util.Arrays;

@Component
public class GenerateSecurePasswordUseCase implements GenerateSecurePasswordInbound {
    @Override
    public String execute() {
        try {
            CharacterRule digitsRule = new CharacterRule(EnglishCharacterData.Digit);
            digitsRule.setNumberOfCharacters(2);
            CharacterRule lowerCharsRule = new CharacterRule(EnglishCharacterData.LowerCase);
            lowerCharsRule.setNumberOfCharacters(4);
            CharacterRule upperCharsRule = new CharacterRule(EnglishCharacterData.UpperCase);
            upperCharsRule.setNumberOfCharacters(2);

            PasswordGenerator passwordGenerator = new PasswordGenerator();

            return passwordGenerator.generatePassword(8, Arrays.asList(digitsRule, lowerCharsRule, upperCharsRule));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate password", e);
        }
    }
}
