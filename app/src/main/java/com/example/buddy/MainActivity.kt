package com.example.buddy


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buddy.daos.PostDao
import com.example.buddy.modles.Post

import com.firebase.ui.firestore.FirestoreRecyclerOptions

import com.google.firebase.firestore.Query

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
       postDao = PostDao()
       val postCollections = postDao.postCollections
        val query = postCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        adapter = PostAdapter(recyclerViewOptions,this)
        recyclerView.adapter = adapter
       recyclerView.layoutManager = LinearLayoutManager(this)
    }
override fun onStart(){
    super.onStart()
    adapter.startListening()
}
    override fun onStop(){
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikedClick(postId: String) {
postDao.updateLikes(postId)
    }

}