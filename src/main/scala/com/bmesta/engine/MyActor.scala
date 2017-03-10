package com.bmesta.engine

import akka.persistence.{PersistentActor, SnapshotOffer}


case class Cmd(data: String)

case class Evt(data: String)

case class ExampleState(events: List[String] = Nil) {
  def updated(evt: Evt): ExampleState = copy(evt.data :: events)

  def size: Int = events.length

  override def toString: String = events.reverse.toString
}

class ExamplePersistentActor extends PersistentActor {
  override def persistenceId = "sample-id-1"

  var state = ExampleState()

  def updateState(event: Evt): Unit =
    state = state.updated(event)

  def numEvents: Int =
    state.size

  val receiveRecover: Receive = {
    case evt: Evt => println(s"recover $evt"); updateState(evt)
    case SnapshotOffer(_, snapshot: ExampleState) => state = snapshot
  }

  val receiveCommand: Receive = {
    case Cmd(data) =>
      println(s"execute command $data")
      persist(Evt(s"$data"))(updateState)
    case "snap" => saveSnapshot(state)
    case "print" => println(state)
  }

}