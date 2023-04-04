package com.example.task1fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Fragment2 : Fragment() {

    private lateinit var actionString: String

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText

    private lateinit var actionButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        actionString  = arguments?.getString(Fragment1.action).toString()

        val view = inflater.inflate(R.layout.fragment_2, container, false)

        editText1 = view.findViewById(R.id.edit_text1)
        editText2 = view.findViewById(R.id.edit_text2)

        actionButton = view.findViewById(R.id.action_button)

        actionButton.text = actionString

        if (actionString == "REVERSE") {
            editText2.visibility = View.GONE
        } else {
            editText2.hint = getString(R.string.enter_a_text) + " for ${actionString.lowercase()}"
        }

        actionButton.setOnClickListener {
            if (editText1.text.trim().isNotEmpty() && (editText2.text.trim().isNotEmpty() || actionString == "REVERSE")) {
                val text1 = editText1.text.toString()

                if (actionString == "UPPERCASE" || actionString == "SPLIT") {
                    val text2 = editText2.text.toString()
                    clickForUppercaseOrSplit(text1, text2)
                } else {
                    clickForReverseOrAppend(text1)
                }
            } else {
                Toast.makeText(activity, "Complete all box", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun clickForUppercaseOrSplit(text1: String, text2: String) {
        val bundle = Bundle()

        if (text1.contains(text2) ||
            (actionString == "UPPERCASE" &&
                    (text1.uppercase()).contains((text2.uppercase())))) {
            bundle.putString(Fragment1.text1, text1)
            bundle.putString(Fragment1.text2, editText2.text.toString())
            bundle.putString(Fragment1.action, actionString)
            bundle.putString(
                Fragment1.result,
                when (actionString) {
                    "SPLIT" -> text1.replace(text2, " $text2", false)
                    "UPPERCASE" -> text1.replace(text2, text2.uppercase(), true)
                    else -> ""
                }
            )
            parentFragmentManager.setFragmentResult(Fragment1.resultText, bundle)
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(activity, "TEXT2 is not contained in TEXT1", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clickForReverseOrAppend(text1: String){
        val bundle = Bundle()

        bundle.putString(Fragment1.text1, text1)
        bundle.putString(Fragment1.action, actionString)
        bundle.putString(
            Fragment1.result,
            when(actionString) {
                "APPEND" -> text1 + editText2.text.toString()
                "REVERSE" -> text1.reversed()
                else -> ""
            }
        )
        if(actionString == "APPEND") {
            bundle.putString(Fragment1.text2, editText2.text.toString())
        }

        parentFragmentManager.setFragmentResult(Fragment1.resultText, bundle)
        parentFragmentManager.popBackStack()
    }

}