
/*
for {
i <- id
r <- result(i)
} yield {
val users = ConcurrentHashMap.empty[String, User]
log.debug("Starting")
val f: Future[User] = getFromCache(r.id).recover {
case e => log.error("Problem"); throw e
}
f.onSuccess {
case u =>
users.update(u.id, user)
actor ! u
}
log.debug("Finished")
...
}


=====>


val res: Future[Result] = for {
  i <- id
  r <- result(i)
} yield r
...
res.map
res.foreach
res.onComplete
res.onFailure
...
Keep yield short, real value, do not perform side effects.
*/
