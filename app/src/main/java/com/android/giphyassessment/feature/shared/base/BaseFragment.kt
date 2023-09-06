package com.android.giphyassessment.feature.shared.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.giphyassessment.R
import com.android.giphyassessment.utils.enums.ErrorEnum
import com.android.giphyassessment.utils.util.DialogUtils

/**
 * Created by Aashis on 06,September,2023
 */
abstract class BaseFragment<BD : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var binding: BD? = null
    abstract fun getViewBinding(): BD
    abstract fun getViewModel(): VM
    private var viewModel: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        initObservers()
    }


    private fun showMessageDialog(message: String?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(context, "", message, { okAction?.invoke() }, {})
    }

    private fun initObservers() {
        with(viewModel) {
            this?.errorResponse?.observe(viewLifecycleOwner) { dialogMessage ->
                showMessageDialog(dialogMessage.asString(context), okAction.value)
            }

            this?.errorEnumResponse?.observe(viewLifecycleOwner) { errorEnum ->
                showMessageDialog(errorEnum, okAction.value)
            }

            this?.isLogout?.observe(viewLifecycleOwner) { isLogOut ->
                if (isLogOut) {

                }
            }
        }
    }

    private fun showMessageDialog(errorEnum: ErrorEnum?, okAction: (() -> Unit?)?) {
        when (errorEnum) {
            ErrorEnum.NoWifi -> {
                showMessageDialog(
                    R.string.error_no_wifi, R.string.error_no_wifi_description, okAction
                )
            }
            ErrorEnum.SessionExpired -> {
                showMessageDialog(
                    R.string.session_expired, R.string.session_expired_description, okAction
                )
            }
            ErrorEnum.DefaultError -> {
                showMessageDialog(R.string.error, R.string.error_default, okAction)
            }
            else -> {
                showMessageDialog(
                    R.string.error, R.string.error_default, okAction
                )
            }
        }
    }

    private fun showMessageDialog(title: Int?, message: Int?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(context,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }
}