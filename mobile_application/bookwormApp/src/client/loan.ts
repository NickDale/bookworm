import { Book } from "./book";

export interface LoanItem {  
    book: Book;
    loan_id: number;
    user_id: number;
    creation_date: string;
    estimate_end_date: string;
    extended_end_date: string;
    end_date: string;
}