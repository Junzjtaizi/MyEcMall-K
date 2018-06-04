package cn.nieking.goodscenter.data.protocol

data class Category(val id: Int,
                    val categoryName: String,
                    val categoryIcon: String = "",
                    val parentId: Int,
                    var isSelected: Boolean = false)