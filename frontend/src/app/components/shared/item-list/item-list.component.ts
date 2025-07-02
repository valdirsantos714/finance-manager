import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.css'
})
export class ItemListComponent implements OnInit {
  @Input() items!: any[];
  @Output() actionEvent = new EventEmitter<void>();

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
