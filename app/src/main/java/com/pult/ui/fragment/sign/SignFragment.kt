package com.pult.ui.fragment.sign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pult.R
import com.pult.data.RSA
import kotlinx.android.synthetic.main.fragment_sign.*

class SignFragment : Fragment() {

    private lateinit var viewModel: SignViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignViewModel::class.java)

        viewModel.keyOwn.observe(requireActivity(), Observer {
            key_gen.text = it
        })

        btn_refresh_key.setOnClickListener {
            viewModel.getNewKeys()
            key_gen.text = RSA.getPrivateKey().toString()
        }

        btn_set_key.setOnClickListener {
            Snackbar.make(sign_root_frame, "Ключ скопирован в буфер", Snackbar.LENGTH_SHORT).show()
        }


    }


}