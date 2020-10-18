import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Shipment} from '../../../model/shipment/shipment';

@Component({
  selector: 'app-customer-shipment-create',
  templateUrl: './customer-shipment-create.component.html',
  styleUrls: ['./customer-shipment-create.component.scss']
})
export class CustomerShipmentCreateComponent implements OnInit {
  shipmentCreateForm: FormGroup;

  visible = true;
  displayed = false;

  @Output()
  shipmentAdded: EventEmitter<Shipment> = new EventEmitter<Shipment>();

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.shipmentCreateForm = this.fb.group({
      description: [''],
      customerId: ['']
    });
  }

  onSubmit() {
    this.shipmentAdded.emit(this.shipmentCreateForm.value);
    console.log(this.shipmentCreateForm.value);
    this.shipmentCreateForm.reset();
    this.toggle();
  }

  toggle() {
    this.visible = false;
    this.displayed = true;
  }
}
