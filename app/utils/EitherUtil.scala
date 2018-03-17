package utils

object EitherUtil {

  implicit class MappableEither[+A, +B](private val e: Either[A, B]) extends AnyVal {

    def mapLeft[X](f: A => X): Either[X, B] = e.left.map(f)

    def mapRight[X](f: B => X): Either[A, X] = e.right.map(f)

    def flatMapLeft[BB >: B, X](f: A => Either[X, BB]): Either[X, BB] = e.left.flatMap(f)

    def flatMapRight[AA >: A, X](f: B => Either[AA, X]): Either[AA, X] = e.right.flatMap(f)

  }

}