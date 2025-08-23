package io.github.dkexception.ghiblix.shared.viewmodel

import androidx.lifecycle.viewmodel.MutableCreationExtras

fun creationExtras(block: MutableCreationExtras.() -> Unit) = MutableCreationExtras().apply(block)
