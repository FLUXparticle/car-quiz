package com.example.positive

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ParsePositiveIntDataDrivenTest : StringSpec({
    "parsePositiveInt verarbeitet mehrere feste Fälle" {
        forAll(
            row("42", 42),
            row("7", 7),
            row("100", 100)
        ) { input, expected ->
            parsePositiveInt(input) shouldBe expected
        }
    }
})
