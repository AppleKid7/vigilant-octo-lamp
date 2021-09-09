package com.example.ticker

import boopickle.Default._
import scala.util.{Failure, Success, Try}
import yahoofinance.YahooFinance
import zhttp.http._
import zhttp.service.Server
import zhttp.socket._
import zio._
import zio.console._
import zio.duration._
import zio.stream.ZStream
import zio.clock.Clock
import zio.console.Console.Service

object CryptoTicker extends App {

  def msg = "I was compiled by Scala 3 and I'm using ZIO 2.0. :)"

  def ticketURLCall(symbol: String): UIO[java.math.BigDecimal] = ZIO.succeed {
    YahooFinance.get(symbol).getQuote(true).getPrice
  }

  def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    Server.start(8090, app).exitCode
  }

  def stringMsg(symbol: String, price: java.math.BigDecimal) = WebSocketFrame.text(s"$symbol: $price")

  private val socket: Socket[Has[zio.clock.Clock.Service], Nothing, WebSocketFrame, WebSocketFrame] = Socket.collect[WebSocketFrame] {
    case WebSocketFrame.Text(symbol)  =>
      ZStream
        .repeatEffect(ticketURLCall(symbol).map(price => stringMsg(symbol, price)))
        .throttleShape(1, 3.seconds)(_ => 1) //.map(parsePrice) // in case we're parsing JSON we'd have a parsePrice function
  }

  private val app = HttpApp.collect {
    case Method.GET -> Root / "greet" / name  => Response.text(s"Greetings $name!")
    case Method.GET -> Root / "ws" => Response.socket(socket)
  }
}
