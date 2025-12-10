import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UiService {

  //initial state -- OPEN 
  #sidebarOpen = signal(true);

  //getter
  public sidebarOpen = this.#sidebarOpen.asReadonly();
  
  // toggle sidebar action
  public toggleSidebar(){
    this.#sidebarOpen.update(value => !value);
  } 
}
