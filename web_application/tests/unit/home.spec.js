import {shallowMount} from "@vue/test-utils";
import Home from "@/views/Home";
import CategoryComponent from "@/components/CategoryComponent";
import ContactComponent from "@/components/ContactComponent";
import AboutUsComponent from "@/components/AboutUsComponent";

describe('Home komponens komponens függőségeinek tesztelése', () => {

    test('Kateóriák betöltése', async () => {
        const wrapper = shallowMount(Home)
        const catComp = wrapper.findComponent(CategoryComponent)
        expect(catComp.exists()).toBe(true)
    })

    test('Rólunk betöltése', async () => {
        const wrapper = shallowMount(Home)
        const about = wrapper.findComponent(AboutUsComponent)
        expect(about.exists()).toBe(true)
    })


    test('Kapcsolat betöltése', async () => {
        const wrapper = shallowMount(Home)
        const contact = wrapper.findComponent(ContactComponent)
        expect(contact.exists()).toBe(true)
    })
});