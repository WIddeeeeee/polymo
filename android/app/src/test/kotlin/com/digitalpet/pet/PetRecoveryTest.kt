package com.digitalpet.pet

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PetRecoveryTest {
    @Test
    fun `accepts only the exact recovery password`() {
        assertTrue(PetRecovery.acceptsPassword("MASONLOVESALLTURTLESANDMANATEES1234"))
        assertFalse(PetRecovery.acceptsPassword("masonlovesallturtlesandmanatees1234"))
        assertFalse(PetRecovery.acceptsPassword("MASONLOVESALLTURTLESANDMANATEES123"))
    }
}