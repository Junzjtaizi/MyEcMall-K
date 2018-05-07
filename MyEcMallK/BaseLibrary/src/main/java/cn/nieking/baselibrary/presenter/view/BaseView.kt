package cn.nieking.baselibrary.presenter.view

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError()
}