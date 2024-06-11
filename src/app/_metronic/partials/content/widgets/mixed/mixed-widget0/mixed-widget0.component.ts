import { Component, Input } from '@angular/core';
import { AuthService } from 'src/app/modules/auth';

@Component({
  selector: 'app-mixed-widget0',
  templateUrl: './mixed-widget0.component.html',
})
export class MixedWidget0Component {
  @Input() color: string = '';

  balance : string = '0 MAD';
  constructor(private auth : AuthService) {}

  ngOnInit(){
    this.balance = this.formatNumber(this.auth.currentUserValue?.account.balance ?? 0);
  }

  formatNumber(value: number): string {
    const formattedNumber = value.toFixed(2);
  
    const parts = formattedNumber.split('.');
  
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  
    return `${parts.join('.')} MAD`;
  }
}
