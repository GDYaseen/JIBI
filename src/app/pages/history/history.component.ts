import { ChangeDetectorRef, Component } from '@angular/core';
import { TransactionHistoryItem } from 'src/app/model/transactionHistoryItem';
import { AuthService } from 'src/app/modules/auth';
import { BankTransferService } from 'src/app/services/bank-transfer.service';
import * as moment from 'moment';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent {

  transactions : TransactionHistoryItem[] = [];
  authenticatedUserId : number;


  constructor(
    private auth : AuthService,
    private transactionService : BankTransferService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    
    this.authenticatedUserId = this.auth.currentUserValue?.id ?? 0;
    this.transactionService.getTransactions().subscribe((data : any) => {
      this.transactions = data.sort((a : TransactionHistoryItem, b : TransactionHistoryItem) => new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime());
      this.cdr.detectChanges();
    });
  }

  getAppropriateMessage(transaction : TransactionHistoryItem) : string {
    
    if(transaction.sender.id == this.authenticatedUserId){
      return `virment en faveur de`;
    }else {
      return `virment reçu de la part de `;
    }
  }

  getAppropriateName(transaction : TransactionHistoryItem) : string {
    if(transaction.sender.id == this.authenticatedUserId){
      return transaction.receiver.name;
    }else {
      return transaction.sender.name;
    }
  }

  getAppropriateColor(transaction : TransactionHistoryItem) : string {
    if(transaction.sender.id == this.authenticatedUserId){
      return 'danger';
    }else {
      return 'success';
    }
  }

  formatDate(date : Date) : string {    
    return moment(date ?? new Date()).format('DD/MM/YYYY HH:mm');

  }

  formatAmount(transaction : TransactionHistoryItem) : string {
    if(transaction.sender.id == this.authenticatedUserId){
      return `- ${transaction.amount.toFixed(2)}`;
    }else {
      return `+ ${transaction.amount.toFixed(2)}`;
    }
  }




}
