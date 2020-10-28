import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {CustomerService} from "../../../service/customer-service/customer.service";
import {Shipment} from "../../../model/shipment/shipment";
import {HttpErrorResponse} from "@angular/common/http";
import {ShipmentService} from "../../../service/shipment-service/shipment.service";
import {Tracking} from "../../../model/tracking/tracking";
import {TrackingService} from "../../../service/tracking-service/tracking.service";

@Component({
  selector: 'app-shipment',
  templateUrl: './shipment.component.html',
  styleUrls: ['./shipment.component.scss']
})
export class ShipmentComponent implements OnInit {

  shipments: Shipment[];
  trackings: Tracking[];
  shipment: Observable<Shipment>;
  customerError: string;


  constructor(private shipmentService: ShipmentService,
              private trackingService: TrackingService) {
  }

  ngOnInit(): void {
  }

  onCustomerIdChanged(id: number) {
    this.shipmentService.getCustomerShipments(id).subscribe(
      shipments => {
        this.shipments = shipments;
        this.customerError = '';
      },
      (error: HttpErrorResponse) => {
            if (error.status == 404) {
                this.customerError = `Customer with id ${error.error.customerId} not found`
            }
          }
    );
  }

  onShipmentAdded(shipment: Shipment) {
    this.shipmentService.createShipment(shipment.customerId, shipment).subscribe((shipment) => {
      this.shipment;

    });
  }

  onShipmentIdChanged(id: number) {
    // console.log("Button");
    this.trackingService.getTrackingByShipmentId(id).subscribe(
      trackings => {
        this.trackings = trackings;
        this.customerError = '';
      },
      (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.customerError = `Tracking with shipment id ${error.error.shipmentId} not found`;
        }
      }
    );
  }
}
