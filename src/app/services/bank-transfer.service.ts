import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { AuthService } from "../modules/auth";
import { Router } from "@angular/router";
import { Transaction } from "../model/transaction";


const API_URL = `${environment.apiUrl}/v1/cmi/transaction`;

@Injectable({
    providedIn: 'root',
  })
  export class BankTransferService {

    constructor(private http : HttpClient, private auth : AuthService, private router : Router){}

    saveTransaction(transaction : Transaction) {
        return this.http.post(`${API_URL}/create`, {
            amount : transaction.amount,
            receiverPhone : transaction.receiverPhone,
            senderPhone : this.auth.currentUserValue?.phoneNumber,
            impayeId : null
        });
    }

    getTransactions(){
        return this.http.get(`${API_URL}/${this.auth.currentUserValue?.phoneNumber}`);
    }



  }