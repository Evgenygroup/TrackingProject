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

  /*getTrackingList(): Observable<any> {
    return this.http.get(`${this.baseUrl}/tracking`);
  }*/
  getTrackingList(shipmentId:number):Observable<Tracking[]>{
    return this.http.get<Tracking[]>(`${this.baseUrl}/trackings/${shipmentId}/trackings`);
  }

  createTracking(tracking: Tracking): Observable<Tracking> {
    return this.http.post<Tracking>(`${this.baseUrl}/tracking`, tracking);
  }

 /*getTrackingById( id: number ) {
    return this.http.get(`${this.baseUrl}/tracking/${id}`);
  }*/

  getTrackingById(shipmentId:number):Observable<Tracking[]>{
    return this.http.get<Tracking[]>(`${this.baseUrl}/trackings/${shipmentId}/trackings`);
  }
}
