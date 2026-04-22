package com.hhp227.parallaxlayout.fragment.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.parallaxlayout.databinding.FragmentFirstTabBinding
import com.hhp227.parallaxlayout.databinding.ItemFirstTextBinding

private const val ARG_PARAM1 = "param1"

class FirstTabFragment : Fragment() {
    private var param1: String? = null

    private lateinit var binding: FragmentFirstTabBinding
    private val viewModel: FirstTabViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstTabBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = SimpleTextAdapter(viewModel.items)
        binding.progressBar.visibility = View.GONE
        binding.swipeRefreshLayout.isEnabled = false
    }

    fun isFirstItemVisible() = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0

    private class SimpleTextAdapter(
        private val items: List<String>
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
            holder.binding.textView.text = items[position]
        }

        override fun getItemCount(): Int = items.size

        class TextViewHolder(val binding: ItemFirstTextBinding) :
            RecyclerView.ViewHolder(binding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FirstTabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
