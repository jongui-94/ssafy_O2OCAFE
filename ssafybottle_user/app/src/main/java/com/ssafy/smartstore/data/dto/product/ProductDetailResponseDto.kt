package com.ssafy.smartstore.data.dto.product

data class ProductDetailResponseDto(
    val img: String,
    val price: Int,
    val name: String,
    val type: String,
    val commentCnt: Int,
    val avg: Float = 0f,
    val rating: Int = 0,
    val sells: Int = 0,
    val comment: String = "",
    val commentId: Int = 0,
    val userName: String = "",
    val user_id: String = ""
) {
    fun toProductDetailDto() =
        ProductDetailDto(
            img = img,
            price = price,
            name = name,
            type = type,
            commentCnt = commentCnt,
            avg = avg,
            rating = rating,
            sells = sells
        )

    fun toProductCommentDto() =
        ProductCommentDto(
            comment = comment,
            commentId = commentId,
            userName = userName,
            user_id = user_id,
            rating = rating
        )
}