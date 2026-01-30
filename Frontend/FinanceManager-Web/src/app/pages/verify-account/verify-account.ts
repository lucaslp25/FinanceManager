import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../core/services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-verify-account',
  standalone: true,
  imports: [],
  templateUrl: './verify-account.html',
  styleUrl: './verify-account.scss',
})
export class VerifyAccount implements OnInit{

  private authService = inject(AuthService);
  private router = inject(Router);
  isAccountOk = signal(false);

  ngOnInit(): void {
    this.takeUrlToken();
  }

  takeUrlToken(){
    const param = new URLSearchParams(window.location.search);
    const token = param.get('token');

    if (!token) return;

    this.authService.emailConfirmation(token).subscribe({

      next: (bit) => {
        console.log("Account verified successfully!")
        if (bit === true){
          this.isAccountOk.set(true);
          this.enableAccount(token);
        }
      },
      error: (err) =>{
        console.error("Error in account verification: ",err)
      }
    });
  }

  enableAccount(token: string):void{
    this.authService.enableAccount(token).subscribe({
      next: () =>{
        console.log("Account Enabled!")
        setTimeout(() => this.switchWindow(), 3500)
      }, 
      error: (err) => console.error("Error in enable account.", err)
    })

  }

  switchWindow():void{
    this.router.navigate(['/login'])
  }

}
