import { AfterViewInit, ChangeDetectorRef, Component } from '@angular/core';
import $ from "jquery";
import "select2";
import { Beneficiary } from '../../model/beneficiary';
import { AuthService } from 'src/app/modules/auth';
import { BeneficiariesService } from 'src/app/services/beneficiaries.service';
import { Router } from '@angular/router';
import { BankTransferService } from 'src/app/services/bank-transfer.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-bank-transfer',
  templateUrl: './bank-transfer.component.html',
  styleUrl: './bank-transfer.component.scss'
})
export class BankTransferComponent {


  beneficiaries : Beneficiary[]
  amounts : number[] = [50, 100, 200]
  amount: number = 50;


  constructor(
    private beneficiaresService: BeneficiariesService,
    private router : Router,
    private transferService : BankTransferService,
    private cdr: ChangeDetectorRef,
    private toaster: ToastrService,
    private authService: AuthService
  ) {}

  ngOnInit(){
    this.beneficiaresService.getBeneficiaries().subscribe((data : any) => {
      this.beneficiaries = data;
      this.cdr.detectChanges();
    });
  }

  ngAfterViewInit(): void {
    $('#kt_docs_select2_rich_content').select2();
  }

  handleTransfer(event : any): void {
    let receiverPhone : string = $('#kt_docs_select2_rich_content').val()?.toString() ?? '';
    if(!this.amount || !receiverPhone){
      return;
    }

    this.transferService.saveTransaction({
      amount : this.amount,
      receiverPhone : receiverPhone
    }).subscribe((data : any) => {
      this.toaster.success('Transaction successful.');
      if(this.authService.currentUserValue && this.authService.currentUserValue.account.balance)
        this.authService.currentUserValue.account.balance -= this.amount;
      this.router.navigate(['/dashboard']);
    }, (error : any) => {
      this.toaster.error('An error occurred, please try again later.');
    })

    
  }

}
