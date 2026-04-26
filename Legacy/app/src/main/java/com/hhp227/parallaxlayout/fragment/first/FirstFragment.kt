package com.hhp227.parallaxlayout.fragment.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.parallaxlayout.MainFragment
import com.hhp227.parallaxlayout.R
import com.hhp227.parallaxlayout.databinding.FragmentFirstBinding
import com.hhp227.parallaxlayout.databinding.ItemFirstTextBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding

    private val viewModel: FirstViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainFragment = requireParentFragment().parentFragment as? MainFragment
        mainFragment?.setNavAppbar(binding.toolbar)
        binding.recyclerView.adapter = SimpleTextAdapter(viewModel.items) {
            mainFragment?.findNavController()?.navigate(R.id.action_mainFragment_to_detailFragment)
        }
        binding.progressBar.visibility = View.GONE
        binding.fab.visibility = View.GONE
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 1_000L)
        }
    }

    private class SimpleTextAdapter(
        private val items: List<String>,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<SimpleTextAdapter.TextViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
            val binding = ItemFirstTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TextViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
            val item = items[position]
            holder.binding.textView.text = item
            holder.binding.root.setOnClickListener {
                onItemClick(item)
            }
        }

        override fun getItemCount(): Int = items.size

        class TextViewHolder(val binding: ItemFirstTextBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}
