import {Component, Input, OnInit} from '@angular/core';
import {Customer} from "../../../model/customer/customer";
import {Shipment} from "../../../model/shipment/shipment";

@Component({
  selector: 'app-shipment-list',
  templateUrl: './shipment-list.component.html',
  styleUrls: ['./shipment-list.component.scss']
})
export class ShipmentListComponent implements OnInit {

  @Input()
  shipments: Shipment[];

  constructor() { }

  ngOnInit(): void {
  }

}
