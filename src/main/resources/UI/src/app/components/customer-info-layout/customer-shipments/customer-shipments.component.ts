import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {CustomerService} from '../../../service/customer-service/customer.service';
import {Shipment} from '../../../model/shipment/shipment';
import {switchMap} from 'rxjs/operators';
import {ShipmentService} from "../../../service/shipment-service/shipment.service";

@Component({
  selector: 'app-customer-shipments',
  templateUrl: './customer-shipments.component.html',
  styleUrls: ['./customer-shipments.component.scss']
})

export class CustomerShipmentsComponent implements OnInit {
  shipments: Shipment[];
  customerId: string;

  constructor(private shipmentService: ShipmentService, private route: ActivatedRoute) {
  }

  ngOnInit() {

    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
          this.customerId = params.get('customerId');
          return this.shipmentService.getCustomerShipments(Number(this.customerId));
        }
      )
    ).subscribe( (shipments: Shipment[]) => this.shipments = shipments);
  }
}
