package tech.funkyra.timeout

import tech.funkyra.timeout.handlers.PlayerTimeoutHandler.timedoutPlayers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TimedoutPlayer {
	def playerAdd(nick: String): Unit =
		timedoutPlayers += nick

	def playerRemove(nick: String): Unit =
		if (timedoutPlayers.contains(nick))
			timedoutPlayers -= nick

	def temporaryAddition(nick: String, time: Int): Unit = {
		playerAdd(nick)
		setTimeout(time) {
			playerRemove(nick)
		}
	}

	def setTimeout(interval: Int)(func: => Unit): Unit = {
		Future {
			Thread.sleep(interval)
			func
		}
	}

	def setInterval(interval: Int)(func: => Unit): Unit = {
		Future {
			while (true) {
				func
				Thread.sleep(interval)
			}
		}
	}
}
