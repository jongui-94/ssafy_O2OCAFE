package com.ssafy.smartstore.utils

import com.ssafy.smartstore.data.dto.card.CardHistory
import com.ssafy.smartstore.data.dto.promotion.Promotion

val dummyPromotions = listOf(
    Promotion("HOLIDAY COLLECTION", "promotion1.png"),
    Promotion("SSAFY COFFEE 제주 지점 OPEN", "promotion2.png")
)

val dummyCardHistories = listOf(
    CardHistory("카드 충전", "2022.05.21 20:34", 50000, true),
    CardHistory("압구정점 사용", "2022.05.21 16:32", 20000, false),
    CardHistory("구미점 사용", "2022.05.21 14:24", 34000, false),
    CardHistory("홍대점 사용", "2022.05.21 08:56", 20000, false),
    CardHistory("카드 충전", "2022.05.20 21:24", 50000, true),
    CardHistory("인계동점 사용", "2022.05.20 19:04", 50000, false),
    CardHistory("홍대점 사용", "2022.05.20 15:13", 50000, false),
    CardHistory("카드 충전", "2022.05.20 10:55", 50000, true)
)