import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CustomerComponent} from './components/customer-layout/customer/customer.component';
import {ShipmentComponent} from './components/shipment-layout/shipment/shipment.component';
import {TrackingComponent} from './components/tracking-layout/tracking/tracking.component';
import {CustomerInfoComponent} from './components/customer-info-layout/customer-info/customer-info.component';
import {ShipmentPageComponent} from "./components/shipment-page/shipment-page.component";
import {CustomerShipmentsComponent} from './components/customer-info-layout/customer-shipments/customer-shipments.component';

const routes: Routes = [
  {path: 'app/customer', component: CustomerComponent},
  {path: 'app/shipment', component: ShipmentComponent},
  {path: 'app/tracking', component: TrackingComponent},
  {path: 'app/customer/:customerId', component: CustomerInfoComponent},
  {path:'app/customer/:customerId/shipments', component: CustomerShipmentsComponent},
  {path: 'app/shipment/:shipmentId', component: ShipmentPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
