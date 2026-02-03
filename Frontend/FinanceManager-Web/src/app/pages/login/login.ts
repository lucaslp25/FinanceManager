import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { LoginDTO } from '../../core/models/auth';
import { Footer } from "../../layouts/footer/footer";
import { VerifyAccount } from "../verify-account/verify-account";
import { D, O, P } from '@angular/cdk/keycodes';
import { sign } from 'chart.js/helpers';
import { Modal } from "../../shared/components/modal/modal";
import { IfStmt } from '@angular/compiler';
import { single } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, Footer, VerifyAccount, Modal],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login implements OnInit{

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private auth = inject(AuthService);

  public showForgotModal = signal(false);
  public errorForgotMessage = signal<string | null>(null);
  public successForgotMessage = signal(false)
  public hasToken = signal(false);
  public recoveryToken = signal<string | null>(null);

  public isVerifing = signal(false);

  public showPassword: boolean = false;

  togglePassword(){
    this.showPassword = !this.showPassword
  }

  loading = signal(false);
  error = signal<String | null>(null);

  form = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  forgotForm = this.fb.group({
    email: ['', [Validators.required]]
  })

  changePassForm = this.fb.group({
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required]
  })
  
  onSubmit(): void{
    if(this.form.invalid){
      // make the errors appear on screen 
      this.form.markAllAsTouched();
      return;
    } 

    this.loading.set(true);
    this.error.set(null);

    const credentials: LoginDTO = {
      email: this.form.value.email || undefined,
      password: this.form.value.password || undefined
    }

    this.auth.login(credentials).subscribe({
        next: (dto) => {
          localStorage.setItem('token', dto.token!)
          localStorage.setItem('user-email', dto.email!)
          localStorage.setItem('user-role', dto.role!)
          this.router.navigate(['/app'])
        },
        error: (err) => {
          console.error("Error in Login request!", err)
          this.loading.set(false);
          
          this.error.set("E-mail or password invalid. Try again!")
        },
        complete: () => {
          this.loading.set(false);
        }
    })
  };

  onRegister(): void{
    this.router.navigate(['/register']);
  }
  
  verifyCurrentURL(){
    const param = new URLSearchParams(window.location.search);
    const token = param.get('token');
    const recoveryToken = param.get('recoveryToken');

    if (token) this.isVerifing.set(true); 
    if (recoveryToken){
      this.hasToken.set(true); 
      this.recoveryToken.set(recoveryToken)
    }
  }

  onConfirmChangePassword(){

    if (this.changePassForm.invalid) {
      this.errorForgotMessage.set("Senha inválida")
      return;
    }
    
    const password = this.changePassForm.value.password;
    const confirmPassword = this.changePassForm.value.confirmPassword;
    const recovery = this.recoveryToken();
    
    if (password != confirmPassword){
      this.errorForgotMessage.set("Senhas não coincidem!")
      return;
    } 

    const dto = {
      recoveryToken: recovery ?? undefined,
      newPassword: password ?? undefined
    }

    this.auth.resetPasswrod(dto).subscribe({
      next: () => {
        console.log("Enviando token e nova senha para API.");
        this.successForgotMessage.set(true);
        this.router.navigate(['/login']);
      },
      error: (err) => console.error("Erro ao resetar senha!", err)
    })

  }

  ngOnInit(): void {
    this.verifyCurrentURL();
  }

  forgotMyPass(){
    this.showForgotModal.set(true);
  }
  
  onCloseModal(){
    this.showForgotModal.set(false);
    this.errorForgotMessage.set(null);
    this.hasToken.set(false);
  }

  onConfirmForgotEmail(){
    if (this.forgotForm.invalid){
      this.errorForgotMessage.set("E-mail inválido.")
      return;
    }

    const email = this.forgotForm.value.email;

    this.auth.forgotPassword(email!).subscribe({
      next: () => {
        console.log("Enviando e-mail para veriricação");
        this.successForgotMessage.set(true);
      },
      error: (err) => console.error("Erro ao enviar seu email para API")
    })

  }

}
