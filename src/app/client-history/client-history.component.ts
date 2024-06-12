import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ClientService } from '../services/client.service';

@Component({
  selector: 'app-client-history',
  templateUrl: './client-history.component.html',
  styleUrls: ['./client-history.component.css'],
})
export class ClientHistoryComponent implements OnInit {
  public transactions:any = [];
  length = 0;
  item_per_page = 5;
  pageSizeOptions: number[] = [5, 10];
  displayedColumns: string[] = ['sender', 'receiver', 'amount', 'status', 'date'];

  constructor(
    private clientService: ClientService,
    private toast: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchTransactions(5, 0);
  }

  changePage(event: any) {
    this.fetchTransactions(event.pageSize, event.pageIndex);
  }

  fetchTransactions(item_per_page: number, pageIndex: number) {
    this.clientService.getAllTransactions(item_per_page, pageIndex).subscribe(
      (response: any) => {
        this.clientService.getAllImpayes().subscribe((res:any)=> {
          this.length = response.length + res.length;
          let allTransactions = [...response, ...res];
          allTransactions = allTransactions.sort((a, b) => new Date(b.transactionDate).getTime() - new Date(a.transactionDate).getTime());
          console.log(allTransactions);
          const startIndex = pageIndex * item_per_page;
          const endIndex = startIndex + item_per_page;

          this.transactions = allTransactions.slice(startIndex, endIndex);
        }, (err) => {
          this.toast
            .error('There is something wrong with the server try later', '', {
              timeOut: 2000,
            })
            .onHidden.subscribe(() => {
            this.router.navigate(['client-home']);
          });
        })

      },
      (error) => {
        this.toast
          .error('There is something wrong with the server try later', '', {
            timeOut: 2000,
          })
          .onHidden.subscribe(() => {
            this.router.navigate(['client-home']);
          });
      }
    );
  }
}
