import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [],
  templateUrl: './modal.html',
  styleUrl: './modal.scss',
})

export class Modal {

  @Input() title: string = '';
  @Output() closeRequest = new EventEmitter<void>(); 
  @Output() confirmRequest = new EventEmitter<void>();

  onClose(): void{
    this.closeRequest.emit();
  }

  onConfirm(): void{
    this.confirmRequest.emit();
  }

}
