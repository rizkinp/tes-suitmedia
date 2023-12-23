package com.example.appc_suitmedia_test.ui
//User Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appc_suitmedia_test.R
import com.example.appc_suitmedia_test.model.User
import com.squareup.picasso.Picasso

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }
    //initialize list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(user)
        }
    }
    //count item
    override fun getItemCount(): Int {
        return userList.size
    }
    //changing item
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        private val tvFirstName: TextView = itemView.findViewById(R.id.tvFirstName)
        private val tvLastName: TextView = itemView.findViewById(R.id.tvLastName)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        fun bind(user: User) {
            // Load avatar image using Picasso
            Picasso.get().load(user.avatar).into(ivAvatar)

            tvFirstName.text = user.first_name
            tvLastName.text = user.last_name
            tvEmail.text = user.email
        }
    }
}
