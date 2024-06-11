import { Component } from "@angular/core";
import { AuthService, UserModel } from "src/app/modules/auth";

@Component({
    selector: 'app-credit-card',
    templateUrl: './credit-card.component.html',
    styleUrls: ['./credit-card.component.scss']
})
export class CreditCardComponent {
    authenticatedUser : UserModel | undefined
    constructor(private auth : AuthService) {
        this.authenticatedUser = this.auth.currentUserValue
     }

    cardNumber: string = "";
    expirationDate: string = "";
    cardHolderName: string = "";

    ngOnInit(){
        this.cardHolderName = this.authenticatedUser?.account.creditCard.cardHolderName.toUpperCase() ?? "Mr. Unknown User"
        this.cardNumber = this.authenticatedUser?.account.creditCard.cardNumber.slice(-4) ?? "XXXX"
        this.expirationDate = this.authenticatedUser?.account.creditCard.expirationDate ?? "XX/XX"
    }

    
}