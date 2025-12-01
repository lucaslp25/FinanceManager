import { Component, inject } from '@angular/core';
import { Header } from "../../layouts/header/header";
import { Router } from '@angular/router';
import { Footer } from "../../layouts/footer/footer";
import { Sidebar } from "../../layouts/sidebar/sidebar";

@Component({
  selector: 'app-app',
  imports: [Header, Footer, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {

  private route = inject(Router);

  logoutRequest():void{
    console.log("Pedido de logout feito...")
    localStorage.removeItem('token');
    this.route.navigate(['/login']);
  }
}
