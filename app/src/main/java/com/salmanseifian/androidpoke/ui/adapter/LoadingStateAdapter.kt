package com.salmanseifian.androidpoke.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salmanseifian.androidpoke.databinding.ItemNetworkStateBinding

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.binding.pb
        val retryBtn = holder.binding.btnRetry
        val txtErrorMessage = holder.binding.txrErrMsg

        if (loadState is LoadState.Loading) {
            progress.isVisible = true
            txtErrorMessage.isVisible = false
            retryBtn.isVisible = false

        } else {
            progress.isVisible = false
        }

        if (loadState is LoadState.Error) {
            txtErrorMessage.isVisible = true
            retryBtn.isVisible = true
            txtErrorMessage.text = loadState.error.localizedMessage
        }


        retryBtn.setOnClickListener {
            retry.invoke()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root)
}