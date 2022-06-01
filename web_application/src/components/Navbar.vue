<template>
  <nav class="navbar">
    <div class="navbar-container container">
      <input type="checkbox" name="" id="">
      <div class="hamburger-lines">
        <span class="line line1"></span>
        <span class="line line2"></span>
        <span class="line line3"></span>
      </div>
      <ul class="menu-items">
        <li><a href="/#about">Rólunk</a></li>
        <li><a href="/books">Könyvek</a></li>
        <li><a href="/#categories">Kategoriák</a></li>
        <li><a href="/#about-us">Vélemények</a></li>
        <li><a href="/#contact">Kapcsolat</a></li>
      </ul>
      <h1 class="logo">
        <a href="/">
          BookWorm
        </a>
      </h1>
    </div>
  </nav>
</template>

<script>
export default {
};
</script>

<style scoped>
a, a:hover {
   color: inherit;
   text-decoration: none;
}

.logo {
  order: 1;
  font-size: 2.3rem;
  margin-bottom: 0.5rem;
}

</style>