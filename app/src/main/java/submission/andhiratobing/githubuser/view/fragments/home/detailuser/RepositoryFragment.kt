package submission.andhiratobing.githubuser.view.fragments.home.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.databinding.FragmentRepositoryBinding

@AndroidEntryPoint
class RepositoryFragment : Fragment() {


    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?, ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}