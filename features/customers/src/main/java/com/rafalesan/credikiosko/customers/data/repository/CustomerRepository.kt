package com.rafalesan.credikiosko.customers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rafalesan.credikiosko.core.commons.data.mappers.toCustomerDomain
import com.rafalesan.credikiosko.core.commons.data.mappers.toCustomerEntity
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.customers.data.datasource.CustomerLocalDataSource
import com.rafalesan.credikiosko.customers.domain.repository.ICustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomerRepository(
    private val customerLocalDataSource: CustomerLocalDataSource
) : ICustomerRepository {
    override fun getLocalCustomerPaged(): Flow<PagingData<Customer>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 2),
            pagingSourceFactory = { customerLocalDataSource.getCustomerPaged() }
        ).flow.map { pagingData ->
            pagingData.map { it.toCustomerDomain() }
        }
    }

    override suspend fun saveCustomer(customer: Customer) {
        customerLocalDataSource.saveCustomer(customer.toCustomerEntity())
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerLocalDataSource.deleteCustomer(customer.toCustomerEntity())
    }
}