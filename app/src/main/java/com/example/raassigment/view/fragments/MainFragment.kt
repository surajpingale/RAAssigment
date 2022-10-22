package com.example.raassigment.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raassigment.R
import com.example.raassigment.databinding.FragmentMainBinding
import com.example.raassigment.model.dataclasses.Exclusion
import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.model.dataclasses.Facility
import com.example.raassigment.model.dataclasses.Option
import com.example.raassigment.utils.Constants
import com.example.raassigment.utils.Response
import com.example.raassigment.view.activities.MainActivity
import com.example.raassigment.view.adapter.FacilityAdapter
import com.example.raassigment.view.adapter.FacilityOptionsAdapter
import com.example.raassigment.viewmodel.FacilityViewModel
import com.example.raassigment.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragment : Fragment(), FacilityOptionsAdapter.OnItemClick {

    private var _binding: FragmentMainBinding? = null

    private val binding
        get() = _binding!!

    lateinit var facilityList: List<Facility>
    lateinit var facilityAdapter: FacilityAdapter
    lateinit var optionsAdapterList: ArrayList<FacilityOptionsAdapter>
    lateinit var allOptionsList: ArrayList<Option>
    lateinit var facilities: Facilities
    private var selectedCount = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FacilityViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activityComponent = (requireActivity() as MainActivity).activityComponent
        activityComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)


        initViews()


        return binding.root
    }

    private fun initViews() {
        facilityList = ArrayList()
        optionsAdapterList = ArrayList()
        allOptionsList = ArrayList()

        viewModel.getData.observe(viewLifecycleOwner) { state ->

            when (state) {
                is Response.Loading -> {
                    binding.rvFacility.visibility = View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Response.Success -> {
                    binding.rvFacility.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    facilityList = state.data!!.facilities
                    facilities = state.data
                    updateOptionsList()

                }

                is Response.Error -> {

                }

            }

        }
    }

    private fun updateRecyclerView(facilities: Facilities) {

        val recyclerView = binding.rvFacility

        facilityAdapter = FacilityAdapter(facilities, this)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = facilityAdapter
        }
    }

    private fun updateOptionsList() {

        for (i in facilityList.indices) {
            for (j in facilityList[i].options.indices) {
                val facilityId = facilityList[i].facilityId
                val option = facilityList[i].options[j]

                option.exclusion = ArrayList()
                val exclusionList = facilities.exclusions
                for (o in exclusionList.indices) {
                    for (p in exclusionList[o].indices) {

                        if (exclusionList[o][p].facilityId == facilityId &&
                            exclusionList[o][p].optionsId == option.id
                        ) {
                            for (q in exclusionList[o].indices) {
                                if (exclusionList[o][q].facilityId != facilityId &&
                                    exclusionList[o][q].optionsId != option.id
                                ) {
                                    val exclusionFacilityId = exclusionList[o][q].facilityId
                                    val exclusionOptionsId = exclusionList[o][q].optionsId

                                    val exclusion =
                                        Exclusion(exclusionFacilityId, exclusionOptionsId)
                                    option.exclusion!!.add(exclusion)
                                }
                            }
                        }
                    }
                }
            }
        }
        updateRecyclerView(facilities)

    }


    override fun getAdapterInstance(
        optionsAdapter: FacilityOptionsAdapter,
        optionsList: List<Option>
    ) {
        optionsAdapterList.add(optionsAdapter)
        allOptionsList.addAll(optionsList)
    }

    override fun itemClicked(
        optionsList: List<Option>,
        option: Option,
        adapterPosition: Int,
        facilityId: String
    ) {

        val list = ArrayList<Option>()
        var lastAdapterPos = 0

        val a = facilityList[adapterPosition].options
        list.addAll(optionsList)

        for (i in list) {
            if (i != option) {
                i.isSelected = Constants.UNSELECTED
            } else {
                if (i.isSelected == Constants.SELECTED) {
                    i.isSelected = Constants.UNSELECTED

                    var lastFcPos = 0
                    var lastOptionPos = 0

                    lifecycleScope.launchWhenStarted {
                        viewModel.lastFacilityPosition.collect {
                            lastFcPos = it
                        }
                    }

                    lifecycleScope.launchWhenStarted {
                        viewModel.lastOptionsPosition.collect {
                            lastOptionPos = it
                            val facility = facilities.facilities[lastFcPos]
                                .options[lastOptionPos]
                            facility.isSelected = Constants.UNSELECTED
//                            viewModel.writeLastSelection(0,0)
                            refreshExclusionRecyclerView(lastFcPos)
                        }
                    }

                } else {

                    if (i.isSelected != Constants.DISABLED) {
                        i.isSelected = Constants.SELECTED
                    }

                    for (o in facilityList.indices) {
                        for (p in facilityList[o].options.indices) {
                            val optionForExclusion = facilityList[o].options[p]
                            i.exclusion?.let {exclusionList->

                                exclusionList.forEach {exclusion->
                                    if (exclusion.facilityId == facilityList[o].facilityId
                                        && exclusion.optionsId == optionForExclusion.id
                                    ) {
                                        Log.d("suraj", "option.. $option")
                                        val disable = facilities.facilities[o]
                                            .options[p]
                                        disable.isSelected = Constants.DISABLED
                                        viewModel.writeLastSelection(o, p)
                                        refreshExclusionRecyclerView(o)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        refreshRecyclerView(adapterPosition)
//        optionsAdapterList[adapterPosition].updateOptionsList(list)
        selectedCount = 0
    }

    private fun refreshRecyclerView(adapterPosition: Int) {
        optionsAdapterList[adapterPosition].notifyDataSetChanged()

    }

    private fun refreshExclusionRecyclerView(exclusionAdapterPos: Int) {
        optionsAdapterList[exclusionAdapterPos].notifyDataSetChanged()
    }

    private fun clickNumber() {
        for (j in allOptionsList) {
            if (j.isSelected == Constants.SELECTED) {




                selectedCount++
            }
        }
        if (selectedCount == 1) {
            Toast.makeText(
                requireActivity(),
                "1st click",
                Toast.LENGTH_SHORT
            ).show()
        } else if (selectedCount == 2) {
            Toast.makeText(
                requireActivity(),
                "2nd click",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireActivity(),
                "3rd click",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}