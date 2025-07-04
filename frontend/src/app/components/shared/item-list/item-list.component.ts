import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ListItem } from '../../../models/ListItem';
import { ItemAction } from '../../../models/enums/ItemAction';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.css'
})
export class ItemListComponent implements OnInit {
  @Input() items!: ListItem[];
  @Output() itemAction = new EventEmitter<{ item: ListItem, action: ItemAction }>();

  ngOnInit(): void {
    // No initialization needed for this component currently
  }

  onEdit(item: ListItem): void {
    this.itemAction.emit({ item, action: ItemAction.Edit });
  }

  onDelete(item: ListItem): void {
    this.itemAction.emit({ item, action: ItemAction.Delete });
  }
}
