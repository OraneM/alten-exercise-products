import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Product } from './product.class';
import {Constant} from "../../constant";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    private static productslist: Product[] = null;
    private products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);

    constructor(private http: HttpClient) { }

    getProducts(): Observable<Product[]> {
        this.http.get<Product[]>(`${Constant.API_URL}/products`,{headers:Constant.JSON_HEADERS})
            .subscribe(products => {
                ProductsService.productslist=products;
                this.products$.next(ProductsService.productslist);
            });
        return this.products$;
    }

    create(prod: Product): Observable<Product[]> {

        this.http.post<Product>(`${Constant.API_URL}/products`, prod,{headers:Constant.JSON_HEADERS})
            .subscribe(product => {
                ProductsService.productslist.push(product);
                this.products$.next(ProductsService.productslist);
            });
        return this.products$;
    }

    update(prod: Product): Observable<Product[]>{
        this.http.patch<Product>(`${Constant.API_URL}/products/${prod.id}`, prod,{headers:Constant.JSON_HEADERS})
            .subscribe(product => {
                ProductsService.productslist.forEach(element => {
                    if(element.id == prod.id){
                        element = product;
                    }
                });
                this.products$.next(ProductsService.productslist);
            });
        return this.products$;
    }


    delete(id: number): Observable<Product[]>{
        this.http.delete<void>(`${Constant.API_URL}/products/${id}`,{headers:Constant.JSON_HEADERS})
            .subscribe(() => {
                ProductsService.productslist = ProductsService.productslist.filter(value => { return value.id !== id } );
                this.products$.next(ProductsService.productslist);
            });
        return this.products$;
    }
}
