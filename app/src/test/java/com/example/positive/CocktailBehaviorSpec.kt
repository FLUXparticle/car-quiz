package com.example.positive

import io.kotest.core.spec.style.BehaviorSpec

class CocktailBehaviorSpec : BehaviorSpec({
    given("ein vorhandener Cocktail") {
        `when`("die Details abgefragt werden") {
            then("wird ein Ergebnis geliefert") {
                // ...
            }
        }
    }
})
