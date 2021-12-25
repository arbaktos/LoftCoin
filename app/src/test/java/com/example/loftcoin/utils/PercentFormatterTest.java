package com.example.loftcoin.utils;


import static com.google.common.truth.Truth.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PercentFormatterTest {

    private PercentFormatter formatter;

    @Before
    public void setUp() throws Exception {
        formatter = new PercentFormatter();
    }

    @Test
    public void string_contains_exact_two_fractional_digits() {
        assertThat(formatter.format(1d)).isEqualTo("1.00%");
        assertThat(formatter.format(1.2345)).isEqualTo("1.23%");
        assertThat(formatter.format(1.2357)).isEqualTo("1.24%");
    }

    @After
    public void tearDown() throws Exception {

    }
}