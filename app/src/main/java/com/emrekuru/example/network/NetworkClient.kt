package com.emrekuru.example.network

import com.emrekuru.example.network.request.EmployeeRequest
import com.emrekuru.example.network.response.EmployeeResponse
import com.emrekuru.example.util.employeeUrl
import retrofit2.http.GET

interface NetworkClient {

    @GET(employeeUrl)
    suspend fun getAllEmployees(): ArrayList<EmployeeResponse>

    @GET("getEmployeeByName")
    suspend fun getEmployeeByName(request : EmployeeRequest): EmployeeResponse
}