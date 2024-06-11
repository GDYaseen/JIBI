import { ChangeDetectorRef, Component } from '@angular/core';
import Swal from 'sweetalert2';
import { Beneficiary } from '../../../model/beneficiary';
import { AuthService, UserModel } from 'src/app/modules/auth';
import { BeneficiariesService } from 'src/app/services/beneficiaries.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-beneficiaries-list',
  templateUrl: './beneficiaries-list.component.html',
  styleUrl: './beneficiaries-list.component.scss'
})
export class BeneficiariesListComponent {
  beneficiaries : Beneficiary[] ;
  autenticatedUser : UserModel | undefined;

  constructor(private auth: AuthService, 
    private cdr: ChangeDetectorRef,
    private beneficiariesService : BeneficiariesService,
    private toaster: ToastrService,
  ) {
    this.autenticatedUser = this.auth.currentUserValue;
    
  }
  ngOnInit(){
    this.beneficiariesService.getBeneficiaries().subscribe((data : any) => {
      this.beneficiaries = data;
      this.cdr.detectChanges();
    });
  }

  remove(id : number | undefined) {
    if(!id) return;
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this beneficiary!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.value) {
        this.beneficiariesService.removeBeneficiary(id).subscribe(() => {
          this.beneficiaries = this.beneficiaries.filter((beneficiary) => beneficiary.id !== id);
          this.toaster.success('Beneficiary removed successfully.');
          this.cdr.detectChanges(); 
        });
      }
    })
  }

}
