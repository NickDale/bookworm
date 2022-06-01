<template>
  <section id="about">
    <div class="about-wrapper container">
      <div class="about-text">
        <p class="small">Rólunk</p>
        <h2>2 évvel ezelőtt egy szívességgel indultunk</h2>
        <p>
          Azért hoztuk létre az alkalmazást, hogy egy gyermekkori kedves könyvtáros barátunknak segítsünk...
          Mára már több könyvtár is használja az alkalmazást és a folyamatos fejlesznek köszönhetően egyre többen szeretnék...
        </p>
      </div>
      <div class="about-img">
        <img alt="food" src="https://static.stylemagazin.hu/medias/80853/1200x1200/Konyvek-melyeket-legalabb-egyszer-olvasnod-kell_9944883d092abb2814bed1f41206fad2.jpg"/>
      </div>
    </div>
  </section>
</template>

<script>
export default {
};
</script>

<style>

#about {
  padding: 50px 0;
  background: #f5f5f7;
}

.about-wrapper {
  display: flex;
  flex-wrap: wrap;
}

#about h2 {
  font-size: 2.3rem;
}

#about p {
  font-size: 1.2rem;
  color: #555;
}

#about .small {
  font-size: 1.2rem;
  color: #666;
  font-weight: 600;
}

.about-img {
  flex: 1 1 400px;
  padding: 30px;
  transform: translateX(150%);
  animation: about-img-animation 1s ease-in-out forwards;
}

@keyframes about-img-animation {
  100% {
    transform: translate(0);
  }
}

.about-text {
  flex: 1 1 400px;
  padding: 30px;
  margin: auto;
  transform: translate(-150%);
  animation: about-text-animation 1s ease-in-out forwards;
}

@keyframes about-text-animation {
  100% {
    transform: translate(0);
  }
}

.about-img img {
  display: block;
  height: 400px;
  max-width: 100%;
  margin: auto;
  object-fit: cover;
  object-position: right;
}

</style>