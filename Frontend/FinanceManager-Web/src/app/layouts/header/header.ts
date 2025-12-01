import { Component, EventEmitter, Output } from '@angular/core';
import { Logo } from "../../core/components/logo/logo";

@Component({
  selector: 'app-header',
  imports: [Logo],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {

  @Output() logoutRequest = new EventEmitter();

  onLogout():void{
    this.logoutRequest.emit();
  }

}
