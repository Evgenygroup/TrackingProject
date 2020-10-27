import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Shipment} from "../../model/shipment/shipment";
import {CustomerName} from "../../model/customerName/customerName";
import {Tracking} from "../../model/tracking/tracking";
import set = Reflect.set;

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {

  private baseUrl = '/api';
  constructor(private http: HttpClient) { }

 /* getShipmentList(): Observable<any> {
    return this.http.get(`${this.baseUrl}/shipment/`);
  }*/

  getCustomerShipments(id:number): Observable<Shipment[]> {
    return this.http.get<Shipment[]>(`${this.baseUrl}/shipments/${id}/shipments`);

  }

  createShipment(customerId:number,shipment:Shipment): Observable<Shipment> {
    return this.http.post<Shipment>(`${this.baseUrl}/shipments/${customerId}/shipment`,shipment);

  }

  getCustomernameAndShipmentDescription(shipmentId:number): Observable<CustomerName> {
    return this.http.get<CustomerName>(`${this.baseUrl}/shipments/${shipmentId}`);
  }

}
