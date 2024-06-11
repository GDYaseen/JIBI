export interface TransactionHistoryItem{
    id: number;
    amount : number;
    impaye : any;
    sender : {
        id : number;
        name : string;
        phoneNumber : string;
        email : string;
    };
    receiver : {
        id : number;
        name : string;
        phoneNumber : string;
        email : string;
    };
    transactionDate : Date;
    status : string;
    

}