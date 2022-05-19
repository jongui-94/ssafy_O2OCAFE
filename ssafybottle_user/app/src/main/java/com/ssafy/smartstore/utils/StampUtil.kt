package com.ssafy.smartstore.utils

fun getStampMessages(stamp : Int) : Array<String> {
    val res = Array<String>(5) {""}

    when(stamp) {
        // 씨앗
        in 0..50 -> {
            res[0] = "씨앗 ${(stamp/10) + 1}단계"
            res[1] = "${stamp%10}"
            res[2] = "10"
            res[3] = "다음 레벨까지 ${10 - (stamp%10)}잔 남았습니다."
            res[4] = "seeds.png"
        }
        // 꽃
        in 51..125 -> {
            res[0] = "꽃 ${((stamp-50)/15) + 1}단계"
            res[1] = "${(stamp-50)%15}"
            res[2] = "15"
            res[3] = "다음 레벨까지 ${15 - ((stamp-50)%15)}잔 남았습니다."
            res[4] = "flower.png"
        }
        // 열매
        in 126..225 -> {
            res[0] = "열매 ${((stamp-125)/20) + 1}단계"
            res[1] = "${(stamp-125)%20}"
            res[2] = "20"
            res[3] = "다음 레벨까지 ${20 - ((stamp-125)%20)}잔 남았습니다."
            res[4] = "coffee_fruit.png"
        }
        // 커피콩
        in 226.. 350 -> {
            res[0] = "열매 ${((stamp-225)/25) + 1}단계"
            res[1] = "${(stamp-225)%25}"
            res[2] = "25"
            res[3] = "다음 레벨까지 ${25 - ((stamp-225)%25)}잔 남았습니다."
            res[4] = "coffee_fruit.png"
        }
        else -> {
            res[0] = "나무 단계"
            res[1] = "10"
            res[2] = "10"
            res[3] = "다음 레벨이 없습니다."
            res[4] = "coffee_tree.png"
        }
    }

    return res
}