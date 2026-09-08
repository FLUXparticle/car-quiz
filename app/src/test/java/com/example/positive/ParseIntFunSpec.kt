package com.example.positive

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ParseIntFunSpec : FunSpec({
    test("parsePositiveInt liefert 42 für \"42\"") {
        parsePositiveInt("42") shouldBe 42
    }
})
