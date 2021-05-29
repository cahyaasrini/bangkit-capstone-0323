package com.cap0323.medy.data.local.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.cap0323.medy.data.local.entity.MedicineEntity
import com.cap0323.medy.data.local.room.medicine.MedicineDao
import com.cap0323.medy.data.local.room.medicine.MedicineDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val medicineDao: MedicineDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MedicineDatabase.getDatabase(application)
        medicineDao = db.medicineDao()
    }

    fun getMedByName(brandName: String): LiveData<List<MedicineEntity>> = medicineDao.getMedByName(brandName)
}