<template>
  <div class="container">
    <div class="content">
      <img
          alt="header-image"
          class="cld-responsive"
          src="https://media.istockphoto.com/photos/button-on-computer-keyboard-picture-id1146311489?k=20&m=1146311489&s=612x612&w=0&h=o-Q7wAa53Drsx-WkMoRs3RK_CPkrL5ZFlCU7Tsq6Y8Q=">
      <h1 class="form-title">Regisztráció</h1>
      <form @submit="onSubmit">
        <label id="lastnameError" v-if="lastnameError" class="error" for="lastname">
          A vezetéknév nem megfelelő formátumú!
        </label>
        <input id="lastname" v-model.trim="lastname"
               placeholder="Vezetéknév"
               required type="text" @change="checkLastname">

        <label id="firstnameError" v-if="firstnameError" class="error" for="firstname">
          A keresztnév nem megfelelő formátumú!
        </label>
        <input id="firstname" v-model.trim="firstname"
               placeholder="Keresztnév"
               required type="text" @change="checkFirstname">

        <label id="emailError" v-if="emailError" class="error" for="email">
          A megadott e-mail cím nem megfelelő formátumú!
        </label>
        <input id="email" v-model.trim="email"
               placeholder="E-mail cím"
               required type="email" @change="checkEmail">
        <div>
          <label>Nyelv:</label>
          <br>
          <select>
            <option selected>HU</option>
            <option disabled>EN</option>
          </select>
        </div>
        <br/>
        <input
            :disabled="!isFormValid"
            type="submit"
            v-bind:class="{ btn: valid }" value="Regisztráció"
        />
      </form>
    </div>
  </div>

</template>

<script>
import axios from 'axios'
import {BASE_URL} from "@/service/url";

const ONLY_LETTERS_REGEX = /^[a-zA-Z]+$/;
const EMAIL_ADDRESS_REGEX = /\S{3,}@\S+\.[a-z0-9]{2,}/;
export default {
  name: "Registation",
  data() {
    return {
      firstname: '',
      lastname: '',
      email: '',
      firstnameError: '',
      lastnameError: '',
      emailError: '',
      isFormValid: false
    }
  },
  methods: {
    checkFirstname() {
      this.firstnameError = this.firstname.trim().length < 3 || !ONLY_LETTERS_REGEX.test(this.firstname.trim())
    },
    checkLastname() {
      this.lastnameError = this.lastname.trim().length < 3 || !ONLY_LETTERS_REGEX.test(this.lastname.trim())
    },
    checkEmail() {
      this.emailError = !EMAIL_ADDRESS_REGEX.test(this.email);
    },
    onSubmit(e) {
      e.preventDefault()

      axios.post(BASE_URL+'/registration', {
        firstname: this.firstname,
        lastname: this.lastname,
        email: this.email
      })
          .then((response) => {
            console.log(response)
            alert("Kérlek ellenőrizd az e-mail fiókod!")
            window.location.replace("/")
          }).catch(err => alert(err.response.data.message)
      );
    }
  },
  computed: {
    valid() {
      this.isFormValid =
          !this.firstnameError &&
          this.firstname !== "" &&
          this.firstname !== undefined &&
          !this.lastnameError &&
          this.lastname !== "" &&
          this.lastname !== undefined &&
          !this.emailError &&
          this.email !== "" &&
          this.email !== undefined
    }
  }
};
</script>
<style scoped>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

html, body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #008cba;
  height: 100%;
}

.container {
  top: 130px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content {
  background-color: white;
  width: 500px;
  height: 500px;
}

img {
  width: 100%;
  height: 55%;
}

.form-title {
  padding: 10px 40px 0px;
}

form {
  padding: 0px 40px;
}

input[type=text], [type=email] {
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  width: 100%;
  margin: 8px 0;
  padding: 10px 0;
}

input[type=number] {
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin: 8px 0;
  padding: 5px 0;
}

input :hover {
  background-color: red;
}

select {
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin: 8px 0;
  padding: 5px 0;
  width: 50%;
}

.beside {
  display: flex;
  justify-content: space-between;
}

button {
  color: #ffffff;
  background-color: #4caf50;
  height: 40px;
  width: 25%;
  margin-top: 15px;
  cursor: pointer;
  border: none;
  border-radius: 2%;
  outline: none;
  text-align: center;
  font-size: 16px;
  text-decoration: none;
  -webkit-transition-duration: 0.4s;
  transition-duration: 0.4s;
}

button:hover {
  background-color: #333333;
}
</style>
