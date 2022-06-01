import {ILogin, LoggedUser} from "../client/login";
import {APPLICATION_JSON, AUTHORIZATION} from "../constans";
import {AbstractService} from "./abstract.service";
import {WebAPI} from "./webAPI";

export class LoginService extends AbstractService {

    login = async (data: ILogin): Promise<LoggedUser> => {
        const requestInit = {
            method: 'POST',
            headers: {
                'Application':'mobile',
                'Content-Type': APPLICATION_JSON,
            },
            body: JSON.stringify(data)
        } as RequestInit;

        let authToken = undefined;
        const loggedUserData = await fetch(`${this.baseUrl}/login`, requestInit)
            .then(response => {
                if (response.status === 200) {
                    authToken = response.headers.get(AUTHORIZATION) ?? undefined;
                    this.setToken(authToken);
                    return response.json();
                }
                return null;
            })
            .catch(ex => {
                WebAPI.storageService.clearStorage();
                new Error("Fetch fail!")
            });

        WebAPI.setLoggedUser(loggedUserData);
        return loggedUserData;
    }

    validateToken = async (): Promise<boolean> => {
        return await this.get('/validate')
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    passwordReset = async (email: string): Promise<boolean | Error> => {
        const data = {username: email, password: undefined};
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': APPLICATION_JSON
            },
            body: JSON.stringify(data)
        } as RequestInit

        return await fetch(this.baseUrl + '/password/reset', options)
            .then(response => response.status === 200)
            .catch(ex => new Error("Fetch fail!"));
    }

    private setToken(token: string | undefined) {
        this.storageService.setToken(token);
    }
}
