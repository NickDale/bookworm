<template>
  <section id="contact">
    <div class="contact-container container">
      <div class="contact-img">
        <img alt="" src="https://ziment.hu/admin/images/write.png"/>
      </div>

      <div class="form-container">
        <h2>Lépjünk kapcsolatba</h2>
        <label v-if="nameError" class="error" for="name">
          A név nem megfelelő formátumú!
        </label>
        <input id="name" v-model.trim="request.name"
               @change="checkName"
               placeholder="Név" type="text"/>

        <label v-if="emailError" class="error" for="email">
          A megadott e-mail cím nem megfelelő formátumú!
        </label>
        <input id="email" v-model.trim="request.email"
               @change="checkEmail"
               placeholder="E-mail cím" type="email"/>

        <label v-if="msgError" class="error" for="email">
          Légyszi írj valami szépet!
        </label>
        <textarea
            @change="checkMsg"
            v-model.trim="request.msg"
            cols="30"
            maxlength="500"
            placeholder="Mondd el véleményed vagy kérdéese..."
            rows="6"
            @keypress="countChar">
        </textarea>
        <div>
          <label>
            Hátralévő karakterek száma: {{ availableCharacterSize }}
          </label>
        </div>
        <input :disabled="!isFormValid"
                class="btn btn-primary"
                type="submit"
                v-bind:class="{ btn: valid }"
                value="Küldés" @click="sendMail()" />
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import {BASE_URL} from "@/service/url";

const MAX_DESCRIPTION_SIZE = 500;
const ONLY_LETTERS_REGEX = /^[a-zA-Z]+$/;
const EMAIL_ADDRESS_REGEX = /\S{3,}@\S+\.[a-z0-9]{2,}/;

export default {
  data() {
    const request = {
      name: '',
      email: '',
      msg: ''
    };
    return {
      nameError: '',
      msgError: '',
      emailError: '',
      isFormValid: false,
      request,
      availableCharacterSize: MAX_DESCRIPTION_SIZE,
    };
  },
  methods: {
    countChar() {
      let len = this.request.msg.length;
      let reminder = MAX_DESCRIPTION_SIZE - 1 - len;
      if (reminder < 0) {
        this.request.msg = this.request.msg.substring(
            0,
            MAX_DESCRIPTION_SIZE - 1
        );
      }
      this.availableCharacterSize = reminder >= 0 ? reminder : 0;
    },
    checkName() {
      this.nameError = this.request.name.trim().length < 3 || !ONLY_LETTERS_REGEX.test(this.request.name.trim().replaceAll(" ",""))
    },

    checkEmail() {
      this.emailError = !EMAIL_ADDRESS_REGEX.test(this.request.email);
    },
    checkMsg(){
      this.msgError = this.request.email === undefined  || this.request.email === "";
    },
    sendMail() {

      axios.post(BASE_URL+'/contacts/mail', {
        name: this.request.name,
        message: this.request.msg,
        email: this.request.email
      })
          .then((response) => {
            console.log(response)
            window.location.replace("/")
          }).catch(err => alert(err.response.data.message)
      );
    }
  },
  computed: {
    valid() {
      this.isFormValid =
          !this.nameError &&
          this.request.name !== "" &&
          this.request.name !== undefined &&
          !this.msgError &&
          this.request.msg !== "" &&
          this.request.msg !== undefined &&
          !this.emailError &&
          this.request.email !== "" &&
          this.request.email !== undefined

      console.log(this.isFormValid)
    }
  }

};
</script>

<style>
#contact {
  padding: 5rem 0;
  background: rgb(226, 226, 226);
}

.contact-container {
  display: flex;
  background: #fff;
}

.contact-img {
  width: 50%;
}

.contact-img img {
  display: block;
  height: 400px;
  width: 100%;
  object-position: center;
  object-fit: cover;
}

.form-container {
  padding: 1rem;
  width: 50%;
  margin: auto;
}

.form-container input {
  display: block;
  width: 100%;
  border: none;
  border-bottom: 2px solid #ddd;
  padding: 1rem 0;
  box-shadow: none;
  outline: none;
  margin-bottom: 1rem;
  color: #444;
  font-weight: 500;
}

.form-container textarea {
  display: block;
  width: 100%;
  border: none;
  border-bottom: 2px solid #ddd;
  color: #444;
  outline: none;
  padding: 1rem 0;
  resize: none;
}

.form-container h2 {
  font-size: 2.7rem;
  font-weight: 500;
  color: #444;
  margin-bottom: 1rem;
  margin-top: -1.2rem;
}

.form-container a {
  font-size: 1.3rem;
}

</style>