package pers.acp.kotlin.test

import org.apache.commons.text.CharacterPredicates
import org.apache.commons.text.RandomStringGenerator
import java.util.*

/**
 * Create by zhangbin on 2017-12-19 11:28
 */
fun main(args: Array<String>) {
    println("Hello World!")

    val generator: RandomStringGenerator = RandomStringGenerator.Builder()
            .withinRange(33, 126)
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build()
    val s1 = generator.generate(32)
    val s2 = generator.generate(16)
    println(s1)
    println(s2)

    println(System.currentTimeMillis())
    println(Date().time)
}