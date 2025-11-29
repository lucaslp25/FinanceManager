import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { LoginDTO } from '../../core/models/auth';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private auth = inject(AuthService);

  loading = signal(false);
  error = signal<String | null>(null);

  form = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });
  
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
}
