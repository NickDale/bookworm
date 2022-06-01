import {mount, shallowMount} from '@vue/test-utils'
import Home from "@/views/Home";
import Registration from "@/views/Registration";
import Books from "@/views/books/Books";


describe('Kategóriák komponens tesztelése', () => {
    test('Létrejött és meg is jelent a komponens', async () => {
        const wrapper = mount(Books)
        const input = wrapper.find('section[name="categories"]')
        expect(input.exists())
        // await input.setValue('Tesztelő')
        // expect(wrapper.vm.nev).toBe('Tesztelő')
    })

})