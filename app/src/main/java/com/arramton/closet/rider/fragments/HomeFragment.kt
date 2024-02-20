package com.arramton.closet.rider.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.activity.DeliveredActivity
import com.arramton.closet.rider.activity.NewJobActivity
import com.arramton.closet.rider.activity.PickupOrderActivity
import com.arramton.closet.rider.activity.SubmittedOrderActivity
import com.arramton.closet.rider.activity.TotalDeliveryActivity
import com.arramton.closet.rider.activity.TotalEarnActivity
import com.arramton.closet.rider.activity.TransacationDetailsActivity
import com.arramton.closet.rider.factory.ProfileFactory
import com.arramton.closet.rider.repository.ProfileRepository
import com.arramton.closet.rider.viewModel.ProfileViewModel


class HomeFragment : Fragment() {
    private lateinit var apiInterface: ApiInterface
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileRepository: ProfileRepository
    private lateinit var tvPickupOrder:TextView
    private lateinit var tvNewJob:TextView
    private lateinit var tvSubmittedOrder:TextView
    private lateinit var tvTodayDelivered:TextView
    private lateinit var tvTotalDelivered:TextView
    private lateinit var tvTodayEarn:TextView
    private lateinit var layoutNewJob:LinearLayout
    private lateinit var layoutPickupOrder:LinearLayout
    private lateinit var layoutSubmittedOrder:LinearLayout
    private lateinit var layoutTodayDelivered:LinearLayout
    private lateinit var layoutTotalDelivered:LinearLayout
    private lateinit var layoutTodayEarn:LinearLayout
    private lateinit var layoutTotalEarn:LinearLayout
    private lateinit var tvUserName:TextView




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

      val   view : View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        return view
    }

    fun init(view: View){

        tvUserName=view.findViewById(R.id.home_page_userName)

        tvSubmittedOrder=view.findViewById(R.id.home_order_submitted)

        tvPickupOrder=view.findViewById(R.id.home_pickup_order)

        tvNewJob=view.findViewById(R.id.home_new_job)

        layoutNewJob=view.findViewById(R.id.home_new_job_layout)

        layoutPickupOrder=view.findViewById(R.id.home_pickup_order_layout)

        layoutSubmittedOrder=view.findViewById(R.id.home_order_submitted_layout)

        layoutTodayDelivered=view.findViewById(R.id.home_today_delivery_layout)

        layoutTotalDelivered=view.findViewById(R.id.home_total_delivered_layout)

        tvTotalDelivered=view.findViewById(R.id.home_total_delivered)

        layoutTotalEarn=view.findViewById(R.id.home_total_earn_layout)

        layoutTodayDelivered.setOnClickListener {
            startActivity(Intent(requireContext() ,TotalDeliveryActivity::class.java))

        }

        layoutTotalEarn.setOnClickListener {
            startActivity(Intent(requireContext() ,TransacationDetailsActivity::class.java))
        }



        layoutTodayEarn=view.findViewById(R.id.home_today_earn_layout)

        layoutTodayEarn.setOnClickListener {
            startActivity(Intent(requireContext() ,TotalEarnActivity::class.java))

        }


        tvTodayDelivered=view.findViewById(R.id.home_today_delivery)

        tvTodayEarn=view.findViewById(R.id.home_today_earn)


        layoutSubmittedOrder.setOnClickListener {
            startActivity(Intent(requireContext(),SubmittedOrderActivity::class.java))
        }

        layoutPickupOrder.setOnClickListener {
            startActivity(Intent(requireContext(),PickupOrderActivity::class.java))

        }

        layoutTotalDelivered.setOnClickListener {
            startActivity(Intent(requireContext(),DeliveredActivity::class.java))

        }



        layoutNewJob.setOnClickListener {
            startActivity(Intent(requireContext(),NewJobActivity::class.java))
        }
        apiInterface=RetrofitBuilder.getInstance(requireActivity().application)!!.api
        profileRepository= ProfileRepository(requireContext(),apiInterface,requireActivity().application)
        profileViewModel=ViewModelProvider(this,ProfileFactory(profileRepository)).get(ProfileViewModel::class.java)

        profileViewModel.profileHomePageResponse.observe(requireActivity(), Observer {
            if (it!=null){
                if (it.success){

                    tvPickupOrder.text=""+it.data.pickupJobCount

                    tvNewJob.text=""+it.data.newJobCount

                    tvSubmittedOrder.text=""+it.data.orderSubmittedCount

                    tvTodayDelivered.text=""+it.data.todayOrderDeliveredCount

                    tvTotalDelivered.text=""+it.data.totalOrderDeliveredCount

                    tvUserName.text=""+it.data.user

//                    tvTodayEarn.text=""+it.data.to
                }
            }
        })
        profileViewModel.profileHomePage()


    }

    override fun onResume() {
        super.onResume()
        profileViewModel.profileHomePage()
    }

}