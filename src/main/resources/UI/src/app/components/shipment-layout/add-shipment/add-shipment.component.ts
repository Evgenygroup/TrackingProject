import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../../../model/customer/customer";
import {Shipment} from "../../../model/shipment/shipment";

@Component({
  selector: 'app-add-shipment',
  templateUrl: './add-shipment.component.html',
  styleUrls: ['./add-shipment.component.scss']
})
export class AddShipmentComponent implements OnInit {
  myReactiveForm: FormGroup;

  @Output()
  shipmentAdded: EventEmitter<Shipment> = new EventEmitter<Shipment>();

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.myReactiveForm = this.fb.group({
      description: ['', [Validators.required]],
      customerId: ['', [Validators.required]]
    });
  }

  getControl(controlName: string): AbstractControl {
    return this.myReactiveForm.get(controlName);
  }

  onSubmit() {
    const controls = this.myReactiveForm.controls;

    if (this.myReactiveForm.invalid) {
      Object.keys(controls).forEach(controlName => controls[controlName].markAsTouched());
      return;
    }
    this.shipmentAdded.emit(this.myReactiveForm.value);
    this.myReactiveForm.reset();
  }
}
