import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Tracking} from '../../../model/tracking/tracking';
import {Shipment} from '../../../model/shipment/shipment';

@Component({
  selector: 'app-tracking-edit',
  templateUrl: './tracking-edit.component.html',
  styleUrls: ['./tracking-edit.component.scss']
})
export class TrackingEditComponent implements OnInit {

  trackingForm: FormGroup;

  @Input()
  tracking: Tracking;
  @Input()
  shipments: Shipment[];

  @Output()
  trackingChanged: EventEmitter<Tracking> = new EventEmitter<Tracking>();

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit() {
    this.trackingChanged.emit(this.trackingForm.value);
    this.trackingForm.reset();
  }

  private initForm() {
    this.trackingForm = this.fb.group({
      id: [this.tracking ? this.tracking.id : ''],
      shipmentId: [this.shipments ? this.shipments : ''],
      status: [this.tracking ? this.tracking.status : '', Validators.required]
    });
    this.trackingForm.get('id').disable();
  }
}

