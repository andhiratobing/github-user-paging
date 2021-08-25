package submission.andhiratobing.githubuser.data.remote.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowersFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.FollowingFragment
import submission.andhiratobing.githubuser.view.fragments.home.detailuser.RepositoryFragment

class SectionPageAdapter (fragment: Fragment, bundle: Bundle) : FragmentStateAdapter(fragment) {

    private val bundleData: Bundle = bundle

    override fun getItemCount(): Int = 3

    @ExperimentalCoroutinesApi
    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = RepositoryFragment()
        }
        fragment.arguments = this.bundleData

        return fragment
    }

}