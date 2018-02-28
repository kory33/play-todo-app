package app

import react.RState

interface TodoItemCreatorState: RState {

    var inputText: String

    var displayEmptyInputError: Boolean

}