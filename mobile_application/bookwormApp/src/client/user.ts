export interface User {
    user_id: number;
    firstname: string;
    middle_name: string;
    lastname: string;
    phone_number: string;
    full_name: string;
    birth_date: string;
    email: string;
    username: string;
    postcode: string;
    city: string;
    street: string,
    street_number: string,
    language: string,
    qr_code: string,
    user_type: UserType;
    img: string;
}

export interface UserType {
    id: number;
    value: string;
}

export interface ProfileUpdateRequest {
    email: string;
    phone: string;
    firstname: string;
    lastname: string;
}