import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { AuthService } from "../modules/auth";
import { Beneficiary } from "../model/beneficiary";

const API_URL = `${environment.apiUrl}/v1/beneficiaire`;

@Injectable({
    providedIn: 'root',
  })
  export class BeneficiariesService {
    private httpHeaders: HttpHeaders;

    constructor(private http: HttpClient, private auth : AuthService) {
         this.httpHeaders = new HttpHeaders({
            Authorization: `Bearer ${auth.getToken()}`,
            Accept: 'application/json',
          });
    }

    getBeneficiaries() {
      return this.http.get(`${API_URL}/${this.auth.currentUserValue?.id}`, {
        headers: this.httpHeaders
      });
    }

    removeBeneficiary(id : number) {
      return this.http.delete(`${API_URL}/${id}`, {
        headers: this.httpHeaders
      });
    }

    addBeneficiary(beneficiary : Beneficiary) {
        
      return this.http.post(`${API_URL}/create`, {
        clientName : beneficiary.clientName,
        phoneNumber : beneficiary.phoneNumber,
        userId : this.auth.currentUserValue?.id,
      }, {
        headers: this.httpHeaders
      } );
    }

  }