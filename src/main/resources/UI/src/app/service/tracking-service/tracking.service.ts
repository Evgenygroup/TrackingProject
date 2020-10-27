import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Tracking} from '../../model/tracking/tracking';


@Injectable({
  providedIn: 'root'
})

export class TrackingService {

  private baseUrl = '/api';
  constructor(private http: HttpClient) { }


  createTrackingByShipmentId(tracking: Tracking): Observable<Tracking> {
    return this.http.post<Tracking>(
      `${this.baseUrl}/tracking`, tracking);
  }

  getTrackingByShipmentId(shipmentId: number): Observable<any> {
    return this.http.get( `${this.baseUrl}/trackings/${shipmentId}/trackings` );
  }

  getTrackingListByShipmentId(shipmentId:number):Observable<Tracking[]>{
    return this.http.get<Tracking[]>(`${this.baseUrl}/trackings/${shipmentId}/trackings`);
  }


  // getTrackingList(): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/tracking`);
  // }
}
