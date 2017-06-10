package net.zhenglai.lib

import java.util.concurrent.ConcurrentHashMap

import scala.concurrent.Future

class KVStore[K, V] {
  private val s = new ConcurrentHashMap[K, V]()

  def create(k: K, v: V): Future[Boolean] = Future.successful(s.putIfAbsent(k, v) == null)
  def read(k: K): Future[Option[V]] = Future.successful(Option(s.get(k)))
  def update(k: K, v: V): Future[Unit] = Future.successful(s.put(k, v))
  def delete(k: K, v: V): Future[Boolean] = Future.successful(s.remove(k) != null)
}

