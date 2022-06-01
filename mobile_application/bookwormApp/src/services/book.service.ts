import {BarcodeImg, Book} from "../client/book";
import {AbstractService} from "./abstract.service";

export class BookService extends AbstractService {

    listBooks = async (page: number, size: number): Promise<Book[]> => {
        return await this.get(`/books/?page=${page}&size=${size}`)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    getBookById = async (bookId: string): Promise<Book> => {
        return await this.get(`/books/${bookId}`)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    getBarcodeImageByBookId = async (bookId: string | number): Promise<BarcodeImg> => {
        return await this.get(`/books/barcode/${bookId}`)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }
}

