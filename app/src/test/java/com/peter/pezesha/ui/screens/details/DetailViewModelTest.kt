package com.peter.pezesha.ui.screens.details

import com.peter.pezesha.domain.model.response.Product
import com.peter.pezesha.domain.usecase.GetProductUseCase
import com.peter.pezesha.domain.usecase.SendEncryptedStockUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @MockK
    private lateinit var getProductUseCase: GetProductUseCase

    @MockK
    private lateinit var sendEncryptedStockUseCase: SendEncryptedStockUseCase

    private lateinit var viewModel: DetailsViewModel
    private val productId = 123

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = DetailsViewModel(
            productId = productId,
            getProductUseCase = getProductUseCase,
            sendEncryptedStockUseCase = sendEncryptedStockUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProduct updates product LiveData on success`() = runTest {
        val mockProduct = Product(
            id = productId,
            title = "Test Product",
            description = "Test Description",
            price = 100.0,
            priceWithoutDiscount = 120,
            discountPercentage = 20,
            rating = 4.5f,
            stock = 10,
            category = "Electronics",
            thumbnailUrl = "https://example.com/image.jpg",
            imageUrls = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg")
        )

        coEvery { getProductUseCase(any(), any(), any()) } answers {
            thirdArg<(Result<Product>) -> Unit>().invoke(Result.success(mockProduct))
        }

        viewModel.getProduct()

        assertEquals(mockProduct, viewModel.product.value)
    }



}



