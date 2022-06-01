import {LoanItem} from "../client/loan";
import {AbstractService} from "./abstract.service";

export class LoanService extends AbstractService {

    getLoanItemsByUserId = async (userId: number, page: number, size: number, onylActive: boolean): Promise<LoanItem[]> => {
        let url = `/loans/?userId=${userId}&page=${page}&size=${size}`;
        if (onylActive) {
            url += "&onlyActive=true";
        } else {
            url += "&onlyHistory=true";
        }
        return await this.get(url)
            .then(response => response.json())
            .catch(ex => new Error("Fetch fail!"));
    }

    extendTheLoanByLoanId = async (loanId: number): Promise<string> => {
        return await this.put(`/loans/extend/${loanId}`)
            .then(async response => {
                if (response.status === 200) return "Sikeresen meghosszabítottuk!";
                if (response.status === 406) {
                    return "A hosszabbítás nem engedélyezett";
                } else if (response.status === 412) {
                    return "Ez a kölcsönzés 1-szer már lett hosszabítva. <br /> Jelenleg több nem engedélyezett.";
                } else {
                    const data = await response.json();
                    return data.message;
                }
            })
            .catch(ex => "Ismeretlen hiba");
    }
}

