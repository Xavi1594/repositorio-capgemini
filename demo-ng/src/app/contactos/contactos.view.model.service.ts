import { HttpClient, HttpContextToken, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoggerService } from '@my/core';
// Removed invalid import from 'jasmine'
import { Observable } from 'rxjs';
import { NotificationService } from '../common-services';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
export type ModoCRUD = 'list' | 'add' | 'edit' | 'view' | 'delete';
export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({
  providedIn: 'root'
})

export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiURL;
  protected http: HttpClient = inject(HttpClient)
  constructor(entidad: string, protected option = {}) {
  this.baseUrl += entidad;
  }
  query(): Observable<Array<T>> {
  return this.http.get<Array<T>>(this.baseUrl, this.option);
  }
  get(id: K): Observable<T> {
  return this.http.get<T>(`${this.baseUrl}/${id}`, this.option);
  }
  add(item: T): Observable<T> {
  return this.http.post<T>(this.baseUrl, item, this.option);
  }
  change(id: K, item: T): Observable<T> {
  return this.http.put<T>(`${this.baseUrl}/${id}`, item, this.option);
  }
  remove(id: K): Observable<T> {
  return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option);
  }
  }
  @Injectable({
    providedIn: 'root'
   })
   export class ContactosDAOService extends RESTDAOService<any, any> {
    constructor() {
    super('contactos');
    }
   }
   @Injectable({
    providedIn: 'root'
  })
export class ContactosViewModelService {
  protected modo: ModoCRUD = 'list';
 protected listado: Array<any> = [];
 protected elemento: any = {};
 protected idOriginal: any = null;
protected listURL = '/contactos';


 constructor(protected notify: NotificationService, protected out: LoggerService,
  protected dao: ContactosDAOService, protected router: Router) { }

  public get Modo(): ModoCRUD { return this.modo; }
 public get Listado(): Array<any> { return this.listado; }
 public get Elemento(): any { return this.elemento; }

 public list(): void {
  this.dao.query().subscribe({
  next: data => {
  this.listado = data;
  this.modo = 'list';
  },
  error: err => this.handleError(err)
  });
  }

  public add(): void {
    this.elemento = {};
    this.modo = 'add';
    }
    public edit(key: any): void {
    this.dao.get(key).subscribe({
    next: data => {
    this.elemento = data;
    this.idOriginal = key;
    this.modo = 'edit';
    },
    error: err => this.handleError(err)
    });
    }

    public view(key: any): void {
      this.dao.get(key).subscribe({
      next: data => {
      this.elemento = data;
      this.modo = 'view';
      },
      error: err => this.handleError(err)
      });
      }
      public delete(key: any): void {
      if (!window.confirm('¿Seguro?')) { return; }
      this.dao.remove(key).subscribe({
      next: () => this.list(),
      error: err => this.handleError(err)
      });
      }

      public cancel(): void {
        this.clear()
        // this.list();
        this.router.navigateByUrl(this.listURL);
        }
        public send(): void {
        switch (this.modo) {
        case 'add':
        this.dao.add(this.elemento).subscribe({
        next: () => this.cancel(),
        error: err => this.handleError(err)
        });
        break;
        case 'edit':
        this.dao.change(this.idOriginal, this.elemento).subscribe({
        next: () => this.cancel(),
        error: err => this.handleError(err)
        });
        break;
        case 'view':
        this.cancel();
        break;
        }
        }

        clear() {
          this.elemento = {};
          this.idOriginal = undefined;
           this.listado = [];
           }

           handleError(err: HttpErrorResponse) {
            let msg = ''
            switch (err.status) {
            case 0: msg = err.message; break;
           case 404: this.router.navigateByUrl('/404.html'); return;
            default:
            msg = `ERROR ${err.status}: ${err.error?.['title'] ??
            err.statusText}.${err.error?.['detail'] ? ` Detalles: ${err.error['detail']}` : ''}`
            break;
            }
            this.notify.add(msg)
            }

}
