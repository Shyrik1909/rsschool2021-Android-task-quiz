package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var score = 0
    private var clickListener: MainActivity? = null
    private var answers: MutableMap<Int, String?> = mutableMapOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg: Bundle? = arguments
        if (arg != null) {
            score = arg.getInt(KEY_RESULT)
            for (i in 1..5) {
                val value = arg.getString(i.toString())
                answers[i] = value
            }
        }
        updateScore()

        binding.shareIv.setOnClickListener {
            clickListener?.share(generateQuizReport())
        }

        binding.resetIv.setOnClickListener {
            clickListener?.reset(true)
        }

        binding.closeIv.setOnClickListener {
            clickListener?.close()
        }
    }

    private fun updateScore() {
        binding.resultTv.text = "Your result: $score %"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateQuizReport(): String {
        var message = "Your result: $score %\n\n"
        for (i in 1..5) {
            message += "$i) ${DataBase.questionsList[i].toString()}\n"
            message += "Your answer: ${answers[i]?.let { DataBase.answersList[i]?.get(it.toInt() - 1) }}\n\n"

        }
        return message
    }

    companion object {
        private const val KEY_RESULT = "key_result"

        fun newInstance(result: Int, answers: Map<Int?, String>): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(KEY_RESULT, result)
            for (answer in answers) {
                args.putString(answer.key.toString(), answer.value)
            }

            fragment.arguments = args
            return fragment
        }
    }
}

