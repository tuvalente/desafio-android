package com.picpay.desafio.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.MainActivity
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

/**
 * Adapter usado para listagem de [User] para o [RecyclerView] na [MainActivity].
 */

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.name.text = user.name
            itemView.username.text = user.username
            itemView.progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.picture, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBar.visibility = View.GONE
                    }
                })
        }
    }
}

private class UserListDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username.equals(newList[newItemPosition].username)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}