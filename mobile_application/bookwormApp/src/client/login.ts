export interface ILogin {
    username: string;
    password: string | undefined;
}

export interface LoggedUser {
    user_id: number;
    full_name: string;
    firstname: string;
    lastname: string;
    email: string;
    username: string;
    phone: string;
}

export interface PasswordChange {
    user_id: string;
    old_password: string
    new_password: string
}