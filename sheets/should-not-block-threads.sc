import java.util.concurrent.{ Executors, ThreadFactory, TimeUnit }

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future, Promise, blocking }
import scala.util.control.NonFatal

import monix.execution.Scheduler
import monix.execution.atomic.AtomicLong
/*
1. When you have a choice, you should never block.
2. Prefer keeping the context of that Future all the way, until the edges of your program
3. For Scala’s Future, checkout the Scala-Async project to make this easier.
4. blocking threads is error prone because you have to know and control the configuration of the underlying thread-pool. For example even
 Scala’s ExecutionContext.Implicits.global has an upper limit to the number of threads spawned, which means that you can end up in a
 dead-lock, because all of your threads can end up blocked, with no threads available in the pool to finish the required callbacks.
5. If blocking, specify explicit timeouts
6. If blocking, use Scala’s BlockContext
  This includes all blocking I/O, including SQL queries.
  Blocking calls are error-prone because one has to be aware of exactly what thread-pool gets affected and given the default
  configuration of the backend app, this can lead to non-deterministic dead-locks. It’s a bug waiting to happen in production.

  Blocking calls have to be marked with a blocking call that signals to the BlockContext a blocking operation.

  ExecutionContext.Implicits.global is backed by a cool ForkJoinPool implementation that has an absolute maximum number of threads limit.
   What this means is that, in spite of well behaved code, you can still hit that limit and you can still end up in a dead-lock.
7. If blocking, use a separate thread-pool for blocking I/O
 */


//val future: java.util.concurrent.Future[String] = ???
// BAD CODE, NEVER DO THIS !!!
//future.get
// TODO: https://monix.io/docs/2x/best-practices/blocking.html#if-blocking-specify-explicit-timeouts
//future.get(3, TimeUnit.SECONDS)


implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
def addOne(x: Int) = Future(x + 1)
def multiply(x: Int, y: Int) = Future {
  val a = addOne(x)
  val b = addOne(y)
  val result = for (r1 <- a; r2 <- b) yield r1 * r2

  // This can dead-lock due to the limited size
  // of our thread-pool!
  Await.result(result, Duration.Inf)
}

private val io = Executors.newCachedThreadPool(
  new ThreadFactory {
    private val counter = AtomicLong(0L)

    override def newThread(r: Runnable) = {
      val th = new Thread(r)
      th.setName("io-thread-" + counter.getAndIncrement().toString())
      th.setDaemon(true)
      th
    }
  }
)
/*
 I prefer to use an unbounded “cached thread-pool”, so it doesn’t have a limit. When doing blocking I/O the idea is that you’ve got to
 have enough threads that you can block. But if unbounded is too much, depending on use-case, you can later fine-tune it, the idea with
 this sample being that you get the ball rolling.
You could also use Monix’s Scheduler.io of course, which is also backed by a “cached thread-pool”:
 */
// TODO: c as /**/

val io2 = Scheduler.io(name = "engine-io")

def executeBlockingIO[T](cb: => T): Future[T] = {
  val p = Promise[T]()
  io2.execute(() => {
    try {
      p.success(blocking(cb))
    } catch {
      case NonFatal(ex) =>
        println(s"Uncaught I/O exception: $ex")
        p.failure(ex)
    }
  })
  p.future
}

executeBlockingIO(println("hello"))
