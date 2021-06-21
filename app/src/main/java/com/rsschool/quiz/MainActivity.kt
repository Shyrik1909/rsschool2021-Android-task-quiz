package com.rsschool.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsschool.quiz.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), FragmentInterface, ResultInterface {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.hostFragment.id, QuizFragment())
                .commit()
        }
    }

    override fun replaceFragment(reset: Boolean, questionCount: Int?, currentAnswers: MutableMap<Int?, String>?) {
        supportFragmentManager.beginTransaction()
            .replace(binding.hostFragment.id, QuizFragment.newInstance(reset, questionCount, currentAnswers))
            .commit()
    }

    override fun openResultFragment(result: Int, answers: Map<Int?, String>) {
        supportFragmentManager.beginTransaction()
            .replace(binding.hostFragment.id, ResultFragment.newInstance(result, answers))
            .commit()
    }

    override fun close() {
        finish()
        exitProcess(0)
    }

    override fun reset(reset: Boolean) {
        replaceFragment(true)
    }

    override fun share(message: String) {
        val shareIntent = Intent()
        shareIntent.apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Quiz Result")
            putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
    }
}