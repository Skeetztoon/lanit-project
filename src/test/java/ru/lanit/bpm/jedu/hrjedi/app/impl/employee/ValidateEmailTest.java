package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ValidateEmailTest {
    @InjectMocks
    private ValidateEmailUseCase validateEmailUseCase;

    @ParameterizedTest
    @ValueSource(strings = {"asd-123@gmad.ti", "привет.мир@почта.ру"})
    void validEmailTest(String email) {
        assertDoesNotThrow(() -> validateEmailUseCase.execute(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@mail.ti", "asd@", "asd@@mail.com"})
    void invalidEmailTest(String email) {
        assertThrows(InvalidEmailException.class, () -> validateEmailUseCase.execute(email));
    }
}
