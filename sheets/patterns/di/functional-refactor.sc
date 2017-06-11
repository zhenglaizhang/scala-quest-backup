//  Service Locator (global collection factory/storage)
//  Dependency Injection
//  Reader - for computations which read values from a shared environment.

class MongoCollection

case class DB[R](read: MongoCollection => R) {
  def apply(c: MongoCollection): R = read(c)
}
            

