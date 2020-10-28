import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../../model/customer/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl: string = '/api';

  constructor(private http: HttpClient) { }


  createCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}/customers`, customer);
  }

  getCustomerList(): Observable<any> {
    return this.http.get(`${this.baseUrl}/customers`);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/customers/${id}`);
  }


  updateCustomer(customer: Customer) {
    return this.http.put<Customer>(`${this.baseUrl}/customers/${customer.id}`, customer);
  }


}
