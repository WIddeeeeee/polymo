package com.digitalpet.pet

/** App-side gate for the explicit dead-pet recovery override. */
object PetRecovery {
    const val PASSWORD = "MASONLOVESALLTURTLESANDMANATEES1234"

    fun acceptsPassword(input: String): Boolean = input == PASSWORD
}