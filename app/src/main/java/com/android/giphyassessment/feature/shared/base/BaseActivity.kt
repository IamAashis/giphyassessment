package com.android.giphyassessment.feature.shared.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.giphyassessment.R
import com.android.giphyassessment.utils.enums.ErrorEnum
import com.android.giphyassessment.utils.util.DialogUtils

/**
 * Created by Aashis on 06,September,2023
 */
abstract class BaseActivity<BD : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected var binding: BD? = null
    abstract fun getViewBinding(): BD
    abstract fun getViewModel(): VM
    private var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        binding = getViewBinding()
        setContentView(binding?.root)
        initBaseObservers()
    }

    private fun showMessageDialog(message: String?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this, "", message, { okAction?.invoke() }, {})
    }

    private fun initBaseObservers() {
        with(viewModel) {
            this?.errorResponse?.observe(this@BaseActivity) { dialogMessage ->
                showMessageDialog(dialogMessage.asString(this@BaseActivity), okAction.value)
            }

            this?.errorEnumResponse?.observe(this@BaseActivity) { errorEnum ->
                showMessageDialog(errorEnum, okAction.value)
            }

            this?.isLogout?.observe(this@BaseActivity) { isLogOut ->
                if (isLogOut) {

                }
            }
        }
    }

    private fun showMessageDialog(errorEnum: ErrorEnum?, okAction: (() -> Unit?)?) {
        when (errorEnum) {
            ErrorEnum.NoWifi -> {
                showMessageDialog(
                    R.string.errorNoWifi, R.string.errorNoWifiDescription, okAction
                )
            }

            ErrorEnum.SessionExpired -> {
                showMessageDialog(
                    R.string.sessionExpired, R.string.sessionExpiredDescription, okAction
                )
            }

            ErrorEnum.DefaultError -> {
                showMessageDialog(R.string.error, R.string.errorDefault, okAction)
            }

            else -> {
                showMessageDialog(
                    R.string.error, R.string.errorDefault, okAction
                )
            }
        }
    }

    private fun showMessageDialog(title: Int?, message: Int?, okAction: (() -> Unit?)?) {
        DialogUtils.showAlertDialog(this,
            getString(title ?: 0),
            getString(message ?: 0),
            { okAction?.invoke() },
            {})
    }
}