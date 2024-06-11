import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BeneficiariesComponent } from './beneficiaries.component';
import { WidgetsModule } from '../../_metronic/partials';
import { InlineSVGModule } from 'ng-inline-svg-2';
import { CreateBeneficiaryComponent } from './create-beneficiary/create-beneficiary.component';
import { BeneficiariesListComponent } from './beneficiaries-list/beneficiaries-list.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/_metronic/shared/shared.module';

@NgModule({
  declarations: [
    BeneficiariesComponent, 
    BeneficiariesListComponent, 
    CreateBeneficiaryComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: 'list',
        component: BeneficiariesListComponent,
      },
      {
        path: 'add',
        component: CreateBeneficiaryComponent,
      },
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: '**', redirectTo: 'list', pathMatch: 'full' },
    ]),
    WidgetsModule,
    InlineSVGModule.forRoot(),
    FormsModule,
    SharedModule,
  ],
})
export class BeneficiariesModule {}
