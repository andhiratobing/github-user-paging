//package submission.andhiratobing.githubuser.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
//import submission.andhiratobing.githubuser.data.local.db.AppDatabase
//import submission.andhiratobing.githubuser.data.local.entities.UserFavorite
//
//class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
//
//    private var favoriteUserDao: FavoriteUserDao?
//    private var appDatabase: AppDatabase? = AppDatabase.getDatabase(application)
//
//    init {
//        favoriteUserDao = appDatabase?.favoriteUserDao()
//    }
//
//    fun getFavoriteUser(): LiveData<List<UserFavorite>>? {
//        return favoriteUserDao?.getFavoriteUser()
//    }
//
//    suspend fun getCountUser(): Int? {
//        return favoriteUserDao?.getCountUsers()
//    }
//
//
//}