package skydu.android.instaclone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.databinding.ActivityProfileBinding
import skydu.android.instaclone.ui.base.BaseActivity
import skydu.android.instaclone.ui.post.PostDetailActivity
import skydu.android.instaclone.ui.profile.adapter.ProfileAdapter

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileAdapter: ProfileAdapter

    private val username: String by lazy {
        intent.getStringExtra("username")
    }

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(username)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileAdapter = ProfileAdapter {
            Intent(this, PostDetailActivity::class.java).run {
                putExtra("id", it.id)
                startActivity(this)
            }
        }


        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (profileAdapter.getItemViewType(position)) {
                    ProfileAdapter.VIEW_TYPE_HEADER -> 3
                    ProfileAdapter.VIEW_TYPE_ITEM -> 1
                    else -> 1
                }
            }
        }
        binding.rvPosts.layoutManager = layoutManager
        binding.rvPosts.adapter = profileAdapter
        setupObservers()
    }

    private fun setupObservers() {
        setupBaseObservers(viewModel)

        viewModel.profile.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    it.data?.run { profileAdapter.data = this }
                    binding.progressBar.visibility = View.GONE
                    binding.rvPosts.visibility = View.VISIBLE
                }
                DataResult.State.LOADING -> {
                    binding.rvPosts.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {
                    it.errorMessage?.run {
                        Toast.makeText(this@ProfileActivity, this, Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.rvPosts.visibility = View.VISIBLE
                }
            }
        }
    }
}