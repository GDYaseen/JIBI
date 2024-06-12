import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SignInComponent } from './sign-in/sign-in.component';

import { AuthGuard } from './guards/auth.guard';
import { LoginGuard } from './guards/login.guard';
import {ClientHomeComponent} from "./client-home/client-home.component";
import {ClientHistoryComponent} from "./client-history/client-history.component";
import { ClientMainPageComponent } from './client-main-page/client-main-page.component';

const routes: Routes = [
  {
    path: '',
    component: SignInComponent,
    canActivate: [LoginGuard]
  },
  {
    path: 'client-home',
    component: ClientHomeComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'com.jibi.back_end.models.Client',
    },
    children: [
      {
        path: 'history',
        component: ClientHistoryComponent,
      },
      {
        path: '',
        component: ClientMainPageComponent,
      },
    ],
  },

  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
