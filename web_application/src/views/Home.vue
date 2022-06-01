<template>
  <section id="showcase" class="showcase-area">
    <div class="showcase-container">
      <h1 id="home" class="main-title">BookWorm</h1>
      <p>Regisztrálj még ma!</p>
      <a class="btn btn-primary" href="/registration">Csináljuk !</a>
    </div>
  </section>

  <AboutUsComponent/>
  <CategoryComponent/>
  <Reviews/>
  <ContactComponent/>
  <footer id="footer">
    <h2>Bookworm &copy; 2022</h2>
  </footer>
</template>

<script>
import CategoryComponent from "@/components/CategoryComponent";
import ContactComponent from "@/components/ContactComponent";
import Reviews from "@/components/Reviews";
import AboutUs from "@/components/Reviews";
import AboutUsComponent from "@/components/AboutUsComponent";

export default {
  name: 'Home',
  components: {
    AboutUsComponent,
    CategoryComponent,
    ContactComponent,
    Reviews,
    AboutUs,
  }
}
</script>
<style>
#footer h2 {
  text-align: center;
  font-size: 0.9rem;
  padding: 1.6rem;
  font-weight: 500;
  color: #fff;
  background: rgb(65, 65, 65);
}

.showcase-area {
  height: 50vh;
  background-size: cover;
  background: linear-gradient(
      rgba(240, 240, 240, 0.144),
      rgba(255, 255, 255, 0.336)
  ), url("https://upload.wikimedia.org/wikipedia/commons/5/5a/Books_HD_%288314929977%29.jpg") no-repeat center;
}

.showcase-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 1.6rem;
}

.main-title {
  text-transform: uppercase;
  margin-top: 1.5em;
}
</style>