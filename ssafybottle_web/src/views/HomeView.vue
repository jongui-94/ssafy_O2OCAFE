<template>
<section class="section-hero">
        <div class="hero">
          <div class="hero-text-box">
            <h1 class="heading-primary">WELCOME TO SAFFYBOTTLE</h1>
            <p class="hero-description">싸피보틀에 오신 것을 환영합니다.</p>
            <a href="javascript:void(0);" class="btn btn--full margin-right-sm" @click="navigateOrder">주문 하러 가기</a>          
          </div>
          <div class="hero-img-box">
            <picture>
              <img src="@/assets/store1.jpg" class="hero-img" alt="Woman enjoying food, meals in storage container, and food bowls on a table"/>
            </picture>
          </div>
        </div>
      </section>
</template>

<script>
export default {
  methods: {
    navigateOrder() {
      this.$router.push('/product-list')
    }
  }
}

</script>
