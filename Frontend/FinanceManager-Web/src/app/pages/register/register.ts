import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth';
import { Router } from '@angular/router';
import { RegisterDTO } from '../../core/models/auth';
import { Footer } from "../../layouts/footer/footer";

@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, Footer],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  timeToConfirm = signal(false)

  public showPassword: boolean = false;

  togglePassword(){
    this.showPassword = !this.showPassword
  }

  loading = signal(false);
  error = signal<string | null>(null);

  form = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    username: [''],
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
    passwordConfirm: ['', [Validators.required]],
  });

  onSubmit():void{

    if(this.form.invalid){
      this.form.markAllAsTouched();
      return;
    }

    if (this.form.value.password != this.form.value.passwordConfirm){
      this.error.set("The passwords don´t match");
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    const credentials: RegisterDTO = {
      firstName: this.form.value.firstName || undefined,
      lastName: this.form.value.lastName || undefined,
      username: this.form.value.username || undefined,
      email: this.form.value.email || undefined,
      password: this.form.value.password || undefined
    };

    this.auth.register(credentials).subscribe({
      next: () => this.timeToConfirm.set(true),
      error: (err) => console.error("Error in register request: ", err),
      complete: () => this.loading.set(false)
    })
  }

  backToLogin(){
   this.router.navigate(['/login']); 
  }

  onCloseModal(){
    this.timeToConfirm.set(false);
    this.form.reset();
    this.router.navigate(['/login']);
  }
}