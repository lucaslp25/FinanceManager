import { Component, EventEmitter, inject, Output } from '@angular/core';
import { Logo } from "../../core/components/logo/logo";
import { UiService } from '../../core/services/ui-service';

@Component({
  selector: 'app-header',
  imports: [Logo],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {

  private uiService = inject(UiService);
  
  @Output() logoutRequest = new EventEmitter();
  
  toggleMenu(){
    this.uiService.toggleSidebar();
  }

  onLogout():void{
    this.logoutRequest.emit();
  }

}
