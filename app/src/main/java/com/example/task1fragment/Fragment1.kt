package com.example.task1fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.task1fragment.databinding.Fragment1Binding


class Fragment1 : Fragment() {

    private lateinit var binding: Fragment1Binding

    private lateinit var value: Value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_1, container, false)

        if(savedInstanceState?.getBoolean("isResult") == true){
            binding.appendButton.visibility = View.GONE
            binding.splitButton.visibility = View.GONE
            binding.reverseButton.visibility = View.GONE
            binding.uppercaseButton.visibility = View.GONE
            binding.resetButton.visibility = View.VISIBLE

            binding.resultText.visibility = View.VISIBLE

            val resultValue = savedInstanceState.getStringArrayList("result")

            if(resultValue!=null) {
                value = Value(
                    resultValue[0],
                    resultValue[1],
                    resultValue[2],
                    resultValue[3]
                )
                binding.result = value
            }
        }

        parentFragmentManager.setFragmentResultListener("result",this) { _, bundle ->

            value = Value(
                bundle.getString("text1").toString(),
                bundle.getString("text2"),
                bundle.getString("result").toString(),
                bundle.getString("action").toString(),
            )
            binding.result = value

            binding.appendButton.visibility = View.GONE
            binding.splitButton.visibility = View.GONE
            binding.reverseButton.visibility = View.GONE
            binding.uppercaseButton.visibility = View.GONE
            binding.resetButton.visibility = View.VISIBLE

            binding.resultText.visibility = View.VISIBLE

        }
        val button = listOf(
            binding.appendButton,
            binding.splitButton,
            binding.reverseButton,
            binding.uppercaseButton,)

        for(click in button){
            click.setOnClickListener {
                clickButton(click)
            }
        }

        binding.resetButton.setOnClickListener{
            binding.appendButton.visibility = View.VISIBLE
            binding.splitButton.visibility = View.VISIBLE
            binding.reverseButton.visibility = View.VISIBLE
            binding.uppercaseButton.visibility = View.VISIBLE
            binding.resetButton.visibility = View.GONE

            binding.resultText.visibility = View.GONE
        }

        return binding.root
    }

    private fun clickButton(click: Button) {
        val str = when(click) {
            binding.appendButton -> binding.appendButton.text
            binding.splitButton -> binding.splitButton.text
            binding.reverseButton -> binding.reverseButton.text
            binding.uppercaseButton -> binding.uppercaseButton.text
            else -> ""
        }

        val bundle = Bundle()
        bundle.putString("bundleKey", str.toString())
        val fragmentB = Fragment2()
        fragmentB.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragmentB)
            addToBackStack(null)
            commit()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(binding.resultText.visibility  == View.VISIBLE) {
            outState.putBoolean("isResult", true)
            outState.putStringArrayList("result", arrayListOf(value.text1, value.text2, value.result, value.action))
        }
    }

}