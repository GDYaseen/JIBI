import { HttpClient } from '@angular/common/http';
import { ASTWithSource } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ClientService } from '../services/client.service';

@Component({
  selector: 'app-client-main-page',
  templateUrl: './client-main-page.component.html',
  styleUrls: ['./client-main-page.component.css']
})
export class ClientMainPageComponent implements OnInit {

  public telecom_entreprises = ["TELECOM","ORANGE","INWI"]
  public selected_entreprise = "TELECOM";
  public recharge_amounts = [5,10,20,50,100,200];
  public transactionId = "";
  public client:any;

  constructor(private clientService:ClientService,
              private router:Router) { }


  ngOnInit(): void {
  }

  next(entreprise:string){
    this.selected_entreprise = entreprise;
    let first_page = document.getElementById("first_page_recharge");
    let next_page = document.getElementById("next_page_recharge");
    first_page?.classList.add("to_the_left");
    next_page?.classList.remove("to_the_right");
    let return_icon = document.getElementById("return_icon")
    return_icon?.classList.remove("d-none");
  }

  return(target:any){
    let first_page = document.getElementById("first_page_recharge");
    let next_page = document.getElementById("next_page_recharge");
    first_page?.classList.remove("to_the_left");
    next_page?.classList.add("to_the_right");
    target.classList.add("d-none");
  }

  hideVerificationContainer(){
    this.clientService.hideVerificationContainer();
  }

  showVerificationContainer(){
    this.clientService.showVerficationContainer();
  }

  makeTransaction(transactionForm:NgForm){
    this.clientService.makeTransaction(transactionForm, localStorage.getItem('phone'));
  }

  validateRechargeTelecom(rechargeForm:NgForm){
    this.clientService.validateRechargeTelecom(rechargeForm, this.selected_entreprise);
  }

  confirmPay(){
    this.clientService.confirmPay();
  }







}
