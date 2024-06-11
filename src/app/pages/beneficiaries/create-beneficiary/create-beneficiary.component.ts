import { Component } from '@angular/core';
import { BeneficiariesService } from 'src/app/services/beneficiaries.service';
import { Beneficiary } from '../../../model/beneficiary';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-beneficiary',
  templateUrl: './create-beneficiary.component.html',
  styleUrl: './create-beneficiary.component.scss'
})
export class CreateBeneficiaryComponent {
  beneficiary : Beneficiary = {
    clientName: "",
    phoneNumber: "",
  }

  constructor(
    private beneficiariesService : BeneficiariesService, 
    private router : Router,
    private toaster: ToastrService,
  ) {}


  handleSubmit(event: any) {
    event.preventDefault();
    if(!this.beneficiary.clientName || !this.beneficiary.phoneNumber) return;

    this.beneficiariesService.addBeneficiary(this.beneficiary).subscribe(() => {
      this.toaster.success('Beneficiary added successfully.');
      this.beneficiary = {
        clientName: "",
        phoneNumber: "",
      }
      this.router.navigate(['/beneficiaries']);
    });
  }

}
