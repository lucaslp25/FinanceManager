import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading',
  standalone: true,
  
  template: `
  <div class="loading-container">
    <div class="spinner"></div>
    @if (message){
      <span class="loading-text"> {{message}} </span>
    } 
  </div>`,

  styles: [`
    .loading-container{
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 20px;
      height: 100%;
      width: 100%;
      min-height: 150px;
    }

    .spinner{
      width: 40px;
      height: 40px;
      border: 4px solid rgba(0, 0, 0, 0.1);
      border-left-color: #36A2EB; 
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }

    .loading-text {
      margin-top: 15px;
      color: #666;
      font-size: 0.9rem;
      font-weight: 500;
      animation: pulse 1.5s infinite ease-in-out;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    @keyframes pulse {
      0% { opacity: 0.6; }
      50% { opacity: 1; }
      100% { opacity: 0.6; }
    }

    `],
})
export class Loading {

  @Input() message: string = 'Carregando...';

}