package skydu.android.instaclone.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.CommentViewData
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.databinding.ActivityPostDetailBinding
import skydu.android.instaclone.ui.base.BaseActivity
import skydu.android.instaclone.ui.post.adapter.CommentAdapter
import skydu.android.instaclone.ui.post.adapter.CommentItemDecoration
import skydu.android.instaclone.ui.profile.ProfileActivity
import skydu.android.instaclone.utils.LoadingDialog
import skydu.android.instaclone.utils.ResourceUtil
import java.lang.NumberFormatException

class PostDetailActivity : BaseActivity() {
    lateinit var binding: ActivityPostDetailBinding

    lateinit var commentAdapter: CommentAdapter

    private val postId: Int by lazy {
        var id = intent.getIntExtra("id", -1)
        if (id == -1) {
            intent.data?.run {
                try {
                    id = this.lastPathSegment?.toInt() ?: -1
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        this@PostDetailActivity,
                        "postId harus angka",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
        id
    }

    private val viewModel: PostDetailViewModel by viewModels {
        PostDetailViewModelFactory(postId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commentAdapter = CommentAdapter {
            Intent(this, ProfileActivity::class.java).run {
                putExtra("username", it.username)
                startActivity(this)
            }
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
            addItemDecoration(
                CommentItemDecoration(this@PostDetailActivity)
            )
        }

        binding.tvPost.isEnabled = false
        binding.etComment.addTextChangedListener {
            viewModel.onEditTextChanged(it?.toString() ?: "")
        }

        binding.tvPost.setOnClickListener {
            viewModel.doComment()
        }
        setupObservers()
    }

    private fun setupObservers() {
        setupBaseObservers(viewModel)

        viewModel.postDetails.observe(this) {
            when (it.state) {
                DataResult.State.LOADING -> {
                    binding.rvPosts.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutComment.visibility = View.GONE
                }
                DataResult.State.SUCCESS -> {
                    it.data?.run { commentAdapter.updateList(this) }
                    binding.progressBar.visibility = View.GONE
                    binding.rvPosts.visibility = View.VISIBLE
                    binding.layoutComment.visibility = View.VISIBLE
                }
                else -> {
                    it.errorMessage?.run {
                        Toast.makeText(
                            this@PostDetailActivity,
                            this,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.rvPosts.visibility = View.VISIBLE
                    binding.layoutComment.visibility = View.GONE
                }

            }
        }

        viewModel.comment.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    binding.tvPost.isEnabled = true
                    if (!binding.etComment.text.toString().equals(it.data)) {
                        binding.etComment.setText(it?.data)
                    }
                }
                else -> {
                    binding.tvPost.isEnabled = false
                }
            }
        }

        val loading = LoadingDialog.get(this, ResourceUtil.getString(R.string.submit_comment))

        viewModel.submitComment.observe(this) {
            when (it.state) {
                DataResult.State.SUCCESS -> {
                    loading.dismiss()
                }
                DataResult.State.LOADING -> {
                    loading.show()
                }
                else -> {
                    it.errorMessage?.run {
                        Toast.makeText(
                            this@PostDetailActivity,
                            this,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    loading.dismiss()
                }
            }
        }
    }
}