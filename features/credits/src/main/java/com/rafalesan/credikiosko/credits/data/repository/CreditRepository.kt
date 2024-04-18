package com.rafalesan.credikiosko.credits.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rafalesan.credikiosko.core.bluetooth_printer.data.datasource.ThermalPrinterDataSource
import com.rafalesan.credikiosko.core.bluetooth_printer.data.models.PrintAlignment
import com.rafalesan.credikiosko.core.bluetooth_printer.data.models.PrintFont
import com.rafalesan.credikiosko.core.bluetooth_printer.data.models.PrintStatus
import com.rafalesan.credikiosko.core.commons.data.datasource.local.BusinessLocalDataSource
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.presentation.utils.DateFormatUtil
import com.rafalesan.credikiosko.core.room.entity.BusinessEntity
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.data.datasource.CreditLocalDataSource
import com.rafalesan.credikiosko.credits.data.mappers.toCreditEntity
import com.rafalesan.credikiosko.credits.data.mappers.toCreditProductEntity
import com.rafalesan.credikiosko.credits.data.mappers.toCreditWithCustomerAndProductsDomain
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.repository.ICreditRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import com.rafalesan.credikiosko.core.R as CoreR

class CreditRepository(
    private val context: Context,
    private val creditLocalDataSource: CreditLocalDataSource,
    private val businessLocalDataSource: BusinessLocalDataSource,
    private val thermalPrinterDataSource: ThermalPrinterDataSource
) : ICreditRepository {
    override suspend fun saveCredit(
        credit: Credit,
        creditProducts: List<CreditProduct>
    ): Long {
        val creditEntity = credit
            .copy(
                businessId = businessLocalDataSource.getBusiness().id
            ).toCreditEntity()

        val savedCreditId = creditLocalDataSource.saveCredit(creditEntity)

        val creditProductEntities = creditProducts.map {
            it.copy(creditId = savedCreditId)
                .toCreditProductEntity()
        }

        creditLocalDataSource.saveCreditProducts(creditProductEntities)

        return savedCreditId
    }

    override suspend fun deleteCredit(credit: Credit) {
        creditLocalDataSource.deleteCredit(credit.toCreditEntity())
    }

    override fun getCreditsWithCustomerAndProducts(): Flow<PagingData<CreditWithCustomerAndProducts>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = {
                creditLocalDataSource.getCreditsWithCustomerAndProducts()
            }
        ).flow
            .map {  pagingData ->
                pagingData.map { it.toCreditWithCustomerAndProductsDomain() }
            }
    }

    override suspend fun findCredit(creditId: Long): CreditWithCustomerAndProducts {
        return creditLocalDataSource
            .findCredit(creditId)
            .toCreditWithCustomerAndProductsDomain()
    }

    override fun printCredit(creditId: Long) : Flow<PrintStatus> = callbackFlow {

        trySend(PrintStatus.ConnectingPrinter)

        val business = businessLocalDataSource.getBusiness()
        val credit = findCredit(creditId)

        thermalPrinterDataSource.connect(
            onConnectionError = { connectionException ->
                trySend(
                    PrintStatus.PrinterConnectionError(connectionException)
                )
                close()
            },
            onConnected = {
                trySend(PrintStatus.PrinterConnected)
            }
        )

        trySend(PrintStatus.Printing)

        try {
            startPrintingCredit(
                business,
                credit
            )
        } catch (ex: Exception) {
            trySend(
                PrintStatus.PrinterConnectionError(ex)
            )
            close()
        }

        trySend(PrintStatus.PrintSuccess)

        awaitClose { thermalPrinterDataSource.close() }

    }

    private fun startPrintingCredit(
        business: BusinessEntity,
        creditWithCustomerAndProducts: CreditWithCustomerAndProducts,
    ) {

        val (credit, customer, products) = creditWithCustomerAndProducts

        with(thermalPrinterDataSource) {

            fillLineWith('-')

            write(
                business.name,
                PrintAlignment.CENTER,
                PrintFont.LARGE
            )

            newLine()

            write(
                key = context.getString(R.string.customer_equals),
                value = customer.name
            )

            write(
                key = context.getString(R.string.date_equals),
                value = DateFormatUtil.getUIDateFormatFrom(credit.date)
            )

            write(
                key = context.getString(R.string.time_equals),
                value = DateFormatUtil.getUITimeFormatFrom(credit.date)
            )

            newLine()

            write(
                context.getString(CoreR.string.products),
                font = PrintFont.BOLD
            )

            newLine()

            products.forEach { product ->

                write(
                    key = context.getString(R.string.product_equals),
                    value = product.productName
                )

                write(
                    key = context.getString(R.string.quantity_equals),
                    value = product.quantity
                )

                write(
                    key = context.getString(R.string.unit_price),
                    value = context.getString(R.string.cordoba_symbol_x, product.productPrice)
                )

                write(
                    key = context.getString(R.string.price_times_quantity),
                    value = context.getString(R.string.cordoba_symbol_x, product.total)
                )

                newLine()

            }

            write(
                key = context.getString(R.string.total),
                value = context.getString(R.string.cordoba_symbol_x, credit.total),
                font = PrintFont.BOLD
            )

            fillLineWith('-')

            printLargeSpace()
        }
    }

}
