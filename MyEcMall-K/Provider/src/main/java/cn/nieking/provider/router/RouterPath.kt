package cn.nieking.provider.router

object RouterPath {

    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/loginActivity"
        }
    }

    class OrderCenter {
        companion object {
            const val PATH_ORDER_CONFIRM = "/orderCenter/orderConfirmActivity"
        }
    }

    class PaySDK {
        companion object {
            const val PATH_PAY = "/paySDK/cashRegisterActivity"
        }
    }

    class MessageCenter {
        companion object {
            const val PATH_MESSAGE_PUSH = "/messageCenter/push/push"
            const val PATH_MESSAGE_ORDER = "/messageCenter/orderDetailActivity/order"
        }
    }
}