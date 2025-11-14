
package com.srjupi.booktracker.backend.book;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookSpecificationTest {

    @Test
    void constructorIsPrivate() throws NoSuchMethodException {
        Constructor<BookSpecification> ctor = BookSpecification.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Constructor should be private");
    }

    @Test
    void staticMethodsReturnNonNullSpecifications() {
        assertNotNull(BookSpecification.hasTitle("a"));
        assertNotNull(BookSpecification.hasAuthors("a"));
        assertNotNull(BookSpecification.hasIsbn("123"));
        assertNotNull(BookSpecification.hasPublisher("p"));
        assertNotNull(BookSpecification.hasLanguage("en"));
    }
}
