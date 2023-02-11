package com.example.taskapp.ui.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.taskapp.databinding.FragmentOnBoardPageBinding


class OnBoardPageFragment : Fragment() {

    private var _binding: FragmentOnBoardPageBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val IS_LAST_ARG = "is_last"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardPageBinding.inflate(LayoutInflater.from(context),
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }


    private fun initViews() {

        binding.dotsIndicator.attachTo(
            (parentFragment as OnBoardListener).getViewPager()
        )

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(BoardModel.ARGS_KEY, BoardModel::class.java)
        } else {
            arguments?.getSerializable(BoardModel.ARGS_KEY) as BoardModel
        }

        val isLast = arguments?.getBoolean(IS_LAST_ARG)

        if (data != null) {
            binding.ivBoardPicture.setImageResource(data.imgResId)
            binding.tvBoardTitle.text = data.title
            binding.tvBoardDesc.text = data.description
        }

        if (isLast == true) {
            binding.apply {
                btnSkip.visibility = View.GONE
                btnNext.visibility = View.GONE
                btnStart.visibility = View.VISIBLE
            }
        } else {
            binding.btnStart.visibility = View.GONE
        }
    }

    private fun initListeners() {
        binding.btnSkip.setOnClickListener {
            (parentFragment as OnBoardListener).onSkipClicked()
        }

        binding.btnNext.setOnClickListener {
            (parentFragment as OnBoardListener).onNextClicked()
        }

        binding.btnStart.setOnClickListener {
            (parentFragment as OnBoardListener).onStartClicked()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface OnBoardListener {
        fun onSkipClicked()
        fun onNextClicked()
        fun onStartClicked()
        fun getViewPager(): ViewPager2
    }
}

