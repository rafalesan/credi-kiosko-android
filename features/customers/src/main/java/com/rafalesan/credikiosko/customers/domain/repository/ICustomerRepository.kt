package com.rafalesan.credikiosko.customers.domain.repository

import androidx.paging.PagingData
import com.rafalesan.credikiosko.customers.domain.entity.Customer
import kotlinx.coroutines.flow.Flow

interface ICustomerRepository {

    fun getLocalCustomerPaged(): Flow<PagingData<Customer>>

    suspend fun saveCustomer(customer: Customer)

    suspend fun deleteCustomer(customer: Customer)

}
