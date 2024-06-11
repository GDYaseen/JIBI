import { AuthModel } from './auth.model';

export class UserModel extends AuthModel {
        id: number
        phoneNumber: string
        name: string
        email: string
        resetCodePass: string = ''
        createdAt: Date
        updatedAt: Date
        account: {
            id: number
            balance: number
            createdAt: Date
            updatedAt: Date,
            creditCard:{
                cardHolderName: string
                cardNumber: string
                expirationDate: string
                cvv: string,
                billingAddress : string,
                createdAt: Date,
                updatedAt: Date
            }
        }
        password: string
        passwordChanged: boolean = false
        solde: number = 0.0
        carteRecto: string = ''
        carteVerso: string = ''
        authorities: [
            {
                authority: string
            }
        ]
        accountNonExpired: boolean = true
        credentialsNonExpired: boolean = true
        enabled: boolean = true
        username: string
        accountNonLocked: boolean = true

        set(data: any): void {
            this.id = data.id
            this.phoneNumber = data.phoneNumber
            this.name = data.name
            this.email = data.email
            this.resetCodePass = data.resetCodePass
            this.createdAt = data.createdAt
            this.updatedAt = data.updatedAt
            this.account = data.account
            this.password = data.password
            this.passwordChanged = data.passwordChanged
            this.solde = data.solde
            this.carteRecto = data.carteRecto
            this.carteVerso = data.carteVerso
            this.authorities = data.authorities
            this.accountNonExpired = data.accountNonExpired
            this.credentialsNonExpired = data.credentialsNonExpired
            this.enabled = data.enabled
            this.username = data.username
            this.accountNonLocked = data.accountNonLocked
        }
}
