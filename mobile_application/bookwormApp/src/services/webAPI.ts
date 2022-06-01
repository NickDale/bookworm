import {StorageService} from "./storage/storage.service";
import {BookService} from "./book.service";
import {UserService} from "./user.service";
import {LoginService} from "./login.service";
import {LoanService} from "./loan.service";
import {USER} from "./storage/storage.keys";
import {LoggedUser} from "../client/login";

export namespace WebAPI {

    export const storageService: StorageService = new StorageService();
    export const bookService: BookService = new BookService();
    export const userService: UserService = new UserService();
    export const loginService: LoginService = new LoginService();
    export const loanService: LoanService = new LoanService();

    export const logout = () => {
        storageService.clearStorage();
    }

    export const setLoggedUser = (user: LoggedUser): void => storageService.set(USER, user);

    export const getLoggedUser = (): LoggedUser => <LoggedUser>storageService.get(USER);

}