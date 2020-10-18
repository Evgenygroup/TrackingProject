import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-shipment-by-tracking',
  templateUrl: './shipment-by-tracking.component.html',
  styleUrls: ['./shipment-by-tracking.component.scss']
})
export class ShipmentByTrackingComponent implements OnInit {

  @Output()
  shipmentIdChanged: EventEmitter<number> = new EventEmitter<number>();

  shipmentTrackingForm: FormGroup;

  constructor(private tb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit() {
    this.shipmentIdChanged.emit(this.shipmentTrackingForm.get('shipmentId').value);
    this.shipmentTrackingForm.reset();
  }

  private initForm() {
    this.shipmentTrackingForm = this.tb.group({
      shipmentId: ['', Validators.required]
    });

  }

}
