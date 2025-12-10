import { Component, inject } from '@angular/core';
import { Header } from "../../layouts/header/header";
import { Router, RouterOutlet } from '@angular/router';
import { Footer } from "../../layouts/footer/footer";
import { Sidebar } from "../../layouts/sidebar/sidebar";
import { Wallet } from "../../core/components/wallet/wallet";
import { UiService } from '../../core/services/ui-service';

@Component({
  selector: 'app-app',
  imports: [Header, Footer, Sidebar, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {

  private route = inject(Router);
  public ui = inject(UiService);

  logoutRequest():void{
    console.log("Pedido de logout feito...")
    localStorage.removeItem('token');
    this.route.navigate(['/login']);
  }
}
