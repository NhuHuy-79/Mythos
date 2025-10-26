package com.nhuhuy.mythos.domain

import androidx.compose.ui.geometry.Rect
import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.model.then
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ResourceTest {
    @Test
    fun `then should call failure block WHEN Resource is Failure`() = runTest {
        val resource = Resource.Failure(Exception("exception"))
        var calledValue: String? = null

        resource.then(
            failure = { throwable -> calledValue = throwable.message},
            success = {}
        )

        Truth.assertThat(calledValue).isEqualTo("exception")
    }

    @Test
    fun `then should call success block when Resource is Success`() = runTest {
        val resource = Resource.Success(1)
        var calledValue: Int? = null

        resource.then(
            failure = { },
            success = { data -> calledValue = data }
        )

        Truth.assertThat(calledValue).isEqualTo(1)
    }
}