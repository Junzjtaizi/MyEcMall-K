package cn.nieking.messagecenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nieking.baselibrary.ext.startLoading
import cn.nieking.baselibrary.ui.fragment.BaseMvpFragment
import cn.nieking.messagecenter.R
import cn.nieking.messagecenter.data.protocol.Message
import cn.nieking.messagecenter.injection.component.DaggerMessageComponent
import cn.nieking.messagecenter.injection.module.MessageModule
import cn.nieking.messagecenter.presenter.MessagePresenter
import cn.nieking.messagecenter.presenter.view.MessageView
import cn.nieking.messagecenter.ui.adapter.MessageAdapter
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun injectComponent() {
        DaggerMessageComponent.builder()
                .activityComponent(mActivityComponent)
                .messageModule(MessageModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mMessageRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter(activity)
        mMessageRv.adapter = mAdapter
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getMessageList()
    }

    override fun onGetMessageListResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
}