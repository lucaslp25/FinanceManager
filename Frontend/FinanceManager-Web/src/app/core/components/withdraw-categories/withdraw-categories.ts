import { Component, inject, OnInit, signal } from '@angular/core';
import { WithdrawCategoryResponseDTO } from '../../services/withdraw-category';
import { Modal } from "../../../shared/components/modal/modal";
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { WithdrawCategoryState } from '../../states/withdraw-category-state';

@Component({
  selector: 'app-withdraw-categories',
  imports: [Modal, ReactiveFormsModule],
  templateUrl: './withdraw-categories.html',
  styleUrl: './withdraw-categories.scss',
})
export class WithdrawCategories implements OnInit{

  public state = inject(WithdrawCategoryState);

  public isNewCategoryModalOpen = signal(false);
  public isEditCategoryModalOpen = signal(false);
  public isDeleteCategoryModalOpen = signal(false);

  // starts with null
  public currentCategory = signal<WithdrawCategoryResponseDTO | null>(null);

  public invalidMessage = signal<string | null>(null);

  public nameControl = new FormControl('', {
    nonNullable: true,
    validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)]
  });

  onNewCategory():void{
    this.nameControl.reset();
    this.isNewCategoryModalOpen.set(true);
  }
  
  onEditCategory(category: WithdrawCategoryResponseDTO): void{
    this.currentCategory.set(category);
    this.isEditCategoryModalOpen.set(true);
    this.nameControl.setValue(category.name);
  }
  
  onDeleteCategory(category: WithdrawCategoryResponseDTO):void{
    this.isDeleteCategoryModalOpen.set(true);
    this.currentCategory.set(category);
  }
  
  //generic close
  onCloseModal(): void{
    
    if(this.isDeleteCategoryModalOpen() == true){
      this.isDeleteCategoryModalOpen.set(false);
      this.currentCategory.set(null);
    }
    
    if(this.isEditCategoryModalOpen() == true){
      this.isEditCategoryModalOpen.set(false);
      this.currentCategory.set(null);
    }

    if(this.isNewCategoryModalOpen() == true){
      this.isNewCategoryModalOpen.set(false);
    }

    this.invalidMessage.set(null);
  }
  
  // requests backend

  onConfirmNewCategory(): void{

    if(this.nameControl.invalid){
      this.invalidMessage.set(' Nome inválido! Precisa ter entre 3 e 30 caracteres.');
      return; 
    }
    const name = this.nameControl.value;

    this.state.create(name).subscribe({
      next: () =>{
        this.onCloseModal();
      }
    })
  } 
  
  onConfirmDeleteCategory(): void{
    
    if(!this.currentCategory()?.id) return;
  
    const id = this.currentCategory()?.id;
    
    this.state.delete(id!).subscribe({
      next: () =>{
        this.onCloseModal();
      }
    });

    this.isDeleteCategoryModalOpen.set(false)
  }

  onConfirmUpdateCategory():void{

    if(!this.currentCategory()?.id) return;

    if(this.nameControl.invalid){
      this.invalidMessage.set('Nome inválido! Mínimo 3 Caracteres.');
      return; 
    }

    const name = this.nameControl.value;
    const id = this.currentCategory()?.id;

    this.state.update(name, id!).subscribe({
      next: () => this.onCloseModal()
    });
  }

  ngOnInit(): void {
    this.state.load();
  };

}
