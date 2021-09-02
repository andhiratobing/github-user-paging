package submission.andhiratobing.githubuser.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowersFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowingFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.RepositoryFragment

@ExperimentalCoroutinesApi
class SectionPageAdapter(fragment: FragmentManager, lifecycle: Lifecycle, bundle: Bundle) :
    FragmentStateAdapter(fragment, lifecycle) {

    private val bundleData: Bundle = bundle

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = RepositoryFragment()
        }

        fragment.arguments = this.bundleData
        return fragment
    }
}