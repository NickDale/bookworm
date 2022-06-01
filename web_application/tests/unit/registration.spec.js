import {shallowMount} from "@vue/test-utils";
import Registration from "@/views/Registration";

describe('Regisztrációs form tesztelése', () => {

    test('Keresztnév mező megfelelő', async () => {
        const wrapper = shallowMount(Registration)
        const input = wrapper.find('input[id="firstname"]')
        expect(input.exists())
    })

    test('Vezetéknév mező megfelelő', async () => {
        const wrapper = shallowMount(Registration)
        const input = wrapper.find('input[id="lastname"]')
        expect(input.exists())
    })

    test('Email mező megfelelő', async () => {
        const wrapper = shallowMount(Registration)
        const input = wrapper.find('input[id="email"]')
        expect(input.exists())
    })

    test('A regisztréció gomb alapjáraton inaktív', async () => {
        const wrapper = shallowMount(Registration)
        const input = wrapper.find('input[type="submit"]')
        expect(input.element.disabled).toBe(true)
    })

    test('A regisztráció kitöltése  - hibás e-mail', async () => {
        const wrapper = shallowMount(Registration)

        let emailError = wrapper.find('label[id="emailError"]')
        expect(emailError.exists()).toBe(false)

        const email = wrapper.find('input[id="email"]')
        await email.setValue("teszt");
        await email.trigger('change')

        emailError = wrapper.find('label[id="emailError"]')
        expect(emailError.exists()).toBe(true)
        expect(emailError.text()).toBe("A megadott e-mail cím nem megfelelő formátumú!")

    })

    test('A regisztráció kitöltése - hiányzó név', async () => {
        const wrapper = shallowMount(Registration)

        let nameError = wrapper.find('label[id="lastnameError"]')
        expect(nameError.exists()).toBe(false)

        const email = wrapper.find('input[id="lastname"]')
        await email.setValue("");
        await email.trigger('change')

        nameError = wrapper.find('label[id="lastnameError"]')
        expect(nameError.exists()).toBe(true)
        expect(nameError.text()).toBe("A vezetéknév nem megfelelő formátumú!")
    })

    test('A regisztráció kitöltése - sikeres kitöltés', async () => {
        const wrapper = shallowMount(Registration)

        let button = wrapper.find('input[type="submit"]')

        expect(button.exists()).toBe(true)
        expect(button.element.disabled).toBe(true)

        const lastname = wrapper.find('input[id="lastname"]')
        const firstname = wrapper.find('input[id="lastname"]')
        const email = wrapper.find('input[id="email"]')

        await lastname.setValue("Teszt");
        await lastname.trigger('change')

        await firstname.setValue("Elek");
        await firstname.trigger('change')

        await email.setValue("telek@gmail.com");
        await email.trigger('change')

        const firstnameError = wrapper.find('label[id="firstnameError"]')
        expect(firstnameError.exists()).toBe(false)

        const lastnameError = wrapper.find('label[id="lastnameError"]')
        expect(lastnameError.exists()).toBe(false)

        const emailError = wrapper.find('label[id="emailError"]')
        expect(emailError.exists()).toBe(false)

    })

});