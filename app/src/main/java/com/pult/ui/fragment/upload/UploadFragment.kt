package com.pult.ui.fragment.upload

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pult.app.utils.PrefUtils
import com.pult.data.DNS
import com.pult.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {

    private lateinit var viewModel: UploadViewModel
    var binding: FragmentUploadBinding? = null
    private var targetDNS = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message: String? = PrefUtils().getDNSRequest()

        binding?.uploadMessage?.text = message
        binding?.uploadButton?.setOnClickListener {
            if (targetDNS.isEmpty())
                Snackbar.make(binding?.root!!, "Введите адрес DNS запроса!", Snackbar.LENGTH_SHORT).show()
            else {
                DNS(message!!, targetDNS)
                Snackbar.make(binding?.root!!, "Данные отправлены!", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding?.uploadInput?.addTextChangedListener(itListener)
    }

    private val itListener = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            targetDNS = s.toString()
        }
        override fun afterTextChanged(s: Editable?) { }
    }

}