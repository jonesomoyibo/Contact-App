package com.decagon.android.sq007.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
private const val ARG_PARAM1 = "param1"
class AppFragment : Fragment(), contactListAdapter.onItemClickListener {

    private var param1: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(param1, container, false)
        exposedRecyclerView = initRecyclerView(view)

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            AppFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }

        private lateinit var exposedRecyclerView: RecyclerView

        fun getRecyclerView(): RecyclerView {

            return exposedRecyclerView
        }
    }

    private fun initRecyclerView(view: View): RecyclerView {

        // Layout Manager responsible for laying the views in the RecyclerView
        val mLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        val contactRecyclerView = view.findViewById<RecyclerView>(R.id.contactrecyclerview)
        val createContactCardView = view.findViewById<CardView>(R.id.newcontactcardview)

        contactRecyclerView.apply {
            setLayoutManager(mLayoutManager)

            adapter = contactListAdapter(view.context, ContactRepository.getContactList(), this@AppFragment)
        }

        createContactCardView.setOnClickListener{

            val contactIntent = Intent(requireContext(), NewContactActivity::class.java)
            contactIntent.putExtra("ACTIVITY_NAME", MainActivity::class.java.name)
            startActivity(contactIntent)
        }


        return contactRecyclerView
    }

    override fun onItemClick(position: Int, userContactList: ArrayList<UserContact>) {
        val contactIntent = Intent(requireContext(), ContactProfileActivity::class.java)

        contactIntent.apply {
            putExtra("CONTACTFIRSTNAME", "${userContactList[position].firstName} ")
            putExtra("CONTACTLASTNAME", "${userContactList[position].lastName} ")
            putExtra("CONTACTNUMBER", userContactList[position].phoneNumber)
            putExtra("CONTACTID", userContactList.get(position).contactId)
        }
        startActivity(contactIntent)
    }
}
