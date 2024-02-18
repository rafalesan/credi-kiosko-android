package com.rafalesan.credikiosko.customers.data.datasource

import androidx.paging.PagingSource
import com.rafalesan.credikiosko.core.room.dao.CustomerDao
import com.rafalesan.credikiosko.core.room.entity.CustomerEntity
import javax.inject.Inject


class CustomerLocalDataSource @Inject constructor(
    private val customerDao: CustomerDao
) {

    fun getCustomerPaged(): PagingSource<Int, CustomerEntity> {
        return customerDao.getCustomerPaged()
    }

    suspend fun saveCustomer(customerEntity: CustomerEntity) {
        customerDao.insertCustomer(customerEntity)
    }

    suspend fun deleteCustomer(customerEntity: CustomerEntity) {
        customerDao.deleteCustomer(customerEntity)
    }

}
