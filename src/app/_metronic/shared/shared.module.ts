import {NgModule} from '@angular/core';
import {KeeniconComponent} from './keenicon/keenicon.component';
import {CommonModule} from "@angular/common";
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    KeeniconComponent
  ],
  imports: [
    CommonModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    })
  ],
  exports: [
    KeeniconComponent
  ]
})
export class SharedModule {
}
