package eventbus.events

import meteor.Event


class LoadingTextChanged(var newText: String) : Event()