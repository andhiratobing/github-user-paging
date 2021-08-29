package submission.andhiratobing.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowersFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowingFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.RepositoryFragment

@ExperimentalCoroutinesApi
class SectionPageAdapter(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {

    var username: String? = null

    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(username)
            1 -> fragment = FollowersFragment.newInstance(username)
            2 -> fragment = RepositoryFragment.newInstance(username)
        }

        return fragment
    }
}