package com.urban.app.loaders;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoaderExceptionTest {
    @Test public void testConstructor() {
        String message = "foo";
        Throwable parentException = new Exception("bar");

        LoaderException exception = new LoaderException(message, parentException);

        assertThat(exception.getMessage(), equalTo(message));
        assertThat(exception.getCause(), sameInstance(parentException));
    }
}
