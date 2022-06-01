import {APPLICATION_JSON, BEARER} from "../constans";
import {BASE_URL} from "./storage/base.url";
import {StorageService} from "./storage/storage.service";

export class AbstractService {

    protected storageService: StorageService = new StorageService();
    protected baseUrl: string;

    constructor() {
        this.baseUrl = BASE_URL;
    }

    getToken = (): string | null => this.storageService.getToken();

    getRequestInit = {
        method: 'GET',
        headers: {
            'Content-Type': APPLICATION_JSON,
            'Authorization': `${BEARER + this.getToken()}`
        }
    } as RequestInit;

    postRequestInit = {
        method: 'POST',
        headers: {
            'Content-Type': APPLICATION_JSON,
            'Authorization': `${BEARER + this.getToken()}`
        }
    } as RequestInit

    patchRequestInit = {
        method: 'PATCH',
        headers: {
            'Content-Type': APPLICATION_JSON,
            'Authorization': `${BEARER + this.getToken()}`
        }
    } as RequestInit

    putRequestInit = {
        method: 'PUT',
        headers: {
            'Content-Type': APPLICATION_JSON,
            'Authorization': `${BEARER + this.getToken()}`
        }
    } as RequestInit

    async get(url: string): Promise<Response> {
        const header = this.getRequestInit;
        return await fetch(this.baseUrl + url, header);
    }

    async post(url: string, data: any): Promise<Response> {
        const header = this.postRequestInit;
        header.body = JSON.stringify(data)
        return await fetch(this.baseUrl + url, header);
    }

    async patch(url: string, data: any): Promise<Response> {
        const header = this.patchRequestInit;
        header.body = JSON.stringify(data);
        return await fetch(this.baseUrl + url, header);
    }

    async put(url: string, data?: any): Promise<Response> {
        const header = this.putRequestInit;
        header.body = JSON.stringify(data)
        return await fetch(this.baseUrl + url, header);
    }

}

