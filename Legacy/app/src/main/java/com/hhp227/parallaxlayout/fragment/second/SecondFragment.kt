package com.hhp227.parallaxlayout.fragment.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.parallaxlayout.MainFragment
import com.hhp227.parallaxlayout.R
import com.hhp227.parallaxlayout.databinding.FragmentSecondBinding
import com.hhp227.parallaxlayout.databinding.ItemGroupGridBinding

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel: SecondViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val mainFragment = requireParentFragment().parentFragment as MainFragment
        mainFragment.setNavAppbar(binding.toolbar)
        binding.spanCount = 2
        binding.isEmpty = viewModel.items.isEmpty()
        binding.isLoading = false
        binding.srlGroup.isEnabled = false
        binding.rvGroup.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGroup.adapter = GroupGridAdapter(viewModel.items) {
            mainFragment.findNavController().navigate(R.id.action_mainFragment_to_parallaxTabFragment)
        }
    }

    private class GroupGridAdapter(
        private val items: List<String>,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<GroupGridAdapter.GroupGridViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupGridViewHolder {
            val binding = ItemGroupGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GroupGridViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GroupGridViewHolder, position: Int) {
            val item = items[position]
            holder.binding.tvTitle.text = item
            holder.binding.ivMore.visibility = View.GONE
            holder.binding.root.setOnClickListener {
                onItemClick(item)
            }
        }

        override fun getItemCount(): Int = items.size

        class GroupGridViewHolder(val binding: ItemGroupGridBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}
