package com.betternfaster.ticker

import org.junit.Test
import org.junit.Assert._

class Test1 {
  @Test def t1(): Unit = {
    assertEquals("I was compiled by Scala 3. :)", CryptoTicker.msg)
  }
}
