@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE"
)

package org.michaelbel.movies.interactor.entity

@JvmInline
expect value class Password(
    val value: String
)