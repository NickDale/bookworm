<template>
  <section id="about-us">
    <h2 class="about-us-title">Rólunk mondták</h2>
    <div class="about-us-container container">
      <div v-for="review in reviews" v-bind:key="review" class="about-us-box">
        <div class="customer-detail">
          <div class="customer-photo">
            <img :src = review.photo alt="">
            <p class="customer-name">{{ review.name }}</p>
          </div>
        </div>
        <div class="star-rating">
          <span v-for="i in 5" v-bind:key="i" class="fa fa-star checked"></span>
        </div>
        <p class="about-us-text">
          {{ review.message }}
        </p>
      </div>
    </div>
  </section>
</template>

<script>

import ReviewData from '../assets/reviews.json'
export default {

  setup() {
    const reviews = [];
    let index = 0;
    do {
      let actualData = ReviewData[Math.floor(Math.random() * ReviewData.length)];
      if (reviews.filter(review => review.id === actualData.id).length !== 0) {
        continue
      }
      reviews[index++] = actualData
    } while (reviews.length < 3)

    return {
      reviews
    };
  },

};
</script>

<style>

.about-us-container {
  display: flex;
  justify-content: space-between;
  font-size: 1.4rem;
  padding: 1rem;
}

#about-us {
  padding: 5rem 0;
  background: rgba(243, 243, 243);
}

.about-us-title {
  text-align: center;
  font-size: 2.8rem;
  font-weight: 400;
  color: #555;
}

.about-us-box .checked {
  color: #ff9529;
}

.about-us-box .about-us-text {
  margin: 1rem 0;
  color: #444;
}

.about-us-box {
  text-align: center;
  padding: 1rem;
}

.customer-photo img {
  display: block;
  width: 150px;
  height: 150px;
  object-fit: cover;
  object-position: center;
  border-radius: 50%;
  margin: auto;
}
</style>