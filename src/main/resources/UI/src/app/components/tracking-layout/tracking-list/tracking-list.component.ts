import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Tracking} from '../../../model/tracking/tracking';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-tracking-list',
  templateUrl: './tracking-list.component.html',
  styleUrls: ['./tracking-list.component.scss']
})
export class TrackingListComponent implements OnInit {
  @Input()
  trackings: Tracking[];
  shipmentIdForm: FormGroup;
  @Output()
  trackingChanged: EventEmitter<number> = new EventEmitter<number>();

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {

    this.initForm();
  }

  onSubmit(): void {
    this.trackingChanged.emit(this.shipmentIdForm.value);
    this.shipmentIdForm.reset();
  }

  private initForm() {
    this.shipmentIdForm = this.fb.group({
      id: ''
    });

  }
}

