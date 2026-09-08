package com.example.positive

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class ParsePositiveIntPropertyTest : StringSpec({
    "für alle positiven Ints gilt: parse(toString(n)) == n" {
        checkAll(Arb.int(1..Int.MAX_VALUE)) { n ->
            parsePositiveInt(n.toString()) shouldBe n
        }
    }

    "führende Nullen ändern den Zahlenwert nicht" {
        checkAll(Arb.int(1..1_000_000)) { n ->
            val withLeadingZeros = "000$n"
            parsePositiveInt(withLeadingZeros) shouldBe n
        }
    }
})
