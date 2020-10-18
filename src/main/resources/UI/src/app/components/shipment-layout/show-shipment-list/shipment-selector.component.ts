import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-shipment-selector-component',
  templateUrl: './shipment-selector.component.html',
  styleUrls: ['./shipment-selector.component.scss']
})
export class ShipmentSelectorComponent implements OnInit {

  shipmentForm: FormGroup;

  @Output()
  customerIdChanged: EventEmitter<number> = new EventEmitter<number>();

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit() {
    this.customerIdChanged.emit(this.shipmentForm.get("id").value);
    this.shipmentForm.reset();
  }

  private initForm() {
    this.shipmentForm = this.fb.group({
      id: ['',Validators.required]
    });

  }
}
