import {JwtToken} from "./storage.keys";

export class StorageService {

    get = <T>(key: string): T | null => {
        const data: any = localStorage.getItem(key);

        if (data && data !== "undefined" && data !== "null") {
            return JSON.parse(data) as T;
        }
        return null;
    }

    getToken = (): string | null => {
        return localStorage.getItem(JwtToken);
    }

    setToken = (value: any): void => localStorage.setItem(JwtToken, value);

    set = (key: string, value: any): void => {
        const data: string = JSON.stringify(value);
        localStorage.setItem(key, data);
    }

    remove = (key: string): void => localStorage.removeItem(key);

    removeToken = (): void => localStorage.removeItem(JwtToken);

    clearStorage = (): void => localStorage.clear();

}