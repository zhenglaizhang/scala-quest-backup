/*
Phantom Types, the reason why they are called phantom is that,
they are used as type constraints but never instantiated.
 */

trait Status

// the traits Open and Closed are never instantiated, we use them as only constraints.
trait Open extends Status

trait Closed extends Status

trait Door[S <: Status]

object Door {
  def apply[S <: Status] = new Door[S] {}

  // we try to use the phantoms to guarantee, at compile time,
  // that we can only open a close door and close an open door.
  def open[S <: Closed](d: Door[S]) = Door[Open]

  def close[S <: Open](d: Door[S]) = Door[Closed]
}

val closedDoor = Door[Closed]
val openDoor = Door.open(closedDoor)
val closedAgainDoor = Door.close(openDoor)

//val closedClosedDoor = Door.close(closedDoor)
//val openOpenDoor = Door.open(openDoor)
