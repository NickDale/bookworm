import {PasswordChange} from "../client/login";
import {ProfileUpdateRequest, User} from "../client/user";
import {AbstractService} from "./abstract.service";
import {BEARER} from "../constans";

export class UserService extends AbstractService {

    getProfileDetails = async (): Promise<User | undefined> => {
        return await this.get('/profiles/details')
            .then(async response => {
                if (response.status === 200) {
                    return response.json();
                }else {
                    this.storageService.clearStorage();
                }
            })
            .catch(ex => new Error("Fetch fail!"));
    }

    changePassword = async (data: PasswordChange): Promise<any> => {
        return await this.post('/profiles/password/change', data)
            .then(async response => {
                if (response.status === 200) return true;
                const data = await response.json();
                return data.message;
            })
            .catch(ex => new Error("Fetch fail!"));
    }

    checkEmail = async (email: string): Promise<boolean> => {
        return await this.get(`/profiles/check/${email}`)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    updateProfile = async (data: ProfileUpdateRequest): Promise<void> => {

        return await this.patch('/profiles/', data)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    uploadImg = async (file: File): Promise<void> => {
        const content = new FormData();
        if (file !== null && file !== undefined) {
            content.append("file", file);
        }

        const options =
            {
                body: content,
                method: "POST",
                headers: {
                    "Accept": "application/json",
                    'Authorization': `${BEARER + this.getToken()}`
                }
            } as RequestInit;

        return await fetch(this.baseUrl + '/profiles/image', options)
            .then(async response => {
                if (response.status === 200) return true;
                const data = await response.json();
                return data.message;
            })
            .catch(ex => new Error("Fetch fail!"));
    }

}

