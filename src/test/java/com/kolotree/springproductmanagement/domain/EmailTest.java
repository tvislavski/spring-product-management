package com.kolotree.springproductmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.kolotree.springproductmanagement.domain.Email.newEmailFrom;

public class EmailTest {

    @Test
    public void testEmailCreationWithNullFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom(null));
    }

    @Test
    public void testEmailCreationWithEmptyStringFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom(""));
    }

    @Test
    public void testEmailCreationWithBlankStringFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom("         "));
    }

    @Test
    public void testEmailCreationWithInvalidEmailFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom("test.abc.gmail"));
    }

    @Test
    public void testEmailCreationWithInvalidEmail2Fails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom("test@abc"));
    }

    @Test
    public void testEmailCreationWithInvalidEmail3Fails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> newEmailFrom("@abc.gmail"));
    }

    @Test
    public void testEmailCreationWithValidEmailSucceeds() {
        Email email = Assertions.assertDoesNotThrow(() -> newEmailFrom("test@gmail.com"));
        Assertions.assertEquals("test@gmail.com", email.toString());
    }

    @Test
    public void testEmailEquality() {
        Email email = Assertions.assertDoesNotThrow(() -> newEmailFrom("test@gmail.com"));
        Email email2 = Assertions.assertDoesNotThrow(() -> newEmailFrom("test@gmail.com"));
        Email email3 = Assertions.assertDoesNotThrow(() -> newEmailFrom("test2@yahoo.com"));
        Assertions.assertEquals(email, email2);
        Assertions.assertNotEquals(email, email3);
    }
}
