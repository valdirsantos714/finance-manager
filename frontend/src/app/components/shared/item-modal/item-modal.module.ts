import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common'; // Add CommonModule import
import { ItemModalComponent } from './item-modal.component';
import { SharedModule } from '../../../shared/shared.module';

@NgModule({
  declarations: [ItemModalComponent],
  imports: [
    SharedModule
  ],
  exports: [ItemModalComponent]
})
export class ItemModalModule { }
