package com.example.educational_practice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

// Сущность для пользователя
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)

// Сущность для целей
@Entity(tableName = "targets")
data class Target(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "target") var target: Int,
    @ColumnInfo(name = "current") var current: Int,
    @ColumnInfo(name = "user_id") var userid: Long
)

// Сущность для доходов
@Entity(tableName = "income")
data class Incomes(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "user_id") val userid: Long
)

// Сущность для расходов
@Entity(tableName = "expense")
data class Expenses(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "user_id") val userid: Long
)


// Сущность для лимитов
@Entity(tableName = "limits")
data class Limit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "limit") val limit: Int,
    @ColumnInfo(name = "current") val current: Int,
    @ColumnInfo(name = "user_id") val userid: Long
)


@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>


    @Query("SELECT * FROM user WHERE login = :login")
    suspend fun getUsersByLogin(
        login : String
    ): User?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUsersByEmail(
        email : String
    ): User?

}

@Dao
interface TargetDao {
    @Insert
    suspend fun insertTarget(target: Target)

    @Query("SELECT * FROM targets WHERE user_id = :id")
    fun getAllTargets(id: Long): List<Target>

    //возможно это не нужно
    /*@Query("SELECT * FROM targets WHERE id = :id")
    suspend fun getTargetById(id: Long): Target?*/

    @Query("SELECT * FROM targets WHERE title = :Title AND user_id = :id")
    suspend fun getTargetByTitle(Title: String, id:Long): Target

    /*@Update
    suspend fun updateTarget(target: Target)*/

    @Query("UPDATE targets SET target = :newValue WHERE title = :Title AND user_id = :id")
    fun updateTargettarget(Title: String, id: Long, newValue: Int)

    @Query("UPDATE targets SET current = :newValue WHERE title = :Title AND user_id = :id")
    fun updateTargetcurrent(Title: String, id: Long, newValue: Int)


    @Query("DELETE FROM targets WHERE title = :Title AND user_id = :id")
    fun deleteTarget(Title: String, id: Long)
}

@Dao
interface IncomesDao {
    @Insert
    suspend fun insertIncomes(incomes: Incomes)

    @Query("SELECT * FROM income WHERE type = :type AND user_id = :id")
    fun getIncomeByType(type: String, id:Long): List<Incomes>

    @Query("SELECT * FROM income WHERE user_id = :id")
    fun getAllIncomes(id: Long): List<Incomes>

    /*@Query("SELECT * FROM saved_cities WHERE city_id = :id")
    suspend fun getSavedCitiesByCity(id: Int): SavedCity?*/

    /*@Query("SELECT * FROM saved_cities WHERE user_login = :userLogin AND city_id = :cityId")
    suspend fun getSavedCitiesByUserAndCity(
        userLogin: String,
        cityId: Int
    ): SavedCity?*/

    //тут подумать
    @Query("DELETE FROM income WHERE user_id = :id")
    fun deleteIncomes(id: Long)
}


@Dao
interface ExpensesDao {
    @Insert
    suspend fun insertExpenses(expenses: Expenses)

    @Query("SELECT * FROM expense WHERE type = :type AND user_id = :id")
    fun getExpensesByType(type: String, id:Long): List<Expenses>

    @Query("SELECT * FROM expense WHERE user_id = :id")
    fun getAllExpenses(id: Long): List<Expenses>


    @Query("DELETE FROM expense WHERE user_id = :id")
    fun deleteExpenses(id: Long)

}

@Dao
interface LimitDao {
    @Insert
    suspend fun insertLimit(limit: Limit)

    @Query("SELECT * FROM limits WHERE user_id = :id")
    fun getAllLimits(id: Long): List<Limit>

    //возможно это не нужно
    /*@Query("SELECT * FROM limits WHERE id = :id")
    suspend fun getLimitById(id: Long): Limit?*/

    @Query("SELECT * FROM limits WHERE title = :Title AND user_id = :id")
    suspend fun getLimitByTitle(Title: String, id:Long): Limit

    @Query("DELETE FROM limits WHERE title = :Title AND user_id = :id")
    fun deleteLimit(Title: String, id: Long)
}

@Database(entities = [User::class, Target::class, Incomes::class, Expenses::class, Limit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun targetDao(): TargetDao
    abstract fun incomesDao(): IncomesDao
    abstract fun expensesDao(): ExpensesDao
    abstract fun limitDao(): LimitDao
}

