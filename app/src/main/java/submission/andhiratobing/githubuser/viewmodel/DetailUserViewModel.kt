//package submission.andhiratobing.githubuser.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
//import submission.andhiratobing.githubuser.data.local.db.AppDatabase
//import submission.andhiratobing.githubuser.data.local.entities.UserFavorite
//
//class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
//
//    private var favoriteUserDao: FavoriteUserDao?
//    private var appDatabase: AppDatabase? = AppDatabase.getDatabase(application)
//
//    init {
//        favoriteUserDao = appDatabase?.favoriteUserDao()
//    }
//
//    fun addFavorite(
//        username: String,
//        name: String,
//        avatar_url: String,
//        company: String,
//        location: String,
//        repository: Int,
//        follower: Int,
//        following: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = UserFavorite(
//                username,
//                name,
//                avatar_url,
//                company,
//                location,
//                repository,
//                follower,
//                following
//            )
//            favoriteUserDao?.addFavoriteUser(user)
//        }
//    }
//
//
//    suspend fun getCountFavorite(username: String) = favoriteUserDao?.getCountFavorite(username)
//
//    fun deleteFavorite(username: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            favoriteUserDao?.deleteFavoriteUser(username)
//        }
//    }
//
//}