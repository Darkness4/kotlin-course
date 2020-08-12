package org.example.sandbox

interface Usecase<in Params, out Type> {
    operator fun invoke(params: Params): Type
}
