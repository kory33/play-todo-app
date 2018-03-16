package utils

object OptionUtil {

  implicit class OptionProjection[L](private val x: Option[L]) extends AnyVal {
    def projectLeftWith[R](right: => R): Either.LeftProjection[L, R] = x.toLeft(right).left
  }

}