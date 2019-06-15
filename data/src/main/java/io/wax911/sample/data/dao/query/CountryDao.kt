package io.wax911.sample.data.dao.query

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.wax911.sample.data.model.attribute.Country
import io.wax911.support.data.dao.ISupportQuery

@Dao
interface CountryDao: ISupportQuery<Country?> {

    @Query("select count(name) from Country")
    suspend fun count(): Int

    @Query("select * from Country")
    suspend fun findAll(): List<Country>?

    @Query("select * from Country limit :limit offset :offset")
    suspend fun findAll(offset: Int, limit: Int): List<Country>?

    @Query("delete from Country")
    suspend fun deleteAll()

    @Query("select * from Country where name = :name order by name asc")
    fun findLiveData(name: String): LiveData<List<Country>>?

    @Query("select * from Country order by name asc")
    fun findAllLiveData(): LiveData<List<Country>?>
}