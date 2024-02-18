package com.rafalesan.credikiosko.customers.presentation.customers_list

import androidx.paging.PagingData
import com.rafalesan.credikiosko.customers.domain.entity.Customer

data class CustomersState(
    val customers: PagingData<Customer> = PagingData.empty()
)
