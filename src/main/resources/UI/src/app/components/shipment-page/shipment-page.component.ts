import {Component, OnInit} from '@angular/core';
import {CustomerName} from "../../model/customerName/customerName";
import {Tracking} from "../../model/tracking/tracking";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {ShipmentService} from "../../service/shipment-service/shipment.service";

@Component({
  selector: 'app-shipment-page',
  templateUrl: './shipment-page.component.html',
  styleUrls: ['./shipment-page.component.scss']
})
export class ShipmentPageComponent implements OnInit {

  customerNameAndDescr: CustomerName;
  trackings: Tracking[];
  status: string;
  shipmentId: number;

  constructor(private shipmentService: ShipmentService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
          this.shipmentId = +params.get('shipmentId');
          this.getTrackingList();
          return this.shipmentService.getCustomernameAndShipmentDescription(Number(this.shipmentId))
        }
      )
    ).subscribe((customerNameAnd: CustomerName) => {
        this.customerNameAndDescr = customerNameAnd
      }
    );
  }

  getTrackingList() {
    this.shipmentService.getTrackingListByShipmentId(Number(this.shipmentId)).subscribe(
      (trackings: Tracking[]) => this.trackings = trackings
    );
  }

  onSelectTrackingEvent() {
    this.addStatus({status: this.status, shipmentId: this.shipmentId});
  }

  addStatus(tracking: Tracking) {
    this.shipmentService.createTrackingByShipmentId(tracking).subscribe(
      tracking => this.getTrackingList()
    );
  }
}




