import { Routes } from '@angular/router';
import { BeneficiariesComponent } from './beneficiaries/beneficiaries.component';
import { CreditCardComponent } from './credit-card/credit-card.component';
import { BankTransferComponent } from './bank-transfer/bank-transfer.component';
import { HistoryComponent } from './history/history.component';

const Routing: Routes = [
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./dashboard/dashboard.module').then((m) => m.DashboardModule),
  },
  {
    path: 'builder',
    loadChildren: () =>
      import('./builder/builder.module').then((m) => m.BuilderModule),
  },
  {
    path: 'crafted/pages/profile',
    loadChildren: () =>
      import('../modules/profile/profile.module').then((m) => m.ProfileModule),
  },
  {
    path: 'crafted/account',
    loadChildren: () =>
      import('../modules/account/account.module').then((m) => m.AccountModule),
  },
  {
    path: 'crafted/pages/wizards',
    loadChildren: () =>
      import('../modules/wizards/wizards.module').then((m) => m.WizardsModule),
  },
  {
    path: 'crafted/widgets',
    loadChildren: () =>
      import('../modules/widgets-examples/widgets-examples.module').then(
        (m) => m.WidgetsExamplesModule
      ),
  },
  {
    path: 'apps/chat',
    loadChildren: () =>
      import('../modules/apps/chat/chat.module').then((m) => m.ChatModule),
  },
  {
    path: 'beneficiaries',
    loadChildren: () =>
      import('./beneficiaries/beneficiaries.module').then((m) => m.BeneficiariesModule),
  },
  {
    path: 'card',
    component: CreditCardComponent,
  },
  {
    path: 'transfer',
    component: BankTransferComponent,
  },
  {
    path: 'history',
    component: HistoryComponent,
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'error/404',
  },
];

export { Routing };
