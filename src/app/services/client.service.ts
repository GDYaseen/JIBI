import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private token: any = '';
  private jwtToken$ = new BehaviorSubject<string>(this.token);
  private CLIENT_URL = environment.API_URL;
  private transactionId = 0;
  private rechargeForm:any;
  private telecomEntreprise:string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private toast: ToastrService
  ) {
    const fetchedToken = localStorage.getItem('act');
    if (fetchedToken) {
      this.token = fetchedToken;
      this.jwtToken$.next(this.token);
    }
  }

  get jwtUserToken(): Observable<string> {
    return this.jwtToken$.asObservable();
  }



  logout() {
    this.token = '';
    this.jwtToken$.next(this.token);
    this.toast
      .success('logged out successfully', '', { timeOut: 500 })
      .onHidden.subscribe(() => {
        localStorage.removeItem('act');
        this.router.navigateByUrl('/home_client').then();
      });
    return '';
  }

  showVerficationContainer() {
    let verificationContainer = document.getElementById(
      'verificationContainer'
    );
    verificationContainer?.classList.remove('opacity-translate');
  }

  hideVerificationContainer() {
    let verificationContainer = document.getElementById(
      'verificationContainer'
    );
    verificationContainer?.classList.add('opacity-translate');
  }

  validateTransactionForm(phone: any, amount: any) {
    if (new RegExp('^0[5-7][0-9]+$').test(phone) == false) {
      this.toast.error('Invalid phone number', '', { timeOut: 2000 });
      return false;
    }
    if (new RegExp('^[0-9]+$').test(amount) == false) {
      this.toast.error('The amount must be a type number', '', {
        timeOut: 2000,
      });
      return false;
    }

    return true;
  }

  makeTransaction(transactionForm: NgForm, phone:any) {
    if (transactionForm.invalid) {
      return;
    }
    const { receiverPhone, amount } = transactionForm.value;
    const validTransaction: boolean = this.validateTransactionForm(
      receiverPhone,
      amount
    );

    if (validTransaction) {
      const loadingToast = this.toast.warning('Loading...', '', {
        timeOut: 10000,
      });
      this.http
        .post(
          `${this.CLIENT_URL}/cmi/transaction/create`,
          { receiverPhone, amount, senderPhone:phone },
          {
            headers: { Authorization: `Bearer ${this.token}` },
          }
        )
        .subscribe(
          (response: any) => {
            this.toast.clear(loadingToast.toastId);
            this.transactionId = response.id;
            this.rechargeForm = ''
            this.showVerficationContainer();
          },
          (error: any) => {
            this.toast.clear(loadingToast.toastId);
            this.toast.error(error.error.message.toString(), '', {
              timeOut: 2000,
            });
          }
        );
    }
  }
  validateRechargeTelecom(rechargeForm: NgForm, telecomEntreprise: string) {
    this.rechargeForm = rechargeForm;
    this.telecomEntreprise = telecomEntreprise;
    this.transactionId = 0;
    this.showVerficationContainer();
  }
  makeRechargeTelecom() {
    if (this.rechargeForm.invalid) {
      return;
    }
    const { receiverPhone, amount } = this.rechargeForm.value;

    const validatTransaction: boolean = this.validateTransactionForm(
      receiverPhone,
      amount
    );
this.transactionId = 0
    if (validatTransaction) {

      let senderPhone = localStorage.getItem("phone");
      this.http
        .post(
          `${this.CLIENT_URL}/cmi/impaye/create`,
          { senderPhone, phoneNumber:receiverPhone, amount, impayeType:this.telecomEntreprise },
          {
            headers: { Authorization: `Bearer ${this.token}` },
          }
        )
        .subscribe(
          (response) => {
            setTimeout(() => {
              this.hideVerificationContainer();
            }, 100);
            this.toast
              .success('The transaction has been done successfully', '', {
                timeOut: 2000,
              })
              .onHidden.subscribe(() => {
              this.router.navigate(['client-home/history']);
            });
          },
          (error) => {
            this.toast.error(error.error.message.toString(), '', {
              timeOut: 2000,
            });
          }
        );
    }
  }

  confirmTransaction() {
    let transactionId: string =  '' +this.transactionId;
    this.rechargeForm = '';
    this.http
      .put(
        `${this.CLIENT_URL}/cmi/transaction/confirmpay?id=${transactionId}`,
        null,
        {
          headers: {
            Authorization: `Bearer ${this.token}`,
            transactionId: transactionId,
          },
        }
      )
      .subscribe(
        (response) => {
          setTimeout(() => {
            this.hideVerificationContainer();
          }, 100);
          this.toast
            .success('The transaction has been done successfully', '', {
              timeOut: 2000,
            })
            .onHidden.subscribe(() => {
            this.router.navigate(['client-home/history']);
          });
        },
        (error) => {
          this.toast.error(error.error.message.toString(), '', {
            timeOut: 2000,
          });
        }
      );
  }
  confirmPay() {
    if(this.transactionId && !this.rechargeForm) {
      this.confirmTransaction();
    }else {
      this.makeRechargeTelecom();
    }
  }

  getAllTransactions(item_per_page: number, pageIndex: number) {
    let phone = localStorage.getItem('phone');
    return this.http.get(
      `${this.CLIENT_URL}/cmi/transaction/${phone}?page=${pageIndex}&pageSize=${item_per_page}`,
      { headers: { Authorization: `Bearer ${this.token}` } }
    );
  }

  getAllImpayes() {
    let phone = localStorage.getItem('phone');
    return this.http.get(
      `${this.CLIENT_URL}/cmi/impaye/${phone}`,
      { headers: { Authorization: `Bearer ${this.token}` } }
    );
  }

  getMyInfo() {
    let phone= localStorage.getItem('phone');
    return this.http.get(`${this.CLIENT_URL}/client/${phone}`, {
      headers: {
        Authorization: `Bearer ${this.token}`,
      },
    });
  }
}
