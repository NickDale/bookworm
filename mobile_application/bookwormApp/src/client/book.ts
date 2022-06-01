export interface Book {
    book_id: number;
    title: string;
    isbn: string;
    img_link: string;
    short_description: string;
    barcode: string;
    loanable: boolean;
    max_day: string;
    category: BookCategory;
    authors: Array<string>;
    authors_string: string;
}

export interface BookCategory {
    id: number;
    name: string;
}

export interface BarcodeImg {
    base64img: string;
}