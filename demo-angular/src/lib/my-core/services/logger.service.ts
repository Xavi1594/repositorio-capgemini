import { Inject, Injectable, InjectionToken, Optional } from '@angular/core';
export const ERROR_LEVEL = new InjectionToken<string>('ERROR_LEVEL')

@Injectable({
  providedIn: 'root'
})
export class LogerService {
  private nivel: number = Number.MAX_VALUE;
  constructor(@Inject(ERROR_LEVEL) @Optional() nivel?:number ) {
    if(nivel || nivel ===0)
    this.nivel = nivel
  }
  public error(message: string): void {
    if (this.nivel > 0)
      console.error(message)
  }
  public warn(message: string): void {
    if (this.nivel > 1)
      console.error(message)
  }
  public info(message: string): void {
    if (this.nivel > 2) {
      if (console.info)
        console.info(message)

    } else {
      console.log(message)
    }
    console.warn(message)
  }


  public log(message: string): void {
    console.log(message);
  }
}
