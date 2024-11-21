package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
public class ValidateEmailTest {
    @InjectMocks
    private ValidateEmailUseCase validateEmailUseCase;

    @Test
    public void validLatinWithDash() {
        String email = "asd-123@gmad.ti";

        assertDoesNotThrow(() -> validateEmailUseCase.execute(email));
    }

    @Test
    public void validCyrilicWithDot() {
        String email = "привет.мир@почта.ру";

        assertDoesNotThrow(() -> validateEmailUseCase.execute(email));
    }

    @Test
    public void invalidNoPrefix() {
        String email = "@mail.ti";

        assertThrows(InvalidEmailException.class, () -> validateEmailUseCase.execute(email));
    }

    @Test
    public void invalidNoPostfix() {
        String email = "asd@";

        assertThrows(InvalidEmailException.class, () -> validateEmailUseCase.execute(email));
    }

    @Test
    public void invalidDoubleAt() {
        String email = "asd@@mail.com";

        assertThrows(InvalidEmailException.class, () -> validateEmailUseCase.execute(email));
    }
}
