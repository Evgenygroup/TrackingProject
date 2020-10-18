import {Component, Input, OnInit} from '@angular/core';
import {Tracking} from "../../../model/tracking/tracking";

@Component({
  selector: 'app-shipment-tracking-list',
  templateUrl: './shipment-tracking-list.component.html',
  styleUrls: ['./shipment-tracking-list.component.scss']
})
export class ShipmentTrackingListComponent implements OnInit {

  @Input()
  trackings: Tracking[];
  constructor() { }

  ngOnInit(): void {
  }

}
