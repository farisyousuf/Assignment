package com.example.enbdassignment.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    abstract fun provideViewModelClass(): Class<VM>
    abstract fun layoutId(): Int
    abstract val bindingVariable: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewBinding: VB
    protected lateinit var viewModel: VM

    protected var isUseCustomViewModelFactory: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = if (isUseCustomViewModelFactory)
            ViewModelProvider(this, viewModelFactory).get(provideViewModelClass())
        else
            ViewModelProvider(this).get(provideViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.setVariable(bindingVariable, viewModel)
        viewBinding.executePendingBindings()
    }
}