package com.example.positive

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ParseIntTest : StringSpec({
    "gibt Zahl bei gültigem Input zurück" {
        parsePositiveInt("42") shouldBe 42
    }

    "wirft Fehler bei ungültigem Input" {
        shouldThrow<IllegalArgumentException> {
            parsePositiveInt("abc")
        }
    }
})
