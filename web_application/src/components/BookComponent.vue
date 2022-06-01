<template>
  <router-link :to="{	name: 'Details', params: { id: book.book_id } }"
               class="event-link"
  >
    <div class="event-card">
      <div class="card-body">
        <h3 class="card-title">{{ book.title }}</h3>
        <div class="status">
          <span v-if="statusColorR" class="status-icon-r"></span>
          <span v-else-if="statusColorG" class="status-icon-g"></span>
          <span>{{ statusColorR ? 'Csak helyben olvasható' : 'Kölcsönözhető' }}</span>
        </div>
        <img :src="book.img_link" class="img-fluid"/>
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      status: this.book.loanable
    };
  },
  computed: {
    statusColorR() {
      return !this.status;
    },
    statusColorG() {
      return this.status;
    }
  }
};
</script>

<style scoped>
.event-card {
  width: 320px;
  height: 550px;
  background: rgb(60, 62, 68);
  border-radius: 20px;
  overflow: hidden;
  padding: 10px 15px;
  margin-bottom: 24px;
  margin-right: 10px;
  transition: all 0.2s linear;
  cursor: pointer;
  color: white;
}

.status {
  display: flex;
  justify-content: left;
}

.status-icon-r,
.status-icon-g {
  height: 0.5rem;
  width: 0.5rem;
  margin: auto 1.4rem auto 0;
  background: rgb(214, 61, 46);
  border-radius: 50%;
}

.status-icon-g {
  background: rgb(92 199 12);
}

img {
  display: flex;
  justify-content: center;
  margin-top: 5%;
  max-width: 250px;
  height: 350px;
}

.event-card:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2),
  0 1px 15px 0 rgba(0, 0, 0, 0.19);
}

.event-card > .title {
  margin: 0;
}

.event-link {
  color: black;
  text-decoration: none;
  font-weight: 100;
}
</style>
