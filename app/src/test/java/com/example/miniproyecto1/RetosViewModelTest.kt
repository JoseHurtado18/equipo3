package com.example.miniproyecto1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.repository.RetosRepository
import com.example.miniproyecto1.viewmodel.RetosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.math.log

@ExperimentalCoroutinesApi
class RetosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var retosViewModel: RetosViewModel
    private lateinit var retosRepository: RetosRepository

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        retosRepository = mock(RetosRepository::class.java)
        retosViewModel = RetosViewModel(retosRepository)
        Dispatchers.setMain(UnconfinedTestDispatcher())

    }

    @Test
    fun `test método para add un reto`(){
        //given
        val mockReto = Reto(descripcion = "Reto 1", id = "1")

        val observer = Observer<MutableList<Reto>> { }
        retosViewModel.retos.observeForever(observer)

        val onFailure: (Throwable) -> Unit
        @Suppress("UNCHECKED_CAST")
        `when`(retosRepository.addReto(mockReto, any(), onFailure = {})).thenAnswer {
            val onSuccess = it.arguments[0] as () -> Unit
            onSuccess()
        }

        //when
        retosViewModel.addReto(mockReto)

        //then
        verify(retosRepository).addReto(mockReto, any(), any())
        val expectedRetos = mutableListOf(mockReto)
        Assert.assertEquals(expectedRetos, retosViewModel.retos.value)
        retosViewModel.retos.removeObserver(observer)


    }

    @Test
    fun `test método para getRetos`() {
        //given

        val mockRetos = mutableListOf(
            Reto(descripcion = "Reto 1", id = "1")
        )

        val observedValue = mutableListOf<List<Reto>>()

        @Suppress("UNCHECKED_CAST")
        `when`(retosRepository.getRetos(onSuccess = {}, onFailure = {})).thenAnswer { invocation ->
            val onSuccess = invocation.arguments[0] as (List<Reto>) -> Unit
            onSuccess(mockRetos)
        }

        // Observa los cambios en LiveData
        val observer = Observer<List<Reto>> { observedValue.add(it) }
        retosViewModel.retos.observeForever(observer)


        //when
        retosViewModel.getRetos()

        // Asegurarse de que la LiveData de productos se haya actualizado correctamente
        //then
        Assert.assertEquals(mockRetos, observedValue.first())

        retosViewModel.retos.removeObserver(observer)
    }
}